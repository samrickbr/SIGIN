#!/usr/bin/env node
/**
 * sync-check.js — SIGIN Postman Sync Checker
 *
 * Compara os endpoints definidos nos Controllers Java com os requests
 * existentes na coleção Postman e reporta o que está faltando.
 *
 * Uso:
 *   node postman/scripts/sync-check.js
 *
 * Saída:
 *   - Lista de endpoints no código que NÃO têm request na coleção
 *   - Lista de requests na coleção que NÃO têm endpoint correspondente no código
 */

const fs   = require('fs');
const path = require('path');

// ─── Configuração ────────────────────────────────────────────────────────────

const ROOT          = path.resolve(__dirname, '../..');
const CONTROLLERS   = path.join(ROOT, 'src/main/java');
const COLLECTION    = path.join(ROOT, 'postman/collections/SIGIN');

// ─── 1. Extrair endpoints dos Controllers Java ───────────────────────────────

function findJavaFiles(dir, files = []) {
  for (const entry of fs.readdirSync(dir, { withFileTypes: true })) {
    const full = path.join(dir, entry.name);
    if (entry.isDirectory()) findJavaFiles(full, files);
    else if (entry.name.endsWith('Controller.java')) files.push(full);
  }
  return files;
}

function extractEndpoints(file) {
  const src        = fs.readFileSync(file, 'utf8');
  const endpoints  = [];

  // Pega o @RequestMapping da classe
  const classMapping = (src.match(/@RequestMapping\("([^"]+)"\)/) || [])[1] || '';

  // Regex para capturar cada método HTTP + path
  const methodRe = /@(Get|Post|Put|Delete|Patch)Mapping(?:\("([^"]*)"\))?\s[\s\S]*?(?:public|private|protected)/g;
  let m;
  while ((m = methodRe.exec(src)) !== null) {
    const verb      = m[1].toUpperCase();
    const subPath   = m[2] || '';
    const fullPath  = (classMapping + subPath).replace(/\/+/g, '/');
    endpoints.push({ method: verb, path: fullPath, file });
  }
  return endpoints;
}

const javaFiles       = findJavaFiles(CONTROLLERS);
const codeEndpoints   = javaFiles.flatMap(extractEndpoints);

// ─── 2. Extrair requests da coleção Postman ──────────────────────────────────

function findRequestFiles(dir, files = []) {
  for (const entry of fs.readdirSync(dir, { withFileTypes: true })) {
    const full = path.join(dir, entry.name);
    if (entry.isDirectory()) findRequestFiles(full, files);
    else if (entry.name.endsWith('.request.yaml')) files.push(full);
  }
  return files;
}

function parseRequestYaml(file) {
  const src    = fs.readFileSync(file, 'utf8');
  const method = (src.match(/^method:\s*(\w+)/m) || [])[1] || '';
  const urlRaw = (src.match(/^url:\s*['"]?([^'"]+)['"]?/m) || [])[1] || '';

  // Normaliza: remove {{baseUrl}}, variáveis de path → :param
  const urlPath = urlRaw
    .replace(/\{\{baseUrl\}\}/i, '')
    .replace(/\{\{[^}]+\}\}/g, ':param')
    .replace(/\/+/g, '/')
    .replace(/\/$/, '') || '/';

  return { method: method.toUpperCase(), path: urlPath, file };
}

const requestFiles      = findRequestFiles(COLLECTION);
const collectionRequests = requestFiles.map(parseRequestYaml);

// ─── 3. Normalizar paths para comparação ─────────────────────────────────────

function normalizePath(p) {
  return p
    .replace(/\{[^}]+\}/g, ':param')   // {id} → :param  (Java style)
    .replace(/\/+/g, '/')
    .replace(/\/$/, '') || '/';
}

const codeSet       = new Set(codeEndpoints.map(e => `${e.method} ${normalizePath(e.path)}`));
const collectionSet = new Set(collectionRequests.map(r => `${r.method} ${r.path}`));

// ─── 4. Diff ──────────────────────────────────────────────────────────────────

const missingInCollection = [...codeSet].filter(e => !collectionSet.has(e));
const extraInCollection   = [...collectionSet].filter(e => !codeSet.has(e));

// ─── 5. Relatório ─────────────────────────────────────────────────────────────

const RED    = '\x1b[31m';
const GREEN  = '\x1b[32m';
const YELLOW = '\x1b[33m';
const CYAN   = '\x1b[36m';
const RESET  = '\x1b[0m';
const BOLD   = '\x1b[1m';

console.log(`\n${BOLD}${CYAN}╔══════════════════════════════════════════════╗`);
console.log(`║       SIGIN — Postman Sync Check             ║`);
console.log(`╚══════════════════════════════════════════════╝${RESET}\n`);

console.log(`${CYAN}Controllers encontrados:${RESET} ${javaFiles.length}`);
console.log(`${CYAN}Endpoints no código:    ${RESET} ${codeEndpoints.length}`);
console.log(`${CYAN}Requests na coleção:    ${RESET} ${collectionRequests.length}\n`);

if (missingInCollection.length === 0) {
  console.log(`${GREEN}${BOLD}✔ Todos os endpoints do código têm request na coleção!${RESET}\n`);
} else {
  console.log(`${RED}${BOLD}✘ Endpoints no código SEM request na coleção (${missingInCollection.length}):${RESET}`);
  missingInCollection.sort().forEach(e => console.log(`  ${RED}➕ ${e}${RESET}`));
  console.log();
}

if (extraInCollection.length > 0) {
  console.log(`${YELLOW}${BOLD}⚠ Requests na coleção SEM endpoint correspondente no código (${extraInCollection.length}):${RESET}`);
  extraInCollection.sort().forEach(e => console.log(`  ${YELLOW}❓ ${e}${RESET}`));
  console.log();
}

// Exit code não-zero se houver divergências (útil em CI/CD)
if (missingInCollection.length > 0) process.exit(1);

package br.com.inova.sigin.usuario.service;

import br.com.inova.sigin.pessoa.entity.Pessoa;
import br.com.inova.sigin.pessoa.repository.PessoaRepository;
import br.com.inova.sigin.shared.exception.RegraNegocioException;
import br.com.inova.sigin.usuario.dto.UsuarioRequest;
import br.com.inova.sigin.usuario.dto.UsuarioResponse;
import br.com.inova.sigin.usuario.entity.Usuario;
import br.com.inova.sigin.usuario.mapper.UsuarioMapper;
import br.com.inova.sigin.usuario.repository.UsuarioRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private final PessoaRepository pessoaRepository;
    private final UsuarioMapper mapper;
    private final PasswordEncoder passwordEncoder;

    public UsuarioResponse criar(UsuarioRequest request) {

        if (repository.existsByLogin(request.getLogin())) {
            throw new RegraNegocioException("Login já cadastrado.");
        }

        if (repository.existsByPessoaId(request.getPessoaId())) {
            throw new RegraNegocioException("Pessoa já possui usuário.");
        }

        Pessoa pessoa = pessoaRepository.findById(request.getPessoaId()).orElseThrow(() -> new RegraNegocioException("Pessoa não encontrada."));

        Usuario usuario = Usuario.builder().pessoa(pessoa).login(request.getLogin()).senha(passwordEncoder.encode(request.getSenha())).ativo(request.getAtivo()).dataCriacao(LocalDateTime.now()).build();

        return mapper.toResponse(repository.save(usuario));
    }

    public List<UsuarioResponse> listar() {
        return repository.findByAtivoTrue().stream().map(mapper::toResponse).toList();
    }

    public UsuarioResponse buscar(Long id) {
        return mapper.toResponse(buscaUsuario(id));
    }

    public UsuarioResponse atualizar(Long id, UsuarioRequest request) {

        Usuario usuario = buscaUsuario(id);

        if (!usuario.getLogin().equals(request.getLogin()) && repository.existsByLogin(request.getLogin())) {
            throw new RegraNegocioException("Login já cadastrado.");
        }

        if (!usuario.getPessoa().getId().equals(request.getPessoaId()) && repository.existsByPessoaId(request.getPessoaId())) {
            throw new RegraNegocioException("Pessoa já possui usuário.");
        }

        Pessoa pessoa = pessoaRepository.findById(request.getPessoaId()).orElseThrow(() -> new RegraNegocioException("Pessoa não encontrada."));

        usuario.setPessoa(pessoa);
        usuario.setLogin(request.getLogin());
        usuario.setSenha(passwordEncoder.encode(request.getSenha()));
        usuario.setAtivo(request.getAtivo());

        return mapper.toResponse(repository.save(usuario));
    }

    public void excluir(Long id) {

        Usuario usuario = buscaUsuario(id);

        usuario.setAtivo(false);

        repository.save(usuario);
    }

    private Usuario buscaUsuario(Long id) {

        return repository.findByIdAndAtivoTrue(id).orElseThrow(() -> new RegraNegocioException("Usuário não encontrado."));
    }
}
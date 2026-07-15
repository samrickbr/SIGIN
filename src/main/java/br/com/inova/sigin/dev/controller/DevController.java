package br.com.inova.sigin.dev.controller;

import br.com.inova.sigin.dev.service.DevService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/dev")
@RequiredArgsConstructor
public class DevController {

    private final DevService service;

    @PostMapping("/popular")
    public ResponseEntity<List<String>> popular() {
        service.popular();
        return ResponseEntity.ok(service.popular());
    }
}
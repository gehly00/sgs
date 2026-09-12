package com.sgs.controller;

import com.sgs.dto.SolicitanteDTO;
import com.sgs.service.SolicitanteService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/solicitantes")
public class SolicitanteController {

    private final SolicitanteService service;

    public SolicitanteController(SolicitanteService service) {
        this.service = service;
    }

    @GetMapping
    public ResponseEntity<List<SolicitanteDTO>> listar() {
        return ResponseEntity.ok(service.listar());
    }

}

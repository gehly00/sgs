package com.sgs.controller;

import com.sgs.dto.AtualizacaoStatusRequestDTO;
import com.sgs.dto.SolicitacaoDetalheDTO;
import com.sgs.dto.SolicitacaoListagemDTO;
import com.sgs.dto.SolicitacaoRequestDTO;
import com.sgs.entity.StatusSolicitacao;
import com.sgs.service.SolicitacaoService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/solicitacoes")
public class SolicitaoController {

    private final SolicitacaoService service;

    public SolicitaoController(SolicitacaoService service) {
        this.service = service;
    }

    @GetMapping("/{id}")
    public ResponseEntity<SolicitacaoDetalheDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(service.buscarPorId(id));
    }

    @GetMapping
    public ResponseEntity<List<SolicitacaoListagemDTO>> listar(StatusSolicitacao status, LocalDate dataInicio, LocalDate dataFim, Long categoriaId) {
        return ResponseEntity.ok(service.listar(status, dataInicio, dataFim, categoriaId));
    }

    @PostMapping
    public ResponseEntity<SolicitacaoDetalheDTO> criar(@Valid @RequestBody SolicitacaoRequestDTO dto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(service.criar(dto));
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<SolicitacaoDetalheDTO> atualizarStatus(@PathVariable Long id, @Valid @RequestBody AtualizacaoStatusRequestDTO dto) {
        return ResponseEntity.ok(service.atualizarStatus(id,dto));
    }

}
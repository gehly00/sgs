package com.sgs.service;

import com.sgs.dto.SolicitanteDTO;
import com.sgs.repository.SolicitanteRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SolicitanteService {

    private final SolicitanteRepository repository;

    public SolicitanteService(SolicitanteRepository repository) {
        this.repository = repository;
    }

    public List<SolicitanteDTO> listar() {
        return repository.findAll().stream().map(
                solicitante -> new SolicitanteDTO(
                        solicitante.getId(),
                        solicitante.getNome(),
                        solicitante.getCpfCnpj()
                )
        ).toList();
    }

}

package com.sgs.service;

import com.sgs.dto.CategoriaDTO;
import com.sgs.repository.CategoriaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CategoriaService {

    private final CategoriaRepository repository;

    public CategoriaService(CategoriaRepository repository) {
        this.repository = repository;
    }

    public List<CategoriaDTO> listar() {
        return repository.findAll().stream().map(
                solicitante -> new CategoriaDTO(
                        solicitante.getId(),
                        solicitante.getNome()
                )
        ).toList();
    }

}
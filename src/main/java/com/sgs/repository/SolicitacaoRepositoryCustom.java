package com.sgs.repository;

import com.sgs.dto.SolicitacaoListagemDTO;
import com.sgs.entity.StatusSolicitacao;

import java.time.LocalDate;
import java.util.List;

public interface SolicitacaoRepositoryCustom {

    List<SolicitacaoListagemDTO> listarComFiltros(
            StatusSolicitacao status,
            LocalDate dataInicio,
            LocalDate dataFim,
            Long categoriaId
    );

}
package com.sgs.repository;

import com.sgs.dto.SolicitacaoListagemDTO;
import com.sgs.entity.StatusSolicitacao;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;

import java.math.BigDecimal;
import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

public class SolicitacaoRepositoryImpl implements SolicitacaoRepositoryCustom{

    @PersistenceContext
    private EntityManager entity;

    @Override
    public List<SolicitacaoListagemDTO> listarComFiltros(StatusSolicitacao status, LocalDate dataInicio, LocalDate dataFim, Long categoriaId){
        StringBuilder sql = new StringBuilder(
                """
                SELECT
                    s.id,
                    so.nome,
                    so.cpf_cnpj,
                    c.nome,
                    s.status,
                    s.valor,
                    s.data_solicitacao
                FROM solicitacao s
                JOIN solicitante so ON so.id = s.solicitante_id
                JOIN categoria c ON c.id = s.categoria_id
                WHERE 1 = 1
                """
        );

        if (status != null) {
            sql.append(" AND s.status = :status");
        }

        if (dataInicio != null) {
            sql.append(" AND s.data_solicitacao >= :dataInicio");
        }

        if (dataFim != null) {
            sql.append(" AND s.data_solicitacao <= :dataFim");
        }

        if (categoriaId != null) {
            sql.append(" AND s.categoria_id = :categoriaId");
        }

        sql.append(" ORDER BY s.data_solicitacao DESC, s.id DESC");

        Query query = entity.createNativeQuery(sql.toString());

        if (status != null) {
            query.setParameter("status", status.name());
        }

        if (dataInicio != null) {
            query.setParameter("dataInicio", dataInicio);
        }

        if (dataFim != null) {
            query.setParameter("dataFim", dataFim);
        }

        if (categoriaId != null) {
            query.setParameter("categoriaId", categoriaId);
        }

        List<Object[]> resultados = query.getResultList();

        return resultados.stream()
                .map(this::mapearParaDTO)
                .toList();
    }

    private SolicitacaoListagemDTO mapearParaDTO(Object[] linha) {

        Long id = ((Number) linha[0]).longValue();
        String solicitanteNome = (String) linha[1];
        String cpfCnpj = (String) linha[2];
        String categoriaNome = (String) linha[3];
        StatusSolicitacao status = StatusSolicitacao.valueOf(linha[4].toString());

        BigDecimal valor = linha[5] instanceof BigDecimal valorDecimal ? valorDecimal : new BigDecimal(linha[5].toString());

        LocalDate dataSolicitacao;

        if (linha[6] instanceof LocalDate data) {
            dataSolicitacao = data;
        } else {
            dataSolicitacao = ((Date) linha[6]).toLocalDate();
        }

        return new SolicitacaoListagemDTO(id, solicitanteNome, cpfCnpj, categoriaNome, status, valor, dataSolicitacao);
    }
}

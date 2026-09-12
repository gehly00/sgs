package com.sgs.dto;

import com.sgs.entity.StatusSolicitacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SolicitacaoDetalheDTO {

    private Long id;

    private Long solicitanteId;

    private String solicitanteNome;

    private String cpfCnpj;

    private Long categoriaId;

    private String categoriaNome;

    private String descricao;

    private BigDecimal valor;

    private LocalDate dataSolicitacao;

    private StatusSolicitacao status;

    public SolicitacaoDetalheDTO(Long id, Long solicitanteId, String solicitanteNome, String cpfCnpj, Long categoriaId, String categoriaNome, String descricao, BigDecimal valor, LocalDate dataSolicitacao, StatusSolicitacao status) {
        this.id = id;
        this.solicitanteId = solicitanteId;
        this.solicitanteNome = solicitanteNome;
        this.cpfCnpj = cpfCnpj;
        this.categoriaId = categoriaId;
        this.categoriaNome = categoriaNome;
        this.descricao = descricao;
        this.valor = valor;
        this.dataSolicitacao = dataSolicitacao;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public Long getSolicitanteId() {
        return solicitanteId;
    }

    public String getSolicitanteNome() {
        return solicitanteNome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public String getDescricao() {
        return descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

}
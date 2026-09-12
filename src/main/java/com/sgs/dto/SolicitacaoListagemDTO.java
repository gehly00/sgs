package com.sgs.dto;

import com.sgs.entity.StatusSolicitacao;

import java.math.BigDecimal;
import java.time.LocalDate;

public class SolicitacaoListagemDTO {

    private Long id;

    private String solicitanteNome;

    private String cpfCnpj;

    private String categoriaNome;

    private StatusSolicitacao status;

    private BigDecimal valor;

    private LocalDate dataSolicitacao;

    public SolicitacaoListagemDTO(Long id, String solicitanteNome, String cpfCnpj, String categoriaNome, StatusSolicitacao status, BigDecimal valor, LocalDate dataSolicitacao) {
        this.id = id;
        this.solicitanteNome = solicitanteNome;
        this.cpfCnpj = cpfCnpj;
        this.categoriaNome = categoriaNome;
        this.status = status;
        this.valor = valor;
        this.dataSolicitacao = dataSolicitacao;
    }

    public Long getId() {
        return id;
    }

    public String getSolicitanteNome() {
        return solicitanteNome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

    public String getCategoriaNome() {
        return categoriaNome;
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public LocalDate getDataSolicitacao() {
        return dataSolicitacao;
    }

}
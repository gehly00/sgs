package com.sgs.dto;

import jakarta.validation.constraints.*;

import java.math.BigDecimal;

public class SolicitacaoRequestDTO {

    @NotNull
    private Long solicitanteId;

    @NotNull
    private Long categoriaId;

    @NotBlank
    @Size(max = 500)
    private String descricao;

    @NotNull
    @DecimalMin(value = "0.01")
    @Digits(integer = 13, fraction = 2)
    private BigDecimal valor;

    public SolicitacaoRequestDTO() {
    }

    public Long getSolicitanteId() {
        return solicitanteId;
    }

    public void setSolicitanteId(Long solicitanteId) {
        this.solicitanteId = solicitanteId;
    }

    public Long getCategoriaId() {
        return categoriaId;
    }

    public void setCategoriaId(Long categoriaId) {
        this.categoriaId = categoriaId;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public BigDecimal getValor() {
        return valor;
    }

    public void setValor(BigDecimal valor) {
        this.valor = valor;
    }

}
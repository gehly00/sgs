package com.sgs.dto;

public class SolicitanteDTO {

    private Long id;

    private String nome;

    private String cpfCnpj;

    public SolicitanteDTO(Long id, String nome, String cpfCnpj) {
        this.id = id;
        this.nome = nome;
        this.cpfCnpj = cpfCnpj;
    }

    public Long getId() {
        return id;
    }

    public String getNome() {
        return nome;
    }

    public String getCpfCnpj() {
        return cpfCnpj;
    }

}
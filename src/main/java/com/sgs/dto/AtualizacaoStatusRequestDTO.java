package com.sgs.dto;

import com.sgs.entity.StatusSolicitacao;
import jakarta.validation.constraints.NotNull;

public class AtualizacaoStatusRequestDTO {

    @NotNull
    private StatusSolicitacao status;

    public AtualizacaoStatusRequestDTO() {
    }

    public StatusSolicitacao getStatus() {
        return status;
    }

    public void setStatus(StatusSolicitacao status) {
        this.status = status;
    }

}
package com.sgs.service;

import com.sgs.dto.AtualizacaoStatusRequestDTO;
import com.sgs.dto.SolicitacaoDetalheDTO;
import com.sgs.dto.SolicitacaoListagemDTO;
import com.sgs.dto.SolicitacaoRequestDTO;
import com.sgs.entity.Categoria;
import com.sgs.entity.Solicitacao;
import com.sgs.entity.Solicitante;
import com.sgs.entity.StatusSolicitacao;
import com.sgs.exception.RecursoNaoEncontradoException;
import com.sgs.exception.RegraDeNegocioException;
import com.sgs.repository.CategoriaRepository;
import com.sgs.repository.SolicitacaoRepository;
import com.sgs.repository.SolicitanteRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class SolicitacaoService {

    private final CategoriaRepository categoriaRepository;
    private final SolicitacaoRepository solicitacaoRepository;
    private final SolicitanteRepository solicitanteRepository;

    public SolicitacaoService(CategoriaRepository categoriaRepository , SolicitacaoRepository solicitacaoRepository, SolicitanteRepository solicitanteRepository){
        this.categoriaRepository = categoriaRepository;
        this.solicitacaoRepository = solicitacaoRepository;
        this.solicitanteRepository = solicitanteRepository;
    }

    public List<SolicitacaoListagemDTO> listar(StatusSolicitacao status, LocalDate dataInicio, LocalDate dataFim, Long cateoriaId) {
        return solicitacaoRepository.listarComFiltros(status, dataInicio,dataFim,cateoriaId);
    }

    public SolicitacaoDetalheDTO buscarPorId(Long id) {
        Solicitacao solicitacao = solicitacaoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("A solicitaçaõ não foi encontrada."));
        return converterParaDetalheDTO(solicitacao);
    }

    public SolicitacaoDetalheDTO criar(SolicitacaoRequestDTO dto) {

        Solicitante solicitante = solicitanteRepository.findById(dto.getSolicitanteId()).orElseThrow(() -> new RecursoNaoEncontradoException("O solicitante não foi encontrado."));

        Categoria categoria = categoriaRepository.findById(dto.getCategoriaId()).orElseThrow(() -> new RecursoNaoEncontradoException("A categoria não foi encontrada."));

        Solicitacao solicitacao = new Solicitacao();

        solicitacao.setSolicitante(solicitante);
        solicitacao.setCategoria(categoria);
        solicitacao.setDescricao(dto.getDescricao());
        solicitacao.setValor(dto.getValor());
        solicitacao.setDataSolicitacao(LocalDate.now());

        // Toda soliciação deve começar com status solicitado
        solicitacao.setStatus(StatusSolicitacao.SOLICITADO);

        return converterParaDetalheDTO(solicitacaoRepository.save(solicitacao));
    }

    public SolicitacaoDetalheDTO atualizarStatus(Long id, AtualizacaoStatusRequestDTO dto) {

        Solicitacao solicitacao = solicitacaoRepository.findById(id).orElseThrow(() -> new RecursoNaoEncontradoException("A solicitacao não foi encontrada"));

        StatusSolicitacao statusAtual = solicitacao.getStatus();
        StatusSolicitacao novoStatus = dto.getStatus();

        if (!transicaoPermitida(statusAtual, novoStatus)) {
            throw new RegraDeNegocioException("Transicao de status invalida: " + statusAtual + " para " + novoStatus);
        }

        solicitacao.setStatus(novoStatus);
        Solicitacao atualizada = solicitacaoRepository.save(solicitacao);

        return converterParaDetalheDTO(atualizada);
    }

    private boolean transicaoPermitida(StatusSolicitacao statusAtual, StatusSolicitacao novoStatus) {

        return switch (statusAtual) {

            case SOLICITADO ->
                    novoStatus == StatusSolicitacao.LIBERADO || novoStatus == StatusSolicitacao.REJEITADO;

            case LIBERADO ->
                    novoStatus == StatusSolicitacao.APROVADO || novoStatus == StatusSolicitacao.REJEITADO;

            case APROVADO ->
                    novoStatus == StatusSolicitacao.CANCELADO;

            case REJEITADO, CANCELADO ->
                    false;
        };
    }


    private SolicitacaoDetalheDTO converterParaDetalheDTO(Solicitacao solicitacao) {

        return new SolicitacaoDetalheDTO(
                solicitacao.getId(),
                solicitacao.getSolicitante().getId(),
                solicitacao.getSolicitante().getNome(),
                solicitacao.getSolicitante().getCpfCnpj(),
                solicitacao.getCategoria().getId(),
                solicitacao.getCategoria().getNome(),
                solicitacao.getDescricao(),
                solicitacao.getValor(),
                solicitacao.getDataSolicitacao(),
                solicitacao.getStatus()
        );
    }

}

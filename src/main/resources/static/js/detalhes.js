const tituloSolicitacao = document.getElementById("titulo-solicitacao");

const campoSolicitante = document.getElementById("solicitante");
const campoCpfCnpj = document.getElementById("cpfCnpj");
const campoCategoria = document.getElementById("categoria");
const campoValor = document.getElementById("valor");
const campoDataSolicitacao = document.getElementById("dataSolicitacao");
const campoStatusAtual = document.getElementById("statusAtual");
const campoDescricao = document.getElementById("descricao");


const parametros = new URLSearchParams(window.location.search);

const idSolicitacao = parametros.get("id");

async function carregarSolicitacao() {
    if (!idSolicitacao) {
        alert("Solicitação não informada.");

        window.location.href = "index.html";

        return;
    }

    try {
        const resposta = await fetch(
            `/api/solicitacoes/${idSolicitacao}`
        );

        if (!resposta.ok) {
            throw new Error("Não foi possível carregar a solicitação.");
        }

        const solicitacao = await resposta.json();

        preencherDetalhes(solicitacao);

    } catch (erro) {
        console.error(erro);

        alert(erro.message);
    }
}

function preencherDetalhes(solicitacao) {
    tituloSolicitacao.textContent = `Solicitação #${solicitacao.id}`;

    campoSolicitante.textContent = solicitacao.solicitanteNome;
    campoCpfCnpj.textContent = solicitacao.cpfCnpj;
    campoCategoria.textContent = solicitacao.categoriaNome;
    campoValor.textContent = formatarValor(solicitacao.valor);
    campoDataSolicitacao.textContent = formatarData(solicitacao.dataSolicitacao);
    campoStatusAtual.textContent = formatarStatus(solicitacao.status);
    campoDescricao.textContent = solicitacao.descricao;
}

function formatarValor(valor) {
    return Number(valor).toLocaleString(
        "pt-BR",
        {
            style: "currency",
            currency: "BRL"
        }
    );
}

function formatarData(data) {
    if (!data) {
        return "";
    }

    const partes = data.split("-");

    return `${partes[2]}/${partes[1]}/${partes[0]}`;
}

function formatarStatus(status) {
    return status.charAt(0) + status.slice(1).toLowerCase();
}

carregarSolicitacao();
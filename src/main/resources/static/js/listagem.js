const campoStatus = document.getElementById("status");
const campoCategoria = document.getElementById("categoria");
const campoDataInicio = document.getElementById("dataInicio");
const campoDataFim = document.getElementById("dataFim");

const botaoFiltrar = document.querySelector(".acoes-filtros .botao");
const botaoLimpar = document.querySelector(".acoes-filtros .botao-secundario");

const tabelaSolicitacoes = document.getElementById("tabela-solicitacoes");


async function carregarCategorias() {
    try {
        const resposta = await fetch("/api/categorias");

        if (!resposta.ok) {
            throw new Error("Erro ao carregar categorias.");
        }

        const categorias = await resposta.json();

        campoCategoria.innerHTML = '<option value="">Todas as categorias</option>';

        categorias.forEach(categoria => {
            const option = document.createElement("option");

            option.value = categoria.id;
            option.textContent = categoria.nome;

            campoCategoria.appendChild(option);
        });

    } catch (erro) {
        console.error(erro);
    }
}


async function carregarSolicitacoes() {
    try {
        const parametros = new URLSearchParams();

        if (campoStatus.value) {
            parametros.append("status", campoStatus.value);
        }

        if (campoCategoria.value) {
            parametros.append("categoriaId", campoCategoria.value);
        }

        if (campoDataInicio.value) {
            parametros.append("dataInicio", campoDataInicio.value);
        }

        if (campoDataFim.value) {
            parametros.append("dataFim", campoDataFim.value);
        }

        let url = "/api/solicitacoes";

        if (parametros.toString()) {
            url += `?${parametros.toString()}`;
        }

        const resposta = await fetch(url);

        if (!resposta.ok) {
            throw new Error("Erro ao carregar solicitações.");
        }

        const solicitacoes = await resposta.json();

        preencherTabela(solicitacoes);

    } catch (erro) {
        console.error(erro);
    }
}

function preencherTabela(solicitacoes) {
    tabelaSolicitacoes.innerHTML = "";

    if (solicitacoes.length === 0) {
        tabelaSolicitacoes.innerHTML = `
            <tr>
                <td colspan="7">
                    Nenhuma solicitação encontrada.
                </td>
            </tr>
        `;

        return;
    }

    solicitacoes.forEach(solicitacao => {
        const linha = document.createElement("tr");

        linha.innerHTML = `
            <td>${solicitacao.solicitanteNome}</td>
            <td>${solicitacao.cpfCnpj}</td>
            <td>${solicitacao.categoriaNome}</td>
            <td>${formatarValor(solicitacao.valor)}</td>
            <td>${formatarData(solicitacao.dataSolicitacao)}</td>
            <td>${formatarStatus(solicitacao.status)}</td>

            <td>
                <div class="acoes-tabela">
                    <div class="acao-status">
                        ${criarAtualizacaoStatus(solicitacao)}
                    </div>
                    <div class="acao-detalhes">
                        <a
                            href="detalhes.html?id=${solicitacao.id}"
                            class="botao-detalhes"
                        >
                            Detalhes
                        </a>
                    </div>
                </div>
            </td>
        `;

        tabelaSolicitacoes.appendChild(linha);
    });

    adicionarEventosAtualizacao();
}

function criarAtualizacaoStatus(solicitacao) {
    const statusPermitidos =
        obterStatusPermitidos(solicitacao.status);

    if (statusPermitidos.length === 0) {
        return "";
    }

    const options = statusPermitidos
        .map(status => `
            <option value="${status}">
                ${formatarStatus(status)}
            </option>
        `)
        .join("");

    return `
        <select
            class="select-status"
            data-id="${solicitacao.id}"
        >
            <option value="">
                Alterar status
            </option>

            ${options}
        </select>

        <button
            type="button"
            class="botao-atualizar-status"
            data-id="${solicitacao.id}"
        >
            Atualizar
        </button>
    `;
}

function obterStatusPermitidos(statusAtual) {
    if (statusAtual === "SOLICITADO") {
        return [
            "LIBERADO",
            "REJEITADO"
        ];
    }

    if (statusAtual === "LIBERADO") {
        return [
            "APROVADO",
            "REJEITADO"
        ];
    }

    if (statusAtual === "APROVADO") {
        return [
            "CANCELADO"
        ];
    }

    return [];
}

function adicionarEventosAtualizacao() {
    const botoes =
        document.querySelectorAll(".botao-atualizar-status");

    botoes.forEach(botao => {
        botao.addEventListener("click", async () => {
            const id = botao.dataset.id;

            const select = document.querySelector(
                `.select-status[data-id="${id}"]`
            );

            const novoStatus = select.value;

            if (!novoStatus) {
                alert("Selecione um novo status.");
                return;
            }

            await atualizarStatus(id, novoStatus);
        });
    });
}

async function atualizarStatus(id, novoStatus) {
    try {
        const resposta = await fetch(
            `/api/solicitacoes/${id}/status`,
            {
                method: "PATCH",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify({
                    status: novoStatus
                })
            }
        );

        if (!resposta.ok) {
            throw new Error(
                "Não foi possível atualizar o status."
            );
        }

        alert("Status atualizado com sucesso.");

        carregarSolicitacoes();

    } catch (erro) {
        console.error(erro);
        alert(erro.message);
    }
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
    return status.charAt(0) +
        status.slice(1).toLowerCase();
}

botaoFiltrar.addEventListener("click", () => {
    carregarSolicitacoes();
});


botaoLimpar.addEventListener("click", () => {
    campoStatus.value = "";
    campoCategoria.value = "";
    campoDataInicio.value = "";
    campoDataFim.value = "";

    carregarSolicitacoes();
});

carregarCategorias();
carregarSolicitacoes();
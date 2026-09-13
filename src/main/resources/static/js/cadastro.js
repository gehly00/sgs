const formulario = document.getElementById("form-solicitacao");

const campoSolicitante = document.getElementById("solicitante");
const campoCategoria = document.getElementById("categoria");
const campoDescricao = document.getElementById("descricao");
const campoValor = document.getElementById("valor");


async function carregarSolicitantes() {
    try {
        const resposta = await fetch("/api/solicitantes");
        if (!resposta.ok) {
            throw new Error("Erro ao carregar solicitantes.");
        }

        const solicitantes = await resposta.json();

        campoSolicitante.innerHTML = '<option value="">Selecione um solicitante</option>';

        solicitantes.forEach(solicitante => {
            const option = document.createElement("option");

            option.value = solicitante.id;
            option.textContent = `${solicitante.nome} - ${solicitante.cpfCnpj}`;

            campoSolicitante.appendChild(option);
        });

    } catch (erro) {
        console.error(erro);
    }
}

async function carregarCategorias() {
    try {
        const resposta = await fetch("/api/categorias");

        if (!resposta.ok) {
            throw new Error("Erro ao carregar categorias.");
        }

        const categorias = await resposta.json();

        campoCategoria.innerHTML = '<option value="">Selecione uma categoria</option>';

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

async function cadastrarSolicitacao(evento) {
    evento.preventDefault();

    const solicitacao = {
        solicitanteId: Number(campoSolicitante.value),
        categoriaId: Number(campoCategoria.value),
        descricao: campoDescricao.value,
        valor: Number(campoValor.value)
    };

    try {
        const resposta = await fetch(
            "/api/solicitacoes",
            {
                method: "POST",

                headers: {
                    "Content-Type": "application/json"
                },

                body: JSON.stringify(solicitacao)
            }
        );

        if (!resposta.ok) {
            throw new Error("Não foi possível cadastrar a solicitação.");
        }

        alert("Solicitação cadastrada com sucesso.");

        window.location.href = "index.html";

    } catch (erro) {
        console.error(erro);
        alert(erro.message);
    }
}

formulario.addEventListener("submit", cadastrarSolicitacao);

carregarSolicitantes();
carregarCategorias();
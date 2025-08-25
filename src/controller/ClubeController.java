package controller;

import model.Clube;
import model.Membro;
import model.Funcionario;
import java.util.ArrayList;
import java.util.List;

// Classe que gerencia operações relacionadas a clubes (inserção, atualização, remoção, listagem)
public class ClubeController {
    private static ClubeController instance;
    private final List<Clube> clubes;
    private int proximoId = 1;

    private ClubeController() {
        clubes = new ArrayList<>();
    }

    // Implementa o padrão Singleton para garantir uma única instância
    public static ClubeController getInstance() {
        if (instance == null) {
            instance = new ClubeController();
        }
        return instance;
    }

    // Insere um novo clube
    public String inserir(String nome, double valorMensalidade) {
        String id = String.valueOf(proximoId);
        Clube clube = new Clube(nome, valorMensalidade);
        clubes.add(clube);
        proximoId++;
        return "Clube inserido com sucesso! ID = " + id;
    }

    // Atualiza os dados de um clube existente, identificado pelo nome
    public String atualizar(String nome, String novoNome, double valorMensalidade) {
        Clube clube = getClubeByNome(nome);
        if (clube != null) {
            clube.setNome(novoNome);
            clube.setValorMensalidade(valorMensalidade);
            return "Clube atualizado com sucesso!";
        }
        return "Erro: Clube não encontrado!";
    }

    // Remove um clube pelo nome
    public String remover(String nome) {
        Clube clube = getClubeByNome(nome);
        if (clube != null) {
            if (!clube.getMembros().isEmpty() || !clube.getFuncionarios().isEmpty()) {
                return "Erro: Clube possui membros ou funcionários associados!";
            }
            clubes.remove(clube);
            return "Clube removido com sucesso!";
        }
        return "Erro: Clube não encontrado!";
    }

    // Lista todos os clubes cadastrados
    public String listar() {
        if (clubes.isEmpty()) {
            return "Nenhum clube cadastrado.";
        }
        StringBuilder sb = new StringBuilder("Lista de clubes:\n");
        for (int i = 0; i < clubes.size(); i++) {
            Clube c = clubes.get(i);
            sb.append(String.format("%d - %s - Mensalidade: %.2f\n", i + 1, c.getNome(), c.getValorMensalidade()));
        }
        return sb.toString();
    }

    // Lista os membros de um clube específico, identificado pelo nome
    public String listarMembros(String nome) {
        Clube clube = getClubeByNome(nome);
        if (clube == null) {
            return "Erro: Clube não encontrado!";
        }
        List<Membro> membros = clube.getMembros();
        if (membros.isEmpty()) {
            return "Nenhum membro associado ao clube " + clube.getNome();
        }
        StringBuilder sb = new StringBuilder("Membros do clube " + clube.getNome() + ":\n");
        for (Membro m : membros) {
            sb.append(String.format("%s - %s - %s - Matrícula: %d - Plano: %s\n",
                    m.getCpf(), m.getNome(), m.getEndereco(), m.getMatricula(), m.getPlano()));
        }
        return sb.toString();
    }

    // Lista os funcionários de um clube específico, identificado pelo nome
    public String listarFuncionarios(String nome) {
        Clube clube = getClubeByNome(nome);
        if (clube == null) {
            return "Erro: Clube não encontrado!";
        }
        List<Funcionario> funcionarios = clube.getFuncionarios();
        if (funcionarios.isEmpty()) {
            return "Nenhum funcionário associado ao clube " + clube.getNome();
        }
        StringBuilder sb = new StringBuilder("Funcionários do clube " + clube.getNome() + ":\n");
        for (Funcionario f : funcionarios) {
            sb.append(String.format("%s - %s - %s - CTPS: %s - Salário: %.2f\n",
                    f.getCpf(), f.getNome(), f.getEndereco(), f.getCtps(), f.getSalario()));
        }
        return sb.toString();
    }

    // Busca um clube pelo nome
    public Clube getClubeByNome(String nome) {
        for (Clube c : clubes) {
            if (c.getNome().equalsIgnoreCase(nome)) {
                return c;
            }
        }
        return null;
    }

    // Busca um clube pelo ID (mantido para compatibilidade interna)
    public Clube getClubeById(String id) {
        try {
            int idNum = Integer.parseInt(id);
            for (Clube c : clubes) {
                if (clubes.indexOf(c) + 1 == idNum) {
                    return c;
                }
            }
        } catch (NumberFormatException e) {
            // Trata ID inválido
        }
        return null;
    }
}
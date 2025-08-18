package controller;

import model.Clube;
import model.Membro;
import model.Funcionario;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ClubeController {
    private List<Clube> clubes;

    public ClubeController() {
        this.clubes = new ArrayList<>();
    }

    public void criarClube(String nome, double valorMensalidade) {
        Clube clube = new Clube(nome, valorMensalidade);
        clubes.add(clube);
    }

    public void listarClubes(PrintWriter out) {
        if (clubes.isEmpty()) {
            out.println("Nenhum clube cadastrado.");
            return;
        }
        out.println("Lista de Clubes:");
        for (int i = 0; i < clubes.size(); i++) {
            Clube c = clubes.get(i);
            out.printf("%d. Nome: %s | Membros: %d | Mensalidade: %.2f\n",
                    i + 1, c.getNome(), c.getQtdMembros(), c.getValorMensalidade());
        }
    }

    public Clube escolherClube(PrintWriter out, BufferedReader in) throws java.io.IOException {
        listarClubes(out);
        if (clubes.isEmpty()) return null;
        out.println("Escolha o número do clube (0 para cancelar): ");
        String input = in.readLine();
        try {
            int idx = Integer.parseInt(input.trim());
            if (idx == 0) return null;
            if (idx > 0 && idx <= clubes.size()) {
                return clubes.get(idx - 1);
            }
            out.println("Seleção inválida.");
            return null;
        } catch (NumberFormatException e) {
            out.println("Entrada inválida.");
            return null;
        }
    }

    public void atualizarClube(Clube clube, String novoNome, double novoValor) {
        if (!novoNome.isEmpty()) clube.setNome(novoNome);
        if (novoValor != 0) clube.setValorMensalidade(novoValor);
    }

    public void deletarClube(Clube clube) {
        for (Membro m : new ArrayList<>(clube.getMembros())) {
            clube.removeMembro(m);
        }
        for (Funcionario f : new ArrayList<>(clube.getFuncionarios())) {
            clube.removeFuncionario(f);
        }
        clubes.remove(clube);
    }

    public List<Clube> getClubes() {
        return new ArrayList<>(clubes);
    }
}
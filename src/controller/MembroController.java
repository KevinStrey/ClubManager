package controller;

import model.Clube;
import model.Membro;
import model.Plano;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;

public class MembroController {
    private ClubeController clubeController;

    public MembroController(ClubeController clubeController) {
        this.clubeController = clubeController;
    }

    public void criarMembro(Clube clube, String cpf, String nome, String endereco, int matricula, int planoOpcao, PrintWriter out) {
        Plano plano = null;
        switch (planoOpcao) {
            case 1: plano = Plano.MENSAL; break;
            case 2: plano = Plano.SEMESTRAL; break;
            case 3: plano = Plano.ANUAL; break;
            default: out.println("Plano inválido."); return;
        }
        Membro membro = new Membro(cpf, nome, endereco, matricula, plano);
        clube.addMembro(membro);
        out.println("Membro criado com sucesso.");
    }

    public void listarMembros(Clube clube, PrintWriter out) {
        List<Membro> membros = clube.getMembros();
        if (membros.isEmpty()) {
            out.println("Nenhum membro no clube.");
            return;
        }
        out.println("Lista de Membros:");
        for (int i = 0; i < membros.size(); i++) {
            Membro m = membros.get(i);
            out.printf("%d. Nome: %s | CPF: %s | Matrícula: %d | Plano: %s\n",
                    i + 1, m.getNome(), m.getCpf(), m.getMatricula(), m.getPlano());
        }
    }

    public void listarTodosMembros(PrintWriter out) {
        List<Clube> clubes = clubeController.getClubes();
        if (clubes.isEmpty()) {
            out.println("Nenhum clube cadastrado.");
            return;
        }
        out.println("Lista de Todos os Membros:");
        int count = 1;
        for (Clube clube : clubes) {
            List<Membro> membros = clube.getMembros();
            if (!membros.isEmpty()) {
                for (Membro m : membros) {
                    out.printf("%d. Clube: %s | Nome: %s | CPF: %s | Matrícula: %d | Plano: %s\n",
                            count++, clube.getNome(), m.getNome(), m.getCpf(), m.getMatricula(), m.getPlano());
                }
            }
        }
        if (count == 1) {
            out.println("Nenhum membro cadastrado.");
        }
    }

    public Membro escolherMembro(Clube clube, PrintWriter out, BufferedReader in) throws java.io.IOException {
        List<Membro> membros = clube.getMembros();
        if (membros.isEmpty()) {
            out.println("Nenhum membro disponível.");
            return null;
        }
        listarMembros(clube, out);
        out.println("Escolha o número do membro (0 para cancelar): ");
        String input = in.readLine();
        try {
            int idx = Integer.parseInt(input.trim());
            if (idx == 0) return null;
            if (idx > 0 && idx <= membros.size()) {
                return membros.get(idx - 1);
            }
            out.println("Seleção inválida.");
            return null;
        } catch (NumberFormatException e) {
            out.println("Entrada inválida.");
            return null;
        }
    }

    public void atualizarMembro(Membro membro, String cpf, String nome, String endereco, int matricula, int planoOpcao) {
        if (!cpf.isEmpty()) membro.setCpf(cpf);
        if (!nome.isEmpty()) membro.setNome(nome);
        if (!endereco.isEmpty()) membro.setEndereco(endereco);
        if (matricula != 0) membro.setMatricula(matricula);
        if (planoOpcao != 0) {
            switch (planoOpcao) {
                case 1: membro.setPlano(Plano.MENSAL); break;
                case 2: membro.setPlano(Plano.SEMESTRAL); break;
                case 3: membro.setPlano(Plano.ANUAL); break;
            }
        }
    }

    public void deletarMembro(Clube clube, Membro membro) {
        clube.removeMembro(membro);
    }
}
package controller;

import model.Clube;
import model.Funcionario;
import java.io.BufferedReader;
import java.io.PrintWriter;
import java.util.List;

public class FuncionarioController {
    private ClubeController clubeController;

    public FuncionarioController(ClubeController clubeController) {
        this.clubeController = clubeController;
    }

    public void criarFuncionario(Clube clube, String cpf, String nome, String endereco, String ctps, double salario) {
        Funcionario funcionario = new Funcionario(cpf, nome, endereco, ctps, salario);
        clube.addFuncionario(funcionario);
    }

    public void listarFuncionarios(Clube clube, PrintWriter out) {
        List<Funcionario> funcionarios = clube.getFuncionarios();
        if (funcionarios.isEmpty()) {
            out.println("Nenhum funcionario no clube.");
            return;
        }
        out.println("Lista de Funcionarios:");
        for (int i = 0; i < funcionarios.size(); i++) {
            Funcionario f = funcionarios.get(i);
            out.printf("%d. Nome: %s | CPF: %s | CTPS: %s | Salário: %.2f\n",
                    i + 1, f.getNome(), f.getCpf(), f.getCtps(), f.getSalario());
        }
    }

    public void listarTodosFuncionarios(PrintWriter out) {
        List<Clube> clubes = clubeController.getClubes();
        if (clubes.isEmpty()) {
            out.println("Nenhum clube cadastrado.");
            return;
        }
        out.println("Lista de Todos os Funcionarios:");
        int count = 1;
        for (Clube clube : clubes) {
            List<Funcionario> funcionarios = clube.getFuncionarios();
            if (!funcionarios.isEmpty()) {
                for (Funcionario f : funcionarios) {
                    out.printf("%d. Clube: %s | Nome: %s | CPF: %s | CTPS: %s | Salário: %.2f\n",
                            count++, clube.getNome(), f.getNome(), f.getCpf(), f.getCtps(), f.getSalario());
                }
            }
        }
        if (count == 1) {
            out.println("Nenhum funcionario cadastrado.");
        }
    }

    public Funcionario escolherFuncionario(Clube clube, PrintWriter out, BufferedReader in) throws java.io.IOException {
        List<Funcionario> funcionarios = clube.getFuncionarios();
        if (funcionarios.isEmpty()) {
            out.println("Nenhum funcionario disponível.");
            return null;
        }
        listarFuncionarios(clube, out);
        out.println("Escolha o número do funcionario (0 para cancelar): ");
        String input = in.readLine();
        try {
            int idx = Integer.parseInt(input.trim());
            if (idx == 0) return null;
            if (idx > 0 && idx <= funcionarios.size()) {
                return funcionarios.get(idx - 1);
            }
            out.println("Seleção inválida.");
            return null;
        } catch (NumberFormatException e) {
            out.println("Entrada inválida.");
            return null;
        }
    }

    public void atualizarFuncionario(Funcionario funcionario, String cpf, String nome, String endereco, String ctps, double salario) {
        if (!cpf.isEmpty()) funcionario.setCpf(cpf);
        if (!nome.isEmpty()) funcionario.setNome(nome);
        if (!endereco.isEmpty()) funcionario.setEndereco(endereco);
        if (!ctps.isEmpty()) funcionario.setCtps(ctps);
        if (salario != 0) funcionario.setSalario(salario);
    }

    public void deletarFuncionario(Clube clube, Funcionario funcionario) {
        clube.removeFuncionario(funcionario);
    }
}
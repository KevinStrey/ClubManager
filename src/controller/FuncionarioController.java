package controller;

import model.Clube;
import model.Funcionario;
import java.util.ArrayList;
import java.util.List;

// Classe que gerencia operações relacionadas a funcionários (inserção, atualização, remoção, listagem)
public class FuncionarioController {
    private static FuncionarioController instance;
    private final List<Funcionario> funcionarios;

    private FuncionarioController() {
        funcionarios = new ArrayList<>();
    }

    // Implementa o padrão Singleton para garantir uma única instância
    public static FuncionarioController getInstance() {
        if (instance == null) {
            instance = new FuncionarioController();
        }
        return instance;
    }

    // Insere um novo funcionário, associando-o a um clube
    public String inserir(String cpf, String nome, String endereco, String ctps, String salario, String clubeNome) {
        for (Funcionario f : funcionarios) {
            if (f.getCpf().equals(cpf)) {
                return "Erro: Funcionário já existe com CPF " + cpf;
            }
        }
        ClubeController clubeController = ClubeController.getInstance();
        Clube clube = clubeController.getClubeByNome(clubeNome);
        if (clube == null) {
            return "Erro: Clube com nome " + clubeNome + " não encontrado!";
        }
        try {
            Funcionario funcionario = new Funcionario(cpf, nome, endereco, ctps, Double.parseDouble(salario));
            funcionarios.add(funcionario);
            clube.addFuncionario(funcionario);
            return "Funcionário inserido com sucesso!";
        } catch (NumberFormatException e) {
            return "Erro: Salário inválido!";
        }
    }

    // Atualiza os dados de um funcionário existente
    public String atualizar(String cpf, String nome, String endereco, String ctps, String salario, String clubeNome) {
        for (Funcionario f : funcionarios) {
            if (f.getCpf().equals(cpf)) {
                ClubeController clubeController = ClubeController.getInstance();
                Clube clube = clubeController.getClubeByNome(clubeNome);
                if (clube == null) {
                    return "Erro: Clube com nome " + clubeNome + " não encontrado!";
                }
                try {
                    f.setNome(nome);
                    f.setEndereco(endereco);
                    f.setCtps(ctps);
                    f.setSalario(Double.parseDouble(salario));
                    Clube oldClube = f.getClube();
                    if (oldClube != null && !oldClube.equals(clube)) {
                        oldClube.removeFuncionario(f);
                        clube.addFuncionario(f);
                    } else if (oldClube == null) {
                        clube.addFuncionario(f);
                    }
                    return "Funcionário atualizado com sucesso!";
                } catch (NumberFormatException e) {
                    return "Erro: Salário inválido!";
                }
            }
        }
        return "Erro: Funcionário não encontrado!";
    }

    // Remove um funcionário pelo CPF
    public String remover(String cpf) {
        for (Funcionario f : funcionarios) {
            if (f.getCpf().equals(cpf)) {
                Clube clube = f.getClube();
                if (clube != null) {
                    clube.removeFuncionario(f);
                }
                funcionarios.remove(f);
                return "Funcionário removido com sucesso!";
            }
        }
        return "Erro: Funcionário não encontrado!";
    }

    // Lista todos os funcionários cadastrados
    public String listar() {
        // Verifica se não há funcionários cadastrados
        if (funcionarios.isEmpty()) {
            return "Nenhum funcionário cadastrado.";
        }

        // Cria um StringBuilder para construir a lista de funcionários
        StringBuilder sb = new StringBuilder("Lista de funcionários:\n");

        // Itera sobre cada funcionário na lista
        for (Funcionario funcionario : funcionarios) {
            // Determina o nome do clube associado ao funcionário
            String nomeClube;
            if (funcionario.getClube() != null) {
                nomeClube = funcionario.getClube().getNome(); // Obtém o nome do clube, se houver
            } else {
                nomeClube = "Sem clube"; // Define "Sem clube" se não houver associação
            }

            // Formata os dados do funcionário em uma linha
            String linhaFuncionario = String.format(
                "%s - %s - %s - CTPS: %s - Salário: %.2f - Clube: %s\n",
                funcionario.getCpf(),
                funcionario.getNome(),
                funcionario.getEndereco(),
                funcionario.getCtps(),
                funcionario.getSalario(),
                nomeClube
            );

            // Adiciona a linha formatada ao StringBuilder
            sb.append(linhaFuncionario);
        }

        // Retorna a lista completa como string
        return sb.toString();
    }
}
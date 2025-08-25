package controller;

import model.Clube;
import model.Membro;
import model.Plano;
import java.util.ArrayList;
import java.util.List;

// Classe que gerencia operações relacionadas a membros (inserção, atualização, remoção, listagem)
public class MembroController {
    private static MembroController instance;
    private final List<Membro> membros;

    private MembroController() {
        membros = new ArrayList<>();
    }

    // Implementa o padrão Singleton para garantir uma única instância
    public static MembroController getInstance() {
        if (instance == null) {
            instance = new MembroController();
        }
        return instance;
    }

    // Insere um novo membro, associando-o a um clube
    public String inserir(String cpf, String nome, String endereco, String matricula, String plano, String clubeNome) {
        System.out.println("Inserir membro: CPF=" + cpf + ", Nome=" + nome + ", Endereço=" + endereco + 
                           ", Matrícula=" + matricula + ", Plano=" + plano + ", ClubeNome=" + clubeNome);
        for (Membro m : membros) {
            if (m.getCpf().equals(cpf)) {
                return "Erro: Membro já existe com CPF " + cpf;
            }
        }
        ClubeController clubeController = ClubeController.getInstance();
        Clube clube = clubeController.getClubeByNome(clubeNome);
        if (clube == null) {
            return "Erro: Clube com nome " + clubeNome + " não encontrado!";
        }
        try {
            Plano planoEnum = Plano.valueOf(plano.toUpperCase());
            Membro membro = new Membro(cpf, nome, endereco, Integer.parseInt(matricula), planoEnum);
            membros.add(membro);
            clube.addMembro(membro);
            return "Membro inserido com sucesso!";
        } catch (NumberFormatException e) {
            return "Erro: Matrícula inválida!";
        } catch (IllegalArgumentException e) {
            return "Erro: Plano inválido!";
        }
    }

    // Atualiza os dados de um membro existente
    public String atualizar(String cpf, String nome, String endereco, String matricula, String plano, String clubeNome) {
        for (Membro m : membros) {
            if (m.getCpf().equals(cpf)) {
                ClubeController clubeController = ClubeController.getInstance();
                Clube clube = clubeController.getClubeByNome(clubeNome);
                if (clube == null) {
                    return "Erro: Clube com nome " + clubeNome + " não encontrado!";
                }
                try {
                    m.setNome(nome);
                    m.setEndereco(endereco);
                    m.setMatricula(Integer.parseInt(matricula));
                    m.setPlano(Plano.valueOf(plano.toUpperCase()));
                    Clube oldClube = m.getClube();
                    if (oldClube != null && !oldClube.equals(clube)) {
                        oldClube.removeMembro(m);
                        clube.addMembro(m);
                    } else if (oldClube == null) {
                        clube.addMembro(m);
                    }
                    return "Membro atualizado com sucesso!";
                } catch (NumberFormatException e) {
                    return "Erro: Matrícula inválida!";
                } catch (IllegalArgumentException e) {
                    return "Erro: Plano inválido!";
                }
            }
        }
        return "Erro: Membro não encontrado!";
    }

    // Remove um membro pelo CPF
    public String remover(String cpf) {
        for (Membro m : membros) {
            if (m.getCpf().equals(cpf)) {
                Clube clube = m.getClube();
                if (clube != null) {
                    clube.removeMembro(m);
                }
                membros.remove(m);
                return "Membro removido com sucesso!";
            }
        }
        return "Erro: Membro não encontrado!";
    }

    // Lista todos os membros cadastrados
    public String listar() {
        // Verifica se não há membros cadastrados
        if (membros.isEmpty()) {
            return "Nenhum membro cadastrado.";
        }

        // Cria um StringBuilder para construir a lista de membros
        StringBuilder sb = new StringBuilder("Lista de membros:\n");

        // Itera sobre cada membro na lista
        for (Membro membro : membros) {
            // Determina o nome do clube associado ao membro
            String nomeClube;
            if (membro.getClube() != null) {
                nomeClube = membro.getClube().getNome(); // Obtém o nome do clube, se houver
            } else {
                nomeClube = "Sem clube"; // Define "Sem clube" se não houver associação
            }

            // Formata os dados do membro em uma linha
            String linhaMembro = String.format(
                "%s - %s - %s - Matrícula: %d - Plano: %s - Clube: %s\n",
                membro.getCpf(),
                membro.getNome(),
                membro.getEndereco(),
                membro.getMatricula(),
                membro.getPlano(),
                nomeClube
            );

            // Adiciona a linha formatada ao StringBuilder
            sb.append(linhaMembro);
        }

        // Retorna a lista completa como string
        return sb.toString();
    }
}
package app;

import controller.ClubeController;
import controller.MembroController;
import controller.FuncionarioController;
import model.Clube;
import model.Membro;
import model.Funcionario;
import model.Plano;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {
    private static final int PORT = 65000; 
    private final ClubeController clubeController;
    private final MembroController membroController;
    private final FuncionarioController funcionarioController;

    public Server() {
        this.clubeController = new ClubeController();
        this.membroController = new MembroController(clubeController);
        this.funcionarioController = new FuncionarioController(clubeController);
    }

    public static void main(String[] args) {
        Server server = new Server();
        server.start();
    }

    public void start() {
        ServerSocket serverSocket = null;
        try {
            serverSocket = new ServerSocket(PORT);
            serverSocket.setReuseAddress(true); // Alinhado com MaquinaA
            System.out.println("Servidor iniciado na porta " + PORT);
            while (true) {
                Socket clientSocket = null;
                try {
                    System.out.println("Aguardando conexão...");
                    clientSocket = serverSocket.accept();
                    System.out.println("Conexão recebida de: " + clientSocket.getInetAddress().getHostAddress());
                    handleClient(clientSocket);
                } catch (IOException e) {
                    System.err.println("Erro ao aceitar conexão: " + e.getMessage());
                } finally {
                    if (clientSocket != null && !clientSocket.isClosed()) {
                        try {
                            clientSocket.close();
                            System.out.println("Socket do cliente encerrado.");
                        } catch (IOException e) {
                            System.err.println("Erro ao fechar socket do cliente: " + e.getMessage());
                        }
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao iniciar o servidor: " + e.getMessage());
        } finally {
            if (serverSocket != null && !serverSocket.isClosed()) {
                try {
                    serverSocket.close();
                    System.out.println("ServerSocket encerrado.");
                } catch (IOException e) {
                    System.err.println("Erro ao fechar ServerSocket: " + e.getMessage());
                }
            }
        }
    }

    private void handleClient(Socket clientSocket) {
        BufferedReader in = null;
        PrintWriter out = null;
        try {
            in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            out = new PrintWriter(clientSocket.getOutputStream(), true); // Autoflush, como MaquinaA
            out.println("Bem-vindo ao Sistema de Gerenciamento de Clubes!");
            boolean running = true;
            while (running) {
                out.println("\nMenu Principal:");
                out.println("1. Gerenciar Clubes");
                out.println("2. Gerenciar Membros");
                out.println("3. Gerenciar Funcionarios");
                out.println("4. Sair");
                out.println("Escolha uma opção: ");
                String input = in.readLine();
                if (input == null || input.trim().equals("exit")) { // Suporta "exit" como MaquinaA
                    out.println("Desconectando...");
                    break;
                }
                switch (input.trim()) {
                    case "1":
                        gerenciarClubes(in, out);
                        break;
                    case "2":
                        gerenciarMembros(in, out);
                        break;
                    case "3":
                        gerenciarFuncionarios(in, out);
                        break;
                    case "4":
                        out.println("Desconectando...");
                        running = false;
                        break;
                    default:
                        out.println("Opção inválida.");
                }
                // Fecha conexão após cada operação, conforme padrão de conexões não ociosas
                break; // Sai do loop após uma operação, como MaquinaA
            }
        } catch (IOException e) {
            System.err.println("Erro ao lidar com cliente: " + e.getMessage());
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
            } catch (IOException e) {
                System.err.println("Erro ao fechar streams: " + e.getMessage());
            }
        }
    }

    private void gerenciarClubes(BufferedReader in, PrintWriter out) throws IOException {
        out.println("\nGerenciar Clubes:");
        out.println("1. Criar Clube");
        out.println("2. Listar Clubes");
        out.println("3. Atualizar Clube");
        out.println("4. Deletar Clube");
        out.println("5. Listar Membros de um Clube");
        out.println("6. Listar Funcionários de um Clube");
        out.println("7. Voltar");
        out.println("Escolha uma opção: ");
        String input = in.readLine();
        if (input == null || input.trim().equals("exit")) {
            out.println("Desconectando...");
            return;
        }
        switch (input.trim()) {
            case "1":
                out.println("Nome do Clube: ");
                String nome = in.readLine();
                out.println("Valor da Mensalidade: ");
                String valorStr = in.readLine();
                try {
                    double valor = Double.parseDouble(valorStr);
                    clubeController.criarClube(nome, valor);
                    out.println("Clube criado com sucesso.");
                } catch (NumberFormatException e) {
                    out.println("Valor inválido para mensalidade.");
                }
                break;
            case "2":
                clubeController.listarClubes(out);
                break;
            case "3":
                Clube clube = clubeController.escolherClube(out, in);
                if (clube != null) {
                    out.println("Novo Nome (Enter para manter): ");
                    String novoNome = in.readLine();
                    out.println("Novo Valor Mensalidade (0 para manter): ");
                    String novoValorStr = in.readLine();
                    double novoValor = novoValorStr.isEmpty() ? 0 : Double.parseDouble(novoValorStr);
                    clubeController.atualizarClube(clube, novoNome, novoValor);
                    out.println("Clube atualizado com sucesso.");
                }
                break;
            case "4":
                clube = clubeController.escolherClube(out, in);
                if (clube != null) {
                    clubeController.deletarClube(clube);
                    out.println("Clube deletado com sucesso.");
                }
                break;
            case "5":
                clube = clubeController.escolherClube(out, in);
                if (clube != null) {
                    membroController.listarMembros(clube, out);
                }
                break;
            case "6":
                clube = clubeController.escolherClube(out, in);
                if (clube != null) {
                    funcionarioController.listarFuncionarios(clube, out);
                }
                break;
            case "7":
                break;
            default:
                out.println("Opção inválida.");
        }
    }

    private void gerenciarMembros(BufferedReader in, PrintWriter out) throws IOException {
        out.println("\nGerenciar Membros:");
        out.println("1. Criar Membro");
        out.println("2. Listar Membros");
        out.println("3. Atualizar Membro");
        out.println("4. Deletar Membro");
        out.println("5. Voltar");
        out.println("Escolha uma opção: ");
        String input = in.readLine();
        if (input == null || input.trim().equals("exit")) {
            out.println("Desconectando...");
            return;
        }
        switch (input.trim()) {
            case "1":
                Clube clube = clubeController.escolherClube(out, in);
                if (clube != null) {
                    out.println("CPF: ");
                    String cpf = in.readLine();
                    out.println("Nome: ");
                    String nome = in.readLine();
                    out.println("Endereço: ");
                    String endereco = in.readLine();
                    out.println("Matrícula: ");
                    String matriculaStr = in.readLine();
                    out.println("Plano: 1. MENSAL | 2. SEMESTRAL | 3. ANUAL");
                    out.println("Escolha o plano: ");
                    String planoStr = in.readLine();
                    try {
                        int matricula = Integer.parseInt(matriculaStr);
                        int planoOpcao = Integer.parseInt(planoStr);
                        membroController.criarMembro(clube, cpf, nome, endereco, matricula, planoOpcao, out);
                    } catch (NumberFormatException e) {
                        out.println("Entrada inválida para matrícula ou plano.");
                    }
                }
                break;
            case "2":
                membroController.listarTodosMembros(out);
                break;
            case "3":
                clube = clubeController.escolherClube(out, in);
                if (clube != null) {
                    Membro membro = membroController.escolherMembro(clube, out, in);
                    if (membro != null) {
                        out.println("Novo CPF (Enter para manter): ");
                        String cpf = in.readLine();
                        out.println("Novo Nome (Enter para manter): ");
                        String nome = in.readLine();
                        out.println("Novo Endereço (Enter para manter): ");
                        String endereco = in.readLine();
                        out.println("Nova Matrícula (0 para manter): ");
                        String matriculaStr = in.readLine();
                        out.println("Novo Plano: 1. MENSAL | 2. SEMESTRAL | 3. ANUAL | 0 para manter");
                        out.println("Escolha o plano: ");
                        String planoStr = in.readLine();
                        try {
                            int matricula = matriculaStr.isEmpty() ? 0 : Integer.parseInt(matriculaStr);
                            int planoOpcao = planoStr.isEmpty() ? 0 : Integer.parseInt(planoStr);
                            membroController.atualizarMembro(membro, cpf, nome, endereco, matricula, planoOpcao);
                            out.println("Membro atualizado com sucesso.");
                        } catch (NumberFormatException e) {
                            out.println("Entrada inválida para matrícula ou plano.");
                        }
                    }
                }
                break;
            case "4":
                clube = clubeController.escolherClube(out, in);
                if (clube != null) {
                    Membro membro = membroController.escolherMembro(clube, out, in);
                    if (membro != null) {
                        membroController.deletarMembro(clube, membro);
                        out.println("Membro deletado com sucesso.");
                    }
                }
                break;
            case "5":
                break;
            default:
                out.println("Opção inválida.");
        }
    }

    private void gerenciarFuncionarios(BufferedReader in, PrintWriter out) throws IOException {
        out.println("\nGerenciar Funcionarios:");
        out.println("1. Criar Funcionario");
        out.println("2. Listar Funcionarios");
        out.println("3. Atualizar Funcionario");
        out.println("4. Deletar Funcionario");
        out.println("5. Voltar");
        out.println("Escolha uma opção: ");
        String input = in.readLine();
        if (input == null || input.trim().equals("exit")) {
            out.println("Desconectando...");
            return;
        }
        switch (input.trim()) {
            case "1":
                Clube clube = clubeController.escolherClube(out, in);
                if (clube != null) {
                    out.println("CPF: ");
                    String cpf = in.readLine();
                    out.println("Nome: ");
                    String nome = in.readLine();
                    out.println("Endereço: ");
                    String endereco = in.readLine();
                    out.println("CTPS: ");
                    String ctps = in.readLine();
                    out.println("Salário: ");
                    String salarioStr = in.readLine();
                    try {
                        double salario = Double.parseDouble(salarioStr);
                        funcionarioController.criarFuncionario(clube, cpf, nome, endereco, ctps, salario);
                        out.println("Funcionario criado com sucesso.");
                    } catch (NumberFormatException e) {
                        out.println("Entrada inválida para salário.");
                    }
                }
                break;
            case "2":
                funcionarioController.listarTodosFuncionarios(out);
                break;
            case "3":
                clube = clubeController.escolherClube(out, in);
                if (clube != null) {
                    Funcionario funcionario = funcionarioController.escolherFuncionario(clube, out, in);
                    if (funcionario != null) {
                        out.println("Novo CPF (Enter para manter): ");
                        String cpf = in.readLine();
                        out.println("Novo Nome (Enter para manter): ");
                        String nome = in.readLine();
                        out.println("Novo Endereço (Enter para manter): ");
                        String endereco = in.readLine();
                        out.println("Nova CTPS (Enter para manter): ");
                        String ctps = in.readLine();
                        out.println("Novo Salário (0 para manter): ");
                        String salarioStr = in.readLine();
                        try {
                            double salario = salarioStr.isEmpty() ? 0 : Double.parseDouble(salarioStr);
                            funcionarioController.atualizarFuncionario(funcionario, cpf, nome, endereco, ctps, salario);
                            out.println("Funcionario atualizado com sucesso.");
                        } catch (NumberFormatException e) {
                            out.println("Entrada inválida para salário.");
                        }
                    }
                }
                break;
            case "4":
                clube = clubeController.escolherClube(out, in);
                if (clube != null) {
                    Funcionario funcionario = funcionarioController.escolherFuncionario(clube, out, in);
                    if (funcionario != null) {
                        funcionarioController.deletarFuncionario(clube, funcionario);
                        out.println("Funcionario deletado com sucesso.");
                    }
                }
                break;
            case "5":
                break;
            default:
                out.println("Opção inválida.");
        }
    }
}
package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

import controller.ClubeController;
import controller.FuncionarioController;
import controller.MembroController;

// Classe Server que escuta conexões de clientes e processa suas requisições
public class Server {
    // Constante que define a porta em que o servidor irá escutar
    private static final int PORT = 12345;

    public static void main(String[] args) {
        // Cria um ServerSocket para escutar conexões de clientes na porta definida
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            // Exibe mensagem confirmando que o servidor está ativo
            System.out.println("Servidor iniciado na porta " + PORT);

            // Loop infinito para aceitar novas conexões de clientes
            while (true) {
                // Aceita uma conexão de cliente, retornando um Socket para comunicação
                Socket socket = serverSocket.accept();
                System.out.println("Cliente conectado: " + socket.getInetAddress());

                // Cria e inicia uma nova thread para lidar com o cliente
                // A expressão lambda () -> handleClient(socket) define a tarefa da thread
                // Ela chama o método handleClient(socket) para processar as mensagens do cliente
                new Thread(() -> handleClient(socket)).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método que gerencia a comunicação com um único cliente em uma thread separada
    private static void handleClient(Socket socket) {
        // Cria BufferedReader para ler mensagens do cliente e PrintWriter para enviar respostas
        try (BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true)) {

            String mensagem;
            // Lê mensagens do cliente até a conexão ser fechada
            while ((mensagem = in.readLine()) != null) {
                System.out.println("Recebido: " + mensagem);
                // Processa a mensagem do cliente e obtém a resposta
                String resposta = processarMensagem(mensagem);
                // Envia a resposta de volta ao cliente
                out.println(resposta);
                System.out.println("Enviado: " + resposta);
            }
        } catch (IOException e) {
            System.out.println("Erro ao atender cliente: " + e.getMessage());
        }
    }

    // Processa a mensagem recebida do cliente e determina a ação a ser executada
    private static String processarMensagem(String mensagem) {
        String[] partes = mensagem.split(";");
        if (partes.length < 2) {
            return "Mensagem inválida";
        }

        String operacao = partes[0].toUpperCase();
        String entidade = partes[1].toLowerCase();

        try {
            switch (entidade) {
                case "membro":
                    return processarMembro(operacao, partes);
                case "funcionario":
                    return processarFuncionario(operacao, partes);
                case "clube":
                    return processarClube(operacao, partes);
                default:
                    return "Entidade desconhecida: " + entidade;
            }
        } catch (Exception e) {
            return "Erro: " + e.getMessage();
        }
    }

    // Processa operações relacionadas a membros
    private static String processarMembro(String operacao, String[] partes) {
        MembroController controller = MembroController.getInstance();
        if (!operacao.equals("INSERT") && !operacao.equals("UPDATE") && !operacao.equals("DELETE") && !operacao.equals("SELECT")) {
            return "Operação inválida para membro: " + operacao;
        }
        if (operacao.equals("INSERT") && partes.length != 8) {
            return "Erro: Parâmetros insuficientes para INSERT membro";
        }
        if (operacao.equals("UPDATE") && partes.length != 8) {
            return "Erro: Parâmetros insuficientes para UPDATE membro";
        }
        if (operacao.equals("DELETE") && partes.length != 3) {
            return "Erro: Parâmetros insuficientes para DELETE membro";
        }
        if (operacao.equals("SELECT") && partes.length != 2) {
            return "Erro: Parâmetros inválidos para SELECT membro";
        }

        switch (operacao) {
            case "INSERT":
                return controller.inserir(partes[2], partes[3], partes[4], partes[5], partes[6], partes[7]);
            case "UPDATE":
                return controller.atualizar(partes[2], partes[3], partes[4], partes[5], partes[6], partes[7]);
            case "DELETE":
                return controller.remover(partes[2]);
            case "SELECT":
                return controller.listar();
            default:
                return "Operação inválida para membro";
        }
    }

    // Processa operações relacionadas a funcionários
    private static String processarFuncionario(String operacao, String[] partes) {
        FuncionarioController controller = FuncionarioController.getInstance();
        if (!operacao.equals("INSERT") && !operacao.equals("UPDATE") && !operacao.equals("DELETE") && !operacao.equals("SELECT")) {
            return "Operação inválida para funcionario: " + operacao;
        }
        if (operacao.equals("INSERT") && partes.length != 8) {
            return "Erro: Parâmetros insuficientes para INSERT funcionario";
        }
        if (operacao.equals("UPDATE") && partes.length != 8) {
            return "Erro: Parâmetros insuficientes para UPDATE funcionario";
        }
        if (operacao.equals("DELETE") && partes.length != 3) {
            return "Erro: Parâmetros insuficientes para DELETE funcionario";
        }
        if (operacao.equals("SELECT") && partes.length != 2) {
            return "Erro: Parâmetros inválidos para SELECT funcionario";
        }

        switch (operacao) {
            case "INSERT":
                return controller.inserir(partes[2], partes[3], partes[4], partes[5], partes[6], partes[7]);
            case "UPDATE":
                return controller.atualizar(partes[2], partes[3], partes[4], partes[5], partes[6], partes[7]);
            case "DELETE":
                return controller.remover(partes[2]);
            case "SELECT":
                return controller.listar();
            default:
                return "Operação inválida para funcionario";
        }
    }

    // Processa operações relacionadas a clubes
    private static String processarClube(String operacao, String[] partes) {
        ClubeController controller = ClubeController.getInstance();
        if (!operacao.equals("INSERT") && !operacao.equals("UPDATE") && !operacao.equals("DELETE") && !operacao.equals("SELECT") &&
            !operacao.equals("LIST_MEMBROS") && !operacao.equals("LIST_FUNCIONARIOS")) {
            return "Operação inválida para clube: " + operacao;
        }
        if (operacao.equals("INSERT") && partes.length != 4) {
            return "Erro: Parâmetros insuficientes para INSERT clube";
        }
        if (operacao.equals("UPDATE") && partes.length != 5) {
            return "Erro: Parâmetros insuficientes para UPDATE clube";
        }
        if (operacao.equals("DELETE") && partes.length != 3) {
            return "Erro: Parâmetros insuficientes para DELETE clube";
        }
        if (operacao.equals("SELECT") && partes.length != 2) {
            return "Erro: Parâmetros inválidos para SELECT clube";
        }
        if (operacao.equals("LIST_MEMBROS") && partes.length != 3) {
            return "Erro: Parâmetros insuficientes para LIST_MEMBROS";
        }
        if (operacao.equals("LIST_FUNCIONARIOS") && partes.length != 3) {
            return "Erro: Parâmetros insuficientes para LIST_FUNCIONARIOS";
        }

        switch (operacao) {
            case "INSERT":
                return controller.inserir(partes[2], Double.parseDouble(partes[3]));
            case "UPDATE":
                return controller.atualizar(partes[2], partes[3], Double.parseDouble(partes[4]));
            case "DELETE":
                return controller.remover(partes[2]);
            case "SELECT":
                return controller.listar();
            case "LIST_MEMBROS":
                return controller.listarMembros(partes[2]);
            case "LIST_FUNCIONARIOS":
                return controller.listarFuncionarios(partes[2]);
            default:
                return "Operação inválida para clube";
        }
    }
}
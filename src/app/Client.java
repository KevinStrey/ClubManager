package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String HOST = "localhost";
    private static final int PORT = 12345;

    public static void main(String[] args) {
        // Estabelece uma conexão com o servidor usando um Socket
        try (Socket socket = new Socket(HOST, PORT);
             // Cria BufferedReader para ler respostas do servidor
             BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
             // Cria PrintWriter para enviar mensagens ao servidor, com autoFlush ativado
             PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
             Scanner sc = new Scanner(System.in)) {

            int opcao;
            do {
                System.out.println("\n===== MENU PRINCIPAL =====");
                System.out.println("1. Clube");
                System.out.println("2. Membro");
                System.out.println("3. Funcionário");
                System.out.println("0. Sair");
                System.out.print("Escolha: ");
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 1 -> menuClube(sc, out, in);
                    case 2 -> menuMembro(sc, out, in);
                    case 3 -> menuFuncionario(sc, out, in);
                }
            } while (opcao != 0);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static void menuClube(Scanner sc, PrintWriter out, BufferedReader in) throws IOException {
        System.out.println("\n--- MENU CLUBE ---");
        System.out.println("1. Inserir");
        System.out.println("2. Atualizar");
        System.out.println("3. Remover");
        System.out.println("4. Listar");
        System.out.println("5. Listar Membros");
        System.out.println("6. Listar Funcionários");
        int op = sc.nextInt();
        sc.nextLine();

        String msg = "";
        switch (op) {
            case 1 -> {
                System.out.print("Nome: ");
                String nome = sc.nextLine();
                System.out.print("Mensalidade: ");
                double mensalidade = sc.nextDouble();
                sc.nextLine();
                msg = "INSERT;clube;" + nome + ";" + mensalidade;
            }
            case 2 -> {
                System.out.print("Nome do clube: ");
                String nome = sc.nextLine();
                System.out.print("Novo nome: ");
                String novoNome = sc.nextLine();
                System.out.print("Nova mensalidade: ");
                double mensalidade = sc.nextDouble();
                sc.nextLine();
                msg = "UPDATE;clube;" + nome + ";" + novoNome + ";" + mensalidade;
            }
            case 3 -> {
                System.out.print("Nome do clube: ");
                String nome = sc.nextLine();
                msg = "DELETE;clube;" + nome;
            }
            case 4 -> msg = "SELECT;clube";
            case 5 -> {
                System.out.print("Nome do clube: ");
                String nome = sc.nextLine();
                msg = "LIST_MEMBROS;clube;" + nome;
            }
            case 6 -> {
                System.out.print("Nome do clube: ");
                String nome = sc.nextLine();
                msg = "LIST_FUNCIONARIOS;clube;" + nome;
            }
        }
        enviarMensagem(out, in, msg);
    }

    private static void menuMembro(Scanner sc, PrintWriter out, BufferedReader in) throws IOException {
        System.out.println("\n--- MENU MEMBRO ---");
        System.out.println("1. Inserir");
        System.out.println("2. Atualizar");
        System.out.println("3. Remover");
        System.out.println("4. Listar");
        int op = sc.nextInt();
        sc.nextLine();

        String msg = "";
        switch (op) {
            case 1 -> {
                System.out.print("CPF: ");
                String cpf = sc.nextLine();
                System.out.print("Nome: ");
                String nome = sc.nextLine();
                System.out.print("Endereço: ");
                String endereco = sc.nextLine();
                System.out.print("Matrícula: ");
                String matricula = sc.nextLine();
                System.out.print("Plano (MENSAL/SEMESTRAL/ANUAL): ");
                String plano = sc.nextLine();
                System.out.print("Nome do clube: ");
                String clubeNome = sc.nextLine();
                msg = "INSERT;membro;" + cpf + ";" + nome + ";" + endereco + ";" + matricula + ";" + plano + ";" + clubeNome;
            }
            case 2 -> {
                System.out.print("CPF: ");
                String cpf = sc.nextLine();
                System.out.print("Novo nome: ");
                String nome = sc.nextLine();
                System.out.print("Novo endereço: ");
                String endereco = sc.nextLine();
                System.out.print("Nova matrícula: ");
                String matricula = sc.nextLine();
                System.out.print("Novo plano (MENSAL/SEMESTRAL/ANUAL): ");
                String plano = sc.nextLine();
                System.out.print("Nome do clube: ");
                String clubeNome = sc.nextLine();
                msg = "UPDATE;membro;" + cpf + ";" + nome + ";" + endereco + ";" + matricula + ";" + plano + ";" + clubeNome;
            }
            case 3 -> {
                System.out.print("CPF: ");
                String cpf = sc.nextLine();
                msg = "DELETE;membro;" + cpf;
            }
            case 4 -> msg = "SELECT;membro";
        }
        enviarMensagem(out, in, msg);
    }

    private static void menuFuncionario(Scanner sc, PrintWriter out, BufferedReader in) throws IOException {
        System.out.println("\n--- MENU FUNCIONÁRIO ---");
        System.out.println("1. Inserir");
        System.out.println("2. Atualizar");
        System.out.println("3. Remover");
        System.out.println("4. Listar");
        int op = sc.nextInt();
        sc.nextLine();

        String msg = "";
        switch (op) {
            case 1 -> {
                System.out.print("CPF: ");
                String cpf = sc.nextLine();
                System.out.print("Nome: ");
                String nome = sc.nextLine();
                System.out.print("Endereço: ");
                String endereco = sc.nextLine();
                System.out.print("CTPS: ");
                String ctps = sc.nextLine();
                System.out.print("Salário: ");
                String salario = sc.nextLine();
                System.out.print("Nome do clube: ");
                String clubeNome = sc.nextLine();
                msg = "INSERT;funcionario;" + cpf + ";" + nome + ";" + endereco + ";" + ctps + ";" + salario + ";" + clubeNome;
            }
            case 2 -> {
                System.out.print("CPF: ");
                String cpf = sc.nextLine();
                System.out.print("Novo nome: ");
                String nome = sc.nextLine();
                System.out.print("Novo endereço: ");
                String endereco = sc.nextLine();
                System.out.print("Novo CTPS: ");
                String ctps = sc.nextLine();
                System.out.print("Novo salário: ");
                String salario = sc.nextLine();
                System.out.print("Nome do clube: ");
                String clubeNome = sc.nextLine();
                msg = "UPDATE;funcionario;" + cpf + ";" + nome + ";" + endereco + ";" + ctps + ";" + salario + ";" + clubeNome;
            }
            case 3 -> {
                System.out.print("CPF: ");
                String cpf = sc.nextLine();
                msg = "DELETE;funcionario;" + cpf;
            }
            case 4 -> msg = "SELECT;funcionario";
        }
        enviarMensagem(out, in, msg);
    }

    // Método que envia uma mensagem ao servidor e lê a resposta
    private static void enviarMensagem(PrintWriter out, BufferedReader in, String msg) throws IOException {
       
    	// Log da mensagem enviada para depuração
        System.out.println("Enviando: " + msg);
        
        // Limpa quaisquer dados obsoletos no fluxo de entrada
        while (in.ready()) {
            System.out.println("Descartando dados obsoletos: " + in.readLine());
        }
        
        // Envia a mensagem ao servidor (println() com autoFlush=true envia imediatamente)
        out.println(msg);
        
        // Lê a resposta do servidor
        StringBuilder resposta = new StringBuilder();
        
        String line;
        
        // Para INSERT, UPDATE, DELETE, espera uma única linha de resposta
        if (msg.startsWith("INSERT") || msg.startsWith("UPDATE") || msg.startsWith("DELETE")) {
            line = in.readLine();
            if (line != null) {
                resposta.append(line);
            }
        } else {
            // Para SELECT, LIST_MEMBROS, LIST_FUNCIONARIOS, lê enquanto houverem linhas disponíveis
            line = in.readLine();
            if (line != null) {
                resposta.append(line).append("\n");
                while (in.ready() && (line = in.readLine()) != null) {
                    resposta.append(line).append("\n");
                }
            }
        }
        
        // Exibe a resposta do servidor, ou mensagem padrão se não houver resposta
        String respostaFinal = resposta.length() > 0 ? resposta.toString().trim() : "Nenhuma resposta recebida";
        System.out.println("Servidor: " + respostaFinal);
    }
}
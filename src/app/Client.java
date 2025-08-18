package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;

public class Client {
    private static final String SERVER_ADDRESS = "localhost"; // Substitua por IP do servidor (ex.: "192.168.x.x") para acesso em rede
    private static final int SERVER_PORT = 12345;

    public static void main(String[] args) {
        try (
            Socket socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            Scanner scanner = new Scanner(System.in)
        ) {
            System.out.println("Conectado ao servidor em " + SERVER_ADDRESS + ":" + SERVER_PORT);
            String serverResponse;
            while ((serverResponse = in.readLine()) != null) {
                System.out.println(serverResponse);
                if (serverResponse.startsWith("Escolha uma opção:") ||
                    serverResponse.startsWith("Nome do Clube:") ||
                    serverResponse.startsWith("Valor da Mensalidade:") ||
                    serverResponse.startsWith("Novo Nome (Enter para manter):") ||
                    serverResponse.startsWith("Novo Valor Mensalidade (0 para manter):") ||
                    serverResponse.startsWith("CPF:") ||
                    serverResponse.startsWith("Nome:") ||
                    serverResponse.startsWith("Endereço:") ||
                    serverResponse.startsWith("Matrícula:") ||
                    serverResponse.startsWith("Plano: 1. MENSAL") ||
                    serverResponse.startsWith("Novo CPF (Enter para manter):") ||
                    serverResponse.startsWith("Novo Endereço (Enter para manter):") ||
                    serverResponse.startsWith("Nova Matrícula (0 para manter):") ||
                    serverResponse.startsWith("Novo Plano: 1. MENSAL") ||
                    serverResponse.startsWith("CTPS:") ||
                    serverResponse.startsWith("Salário:") ||
                    serverResponse.startsWith("Nova CTPS (Enter para manter):") ||
                    serverResponse.startsWith("Novo Salário (0 para manter):")) {
                    String input = scanner.nextLine();
                    out.println(input);
                }
                if (serverResponse.equals("Desconectando...")) {
                    break;
                }
            }
        } catch (IOException e) {
            System.err.println("Erro ao conectar ou comunicar com o servidor: " + e.getMessage());
        }
    }
}
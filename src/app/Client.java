package app;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Scanner;


public class Client {
    private static final String SERVER_ADDRESS = "192.168.1.2";
    private static final int SERVER_PORT = 65000; 

    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        scan.useDelimiter("\n"); 
        while (true) {
        	
            Socket socket = null;
            BufferedReader in = null;
            PrintWriter out = null;
            
            try {
                socket = new Socket(SERVER_ADDRESS, SERVER_PORT);
                System.out.println("Conexão estabelecida com " + SERVER_ADDRESS + ":" + SERVER_PORT);
                in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                out = new PrintWriter(socket.getOutputStream(), true); // Autoflush, como MaquinaB
                String serverResponse;
                while ((serverResponse = in.readLine()) != null) {
                    System.out.println(serverResponse);
                    if (serverResponse.startsWith("Escolha uma opção:") ||
                        serverResponse.startsWith("Nome do Clube:") ||
                        serverResponse.startsWith("Valor da Mensalidade:") ||
                        serverResponse.startsWith("CPF:") ||
                        serverResponse.startsWith("Nome:") ||
                        serverResponse.startsWith("Endereço:") ||
                        serverResponse.startsWith("Matrícula:") ||
                        serverResponse.startsWith("Plano:") ||
                        serverResponse.startsWith("Escolha o Plano: 1. MENSAL | 2. SEMESTRAL | 3. ANUAL") ||
                        serverResponse.startsWith("CTPS:") ||
                        serverResponse.startsWith("Salário:") ||
                        serverResponse.startsWith("Novo CPF") ||
                        serverResponse.startsWith("Novo Nome") ||
                        serverResponse.startsWith("Novo Endereço") ||
                        serverResponse.startsWith("Nova Matrícula") ||
                        serverResponse.startsWith("Novo Plano") ||
                        serverResponse.startsWith("Nova CTPS") ||
                        serverResponse.startsWith("Novo Salário") ||
                    	serverResponse.startsWith("Escolha o número do clube (0 para cancelar):") ||
                    	serverResponse.startsWith("Escolha o número do membro (0 para cancelar):") ||
                    	serverResponse.startsWith("Escolha o número do funcionario (0 para cancelar):") ||
                    	serverResponse.startsWith("Novo Valor Mensalidade (0 para manter)")) {
                        System.out.print("Digite: ");
                        String input = scan.nextLine();
                        out.println(input);
                        if (input.equals("exit")) { // Suporta "exit" como MaquinaB
                            break;
                        }
                    }
                    if (serverResponse.equals("Desconectando...")) {
                        break;
                    }
                }
            } catch (IOException e) {
                System.err.println("Erro ao conectar: " + e.getMessage());
            } finally {
                try {
                    if (in != null) in.close();
                    if (out != null) out.close();
                    if (socket != null && !socket.isClosed()) {
                        socket.close();
                        System.out.println("Socket encerrado.");
                    }
                } catch (IOException e) {
                    System.err.println("Erro ao fechar conexão: " + e.getMessage());
                }
            }
            System.out.println("Deseja reconectar? (s/n): ");
            if (!scan.nextLine().equalsIgnoreCase("s")) {
                break;
            }
        }
        scan.close();
        System.out.println("Conexão encerrada!");
    }
}
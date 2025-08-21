package validation;

public class ValidatorTest {
    
    public static void main(String[] args) {
        System.out.println("=== TESTE DO SISTEMA DE VALIDAÇÃO ===\n");
        
        // Teste de CPF
        testarCPF();
        
        // Teste de Nome
        testarNome();
        
        // Teste de Endereço
        testarEndereco();
        
        // Teste de Nome do Clube
        testarNomeClube();
        
        // Teste de Valores Monetários
        testarValorMonetario();
        
        // Teste de CTPS
        testarCTPS();
        
        // Teste de Matrícula
        testarMatricula();
        
        // Teste de Plano
        testarPlano();
        
        // Teste de Opções de Menu
        testarOpcaoMenu();
        
        System.out.println("\n=== FIM DOS TESTES ===");
    }
    
    private static void testarCPF() {
        System.out.println("--- Teste de CPF ---");
        
        // CPFs válidos
        testarValidacao("CPF válido 1", "123.456.789-09", Validator.validarCPF("123.456.789-09"));
        testarValidacao("CPF válido 2", "11144477735", Validator.validarCPF("11144477735"));
        
        // CPFs inválidos
        testarValidacao("CPF vazio", "", Validator.validarCPF(""));
        testarValidacao("CPF null", null, Validator.validarCPF(null));
        testarValidacao("CPF com menos dígitos", "123.456.789", Validator.validarCPF("123.456.789"));
        testarValidacao("CPF com dígitos iguais", "111.111.111-11", Validator.validarCPF("111.111.111-11"));
        testarValidacao("CPF inválido", "123.456.789-10", Validator.validarCPF("123.456.789-10"));
    }
    
    private static void testarNome() {
        System.out.println("\n--- Teste de Nome ---");
        
        // Nomes válidos
        testarValidacao("Nome válido", "João Silva", Validator.validarNome("João Silva"));
        testarValidacao("Nome com acento", "José Antônio", Validator.validarNome("José Antônio"));
        
        // Nomes inválidos
        testarValidacao("Nome vazio", "", Validator.validarNome(""));
        testarValidacao("Nome null", null, Validator.validarNome(null));
        testarValidacao("Nome muito curto", "A", Validator.validarNome("A"));
        testarValidacao("Nome com números", "João123", Validator.validarNome("João123"));
        testarValidacao("Nome com caracteres especiais", "João@Silva", Validator.validarNome("João@Silva"));
    }
    
    private static void testarEndereco() {
        System.out.println("\n--- Teste de Endereço ---");
        
        // Endereços válidos
        testarValidacao("Endereço válido", "Rua das Flores, 123", Validator.validarEndereco("Rua das Flores, 123"));
        testarValidacao("Endereço longo", "Avenida Presidente Juscelino Kubitschek, 1500, São Paulo, SP", 
                       Validator.validarEndereco("Avenida Presidente Juscelino Kubitschek, 1500, São Paulo, SP"));
        
        // Endereços inválidos
        testarValidacao("Endereço vazio", "", Validator.validarEndereco(""));
        testarValidacao("Endereço null", null, Validator.validarEndereco(null));
        testarValidacao("Endereço muito curto", "Rua", Validator.validarEndereco("Rua"));
    }
    
    private static void testarNomeClube() {
        System.out.println("\n--- Teste de Nome do Clube ---");
        
        // Nomes válidos
        testarValidacao("Nome válido", "Clube Atlético", Validator.validarNomeClube("Clube Atlético"));
        testarValidacao("Nome simples", "Fitness", Validator.validarNomeClube("Fitness"));
        
        // Nomes inválidos
        testarValidacao("Nome vazio", "", Validator.validarNomeClube(""));
        testarValidacao("Nome null", null, Validator.validarNomeClube(null));
        testarValidacao("Nome muito curto", "A", Validator.validarNomeClube("A"));
    }
    
    private static void testarValorMonetario() {
        System.out.println("\n--- Teste de Valor Monetário ---");
        
        // Valores válidos
        testarValidacao("Valor válido", "100.50", Validator.validarValorMonetario("100.50"));
        testarValidacao("Valor zero", "0", Validator.validarValorMonetario("0"));
        testarValidacao("Valor alto", "50000", Validator.validarValorMonetario("50000"));
        
        // Valores inválidos
        testarValidacao("Valor vazio", "", Validator.validarValorMonetario(""));
        testarValidacao("Valor null", null, Validator.validarValorMonetario(null));
        testarValidacao("Valor negativo", "-100", Validator.validarValorMonetario("-100"));
        testarValidacao("Valor muito alto", "2000000", Validator.validarValorMonetario("2000000"));
        testarValidacao("Valor não numérico", "abc", Validator.validarValorMonetario("abc"));
    }
    
    private static void testarCTPS() {
        System.out.println("\n--- Teste de CTPS ---");
        
        // CTPS válidos
        testarValidacao("CTPS válido 1", "12345678-9", Validator.validarCTPS("12345678-9"));
        testarValidacao("CTPS válido 2", "87654321-1", Validator.validarCTPS("87654321-1"));
        
        // CTPS inválidos
        testarValidacao("CTPS vazio", "", Validator.validarCTPS(""));
        testarValidacao("CTPS null", null, Validator.validarCTPS(null));
        testarValidacao("CTPS formato inválido", "123456-789", Validator.validarCTPS("123456-789"));
        testarValidacao("CTPS com letras", "1234567a-9", Validator.validarCTPS("1234567a-9"));
    }
    
    private static void testarMatricula() {
        System.out.println("\n--- Teste de Matrícula ---");
        
        // Matrículas válidas
        testarValidacao("Matrícula válida", "12345", Validator.validarMatricula("12345"));
        testarValidacao("Matrícula grande", "999999999", Validator.validarMatricula("999999999"));
        
        // Matrículas inválidas
        testarValidacao("Matrícula vazia", "", Validator.validarMatricula(""));
        testarValidacao("Matrícula null", null, Validator.validarMatricula(null));
        testarValidacao("Matrícula zero", "0", Validator.validarMatricula("0"));
        testarValidacao("Matrícula negativa", "-123", Validator.validarMatricula("-123"));
        testarValidacao("Matrícula muito alta", "1000000000", Validator.validarMatricula("1000000000"));
        testarValidacao("Matrícula com letras", "123a", Validator.validarMatricula("123a"));
    }
    
    private static void testarPlano() {
        System.out.println("\n--- Teste de Plano ---");
        
        // Planos válidos
        testarValidacao("Plano 1 (MENSAL)", "1", Validator.validarPlano("1"));
        testarValidacao("Plano 2 (SEMESTRAL)", "2", Validator.validarPlano("2"));
        testarValidacao("Plano 3 (ANUAL)", "3", Validator.validarPlano("3"));
        
        // Planos inválidos
        testarValidacao("Plano vazio", "", Validator.validarPlano(""));
        testarValidacao("Plano null", null, Validator.validarPlano(null));
        testarValidacao("Plano 0", "0", Validator.validarPlano("0"));
        testarValidacao("Plano 4", "4", Validator.validarPlano("4"));
        testarValidacao("Plano com letras", "a", Validator.validarPlano("a"));
    }
    
    private static void testarOpcaoMenu() {
        System.out.println("\n--- Teste de Opção de Menu ---");
        
        // Opções válidas (menu com 5 opções)
        testarValidacao("Opção 0", "0", Validator.validarOpcaoMenu("0", 5));
        testarValidacao("Opção 1", "1", Validator.validarOpcaoMenu("1", 5));
        testarValidacao("Opção 5", "5", Validator.validarOpcaoMenu("5", 5));
        
        // Opções inválidas
        testarValidacao("Opção vazia", "", Validator.validarOpcaoMenu("", 5));
        testarValidacao("Opção null", null, Validator.validarOpcaoMenu(null, 5));
        testarValidacao("Opção -1", "-1", Validator.validarOpcaoMenu("-1", 5));
        testarValidacao("Opção 6", "6", Validator.validarOpcaoMenu("6", 5));
        testarValidacao("Opção com letras", "a", Validator.validarOpcaoMenu("a", 5));
    }
    
    private static void testarValidacao(String descricao, String valor, Validator.ValidationResult resultado) {
        System.out.printf("%-30s | Valor: %-15s | Resultado: %s\n", 
                         descricao, 
                         valor == null ? "null" : "\"" + valor + "\"",
                         resultado.isValid() ? "VÁLIDO" : "INVÁLIDO - " + resultado.getMessage());
    }
}

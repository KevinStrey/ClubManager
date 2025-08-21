package validation;

import java.util.regex.Pattern;

public class Validator {
    
    // Padrão para CPF brasileiro (formato: XXX.XXX.XXX-XX ou XXXXXXXXXXX)
    private static final Pattern CPF_PATTERN = Pattern.compile("^\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}$");
    
    // Padrão para nomes (apenas letras, espaços e acentos)
    private static final Pattern NOME_PATTERN = Pattern.compile("^[a-zA-ZÀ-ÿ\\s]+$");
    
    // Padrão para CTPS (formato: XXXXXXXX-X ou XXXXXXXXX)
    private static final Pattern CTPS_PATTERN = Pattern.compile("^\\d{8}-?\\d{1}$");
    
    // Padrão para matrícula (apenas números)
    private static final Pattern MATRICULA_PATTERN = Pattern.compile("^\\d+$");
    
    /**
     * Valida CPF brasileiro
     */
    public static ValidationResult validarCPF(String cpf) {
        if (cpf == null || cpf.trim().isEmpty()) {
            return new ValidationResult(false, "CPF não pode estar vazio");
        }
        
        // Remove caracteres especiais
        String cpfLimpo = cpf.replaceAll("[^\\d]", "");
        
        if (cpfLimpo.length() != 11) {
            return new ValidationResult(false, "CPF deve ter 11 dígitos");
        }
        
        // Verifica se todos os dígitos são iguais
        if (cpfLimpo.matches("(\\d)\\1{10}")) {
            return new ValidationResult(false, "CPF inválido");
        }
        
        // Validação dos dígitos verificadores
        try {
            int soma = 0;
            for (int i = 0; i < 9; i++) {
                soma += Integer.parseInt(String.valueOf(cpfLimpo.charAt(i))) * (10 - i);
            }
            int resto = 11 - (soma % 11);
            int digito1 = (resto < 2) ? 0 : resto;
            
            soma = 0;
            for (int i = 0; i < 10; i++) {
                soma += Integer.parseInt(String.valueOf(cpfLimpo.charAt(i))) * (11 - i);
            }
            resto = 11 - (soma % 11);
            int digito2 = (resto < 2) ? 0 : resto;
            
            if (Integer.parseInt(String.valueOf(cpfLimpo.charAt(9))) != digito1 ||
                Integer.parseInt(String.valueOf(cpfLimpo.charAt(10))) != digito2) {
                return new ValidationResult(false, "CPF inválido");
            }
            
            return new ValidationResult(true, "CPF válido");
        } catch (NumberFormatException e) {
            return new ValidationResult(false, "CPF deve conter apenas números");
        }
    }
    
    /**
     * Valida nome de pessoa
     */
    public static ValidationResult validarNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return new ValidationResult(false, "Nome não pode estar vazio");
        }
        
        if (nome.trim().length() < 2) {
            return new ValidationResult(false, "Nome deve ter pelo menos 2 caracteres");
        }
        
        if (nome.trim().length() > 100) {
            return new ValidationResult(false, "Nome deve ter no máximo 100 caracteres");
        }
        
        if (!NOME_PATTERN.matcher(nome.trim()).matches()) {
            return new ValidationResult(false, "Nome deve conter apenas letras e espaços");
        }
        
        return new ValidationResult(true, "Nome válido");
    }
    
    /**
     * Valida endereço
     */
    public static ValidationResult validarEndereco(String endereco) {
        if (endereco == null || endereco.trim().isEmpty()) {
            return new ValidationResult(false, "Endereço não pode estar vazio");
        }
        
        if (endereco.trim().length() < 5) {
            return new ValidationResult(false, "Endereço deve ter pelo menos 5 caracteres");
        }
        
        if (endereco.trim().length() > 200) {
            return new ValidationResult(false, "Endereço deve ter no máximo 200 caracteres");
        }
        
        return new ValidationResult(true, "Endereço válido");
    }
    
    /**
     * Valida nome do clube
     */
    public static ValidationResult validarNomeClube(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            return new ValidationResult(false, "Nome do clube não pode estar vazio");
        }
        
        if (nome.trim().length() < 2) {
            return new ValidationResult(false, "Nome do clube deve ter pelo menos 2 caracteres");
        }
        
        if (nome.trim().length() > 50) {
            return new ValidationResult(false, "Nome do clube deve ter no máximo 50 caracteres");
        }
        
        return new ValidationResult(true, "Nome do clube válido");
    }
    
    /**
     * Valida valor monetário (mensalidade, salário)
     */
    public static ValidationResult validarValorMonetario(String valorStr) {
        if (valorStr == null || valorStr.trim().isEmpty()) {
            return new ValidationResult(false, "Valor não pode estar vazio");
        }
        
        try {
            double valor = Double.parseDouble(valorStr.trim());
            
            if (valor < 0) {
                return new ValidationResult(false, "Valor não pode ser negativo");
            }
            
            if (valor > 1000000) {
                return new ValidationResult(false, "Valor muito alto (máximo: R$ 1.000.000,00)");
            }
            
            return new ValidationResult(true, "Valor válido");
        } catch (NumberFormatException e) {
            return new ValidationResult(false, "Valor deve ser um número válido");
        }
    }
    
    /**
     * Valida CTPS
     */
    public static ValidationResult validarCTPS(String ctps) {
        if (ctps == null || ctps.trim().isEmpty()) {
            return new ValidationResult(false, "CTPS não pode estar vazio");
        }
        
        if (!CTPS_PATTERN.matcher(ctps.trim()).matches()) {
            return new ValidationResult(false, "CTPS deve ter o formato XXXXXXXX-X");
        }
        
        return new ValidationResult(true, "CTPS válido");
    }
    
    /**
     * Valida matrícula
     */
    public static ValidationResult validarMatricula(String matriculaStr) {
        if (matriculaStr == null || matriculaStr.trim().isEmpty()) {
            return new ValidationResult(false, "Matrícula não pode estar vazia");
        }
        
        if (!MATRICULA_PATTERN.matcher(matriculaStr.trim()).matches()) {
            return new ValidationResult(false, "Matrícula deve conter apenas números");
        }
        
        try {
            int matricula = Integer.parseInt(matriculaStr.trim());
            
            if (matricula <= 0) {
                return new ValidationResult(false, "Matrícula deve ser um número positivo");
            }
            
            if (matricula > 999999999) {
                return new ValidationResult(false, "Matrícula muito alta");
            }
            
            return new ValidationResult(true, "Matrícula válida");
        } catch (NumberFormatException e) {
            return new ValidationResult(false, "Matrícula deve ser um número válido");
        }
    }
    
    /**
     * Valida opção de plano
     */
    public static ValidationResult validarPlano(String planoStr) {
        if (planoStr == null || planoStr.trim().isEmpty()) {
            return new ValidationResult(false, "Plano não pode estar vazio");
        }
        
        try {
            int plano = Integer.parseInt(planoStr.trim());
            
            if (plano < 1 || plano > 3) {
                return new ValidationResult(false, "Plano deve ser 1 (MENSAL), 2 (SEMESTRAL) ou 3 (ANUAL)");
            }
            
            return new ValidationResult(true, "Plano válido");
        } catch (NumberFormatException e) {
            return new ValidationResult(false, "Plano deve ser um número válido");
        }
    }
    
    /**
     * Valida opção de menu
     */
    public static ValidationResult validarOpcaoMenu(String opcaoStr, int maxOpcoes) {
        if (opcaoStr == null || opcaoStr.trim().isEmpty()) {
            return new ValidationResult(false, "Opção não pode estar vazia");
        }
        
        try {
            int opcao = Integer.parseInt(opcaoStr.trim());
            
            if (opcao < 0 || opcao > maxOpcoes) {
                return new ValidationResult(false, "Opção deve estar entre 0 e " + maxOpcoes);
            }
            
            return new ValidationResult(true, "Opção válida");
        } catch (NumberFormatException e) {
            return new ValidationResult(false, "Opção deve ser um número válido");
        }
    }
    
    /**
     * Classe para armazenar o resultado da validação
     */
    public static class ValidationResult {
        private final boolean isValid;
        private final String message;
        
        public ValidationResult(boolean isValid, String message) {
            this.isValid = isValid;
            this.message = message;
        }
        
        public boolean isValid() {
            return isValid;
        }
        
        public String getMessage() {
            return message;
        }
    }
}

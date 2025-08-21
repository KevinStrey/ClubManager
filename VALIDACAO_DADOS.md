# Sistema de Validação de Dados - ClubManager

## Visão Geral

Este documento descreve o sistema de validação implementado para garantir a integridade e qualidade dos dados enviados do Cliente para o Servidor no sistema ClubManager.

## Estrutura de Validação

### Classe Validator (`src/validation/Validator.java`)

A classe `Validator` contém métodos estáticos para validar diferentes tipos de dados:

#### Validações Implementadas

1. **CPF (Cadastro de Pessoa Física)**
   - Validação completa do algoritmo brasileiro de CPF
   - Aceita formatos: XXX.XXX.XXX-XX ou XXXXXXXXXXX
   - Verifica dígitos verificadores
   - Rejeita CPFs com todos os dígitos iguais

2. **Nome**
   - Mínimo: 2 caracteres
   - Máximo: 100 caracteres
   - Apenas letras, espaços e acentos
   - Não pode estar vazio

3. **Endereço**
   - Mínimo: 5 caracteres
   - Máximo: 200 caracteres
   - Não pode estar vazio

4. **Nome do Clube**
   - Mínimo: 2 caracteres
   - Máximo: 50 caracteres
   - Não pode estar vazio

5. **Valores Monetários (Mensalidade, Salário)**
   - Deve ser um número válido
   - Não pode ser negativo
   - Máximo: R$ 1.000.000,00
   - Não pode estar vazio

6. **CTPS (Carteira de Trabalho e Previdência Social)**
   - Formato: XXXXXXXX-X ou XXXXXXXXX
   - Não pode estar vazio

7. **Matrícula**
   - Apenas números
   - Deve ser positivo
   - Máximo: 999.999.999
   - Não pode estar vazio

8. **Plano**
   - Deve ser 1 (MENSAL), 2 (SEMESTRAL) ou 3 (ANUAL)
   - Não pode estar vazio

9. **Opções de Menu**
   - Deve ser um número válido
   - Deve estar dentro do intervalo permitido (0 a maxOpcoes)

## Integração no Servidor

### Modificações no `Server.java`

O servidor foi modificado para incluir validações em todos os pontos onde dados são recebidos do cliente:

#### 1. Menu Principal
- Validação das opções de menu (1-4)

#### 2. Gerenciamento de Clubes
- **Criar Clube**: Validação de nome e valor da mensalidade
- **Atualizar Clube**: Validação condicional (apenas se novos valores forem fornecidos)
- **Opções de menu**: Validação das opções (1-7)

#### 3. Gerenciamento de Membros
- **Criar Membro**: Validação de CPF, nome, endereço, matrícula e plano
- **Atualizar Membro**: Validação condicional para todos os campos
- **Opções de menu**: Validação das opções (1-5)

#### 4. Gerenciamento de Funcionários
- **Criar Funcionário**: Validação de CPF, nome, endereço, CTPS e salário
- **Atualizar Funcionário**: Validação condicional para todos os campos
- **Opções de menu**: Validação das opções (1-5)

## Fluxo de Validação

1. **Recebimento de Dados**: O servidor recebe dados do cliente
2. **Validação**: Os dados são validados usando os métodos da classe `Validator`
3. **Resultado**: Se a validação falhar, uma mensagem de erro é enviada ao cliente
4. **Processamento**: Se a validação passar, os dados são processados normalmente

## Mensagens de Erro

O sistema fornece mensagens de erro específicas e informativas:

- **CPF**: "CPF deve ter 11 dígitos", "CPF inválido", etc.
- **Nome**: "Nome deve ter pelo menos 2 caracteres", "Nome deve conter apenas letras e espaços", etc.
- **Valores**: "Valor não pode ser negativo", "Valor deve ser um número válido", etc.
- **Opções**: "Opção deve estar entre 0 e X", "Opção deve ser um número válido", etc.

## Testes Realizados

### Classe de Teste (`src/validation/ValidatorTest.java`)

Foi criada uma classe de teste completa que demonstra todas as validações implementadas:

#### Resultados dos Testes:

✅ **CPF**: Validação correta de CPFs válidos e inválidos
✅ **Nome**: Validação de nomes com e sem acentos, rejeição de caracteres especiais
✅ **Endereço**: Validação de endereços de diferentes tamanhos
✅ **Nome do Clube**: Validação de nomes de clubes
✅ **Valores Monetários**: Validação de valores positivos, negativos e limites
✅ **CTPS**: Validação do formato correto da carteira de trabalho
✅ **Matrícula**: Validação de números positivos e limites
✅ **Plano**: Validação das opções 1, 2 e 3
✅ **Opções de Menu**: Validação de intervalos numéricos

### Execução dos Testes

```bash
javac -cp src src/validation/ValidatorTest.java
java -cp src validation.ValidatorTest
```

## Benefícios da Implementação

1. **Integridade dos Dados**: Garante que apenas dados válidos sejam processados
2. **Experiência do Usuário**: Fornece feedback claro sobre erros de entrada
3. **Segurança**: Previne entrada de dados maliciosos ou inválidos
4. **Manutenibilidade**: Código centralizado e reutilizável para validações
5. **Consistência**: Padrão uniforme de validação em todo o sistema

## Exemplo de Uso

```java
// Validação de CPF
ValidationResult cpfResult = Validator.validarCPF(cpf);
if (!cpfResult.isValid()) {
    out.println("Erro: " + cpfResult.getMessage());
    break;
}

// Validação de valor monetário
ValidationResult valorResult = Validator.validarValorMonetario(valorStr);
if (!valorResult.isValid()) {
    out.println("Erro: " + valorResult.getMessage());
    break;
}
```

## Considerações Técnicas

- **Performance**: Validações são rápidas e eficientes
- **Flexibilidade**: Fácil adição de novas validações
- **Reutilização**: Métodos podem ser usados em outras partes do sistema
- **Manutenção**: Mudanças nas regras de validação são centralizadas

## Arquivos Criados/Modificados

1. **`src/validation/Validator.java`** - Classe principal de validação (NOVO)
2. **`src/validation/ValidatorTest.java`** - Classe de testes (NOVO)
3. **`src/app/Server.java`** - Servidor com validações integradas (MODIFICADO)
4. **`VALIDACAO_DADOS.md`** - Documentação do sistema (NOVO)

## Status da Implementação

✅ **COMPLETO** - Sistema de validação totalmente implementado e testado
✅ **FUNCIONAL** - Todas as validações estão operacionais
✅ **DOCUMENTADO** - Documentação completa disponível
✅ **TESTADO** - Testes automatizados validam todas as funcionalidades

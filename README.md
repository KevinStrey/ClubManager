# Sistema de Gerenciamento de Clubes

## Descrição
Sistema cliente-servidor em Java para gerenciamento de clubes, membros e funcionários, utilizando sockets para comunicação na porta 65000. Suporta operações CRUD (criar, listar, atualizar, deletar) com validação de entradas.

## Estrutura
- **Servidor**: Gerencia conexões via `ServerSocket`, processa requisições com controladores (`ClubeController`, `MembroController`, `FuncionarioController`).
- **Cliente**: Interface de texto que se conecta ao servidor via socket, enviando comandos e exibindo respostas.
- **Validação**: Classe `Validator` garante entradas válidas (CPF, nome, valores monetários).

## Funcionalidades
- **Clubes**: Criar, listar, atualizar, deletar clubes; listar membros e funcionários por clube.
- **Membros**: Criar, listar, atualizar, deletar membros com CPF, nome, endereço, matrícula e plano (mensal, semestral, anual).
- **Funcionários**: Criar, listar, atualizar, deletar funcionários com CPF, nome, endereço, CTPS e salário.
- **Comunicação**: Socket na porta 65000 com reutilização (`setReuseAddress(true)`) e autoflush para respostas imediatas.

## Requisitos
- Java 8 ou superior.
- Compilador Java (javac) e JVM para executar.

## Como Executar
1. **Compilar**:
   ```bash
   javac -d bin src/app/*.java
   ```
2. **Executar Servidor**:
   ```bash
   java -cp bin app.Server
   ```
3. **Executar Cliente**:
   ```bash
   java -cp bin app.Client
   ```
4. No cliente, siga o menu interativo. Digite "exit" ou escolha a opção 4 para desconectar.

## Notas
- O servidor suporta múltiplas conexões em sequência, com tratamento de erros para falhas de rede.
- O cliente permite reconexões para novas operações (opção "s" após desconexão).
- Arquitetura modular facilita manutenção e extensibilidade.

## Limitações
- Conexões são tratadas sequencialmente; para maior escalabilidade, considere threads.
- Interface limitada a texto; pode ser estendida para GUI.

# Analisador Léxico para Mini-Pascal em Java

Este é um analisador léxico para a linguagem de programação **Mini-Pascal**, implementado em **Java**. Ele lê um arquivo de código-fonte e gera um arquivo de saída contendo a lista de tokens reconhecidos.

## Como Executar

**Pré-requisitos:** Certifique-se de ter o **JDK (Java Development Kit)** instalado em seu sistema.

### Compilação

Abra um terminal na pasta onde os arquivos estão salvos e execute:

```sh
javac analisador_lexico.java
```

Isso criará o arquivo `analisador_lexico.class`.

### Execução

Para executar o analisador, use:

```sh
java analisador_lexico exemplo_entrada.txt
```

Se nenhum nome de arquivo de entrada for fornecido, o programa tentará usar `exemplo_entrada.txt` por padrão.

### Saída

Após a execução, será gerado o arquivo `saida_lexica.txt`, contendo a saída do analisador, com cada linha no formato `<lexema> <token>`.

**Exemplo de Saída (`saida_lexica.txt`):**

```
program PALAVRA_RESERVADA
teste IDENTIFICADOR
...
```

## Estrutura do Código

O código em `analisador_lexico.java` simula um **autômato finito determinístico (AFD)** de forma procedural. O fluxo principal:

- Método `analisar(String codigoFonte)`:
  - Percorre o `codigoFonte` caractere por caractere.
  - Aplica regras para identificar e  agrupar caracteres em **lexemas**.
  - Determina o **token** correspondente para cada lexema, utilizando `HashMap`s para as tabelas de palavras reservadas e símbolos especiais.
  - Ignora espaços e comentários.
  - Identifica números, identificadores, strings, caracteres e símbolos.
  - Emite um `ERRO_LEXICO` para caracteres desconhecidos ou erros de formatação.

### Tabela de Palavras Reservadas

Utiliza-se um `HashMap` para armazenar as palavras reservadas, garantindo busca eficiente (**O(1)**).

### Detecção de Erros

- O analisador detecta símbolos desconhecidos e emite um token `ERRO_LEXICO`.
- Também lida com erros como comentários de bloco ou strings não fechadas.

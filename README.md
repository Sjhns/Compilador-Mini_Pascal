# Projeto de Compiladores - Mini_Pascal

## Dados do Projeto

- **Instituição:** Universidade Estadual do Sudoeste da Bahia (UESB)
- **Disciplina:** Compiladores
- **Professor:** José Carlos Martins Oliveira
- **Curso:** Ciência da Computação
- **Período:** 2025.2

## Introdução

Este repositório contém a implementação do primeiro trabalho prático da disciplina de Compiladores, focado na **Análise Léxica** para a linguagem simplificada **Mini_Pascal**. O objetivo principal desta etapa é construir um analisador léxico capaz de ler um código-fonte na linguagem especificada e gerar um arquivo de saída contendo a lista de tokens reconhecidos.

> Este projeto é uma solução parcial do compilador completo, que será construído e expandido nas próximas unidades para incluir as fases de **Análise Sintática** e **Análise Semântica**.

## Funcionalidades Implementadas (I Unidade)

- **Analisador Léxico:** Implementação baseada em um autômato finito determinístico (AFD) para reconhecer os itens léxicos do Mini_Pascal.
- **Identificação de Tokens:** O analisador identifica e classifica os seguintes tipos de tokens:
  - Palavras Reservadas
  - Identificadores
  - Números Inteiros e Reais
  - Operadores Aritméticos, Relacionais e Lógicos
  - Símbolos Especiais, de Atribuição e Fim de Programa
- **Tratamento de Caracteres Não Significativos:** Espaços, tabulações, quebras de linha e comentários de bloco (`/* ... */`) são desconsiderados.
- **Tratamento de Erros Léxicos:** Detecção e reporte de símbolos desconhecidos, comentários e strings não fechadas.
- **Estruturas de Dados Auxiliares:** Utilização de um `HashMap` para armazenar as palavras reservadas, garantindo busca eficiente (**O(1)**).
- **Entrada e Saída:** O programa lê um arquivo de entrada `.txt` e produz um arquivo de saída `.txt` com o par `<lexema, token>` por linha.

## Decisões de Projeto e Implementação

- **Linguagem de Implementação:** Java, conforme especificação do projeto.
- **Tokens de Palavras Reservadas:** A saída utiliza o próprio nome da palavra como lexema, seguido do token `PALAVRA_RESERVADA` (exemplo: `<program, PALAVRA_RESERVADA>`).
- **Analisador Léxico como Função:** Implementado em método estático, pronto para integração ao futuro analisador sintático.

## Próximos Passos

- **Analisador Sintático:** Implementar usando técnica SLR(1).
- **Analisador Semântico:** Adicionar verificação de coerência e significado do código.
- **Geração de Código:** Finalizar com geração de código para um alvo específico.

## Como Executar

Para compilar e executar o analisador léxico, siga as instruções detalhadas no arquivo `analisador_lexico/README.md`.

package analisador_lexico;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AnalisadorLexico {

    private static final String PALAVRA_RESERVADA = "PALAVRA_RESERVADA";
    private static final String OPERADOR_ARITMETICO = "OPERADOR_ARITMETICO";
    private static final String OPERADOR_LOGICO = "OPERADOR_LOGICO";
    private static final String OPERADOR_RELACIONAL = "OPERADOR_RELACIONAL";
    private static final String SIMBOLO_ESPECIAL = "SIMBOLO_ESPECIAL";
    private static final String ATRIBUICAO = "ATRIBUICAO";
    private static final String FIM = "FIM";
    private static final Map<String, String> PALAVRAS_RESERVADAS = new HashMap<>();
    static {
        PALAVRAS_RESERVADAS.put("ABSOLUTE", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("ARRAY", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("BEGIN", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("CASE", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("CHAR", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("CONST", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("DIV", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("DO", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("DOWTO", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("ELSE", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("END", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("EXTERNAL", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("FILE", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("FOR", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("FORWARD", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("FUNC", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("FUNCTION", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("GOTO", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("IF", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("IMPLEMENTATION", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("INTEGER", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("INTERFACE", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("INTERRUPT", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("LABEL", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("MAIN", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("NIL", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("NIT", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("OF", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("PACKED", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("PROC", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("PROGRAM", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("REAL", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("RECORD", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("REPEAT", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("SET", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("SHL", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("SHR", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("STRING", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("THEN", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("TO", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("TYPE", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("UNIT", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("UNTIL", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("USES", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("VAR", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("WHILE", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("WITH", PALAVRA_RESERVADA);
        PALAVRAS_RESERVADAS.put("XOR", PALAVRA_RESERVADA);

        // Operadores lógicos e aritméticos que são palavras
        PALAVRAS_RESERVADAS.put("mod", OPERADOR_ARITMETICO);
        PALAVRAS_RESERVADAS.put("and", OPERADOR_LOGICO);
        PALAVRAS_RESERVADAS.put("or", OPERADOR_LOGICO);
        PALAVRAS_RESERVADAS.put("not", OPERADOR_LOGICO);
    }


    private static final Map<String, String> TOKEN_MAP = new HashMap<>();
    static {
        TOKEN_MAP.put("(", SIMBOLO_ESPECIAL);
        TOKEN_MAP.put(")", SIMBOLO_ESPECIAL);
        TOKEN_MAP.put(",", SIMBOLO_ESPECIAL);
        TOKEN_MAP.put(":", SIMBOLO_ESPECIAL);
        TOKEN_MAP.put(";", SIMBOLO_ESPECIAL);
        TOKEN_MAP.put(":=", ATRIBUICAO);
        TOKEN_MAP.put("=", OPERADOR_RELACIONAL);
        TOKEN_MAP.put(">=", OPERADOR_RELACIONAL);
        TOKEN_MAP.put("<=", OPERADOR_RELACIONAL);
        TOKEN_MAP.put(">", OPERADOR_RELACIONAL);
        TOKEN_MAP.put("<", OPERADOR_RELACIONAL);
        TOKEN_MAP.put("<>", OPERADOR_RELACIONAL);
        TOKEN_MAP.put("+", OPERADOR_ARITMETICO);
        TOKEN_MAP.put("-", OPERADOR_ARITMETICO);
        TOKEN_MAP.put("*", OPERADOR_ARITMETICO);
        TOKEN_MAP.put("/", OPERADOR_ARITMETICO);
        TOKEN_MAP.put(".", FIM);
    }

    public static List<String[]> analisar(String codigoFonte) {
        List<String[]> tokensEncontrados = new ArrayList<>();
        int i = 0;
        int linha = 1;

        while (i < codigoFonte.length()) {
            char caractereAtual = codigoFonte.charAt(i);

            if (isWhitespace(caractereAtual)) {
                linha = handleWhitespace(codigoFonte, i, linha);
                i++;
                continue;
            }

            if (isCommentStart(codigoFonte, i)) {
                int result = handleComment(codigoFonte, i, linha, tokensEncontrados);
                if (result == -1) break;
                i = result;
                continue;
            }

            if (isStringOrChar(caractereAtual)) {
                int result = handleStringOrChar(codigoFonte, i, linha, tokensEncontrados);
                if (result == -1) break;
                i = result;
                continue;
            }

            if (Character.isLetter(caractereAtual)) {
                i = handleIdentifier(codigoFonte, i, tokensEncontrados);
                continue;
            }

            if (Character.isDigit(caractereAtual)) {
                i = handleNumber(codigoFonte, i, linha, tokensEncontrados);
                continue;
            }

            int opResult = handleOperatorOrSymbol(codigoFonte, i, tokensEncontrados);
            if (opResult > i) {
                i = opResult;
                continue;
            }

            handleError(caractereAtual, linha, tokensEncontrados);
            i++;
        }

        return tokensEncontrados;
    }

    private static boolean isWhitespace(char c) {
        return Character.isWhitespace(c);
    }

    private static int handleWhitespace(String codigoFonte, int i, int linha) {
        if (codigoFonte.charAt(i) == '\n') {
            linha++;
        }
        return linha;
    }

    private static boolean isCommentStart(String codigoFonte, int i) {
        return codigoFonte.charAt(i) == '/' && i + 1 < codigoFonte.length() && codigoFonte.charAt(i + 1) == '*';
    }

   private static int handleComment(String codigoFonte, int i, int linha, List<String[]> tokensEncontrados) {
        int endCommentIndex = codigoFonte.indexOf("*/", i + 2);
        if (endCommentIndex != -1) {
            return endCommentIndex + 2;
        } else {
            tokensEncontrados.add(new String[] {
                    String.format("Erro na linha %d: Comentário não fechado", linha), "ERRO_LEXICO"
            });
            return -1;
        }
    }

    private static boolean isStringOrChar(char c) {
        return c == '"' || c == '\'';
    }

    private static int handleStringOrChar(String codigoFonte, int i, int linha, List<String[]> tokensEncontrados) {
        char delimitador = codigoFonte.charAt(i);
        int inicio = i + 1;
        i++;
        try {
            while (i < codigoFonte.length() && codigoFonte.charAt(i) != delimitador
                    && codigoFonte.charAt(i) != '\n') {
                i++;
            }
            if (i >= codigoFonte.length() || codigoFonte.charAt(i) == '\n') {
                tokensEncontrados.add(new String[] {
                        String.format("Erro na linha %d: Delimitador de string/char não encontrado", linha),
                        "ERRO_LEXICO"
                });
                return -1;
            }
            String lexema = codigoFonte.substring(inicio, i);
            String tokenTipo = (delimitador == '"') ? "CONSTANTE_STRING" : "CONSTANTE_CHAR";
            tokensEncontrados.add(new String[] { lexema, tokenTipo });
            i++;
        } catch (StringIndexOutOfBoundsException e) {
            tokensEncontrados.add(new String[] {
                    String.format("Erro na linha %d: Delimitador de string/char não encontrado", linha),
                    "ERRO_LEXICO"
            });
            return -1;
        }
        return i;
    }

    private static int handleIdentifier(String codigoFonte, int i, List<String[]> tokensEncontrados) {
        StringBuilder lexema = new StringBuilder();
        while (i < codigoFonte.length()
                && (Character.isLetterOrDigit(codigoFonte.charAt(i)) || codigoFonte.charAt(i) == '_')) {
            lexema.append(codigoFonte.charAt(i));
            i++;
        }
        String lexemaStr = lexema.toString();
        String tokenTipo = PALAVRAS_RESERVADAS.getOrDefault(lexemaStr.toUpperCase(), "IDENTIFICADOR");
        tokensEncontrados.add(new String[] { lexemaStr, tokenTipo });
        return i;
    }

    private static int handleNumber(String codigoFonte, int i, int linha, List<String[]> tokensEncontrados) {
        StringBuilder lexema = new StringBuilder();
        boolean isReal = false;
        while (i < codigoFonte.length()
                && (Character.isDigit(codigoFonte.charAt(i)) || codigoFonte.charAt(i) == '.')) {
            if (codigoFonte.charAt(i) == '.') {
                if (isReal) {
                    tokensEncontrados.add(new String[] {
                            String.format("Erro na linha %d: Número real mal formatado", linha), "ERRO_LEXICO"
                    });
                    while (i < codigoFonte.length() && !Character.isWhitespace(codigoFonte.charAt(i))) {
                        i++;
                    }
                    return i;
                }
                isReal = true;
            }
            lexema.append(codigoFonte.charAt(i));
            i++;
        }
        String lexemaStr = lexema.toString();
        if (isReal) {
            tokensEncontrados.add(new String[] { lexemaStr, "NUMERO_REAL" });
        } else {
            tokensEncontrados.add(new String[] { lexemaStr, "NUMERO_INTEIRO" });
        }
        return i;
    }

    private static int handleOperatorOrSymbol(String codigoFonte, int i, List<String[]> tokensEncontrados) {
        String tokenDuplo = "";
        if (i + 1 < codigoFonte.length()) {
            tokenDuplo = codigoFonte.substring(i, i + 2);
        }
        if (TOKEN_MAP.containsKey(tokenDuplo)) {
            tokensEncontrados.add(new String[] { tokenDuplo, TOKEN_MAP.get(tokenDuplo) });
            return i + 2;
        }

        String caractereStr = String.valueOf(codigoFonte.charAt(i));
        if (TOKEN_MAP.containsKey(caractereStr)) {
            tokensEncontrados.add(new String[] { caractereStr, TOKEN_MAP.get(caractereStr) });
            return i + 1;
        }
        return i;
    }

    private static void handleError(char caractereAtual, int linha, List<String[]> tokensEncontrados) {
        tokensEncontrados.add(new String[] {
                String.format("Símbolo desconhecido '%c' na linha %d", caractereAtual, linha), "ERRO_LEXICO"
        });
    }

    public static void main(String[] args) {
        String inputFileName = "exemplo_entrada.txt";
        String outputFileName = "saida_lexica.txt";

        if (args.length > 0) {
            inputFileName = args[0];
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(inputFileName))) {
            StringBuilder codigoFonteBuilder = new StringBuilder();
            String linha;
            while ((linha = reader.readLine()) != null) {
                codigoFonteBuilder.append(linha).append("\n");
            }
            String codigoFonte = codigoFonteBuilder.toString();

            List<String[]> tokens = analisar(codigoFonte);

            try (BufferedWriter writer = new BufferedWriter(new FileWriter(outputFileName))) {
                for (String[] token : tokens) {
                    writer.write(token[0] + " " + token[1] + "\n");
                }
                System.out.println("Análise léxica concluída. Tokens salvos em '" + outputFileName + "'.");
            } catch (IOException e) {
                System.err.println("Erro ao escrever no arquivo de saída: " + e.getMessage());
            }

        } catch (FileNotFoundException e) {
            System.err.println("Erro: O arquivo de entrada '" + inputFileName + "' não foi encontrado.");
        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo de entrada: " + e.getMessage());
        }
    }
}

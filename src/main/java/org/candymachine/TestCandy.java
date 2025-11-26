package org.candymachine;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import java_cup.runtime.Symbol;
import parserpack.sym;

public class TestCandy {

    public static void main(String[] args) {
        try {
            // 1. A expressão de teste (Ex: Doce 10A, mais, Doce 5B)
            String expr = "10A + 5B + 2C";

            System.out.println("Analizando: " + expr);

            ByteArrayInputStream stream = new ByteArrayInputStream(
                    expr.getBytes(StandardCharsets.UTF_8)
            );

            Scanner lexer = new Scanner(stream);

            Symbol simbolo;
            do {
                simbolo = lexer.next_token();
                printToken(simbolo);

            } while (simbolo.sym != sym.EOF);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (Exception e) {
            System.err.println("Erro na análise: " + e.getMessage());
        }
    }

    // Método auxiliar apenas para imprimir bonito no console
    private static void printToken(Symbol s) {
        String nomeToken = "DESCONHECIDO";

        if (s.sym == sym.DOCE) nomeToken = "DOCE";
        else if (s.sym == sym.INTEIRO) nomeToken = "INTEIRO";
        else if (s.sym == sym.MAIS) nomeToken = "MAIS";
        else if (s.sym == sym.EOF) nomeToken = "EOF";
        // ... outros casos

        System.out.print("ID: " + s.sym + " (" + nomeToken + ")");

        if (s.value != null) {
            if (s.sym == sym.DOCE) {
                Object[] dados = (Object[]) s.value;
                System.out.print(" | Valor: " + dados[0] + ", Tipo: " + dados[1]);
            } else {
                System.out.print(" | Valor: " + s.value);
            }
        }
        System.out.println();
    }
}
package org.candymachine;

import java_cup.runtime.Symbol;
import parserpack.sym;

%%

%class Scanner
%unicode
%cup
%line
%column
%public

%eofval{
    return new Symbol(sym.EOF);
%eofval}

%{
    // Construtor para entrada UTF-8
    public Scanner(java.io.InputStream in) {
        this(new java.io.InputStreamReader(in, java.nio.charset.Charset.forName("UTF-8")));
    }
%}

/* =============================
   DEFINIÇÕES DE PADRÕES
   ============================= */

digit      = [0-9]
letter     = [A-Za-z]
inteiro    = {digit}+
doce       = {inteiro}{letter}
espaco     = [ \t\r\n]+

%%

/* =============================
   TOKENS
   ============================= */

/* DOCE: exemplo → 55A, 10B, 3C */
{doce} {
    String txt = yytext();
    int valor = Integer.parseInt(txt.substring(0, txt.length() - 1));
    char tipo = txt.charAt(txt.length() - 1);

    return new Symbol(
        sym.DOCE,
        yyline,
        yycolumn,
        new Object[]{valor, tipo}
    );
}

/* Números isolados */
{inteiro} {
    return new Symbol(
        sym.INTEIRO,
        yyline,
        yycolumn,
        Integer.parseInt(yytext())
    );
}

/* Letras isoladas */
{letter} {
    return new Symbol(
        sym.LETRA,
        yyline,
        yycolumn,
        yytext()
    );
}

/* Operadores */
"+"    { return new Symbol(sym.MAIS, yyline, yycolumn); }
"-"    { return new Symbol(sym.MENOS, yyline, yycolumn); }
"*"    { return new Symbol(sym.MULT, yyline, yycolumn); }
"/"    { return new Symbol(sym.DIV, yyline, yycolumn); }

/* Espaços → ignorar */
{espaco} { /* ignora */ }

/* Qualquer outra coisa → inválido */
. {
    return new Symbol(sym.INVALIDO, yyline, yycolumn, yytext());
}

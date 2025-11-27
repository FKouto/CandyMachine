package scanner;

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

/* Palavras reservadas */
"if"        { return new Symbol(sym.IF, yyline, yycolumn); }
"while"     { return new Symbol(sym.WHILE, yyline, yycolumn); }

/* Delimitadores de bloco */
"{"         { return new Symbol(sym.ABRE_CHAVE, yyline, yycolumn); }
"}"         { return new Symbol(sym.FECHA_CHAVE, yyline, yycolumn); }

/* Parênteses */
"("         { return new Symbol(sym.ABRE_PAR, yyline, yycolumn); }
")"         { return new Symbol(sym.FECHA_PAR, yyline, yycolumn); }

/* Ponto e vírgula */
";"         { return new Symbol(sym.PONTO_VIRGULA, yyline, yycolumn); }

/* Operadores relacionais */
">="        { return new Symbol(sym.MAIOR_IGUAL, yyline, yycolumn); }
"<="        { return new Symbol(sym.MENOR_IGUAL, yyline, yycolumn); }
"=="        { return new Symbol(sym.IGUAL_IGUAL, yyline, yycolumn); }
"!="        { return new Symbol(sym.DIFERENTE, yyline, yycolumn); }
">"         { return new Symbol(sym.MAIOR, yyline, yycolumn); }
"<"         { return new Symbol(sym.MENOR, yyline, yycolumn); }

/* Operadores incremento/decremento */
"++"        { return new Symbol(sym.INC, yyline, yycolumn); }
"--"        { return new Symbol(sym.DEC, yyline, yycolumn); }

/* DOCE → ex.: 55A, 10B, 3C */
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

/* Operadores aritméticos */
"+"    { return new Symbol(sym.MAIS, yyline, yycolumn); }
"-"    { return new Symbol(sym.MENOS, yyline, yycolumn); }

/* Espaços → ignorar */
{espaco} { /* ignora */ }

/* Qualquer outra coisa → inválido */
. {
    return new Symbol(sym.INVALIDO, yyline, yycolumn, yytext());
}

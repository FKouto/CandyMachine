# CandyMachine

- Projeto que implementa um scanner (JFlex) e um parser (CUP) para uma linguagem simples com expressões aritméticas, operadores relacionais, incremento/decremento e um token especial `DOCE` (ex.: `55A`).
- Estrutura do projeto segue `src/main/java` e contém as especificações JFlex/CUP e os arquivos gerados.

Estrutura de pastas (principal)
- `src/main/java/parserpack/ParserGenerator.java` — utilitário para gerar o parser a partir de `Parse.cup`.
- `src/main/java/parserpack/sym.java` — constantes de símbolos geradas pelo CUP.
- `src/main/java/scanner/Scanner.flex` — especificação JFlex (fonte).
- `src/main/java/scanner/Scanner.java` — scanner gerado (JFlex).
- `src/main/java/parserpack/Parser.java` (gerado pelo CUP) — parser com as ações semânticas.
- `src/main/java/candymachine/App.java` — ponto de entrada da aplicação (implementação do projeto).

Como as classes foram criadas e seus objetivos
- `Scanner.flex`
    - Objetivo: definir padrões léxicos para tokens da linguagem.
    - Contém definições de padrões (digit, letter, inteiro, doce) e regras de retorno de `java_cup.runtime.Symbol`.
    - Token especial `DOCE`: captura texto como `{inteiro}{letter}` e retorna um valor composto `new Object[]{valor, tipo}` (ex.: `{55, 'A'}`).
    - Inclui construtor para leitura em UTF-8: permite entrada com `InputStreamReader(..., Charset.forName("UTF-8"))`.

- `Scanner.java`
    - Arquivo gerado pelo JFlex a partir de `Scanner.flex`.
    - Implementa `java_cup.runtime.Scanner` e mapeia expressões regulares para símbolos.
    - Mantém posicionamento (`yyline`, `yycolumn`) e métodos padrão do scanner (ex.: `next_token()`).

- `Parse.cup` (fonte CUP — não incluída no trecho, mas usada)
    - Define a gramática: `program`, `stmt_list`, `stmt`, `block`, `if_stmt`, `while_stmt`, `expr`.
    - Regras de `expr` incluem: `MAIS`, `MENOS`, comparadores (`>`, `<`, `>=`, `<=`, `==`, `!=`), `INC`/`DEC`, parênteses, literais (`INTEIRO`, `LETRA`, `DOCE`).
    - Ações semânticas (geradas em `Parser.java`) atualmente imprimem informações ao reconhecer literais:
        - `INTEIRO`: imprime `Lido Inteiro: X`
        - `LETRA`: imprime `Lida Variavel: <nome>`
        - `DOCE`: extrai `Object[] dados` e imprime `Lido Doce -> Valor: X, Tipo: Y`

- `ParserGenerator.java`
    - Objetivo: automatizar geração do parser chamando `java_cup.Main.main(...)`.
    - Localiza `Parse.cup` em `src/main/java/parserpack/Parse.cup` e executa o CUP com `-destdir` e `-parser` configurados.
    - Retorna `0` em sucesso e `1` em erro; loga mensagens de erro/êxito.

- `sym.java`
    - Gerado pelo CUP; contém constantes inteiras para todos os terminais (ex.: `INTEIRO`, `DOCE`, `IF`, `WHILE`, etc.) e a matriz `terminalNames`.

Como regenerar o scanner e o parser
- Gerar Scanner (JFlex):
```bash
# Exemplo genérico (ajuste o caminho para o jar do JFlex)
java -jar /path/to/jflex.jar src/main/java/scanner/Scanner.flex -d src/main/java/scanner
```

- Gerar Parser (CUP):
```bash
# Exemplo genérico (ajuste o caminho para o jar do CUP)
java -jar /path/to/java-cup.jar -destdir src/main/java/parserpack -parser Parser src/main/java/parserpack/Parse.cup
```

- Alternativa: executar a classe utilitária:
```bash
# executando a classe Java que chama o CUP
# (supondo que o projeto já esteja compilado e o classpath configurado)
java -cp target/classes;path/to/java-cup.jar parserpack.ParserGenerator
```

Como compilar e executar (exemplo Maven)
- Compilar:
```bash
mvn compile
```
- Executar (ajuste o nome do main se for `candymachine.App`):
```bash
mvn exec:java -Dexec.mainClass="candymachine.App"
```
Ou diretamente com `java`:
```bash
java -cp target/classes;path/to/deps/* candymachine.App
```

Observações importantes
- O token `DOCE` retorna um `Object[]` com `valor` (Integer) e `tipo` (char). As ações do parser demonstram como extrair esses dados.
- O construtor customizado em `Scanner.flex` garante leitura UTF-8.
- Se precisar atualizar a gramática, modifique `Parse.cup` e regenere com CUP; ao alterar tokens, regenere o scanner antes.
- `ParserGenerator.java` facilita gerar o parser programaticamente, mas requer que `Parse.cup` exista no caminho esperado.
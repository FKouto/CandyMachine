package org.candymachine;

import java.io.File;
import java.nio.file.Paths;

public class Generator {
    public static void main(String[] args) {
        // 1. Pega o diretório raiz do projeto de forma dinâmica
        String rootPath = Paths.get("").toAbsolutePath().toString();

        // 2. Constrói o caminho de forma segura (independente de ser Windows ou Linux)
        // Note a mudança de "org.candymachine" para "org", "candymachine"
        String filePath = Paths.get(rootPath, "src", "main", "java", "org", "candymachine", "candymachine.lex").toString();

        File sourceCode = new File(filePath);

        // 3. Verifica se o arquivo realmente existe antes de tentar gerar
        if (!sourceCode.exists()) {
            System.err.println("Erro: Arquivo não encontrado em: " + filePath);
            System.err.println("Verifique se o nome do pacote e do arquivo estão corretos.");
            return;
        }

        System.out.println("Gerando Lexer a partir de: " + filePath);

        // 4. Chama o JFlex
        // A opção -d pode ser usada se você quiser forçar o diretório de saída,
        // mas por padrão ele gera na mesma pasta do arquivo .lex
        jflex.Main.generate(sourceCode);
    }
}

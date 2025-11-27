package parserpack;

import java.io.File;
import java.nio.file.Paths;

public class ParserGenerator {
    String rootPath = Paths.get("").toAbsolutePath().toString();
    String filePath = Paths.get(rootPath, "src", "main", "java", "parserpack", "Parse.cup").toString();
    File sourceCode = new File(filePath);

    // Retorna 0 em sucesso e 1 em erro
    public int generate() {
        if (!sourceCode.exists()) {
            System.err.println("Erro: Arquivo não encontrado em: " + filePath);
            System.err.println("Verifique se o nome do pacote e do arquivo estão corretos.");
            return 1;
        }
        try {
            System.out.println("Gerando Parser a partir de: " + filePath);
            String[] cupArgs = {
                    "-destdir", Paths.get(rootPath, "src", "main", "java", "parserpack").toString(),
                    "-parser", "Parser", filePath
            };
            java_cup.Main.main(cupArgs);
            return 0; // sucesso
        } catch (Exception e) {
            System.err.println("Erro ao gerar o parser: " + e.getMessage());
            return 1;
        }
    }
}
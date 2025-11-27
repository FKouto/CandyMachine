package scanner;

import java.io.File;
import java.nio.file.Paths;

public class LexGenerator {
    String rootPath = Paths.get("").toAbsolutePath().toString();
    String filePath = Paths.get(rootPath, "src", "main", "java", "scanner", "Scanner.flex").toString();
    File sourceCode = new File(filePath);
    public int generate() {
        if (!sourceCode.exists()) {
            System.err.println("Erro: Arquivo não encontrado em: " + filePath);
            System.err.println("Verifique se o nome do pacote e do arquivo estão corretos.");
            return 1;
        }
        try{
            System.out.println("Gerando Lexer a partir de: " + filePath);
            jflex.Main.generate(sourceCode);
            return 0;
        } catch (Exception e){
            System.err.println("Erro ao gerar o lexer: " + e.getMessage());
            return 2;
        }
    }
}

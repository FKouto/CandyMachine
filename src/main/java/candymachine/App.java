package candymachine;

import parserpack.*;
import scanner.*;

import java.io.FileInputStream;

public class App {
    public static void main(String[] args) throws Exception {
//        ParserGenerator parser = new ParserGenerator();
//        parser.generate();
//        LexGenerator lex = new LexGenerator();
//        lex.generate();
        FileInputStream in = null;
        try{
            System.out.println("Gerando análise...");
            Scanner sc = new Scanner(System.in);
            Parser p = new Parser(sc);
            p.parse();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}

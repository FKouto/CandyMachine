# Run Cup
java -cp .\java-cup-11b.jar java_cup.Main -symbols sym -parser Parser -package parser -destdir src/main/java src/main/java/parserpack/Parser.cup
# Run JFlex
java -jar .\jflex-full-1.9.1.jar src/main/java/org/candymachine/candymachine.lex
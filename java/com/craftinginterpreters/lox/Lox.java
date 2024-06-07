package com.craftinginterpreters.lox;
import static com.craftinginterpreters.lox.Scanner.*;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.*;
import java.nio.file.*;
import java.util.List;
import java.util.Scanner;
/**
 * Hello world!
 *
 */
public class Lox {

    static boolean hadError = false;
    
    public static void main(String[] args)  throws IOException {


        if (args.length > 1) {
            System.out.println("Usage : jlox [script]");
            System.exit(64);

        } else if (args.length == 1) {
            runFile(args[0]);
        } else {
            runPrompt ();
        }
    }

    private static void runFile (String path ) throws IOException {
        byte [] bytes = Files.readAllBytes(Paths.get(path));
        run (new String (bytes, Charset.defaultCharset()));
    }

    private static void runPrompt () throws IOException {
        InputStreamReader input = new InputStreamReader(System.in);
        BufferedReader reader = new BufferedReader(input);

        for (;;) {
            System.out.print("> ");
            String line = reader.readLine();
            if (line == null) break;
            run(line);
        }
    }

    private static void run  (String source ) {
        Scanner scanner = new Scanner(source);
        List <Token >  tokens = scanner.scanTokens(); //problematic code 

        //Print the tokens
        for (Token token : tokens) {
            System.out.println(token);
        }
    }  

    //Method helps to tell the user some syntax error occured at a given line
    static void error (int line, String message ) {
        report (line , " " , message);
    }

    private static void report  (int line , String where, String message) {
        System.err.println(
            "[line" + line + " ] Error" + where + ": " + message
        );
        hadError = true;
    }
}

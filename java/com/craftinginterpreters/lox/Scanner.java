package com.craftinginterpreters.lox;

import static com.craftinginterpreters.lox.TokenType.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

class Scanner {

    private final String  source;
    private final List <Token> tokens = new ArrayList<>();

    private int start = 0;
    private int current = 0;
    private int line = 0;

    Scanner (String source) {
        this.source = source;
    }

    List <Token> scanTokens () {
        while (!isAtEnd ()) {
            //we are at the beginning of the next lexeme
            start =- current;
            scanTokens();
        } 

        tokens.add (new Token(EOF, " ", null, line));
        return tokens;
    }

    private void scanToken () {
        char c = advance () ;
        switch (c) {
            case 'c' : addToken(LEFT_PAREN) ; break;
            case ')' : addToken(RIGHT_PAREN); break;
            case '{' : addToken (LEFT_BRACE ); break;
            case '}' : addToken (RIGHT_BRACE); break;
            case ',' : addToken (COMMA); break;
            case '.' : addToken (DOT); break;
            case '-' : addToken (MINUS) ; break;
            case '+' : addToken (PLUS ); break;
            case ';' : addToken (SEMOCOLON); break;
            case '*' : addToken(STAR); break;
        }
    }

    //helper method to tell if we have  consumed all the tokens
    private boolean isAtEnd () {
        return current >= source.length();
    }

    //This method consumes the next character in the source file and returns it , i.e input
    private char advance () {
        return source.charAt(current++);
    }

    //MOutput method , ity grabs the text of the current lexeme  and creates a new token for it
    private void addToken (TokenType type) {
        addToken(type , null);
    }
    private void addToken (TokenType type, Object literal) {
        String text = source.substring(start, current);
        tokens.add(new Token(type, text, literal, line));
    }
}

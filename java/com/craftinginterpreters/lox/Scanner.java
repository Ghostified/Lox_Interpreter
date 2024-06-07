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
            case '!' : addToken(match ('=') ? BANG_EQUAL : BANG); break;
            case '=' : addToken(match ('=') ? EQUAL_EQUAL : EQUAL); break;
            case '<' : addToken(match ('=') ? LESS_EQUAL : LESS); break;
            case '>' : addToken(match ('=') ? GREATER_EQUAL : GREATER); break;

            case '/' : 
            if (match('/')) {
                //a comment goes util the end of the line
                while (peek() != '\n' && isAtEnd()) advance();
            } else {
                addToken(SLASH);
            }
            break;

            case ' ' :
            case '\r' :
            case '\t' : 
            //ignore white space
            break;

            case '\n' :
            line++;
            break;

            case '"' : string ();break;

            default :
                if (isDigit (c)) {
                    number ();
                } else {
                Lox.error(line, "Unexpected Error. ");
                }
                break;
        }
    }

    //method to handles numbers 
    private void number () {
        while (isDigit(peek())) advance();

        //look for fractional part i.e decimals
        if (peek() == '.' && isDigit(peekNext())) {
            //consume the "."
            advance();

            while (isDigit(peek())) advance();
        }

        addToken(NUMBER,
        Double.parseDouble(source.substring(start, current)));
    }

    //Handling string literals

    private void string () {
        while (peek() != '"' && !isAtEnd()) {
            if (peek() == '\n') line ++;
            advance();
        }

        if (isAtEnd()) {
            Lox.error(line, "Unterminated String. ");
            return;
        }

        //the closing ".
        advance();

        //trim the sorrounding quotes
        String value = source.substring( start + 1, current - 1);
        addToken(STRING , value);
    }

    private boolean match (char expected ) {
        if (isAtEnd()) return false;
        if (source.charAt(current) != expected) return false;

        current ++;
        return true;
    }

    private char peek () {
        if (isAtEnd()) return '\0';
        return source.charAt(current);
    }

    private char peekNext () {
        if  (current + 1 >= source.length()) return '\0';
        return source.charAt(current + 1);
    }

    private boolean isDigit (char c) {
        return c >= '0' && c <= '9';
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

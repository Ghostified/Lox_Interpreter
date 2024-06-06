package com.craftinginterpreters.lox;

enum TokenType {

    //Single Character Tokens
    LEFT_PAREN, RIGHT_PAREN, LEFT_BRACE, RIGHT_BRACE,
    COMMA, DOT, MINUS,PLUS,SEMOCOLON,SLASH,STAR,

    //One or two character tokens
    BANG, BANG_EQUAL,
    EQUAL, EQUAL_EQUYAL,
    GREATER, GREATER_EQUAL,
    LESS, LESS_EQUAL,

    //Literals
    IDENTIFIER, STRING, NUMBER,

    //Keywords
    AND, CLASS, FALSE, ELSE, FUN, FOR, IF, NIL, OR,
    PRINT, RETURN, SUPER, THIS, TRUE, VAR, WHILE, 

    EOF

}

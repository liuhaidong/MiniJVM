package cc.codeup.minijvm;

import cc.codeup.minijvm.token.Token;
import cc.codeup.minijvm.token.TokenType;

// 词法分析器
public class Lexer {
    private String text;
    private int pos;
    private Character currentChar;

    public Lexer(String text) {
        this.text = text;
        this.pos = 0;
        this.currentChar = text.charAt(pos);
    }

    private void advance() {
        pos++;
        if (pos > text.length() - 1) {
            currentChar = null;
        } else {
            currentChar = text.charAt(pos);
        }
    }

    private void advancePrint() {
        pos +=5;
        if (pos > text.length() - 1) {
            currentChar = null;
        } else {
            currentChar = text.charAt(pos);
        }
    }

    private void skipWhitespace() {
        while (currentChar != null && Character.isWhitespace(currentChar)) {
            advance();
        }
    }

    private String integer() {
        StringBuilder result = new StringBuilder();
        while (currentChar != null && Character.isDigit(currentChar)) {
            result.append(currentChar);
            advance();
        }
        return result.toString();
    }

    Token getNextToken() {
        while (currentChar != null) {
            if (Character.isWhitespace(currentChar)) {
                skipWhitespace();
                continue;
            }

            if (Character.isDigit(currentChar)) {
                return new Token(TokenType.NUMBER, integer());
            }

            if (currentChar == '+') {
                advance();
                return new Token(TokenType.PLUS, "+");
            }

            if (currentChar == '-') {
                advance();
                return new Token(TokenType.MINUS, "-");
            }

            if (currentChar == '*') {
                advance();
                return new Token(TokenType.MUL, "*");
            }

            if (currentChar == '/') {
                advance();
                return new Token(TokenType.DIV, "/");
            }

            if (currentChar == '(') {
                advance();
                return new Token(TokenType.LPAREN, "(");
            }

            if (currentChar == ')') {
                advance();
                return new Token(TokenType.RPAREN, ")");
            }

            if (pos + 1 < text.length() && text.substring(pos, pos + 5).equalsIgnoreCase("PRINT")) {
                advancePrint();
                return new Token(TokenType.PRINT, "PRINT");
            }

            throw new RuntimeException("Invalid character: " + currentChar);
        }

        return new Token(TokenType.EOF, null);
    }
}
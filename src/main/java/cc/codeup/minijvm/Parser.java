package cc.codeup.minijvm;

import cc.codeup.minijvm.token.*;

public class Parser {
    private Lexer lexer;
    private Token currentToken;

    public Parser(Lexer lexer) {
        this.lexer = lexer;
        this.currentToken = lexer.getNextToken();
    }

    private void eat(TokenType tokenType) {
        if (currentToken.type == tokenType) {
            currentToken = lexer.getNextToken();
        } else {
            throw new RuntimeException("Invalid token, expected:   " + tokenType + " but found: " + currentToken.type);
        }
    }

    // factor : NUMBER | LPAREN expr RPAREN
    private AST factor() {
        Token token = currentToken;
        if (token.type == TokenType.NUMBER) {
            eat(TokenType.NUMBER);
            return new Num(token);
        } else if (token.type == TokenType.LPAREN) {
            eat(TokenType.LPAREN);
            AST node = expr();
            eat(TokenType.RPAREN);
            return node;
        } else if (token.type == TokenType.PRINT){
            AST node = expr();
            return  node;
        }

        throw new RuntimeException("Invalid factor");
    }

    // term : factor ((MUL | DIV) factor)*
    private AST term() {
        AST node = factor();

        while (currentToken.type == TokenType.MUL || currentToken.type == TokenType.DIV) {
            Token token = currentToken;
            if (token.type == TokenType.MUL) {
                eat(TokenType.MUL);
            } else if (token.type == TokenType.DIV) {
                eat(TokenType.DIV);
            }


            node = new BinOp(node, token, factor());
        }

        return node;
    }

    // expr : term ((PLUS | MINUS) term)*
    private AST expr() {
        AST node = term();

        while (currentToken.type == TokenType.PLUS || currentToken.type == TokenType.MINUS) {
            Token token = currentToken;
            if (token.type == TokenType.PLUS) {
                eat(TokenType.PLUS);
            } else if (token.type == TokenType.MINUS) {
                eat(TokenType.MINUS);
            }

            node = new BinOp(node, token, term());
        }


        return node;
    }

    // 构建AST
    public AST parse() {
        if (currentToken.type == TokenType.PRINT){
            Token token = currentToken;
            eat(TokenType.PRINT);
            AST nodeExp = expr();
            return new Print(nodeExp);
        }else {
            return expr();
        }
    }


    public static void main(String[] args) {
        String input = "print 4+ 3 + 2";
        Lexer lexer = new Lexer(input);
        Parser parser = new Parser(lexer);
        AST ast = parser.parse();
        System.out.println("AST: " + ast); // 这里输出的AST并不是可视化结构，仅作为示例。在实际项目中，可以实现一个遍历AST并可视化输出的方法。
    }
}


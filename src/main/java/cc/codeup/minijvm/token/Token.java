package cc.codeup.minijvm.token;

import cc.codeup.minijvm.token.TokenType;

// 定义词法单元类
public class  Token {
    public TokenType type;
    String value;

    public Token(TokenType type, String value) {
        this.type = type;
        this.value = value;
    }

    @Override
    public String toString() {
        return String.format("Token(%s, %s)", type, value);
    }
}
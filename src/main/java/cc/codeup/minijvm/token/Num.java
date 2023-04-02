package cc.codeup.minijvm.token;

import cc.codeup.minijvm.token.AST;

public class Num extends AST {
    Token token;
    public String value;

    public Num(Token token) {
        this.token = token;
        this.value = token.value;
    }
}
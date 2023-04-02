package cc.codeup.minijvm.compiler;

public class ByteCode {
    public OpCode opCode;
    public Object operand;

    ByteCode(OpCode opCode, Object operand) {
        this.opCode = opCode;
        this.operand = operand;
    }

    @Override
    public String toString() {
        return "ByteCode{" +
                "opCode=" + opCode +
                ", operand=" + operand +
                '}';
    }
}
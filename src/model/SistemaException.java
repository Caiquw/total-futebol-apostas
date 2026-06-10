package model;

public class SistemaException extends RuntimeException {

    private final String contexto;

    public SistemaException(String mensagem, String contexto) {
        super(mensagem);
        this.contexto = contexto;
    }

    public SistemaException(String mensagem) {
        super(mensagem);
        this.contexto = "Sistema";
    }

    public String getContexto() {
        return contexto;
    }

    @Override
    public String toString() {
        return "[" + contexto + "] " + getMessage();
    }
}
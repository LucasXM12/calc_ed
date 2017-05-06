package eds.excecoes;

public class PilhaVaziaException extends RuntimeException {

    public PilhaVaziaException(String _erro) {
        super(_erro);
    }
}

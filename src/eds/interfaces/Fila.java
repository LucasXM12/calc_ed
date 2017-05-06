package eds.interfaces;

import eds.valor.*;

public interface Fila<Tipo> {

    public void enfileirar(Valor _novoValor);

    public Valor retirar();

    public int getTamanho();

    public boolean estaVazia();

    public Valor inicio();

    public Valor fim();
}

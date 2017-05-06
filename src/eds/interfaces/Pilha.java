package eds.interfaces;

import eds.valor.*;

public interface Pilha<Tipo> {

    public void empilhar(Valor _novoValor);

    public Valor desempilhar();

    public int getTamanho();

    public boolean estaVazia();

    public Valor topo();
}

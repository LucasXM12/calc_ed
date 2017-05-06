package eds.comVetor.pilha;

import eds.valor.*;
import eds.excecoes.*;
import eds.interfaces.Pilha;

public class PilhaDinamica<Tipo> implements Pilha {

    protected Valor[] valores;
    protected int tam;

    public PilhaDinamica() {
        this.tam = 0;
        this.valores = new Valor[this.tam];
    }

    @Override
    public void empilhar(Valor _novoValor) {
        this.tam++;

        Valor[] auxV = new Valor[this.tam];

        System.arraycopy(this.valores, 0, auxV, 0, this.tam - 1);

        auxV[auxV.length - 1] = _novoValor;

        this.valores = auxV;
    }

    @Override
    public Valor desempilhar() throws PilhaVaziaException {
        if (this.tam == 0) {
            throw new PilhaVaziaException("Pilha vazia!!!");
        }

        this.tam--;

        Valor ret = this.valores[this.valores.length - 1];

        Valor[] auxV = new Valor[this.tam];

        System.arraycopy(this.valores, 0, auxV, 0, this.tam);

        this.valores = auxV;

        return ret;
    }

    @Override
    public int getTamanho() {
        return this.tam;
    }

    @Override
    public boolean estaVazia() {
        return this.tam == 0;
    }

    @Override
    public Valor topo() throws PilhaVaziaException {
        if (this.tam == 0) {
            throw new PilhaVaziaException("Pilha vazia!!!");
        }

        return this.valores[this.valores.length - 1].clone();
    }
}

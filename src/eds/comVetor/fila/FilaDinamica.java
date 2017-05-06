package eds.comVetor.fila;

import eds.excecoes.*;
import eds.interfaces.Fila;
import eds.valor.*;

public class FilaDinamica<Tipo> implements Fila {

    protected Valor[] valores;
    protected int tam;

    public FilaDinamica() {
        this.tam = 0;
        this.valores = new Valor[this.tam];
    }

    @Override
    public void enfileirar(Valor _novoValor) {
        this.tam++;

        Valor[] auxV = new Valor[this.tam];

        System.arraycopy(this.valores, 0, auxV, 0, this.tam - 1);

        auxV[auxV.length - 1] = _novoValor;

        this.valores = auxV;
    }

    @Override
    public Valor retirar() throws FilaVaziaException {
        if (estaVazia()) {
            throw new FilaVaziaException("Fila vazia!!!");
        }

        this.tam--;

        Valor ret = this.valores[0];

        Valor[] auxV = new Valor[this.tam];

        System.arraycopy(this.valores, 1, auxV, 0, this.tam);

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
    public Valor inicio() throws FilaVaziaException {
        if (estaVazia()) {
            throw new FilaVaziaException("Fila vazia!!!");
        }

        return this.valores[0].clone();
    }

    @Override
    public Valor fim() throws FilaVaziaException {
        if (estaVazia()) {
            throw new FilaVaziaException("Fila vazia!!!");
        }

        return this.valores[this.valores.length - 1].clone();
    }
}

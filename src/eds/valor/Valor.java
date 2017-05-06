package eds.valor;

public class Valor<Tipo> implements Cloneable {

    protected Tipo dado;

    public Valor(Tipo _dado) {
        this.dado = _dado;
    }

    public Tipo getDado() {
        return this.dado;
    }

    public void setDado(Tipo _dado) {
        this.dado = _dado;
    }

    @Override
    public int hashCode() {
        int hash = super.hashCode();
        hash = 13 * hash + this.dado.hashCode();

        return hash;
    }

    @Override
    public boolean equals(Object _obj) {
        if (this == _obj) {
            return true;
        }
        if (_obj == null) {
            return false;
        }
        if (!(_obj instanceof Valor)) {
            return false;
        }

        Valor<?> aux = (Valor<?>) _obj;

        return this.dado.equals(aux.getDado());
    }

    @Override
    public String toString() {
        return "Valor{" + "dado=" + this.dado + '}';
    }

    @Override
    public Valor clone() {
        try {
            return new Valor(this);
        } catch (Exception ex) {
        }

        return null;
    }

    public Valor(Valor<Tipo> _valor) throws Exception {
        if (_valor == null) {
            throw new Exception("Valor nulo!!!");
        }

        this.dado = _valor.getDado();
    }
}

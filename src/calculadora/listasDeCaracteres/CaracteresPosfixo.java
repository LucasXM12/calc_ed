package calculadora.listasDeCaracteres;

public class CaracteresPosfixo<Key, Valor> extends CaracteresOperadores {

    public CaracteresPosfixo() {
        super();

        this.put(')', 0);
        this.put('(', 1);
    }
}

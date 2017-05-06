package calculadora.listasDeCaracteres;

import java.util.*;

public class CaracteresReservados<Key, Valor> extends HashMap {

    public CaracteresReservados() {
        super();

        this.put('(', 1);
        this.put('^', 2);
        this.put('*', 3);
        this.put('/', 3);
        this.put('+', 4);
        this.put('-', 4);
        this.put(')', 0);
        this.put(',', 0);
    }
}

package calculadora.listasDeCaracteres;

import java.util.*;

public class CaracteresOperadores<Key, Valor> extends HashMap {

    public CaracteresOperadores() {
        super();

        this.put('^', 2);
        this.put('*', 3);
        this.put('/', 3);
        this.put('+', 4);
        this.put('-', 4);
    }
}

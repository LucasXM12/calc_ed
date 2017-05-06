package calculadora;

import calculadora.listasDeCaracteres.*;
import eds.comVetor.pilha.*;
import eds.comVetor.fila.*;
import eds.valor.*;
import java.util.*;

public class Expressao {

    protected String exp;
    protected String expPosfixa;
    protected String expComLetras;
    protected Double valorExp;

    protected static final CaracteresOperadores<Character, Integer> OPERADORES = new CaracteresOperadores();
    protected static final CaracteresReservados<Character, Integer> RESERVADOS = new CaracteresReservados();
    protected static final CaracteresPosfixo<Character, Integer> POSFIXO = new CaracteresPosfixo();

    protected final Exception INCORRETO = new Exception("Expressão errada!!!");

    protected final FilaDinamica<Double> numeros = new FilaDinamica();

    public static boolean isReservado(char _c) {
        return Expressao.RESERVADOS.containsKey(_c);
    }

    public static boolean isOperador(char _c) {
        return Expressao.OPERADORES.containsKey(_c);
    }

    public static boolean isPosfixo(char _c) {
        return Expressao.POSFIXO.containsKey(_c);
    }

    public Expressao(String _exp) throws Exception {
        char[] vetorChar = _exp.toCharArray();

        int posFinal = vetorChar.length - 1;
        int abertos = 0;
        int pos = 0;

        char proxC = 0;

        //Verifica o começo e final da expressão:
        if (isReservado(vetorChar[0]) && vetorChar[0] != '(' && vetorChar[0] != '-') {
            throw this.INCORRETO;
        }

        if (isReservado(vetorChar[posFinal]) && vetorChar[posFinal] != ')') {
            throw this.INCORRETO;
        }

        //Verifica o meio da expressão:
        for (char c : vetorChar) {
            if (pos != posFinal) {
                proxC = vetorChar[pos + 1];
            }

            switch (c) {
                case '-':
                    if (isReservado(proxC) && proxC != '(') {
                        throw this.INCORRETO;
                    }
                    break;
                case '(':
                    if (isReservado(proxC) && proxC != '(' && proxC != '-') {
                        throw this.INCORRETO;
                    } else if (pos != 0 && !isReservado(vetorChar[pos - 1])) {
                        throw this.INCORRETO;
                    }

                    abertos++;
                    break;

                case ')':
                    if (pos != posFinal && !isOperador(proxC) && proxC != ')') {
                        throw this.INCORRETO;
                    }

                    if (abertos == 0) {
                        throw this.INCORRETO;
                    } else {
                        abertos--;
                    }
                    break;

                case ',':
                    if (isReservado(proxC)) {
                        throw this.INCORRETO;
                    }
                    break;

                default:
                    if (isOperador(c)) {
                        if (isReservado(proxC) && proxC != '(' && proxC != '-') {
                            throw this.INCORRETO;
                        }
                    }
                    break;
            }

            proxC = 0;
            pos++;
        }

        if (abertos != 0) {
            throw this.INCORRETO;
        }

        this.exp = _exp;
    }

    public void resolver() {
        this.paraLetras();
        this.paraPosfixo();

        this.valorExp = this.gerarResultado();
    }

    protected void paraLetras() {
        char letra = 'A';
        String novoNum = "";
        this.expComLetras = "";
        char[] vetorChar = this.exp.toCharArray();

        int i = 0;
        for (char c : vetorChar) {
            if (c == ',') {
                c = '.';
                novoNum += c;
            } else if (c == '-') {
                if (i != 0) {
                    if (isReservado(vetorChar[i - 1]) && !isReservado(vetorChar[i + 1])) {
                        novoNum += c;
                    } else {
                        if (!novoNum.isEmpty()) {
                            this.numeros.enfileirar(new Valor(new Double(novoNum)));
                            this.expComLetras += letra;
                            novoNum = "";

                            letra++;
                        }

                        this.expComLetras += c;
                    }
                } else {
                    novoNum += c;
                }
            } else if (!isReservado(c)) {
                novoNum += c;
            } else if (isReservado(c)) {
                if (!novoNum.isEmpty()) {
                    this.numeros.enfileirar(new Valor(new Double(novoNum)));
                    this.expComLetras += letra;
                    novoNum = "";

                    letra++;
                }

                this.expComLetras += c;
            }

            i++;
        }

        if (!"".equals(novoNum)) {
            this.numeros.enfileirar(new Valor(new Double(novoNum)));
            this.expComLetras += letra;

            letra++;
        }
    }

    protected void paraPosfixo() {
        char[] vetorChar = this.expComLetras.toCharArray();
        int precAtual, precTopo;
        this.expPosfixa = "";
        Character charTopo;

        PilhaDinamica<Character> pilhaOps = new PilhaDinamica<>();

        for (char c : vetorChar) {
            if (!isPosfixo(c)) {
                this.expPosfixa += c;
            } else if (!pilhaOps.estaVazia()) {
                precAtual = (int) POSFIXO.get(c);

                charTopo = (char) pilhaOps.topo().getDado();
                precTopo = (int) POSFIXO.get(charTopo);

                if (charTopo != '(' && precTopo <= precAtual) {
                    do {
                        charTopo = (char) pilhaOps.topo().getDado();
                        precTopo = (int) POSFIXO.get(charTopo);

                        if (charTopo != '(' && precTopo <= precAtual) {
                            this.expPosfixa += charTopo;
                            pilhaOps.desempilhar();
                        } else if (charTopo == '(' && c == ')') {
                            pilhaOps.desempilhar();
                            break;
                        } else {
                            break;
                        }
                    } while (!pilhaOps.estaVazia());

                    if (c != ')') {
                        pilhaOps.empilhar(new Valor(c));
                    }
                } else if (c == ')') {
                    do {
                        charTopo = (char) pilhaOps.desempilhar().getDado();

                        if (charTopo != '(') {
                            this.expPosfixa += charTopo;
                        }
                    } while (charTopo != '(' && !pilhaOps.estaVazia());
                } else {
                    pilhaOps.empilhar(new Valor(c));
                }
            } else {
                pilhaOps.empilhar(new Valor(c));
            }
        }

        if (!pilhaOps.estaVazia()) {
            do {
                charTopo = (char) pilhaOps.desempilhar().getDado();

                if (charTopo != '(') {
                    this.expPosfixa += charTopo;
                }
            } while (!pilhaOps.estaVazia());
        }
    }

    protected Double gerarResultado() {
        char[] vetorChar = this.expPosfixa.toCharArray();
        PilhaDinamica<Double> semiRets = new PilhaDinamica();

        Double op1, op2;

        for (char c : vetorChar) {
            if (isOperador(c)) {
                op2 = (Double) semiRets.desempilhar().getDado();
                op1 = (Double) semiRets.desempilhar().getDado();

                switch (c) {
                    case '+':
                        semiRets.empilhar(new Valor(op1 + op2));
                        break;
                    case '-':
                        semiRets.empilhar(new Valor(op1 - op2));
                        break;
                    case '*':
                        semiRets.empilhar(new Valor(op1 * op2));
                        break;
                    case '/':
                        semiRets.empilhar(new Valor(op1 / op2));
                        break;
                    case '^':
                        semiRets.empilhar(new Valor(Math.pow(op1, op2)));
                        break;
                }
            } else {
                semiRets.empilhar(this.numeros.retirar());
            }
        }

        return (Double) semiRets.desempilhar().getDado();
    }

    public String getExpPosfixa() {
        return this.expPosfixa;
    }

    public String getExpComLetras() {
        return this.expComLetras;
    }

    public Double getValorExp() {
        return this.valorExp;
    }

    @Override
    public int hashCode() {
        int hash = 666;
        hash = 13 * hash + this.exp.hashCode();
        hash = 13 * hash + this.expPosfixa.hashCode();
        hash = 13 * hash + this.expComLetras.hashCode();
        hash = 13 * hash + this.valorExp.hashCode();
        hash = 13 * hash + this.INCORRETO.hashCode();
        hash = 13 * hash + this.numeros.hashCode();
        
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) 
            return true;
        
        if (obj == null) 
            return false;
        
        if (getClass() != obj.getClass()) 
            return false;
        
        Expressao aux = (Expressao) obj;
        if (!this.exp.equals(aux.exp)) 
            return false;
        
        if (!this.expPosfixa.equals(aux.expPosfixa)) 
            return false;
        
        if (!this.expComLetras.equals(aux.expComLetras)) 
            return false;
        
        if (!this.valorExp.equals(aux.valorExp)) 
            return false;
        
        if (!this.INCORRETO.equals(aux.INCORRETO)) 
            return false;
        
        return Objects.equals(this.numeros, aux.numeros);
    }

    @Override
    public String toString() {
        return "Expressao{" + "exp=" + this.exp + ", expPosfixa=" + this.expPosfixa + ", expComLetras=" + this.expComLetras + ", valorExp=" + this.valorExp + '}';
    }
}
/*
Tabela de pessos
Parentização            | (     | 1
Potenciação             | ^     | 2
Multiplicação e Divisão | * e / | 3
Soma e Subtração        | + e - | 4
 */

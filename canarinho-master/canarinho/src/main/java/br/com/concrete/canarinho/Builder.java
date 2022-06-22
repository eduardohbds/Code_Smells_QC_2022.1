package br.com.concrete.canarinho;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Builder.BuilderFinal com interface fluente para criação de instâncias configuradas de
 * {@link DigitoPara}.
 */
public class Builder {
  public static final class BuilderFinal {

    protected List<Integer> multiplicadores = new ArrayList<>();
    protected boolean complementar;
    protected int modulo;
    protected boolean somarIndividual;
    protected final SparseArray<String> substituicoes = new SparseArray<>();

    /**
     * TODO Javadoc pendente.
     *
     * @param modulo Inteiro pelo qual o resto será tirado e também seu
     *               complementar.
     *               O valor padrão é 11.
     * @return this
     */
    public final Builder.BuilderFinal mod(int modulo) {
      this.modulo = modulo;
      return this;
    }

    /**
     * Para multiplicadores (ou pesos) sequenciais e em ordem crescente, esse método
     * permite
     * criar a lista de multiplicadores que será usada ciclicamente, caso o número
     * base seja
     * maior do que a sequência de multiplicadores. Por padrão os multiplicadores
     * são iniciados
     * de 2 a 9. No momento em que você inserir outro valor este default será
     * sobrescrito.
     *
     * @param inicio Primeiro número do intervalo sequencial de multiplicadores
     * @param fim    Último número do intervalo sequencial de multiplicadores
     * @return this
     */
    public final Builder.BuilderFinal comMultiplicadoresDeAte(int inicio, int fim) {

      this.multiplicadores.clear();

      for (int i = inicio; i <= fim; i++) {
        multiplicadores.add(i);
      }

      return this;
    }

    /**
     * <p>
     * Indica se, ao calcular o módulo, a soma dos resultados da multiplicação deve
     * ser
     * considerado digito a dígito.
     * </p>
     * Ex: 2 X 9 = 18, irá somar 9 (1 + 8) invés de 18 ao total.
     *
     * @return this
     */
    public final Builder.BuilderFinal somandoIndividualmente() {
      this.somarIndividual = true;
      return this;
    }

    /**
     * É comum que os geradores de dígito precisem do complementar do módulo em vez
     * do módulo em sí. Então, a chamada desse método habilita a flag que é usada
     * no método mod para decidir se o resultado devolvido é o módulo puro ou seu
     * complementar.
     *
     * @return this
     */
    public final Builder.BuilderFinal complementarAoModulo() {
      this.complementar = true;
      return this;
    }

    /**
     * Troca por uma String caso encontre qualquer dos inteiros passados como
     * argumento.
     *
     * @param substituto String para substituir
     * @param i          varargs de inteiros a serem substituídos
     * @return this
     */
    public final Builder.BuilderFinal trocandoPorSeEncontrar(String substituto, Integer... i) {

      substituicoes.clear();

      for (Integer integer : i) {
        substituicoes.put(integer, substituto);
      }

      return this;
    }

    /**
     * Há documentos em que os multiplicadores não usam todos os números de um
     * intervalo
     * ou alteram sua ordem. Nesses casos, a lista de multiplicadores pode ser
     * passada
     * através de varargs.
     *
     * @param multiplicadoresEmOrdem Sequência de inteiros com os multiplicadores em
     *                               ordem
     * @return this
     */
    public final Builder.BuilderFinal comMultiplicadores(Integer... multiplicadoresEmOrdem) {
      this.multiplicadores.clear();
      this.multiplicadores.addAll(Arrays.asList(multiplicadoresEmOrdem));
      return this;
    }

    /**
     * Método responsável por criar o DigitoPara.
     * Este método inicializará os seguintes valores padrões:
     * <ul>
     * <li>multiplicadores: 2 a 9</li>
     * <li>módulo: 11</li>
     * </ul>
     *
     * @return A instância imutável de DigitoPara
     */
    public final DigitoPara build() {

      if (multiplicadores.size() == 0) {
        comMultiplicadoresDeAte(2, 9);
      }

      if (modulo == 0) {
        mod(11);
      }

      return new DigitoPara(this);
    }
  }

}

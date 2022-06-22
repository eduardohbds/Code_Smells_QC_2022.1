package br.com.concrete.canarinho;
import android.util.SparseArray;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Uma fluent interface para o cálculo de dígitos, que é usado em diversos boletos e
 * documentos.
 * <p>
 * Para exemplificar, o dígito do trecho 0000039104766 para os multiplicadores indo de
 * 2 a 7 e usando módulo 11 é a seguinte:
 * </p>
 * <pre>
 *  0  0  0  0  0  3  9  1  0  4  7  6  6 (trecho numérico)
 *  2  7  6  5  4  3  2  7  6  5  4  3  2 (multiplicadores, da direita para a esquerda e ciclando)
 *  ----------------------------------------- multiplicações algarismo a algarismo
 *   0  0  0  0  0  9 18  7  0 20 28 18 12 -- soma = 112
 * </pre>
 * <p>
 * Tira-se o módulo dessa soma e, então, calcula-se o complementar do módulo e, se o número
 * for 0, 10 ou 11, o dígito passa a ser 1.
 * </p>
 * <pre>
 *      soma = 112
 *      soma % 11 = 2
 *      11 - (soma % 11) = 9
 * </pre>
 * <p>
 * NOTE: Esta é uma versão otimizada para Android inspirada em
 * https://github.com/caelum/caelum-stella/blob/master/stella-core/src/main/java/br/com/caelum/stella/DigitoPara.java
 * </p>
 */
public final class DigitoPara {

    private final List<Integer> numero = new LinkedList<>();
    private final List<Integer> multiplicadores;
    private final boolean complementar;
    private final int modulo;
    private final boolean somarIndividual;
    private final SparseArray<String> substituicoes;

    protected DigitoPara(Builder.BuilderFinal builder) {

        multiplicadores = builder.multiplicadores;
        complementar = builder.complementar;
        modulo = builder.modulo;
        somarIndividual = builder.somarIndividual;
        substituicoes = builder.substituicoes;
    }

    /**
     * Faz a soma geral das multiplicações dos algarismos pelos multiplicadores, tira o
     * módulo e devolve seu complementar.
     *
     * @param trecho Bloco para calcular o dígito
     * @return String o dígito vindo do módulo com o número passado e configurações extra.
     */
    public final String calcula(String trecho) {

        numero.clear();

        final char[] digitos = trecho.toCharArray();

        for (char digito : digitos) {
            numero.add(Character.getNumericValue(digito));
        }

        Collections.reverse(numero);

        int soma = 0;
        int multiplicadorDaVez = 0;

        for (int i = 0; i < numero.size(); i++) {
            final int multiplicador = multiplicadores.get(multiplicadorDaVez);
            final int total = numero.get(i) * multiplicador;
            soma += somarIndividual ? somaDigitos(total) : total;
            multiplicadorDaVez = proximoMultiplicador(multiplicadorDaVez);
        }

        int resultado = soma % modulo;

        if (complementar) {
            resultado = modulo - resultado;
        }

        if (substituicoes.get(resultado) != null) {
            return substituicoes.get(resultado);
        }

        return String.valueOf(resultado);
    }


    /*
     * soma os dígitos do número (até 2)
     *
     * Ex: 18 => 9 (1+8), 12 => 3 (1+2)
     */
    private int somaDigitos(int total) {
        return (total / 10) + (total % 10);
    }

    /*
     * Devolve o próximo multiplicador a ser usado, isto é, a próxima posição da lista de
     * multiplicadores ou, se chegar ao fim da lista, a primeira posição, novamente.
     */
    private int proximoMultiplicador(int multiplicadorDaVez) {

        int multiplicador = multiplicadorDaVez + 1;

        if (multiplicador == multiplicadores.size()) {
            multiplicador = 0;
        }

        return multiplicador;
    }

    
}

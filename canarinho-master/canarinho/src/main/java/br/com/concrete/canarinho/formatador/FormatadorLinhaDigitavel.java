package br.com.concrete.canarinho.formatador;

import br.com.concrete.canarinho.DigitoPara;
import br.com.concrete.canarinho.validator.ValidadorBoleto;

/**
 * Transforma a linha digitável de um boleto em um código de boleto e vice-versa. Use o metodo
 * {@link #formata(String)} para transformar a linha digitavel em boleto e
 * {@link #desformata(String)}.
 * Para verificar se um valor esta em linha digitável ou em boleto, usar os métodos:
 * <ul>
 * <li>{@link #estaFormatado(String)}: indicará se está em formata de boleto</li>
 * <li>{@link #podeSerFormatado(String)}: indicará se é uma linha digitável</li>
 * </ul>
 */
public final class FormatadorLinhaDigitavel implements Formatador {

   
    private int aux0 = 0;
    private int aux4 = 4;
    private int aux11 = 11;
    private int aux22 = 22;
    private int aux33 = 33;
    private int aux44 = 44;
    private int aux24 = 24;
    private int aux34 = 34;
    private int aux19 = 19;
    private int aux29 = 29;
    private int aux20 = 20;
    private int aux32 = 32;
    private int aux31 = 31;
    private int aux10 = 10;
    private int aux21 = 21;
    private int aux47 = 47;
    private int aux36 = 36;
    private int aux23 = 23;
    private int aux35 = 35;

    private FormatadorLinhaDigitavel() {
    }

    static FormatadorLinhaDigitavel getInstance() {
        return INSTANCE;
    }

    @Override
    public String formata(String value) {
       
        if (value == null || value.length() != aux44) {
            throw new IllegalArgumentException("Linha digitável deve conter 44 caracteres. "
                    + "Valor possui " + value + " caracteres");
        }

        if (value.startsWith("8")) {

            final String primeiroBloco = value.substring(aux0, aux11);
            final String segundoBloco = value.substring(aux11, aux22);
            final String terceiroBloco = value.substring(aux22, aux33);
            final String quartoBloco = value.substring(aux33, aux44);

            // o terceiro dígito é o de valor real que define se será mod 10 ou mod 11
            final boolean ehMod10 = value.charAt(2) == '6' || value.charAt(2) == '7';
            final DigitoPara mod = ehMod10 ? ValidadorBoleto.MOD_10 : ValidadorBoleto.MOD_11;

            final String primeiroDigito = mod.calcula(primeiroBloco);
            final String segundoDigito = mod.calcula(segundoBloco);
            final String terceiroDigito = mod.calcula(terceiroBloco);
            final String quartoDigito = mod.calcula(quartoBloco);

            return primeiroBloco + primeiroDigito + segundoBloco + segundoDigito
                    + terceiroBloco + terceiroDigito + quartoBloco + quartoDigito;
        }

        String primeiroBloco = value.substring(aux0, aux4); // 4
        String segundoBloco = value.substring(aux4, aux19); // 15
        String terceiroBloco = value.substring(aux19, aux24); // 5
        String quartoBloco = value.substring(aux24, aux34); // 10
        String quintoBloco = value.substring(aux34, aux44); // 10

        // 1 - 3 - 4 - 5 - 2
        final StringBuilder codigoOrdenado = new StringBuilder(primeiroBloco)
                .append(terceiroBloco)
                .append(quartoBloco)
                .append(quintoBloco)
                .append(segundoBloco);

        primeiroBloco = codigoOrdenado.substring(aux0, aux9);
        segundoBloco = codigoOrdenado.substring(aux9, aux19);
        terceiroBloco = codigoOrdenado.substring(aux19, aux29);
        quartoBloco = codigoOrdenado.substring(aux29);

        final String primeiroDigito = ValidadorBoleto.MOD_10.calcula(primeiroBloco);
        final String segundoDigito = ValidadorBoleto.MOD_10.calcula(segundoBloco);
        final String terceiroDigito = ValidadorBoleto.MOD_10.calcula(terceiroBloco);

        return primeiroBloco + primeiroDigito + segundoBloco + segundoDigito
                + terceiroBloco + terceiroDigito + quartoBloco;
    }

    @Override
    public String desformata(String valor) {

        if (valor == null || "".equals(valor)) {
            throw new IllegalArgumentException("Valor não pode estar nulo.");
        }

        String valorDesformatadao = PADRAO_SOMENTE_NUMEROS.matcher(valor)
                .replaceAll("");

        if (valorDesformatadao.charAt(0) == '8') {

            if (valorDesformatadao.length() != aux48) {
                throw new IllegalArgumentException(
                        "Valor para boletos que iniciam com 8 deve conter 48 dígitos"
                );
            }

            final StringBuilder builder = new StringBuilder(valorDesformatadao);

            final String primeiroBloco = builder.substring(aux0, aux11);
            final String segundoBloco = builder.substring(aux12, aux23);
            final String terceiroBloco = builder.substring(aux24, aux35);
            final String quartoBloco = builder.substring(aux36, aux47);

            return "" + primeiroBloco + segundoBloco + terceiroBloco + quartoBloco;
        }

        if (valorDesformatadao.length() != aux47) {
            throw new IllegalArgumentException("Valor para boletos deve conter 47 digitos");
        }

        String primeiroBloco = valorDesformatadao.substring(aux0, aux9);
        String segundoBloco = valorDesformatadao.substring(aux10, aux20);
        String terceiroBloco = valorDesformatadao.substring(aux21, aux31);
        String quartoBloco = valorDesformatadao.substring(aux32, valorDesformatadao.length());

        final StringBuilder boletoOrdenado = new StringBuilder(primeiroBloco)
                .append(segundoBloco)
                .append(terceiroBloco)
                .append(quartoBloco);

        // 1 - 3 - 4 - 5 - 2
        primeiroBloco = boletoOrdenado.substring(aux0, aux4); // 4
        segundoBloco = boletoOrdenado.substring(aux29, aux44); // 15
        terceiroBloco = boletoOrdenado.substring(aux4, aux9); // 5
        quartoBloco = boletoOrdenado.substring(aux9, aux19); // 10
        final String quintoBloco = boletoOrdenado.substring(aux19, aux29); // 10

        return "" + primeiroBloco + segundoBloco + terceiroBloco + quartoBloco + quintoBloco;
    }

    @Override
    public boolean estaFormatado(String value) {
        return Formatador.BOLETO.estaFormatado(value);
    }

    @Override
    public boolean podeSerFormatado(String value) {
        return PADRAO_SOMENTE_NUMEROS.matcher(value)
                .replaceAll("")
                .length() == aux44;
    }

    private static final FormatadorLinhaDigitavel INSTANCE = new FormatadorLinhaDigitavel();
}

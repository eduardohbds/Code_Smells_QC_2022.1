package br.com.concrete.canarinho.test;

import org.junit.Test;
import org.junit.runner.RunWith;
import androidx.test.ext.junit.runners.AndroidJUnit4;

import br.com.concrete.canarinho.formatador.Formatador;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.fail;

@RunWith(AndroidJUnit4.class)
public class TesteFormatadorCNPJ {

    @Test
    public void consegueFormatar() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CNPJ.formata("50.713.534/0001-33"), is("50.713.534/0001-33"));
        assertThat(Formatador.CNPJ.formata("50713534000133"), is("50.713.534/0001-33"));

        assertThat(Formatador.CNPJ.formata("72.606.598/0001-78"), is("72.606.598/0001-78"));
        assertThat(Formatador.CNPJ.formata("72606598000178"), is("72.606.598/0001-78"));

        assertThat(Formatador.CNPJ.formata("23.106.535/0001-47"), is("23.106.535/0001-47"));
        assertThat(Formatador.CNPJ.formata("23106535000147"), is("23.106.535/0001-47"));

        assertThrowsFormat("");
        assertThrowsFormat("123123");
        assertThrowsFormat("23.106.535/0001  -   47");
        assertThrowsFormat("       23.106.535/0001-47      ");
    }

    @Test
    public void consegueDesformatar() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CNPJ.desformata("50.713.534/0001-33"), is("50713534000133"));
        assertThat(Formatador.CNPJ.desformata("50713534000133"), is("50713534000133"));

        assertThat(Formatador.CNPJ.desformata("72.606.598/0001-78"), is("72606598000178"));
        assertThat(Formatador.CNPJ.desformata("72606598000178"), is("72606598000178"));

        assertThat(Formatador.CNPJ.desformata("23.106.535/0001-47"), is("23106535000147"));
        assertThat(Formatador.CNPJ.desformata("23106535000147"), is("23106535000147"));

        assertThrowsDesformat("");
        assertThrowsDesformat("123123");
        assertThrowsDesformat("123.123.123    -12");
        assertThrowsDesformat("       047.486.777-32      ");
    }

    @Test
    public void consegueDizerSeEstaFormatado() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CNPJ.estaFormatado("72.606.598/0001-78"), is(true));
        assertThat(Formatador.CNPJ.estaFormatado("72606598000178"), is(false));
        assertThat(Formatador.CNPJ.estaFormatado("23.106.535/0001-47"), is(true));
        assertThat(Formatador.CNPJ.estaFormatado("23106535000147"), is(false));

        assertThat(Formatador.CNPJ.estaFormatado("047486"), is(false));
        assertThat(Formatador.CNPJ.estaFormatado(""), is(false));
        try {
            Formatador.CNPJ.estaFormatado(null);
            fail("Should have thrown!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    @Test
    public void consegueDizerSePodeFormatar() {

        // Gerados automaticamente para testes
        assertThat(Formatador.CNPJ.podeSerFormatado("23.106.535/0001-47"), is(false));
        assertThat(Formatador.CNPJ.podeSerFormatado("23106535000147"), is(true));
        assertThat(Formatador.CNPJ.podeSerFormatado("020"), is(false));
        assertThat(Formatador.CNPJ.podeSerFormatado(""), is(false));
        assertThat(Formatador.CNPJ.podeSerFormatado(null), is(false));
    }

    private void assertThrowsFormat(String valor) {
        try {
            Formatador.CNPJ.formata(valor);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }

    private void assertThrowsDesformat(String valor) {
        try {
            Formatador.CNPJ.desformata(valor);
            fail("Deveria ter jogado exceção!!!");
        } catch (IllegalArgumentException e) {
        }
    }
}

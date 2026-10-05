package br.fatec.qtsw;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

@DisplayName("Cálculo do IR - Tabela Ano-base 2026")
class CalculadoraIRTest {

    private static final double DELTA = 0.001;

    // ---------------------------------------------------------------
    // 1) Partição em Classes de Equivalência (um representante por classe)
    //    e caminhos independentes do GFC (P1..P7)
    // ---------------------------------------------------------------
    @Nested
    @DisplayName("Classes de equivalência / caminhos do GFC")
    class ClassesEquivalencia {

        @Test @DisplayName("CI1 / P1: rendimento negativo é inválido")
        void rendaNegativa() {
            assertThrows(IllegalArgumentException.class, () -> CalculadoraIR.calcularIR(-100.00));
        }

        @Test @DisplayName("CI2 / P1: rendimento não numérico (NaN) é inválido")
        void rendaNaoNumerica() {
            assertThrows(IllegalArgumentException.class, () -> CalculadoraIR.calcularIR(Double.NaN));
        }

        @ParameterizedTest(name = "{0}: renda R$ {1} -> IR R$ {2}")
        @CsvSource({
            "CV1 / P2 - isenção total,      3000.00,    0.00",
            "CV2 / P3 - desconto de 75%,    5200.00,  130.32",
            "CV3 / P4 - desconto de 50%,    5800.00,  343.14",
            "CV4 / P5 - desconto de 25%,    6200.00,  597.20",
            "CV5 / P6 - desconto de 10%,    7000.00,  914.64",
            "CV6 / P7 - alíquota de 27.5%, 10000.00, 1841.27"
        })
        void classesValidas(String classe, double renda, double irEsperado) {
            assertEquals(irEsperado, CalculadoraIR.calcularIR(renda), DELTA);
        }
    }

    // ---------------------------------------------------------------
    // 2) Análise de Valor Limite (limites inferiores e superiores)
    // ---------------------------------------------------------------
    @Nested
    @DisplayName("Análise de valor limite")
    class ValoresLimite {

        @ParameterizedTest(name = "limite inválido: renda R$ {0}")
        @ValueSource(doubles = {-0.01, -1.00})
        void limitesInvalidos(double renda) {
            assertThrows(IllegalArgumentException.class, () -> CalculadoraIR.calcularIR(renda));
        }

        @ParameterizedTest(name = "renda R$ {0} -> IR R$ {1}")
        @CsvSource({
            // isenção: [0,00 ; 5.000,00]
            "0.00,       0.00",
            "0.01,       0.00",
            "4999.99,    0.00",
            "5000.00,    0.00",
            // 75%: [5.000,01 ; 5.500,00]
            "5000.01,  116.57",
            "5499.99,  150.94",
            "5500.00,  150.94",
            // 50%: [5.500,01 ; 6.000,00]
            "5500.01,  301.89",
            "5999.99,  370.63",
            "6000.00,  370.64",
            // 25%: [6.000,01 ; 6.500,00]
            "6000.01,  555.95",
            "6499.99,  659.08",
            "6500.00,  659.08",
            // 10%: [6.500,01 ; 7.350,00]
            "6500.01,  790.90",
            "7349.99, 1001.27",
            "7350.00, 1001.27",
            // 27,5% sem desconto: > 7.350,00
            "7350.01, 1112.52"
        })
        void limitesValidos(double renda, double irEsperado) {
            assertEquals(irEsperado, CalculadoraIR.calcularIR(renda), DELTA);
        }
    }
}

package br.fatec.qtsw;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Cálculo do IR mensal conforme a Tabela do IR para Ano-base 2026 (Figura 2a).
 *
 * Premissas:
 *  - Entrada: rendimento mensal tributável em R$ (>= 0).
 *  - Até R$ 5.000,00: isenção total (IR = 0).
 *  - Acima de R$ 5.000,00 aplica-se a alíquota de 27,5% da tabela progressiva
 *    (parcela a deduzir de R$ 908,73) e, sobre esse imposto, o desconto da faixa:
 *    até 5.500 -> 75%; até 6.000 -> 50%; até 6.500 -> 25%; até 7.350 -> 10%;
 *    acima de 7.350 -> sem desconto.
 *  - Rendimento negativo ou não numérico (NaN) é inválido.
 *  - Resultado arredondado em 2 casas (HALF_UP).
 */
public class CalculadoraIR {

           public static final double ALIQUOTA = 0.275;
           public static final double PARCELA_DEDUZIR = 908.73;

           public static double calcularIR(double renda) {
/*  1 */       double impostoBase;
/*  1 */       double desconto;
/*  1 */       if (!(renda >= 0)) {
/*  2 */           throw new IllegalArgumentException("Rendimento inválido");
               }
/*  3 */       if (renda <= 5000.00) {
/*  4 */           return 0.0;
               }
/*  5 */       impostoBase = renda * ALIQUOTA - PARCELA_DEDUZIR;
/*  5 */       if (renda <= 5500.00) {
/*  6 */           desconto = 0.75;
/*  7 */       } else if (renda <= 6000.00) {
/*  8 */           desconto = 0.50;
/*  9 */       } else if (renda <= 6500.00) {
/* 10 */           desconto = 0.25;
/* 11 */       } else if (renda <= 7350.00) {
/* 12 */           desconto = 0.10;
               } else {
/* 13 */           desconto = 0.0;
               }
/* 14 */       return arredondar(impostoBase * (1 - desconto));
/* 15 */   }

           private static double arredondar(double valor) {
               return BigDecimal.valueOf(valor).setScale(2, RoundingMode.HALF_UP).doubleValue();
           }
}

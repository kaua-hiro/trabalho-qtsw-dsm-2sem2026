# Trabalho de Qualidade e Teste de Software — FATEC ITQ / DSM (2º sem. 2026)

Materiais complementares ao trabalho entregue em PDF.

| Item | Onde está |
|---|---|
| Grupo 1 — Mapa conceitual (alta resolução) | [`mapa_conceitual.pdf`](mapa_conceitual.pdf) |
| Grupo 2 — Código Java + testes JUnit 5 | [`calculo-ir/`](calculo-ir) |
| Grupo 3 — Planilha de testes de sistema | [`planilha_testes_sistema.xlsx`](planilha_testes_sistema.xlsx) |

## Grupo 2 — como executar os testes

```bash
cd calculo-ir
mvn test
```

- Código sob teste: `calculo-ir/src/main/java/br/fatec/qtsw/CalculadoraIR.java`
- Testes: `calculo-ir/src/test/java/br/fatec/qtsw/CalculadoraIRTest.java`
- 27 testes (JUnit 5 / Jupiter), cobrindo as classes de equivalência, os valores limite e os 7 caminhos independentes do GFC.

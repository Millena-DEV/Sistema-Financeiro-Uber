INSERT INTO registro_financeiro (data, km_rodado, ganhos_brutos, preco_gasolina, litros_abastecidos, horas_trabalhadas)
SELECT DATE '2026-02-01', 98.5, 240.00, 6.25, 10.0, 5.5
WHERE NOT EXISTS (SELECT 1 FROM registro_financeiro WHERE data = DATE '2026-02-01');

INSERT INTO registro_financeiro (data, km_rodado, ganhos_brutos, preco_gasolina, litros_abastecidos, horas_trabalhadas)
SELECT DATE '2026-02-02', 110.0, 280.00, 6.30, 12.5, 6.0
WHERE NOT EXISTS (SELECT 1 FROM registro_financeiro WHERE data = DATE '2026-02-02');

INSERT INTO registro_financeiro (data, km_rodado, ganhos_brutos, preco_gasolina, litros_abastecidos, horas_trabalhadas)
SELECT DATE '2026-02-03', 132.0, 320.00, 6.29, 13.2, 7.0
WHERE NOT EXISTS (SELECT 1 FROM registro_financeiro WHERE data = DATE '2026-02-03');

INSERT INTO registro_financeiro (data, km_rodado, ganhos_brutos, preco_gasolina, litros_abastecidos, horas_trabalhadas)
SELECT DATE '2026-02-04', 90.0, 215.00, 6.22, 9.0, 4.8
WHERE NOT EXISTS (SELECT 1 FROM registro_financeiro WHERE data = DATE '2026-02-04');

INSERT INTO registro_financeiro (data, km_rodado, ganhos_brutos, preco_gasolina, litros_abastecidos, horas_trabalhadas)
SELECT DATE '2026-02-05', 150.0, 360.00, 6.35, 14.5, 8.0
WHERE NOT EXISTS (SELECT 1 FROM registro_financeiro WHERE data = DATE '2026-02-05');

INSERT INTO registro_financeiro (data, km_rodado, ganhos_brutos, preco_gasolina, litros_abastecidos, horas_trabalhadas)
SELECT DATE '2026-02-06', 75.0, 190.00, 6.18, 8.1, 4.2
WHERE NOT EXISTS (SELECT 1 FROM registro_financeiro WHERE data = DATE '2026-02-06');

INSERT INTO registro_financeiro (data, km_rodado, ganhos_brutos, preco_gasolina, litros_abastecidos, horas_trabalhadas)
SELECT DATE '2026-02-07', 140.0, 350.00, 6.40, 13.8, 7.5
WHERE NOT EXISTS (SELECT 1 FROM registro_financeiro WHERE data = DATE '2026-02-07');

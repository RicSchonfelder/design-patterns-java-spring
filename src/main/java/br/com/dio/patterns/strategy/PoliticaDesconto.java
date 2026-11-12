package br.com.dio.patterns.strategy;

import br.com.dio.patterns.domain.Pedido;

import java.math.BigDecimal;

/**
 * Strategy: cada politica de desconto e intercambiavel em tempo de execucao.
 */
public interface PoliticaDesconto {

    BigDecimal aplicar(Pedido pedido);

    String nome();
}

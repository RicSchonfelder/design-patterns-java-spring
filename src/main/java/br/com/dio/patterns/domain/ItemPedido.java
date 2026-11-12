package br.com.dio.patterns.domain;

import java.math.BigDecimal;

public record ItemPedido(String descricao, BigDecimal precoUnitario, int quantidade) {

    public ItemPedido {
        if (descricao == null || descricao.isBlank()) {
            throw new IllegalArgumentException("descricao obrigatoria");
        }
        if (precoUnitario == null || precoUnitario.signum() < 0) {
            throw new IllegalArgumentException("precoUnitario invalido");
        }
        if (quantidade <= 0) {
            throw new IllegalArgumentException("quantidade deve ser positiva");
        }
    }

    public BigDecimal subtotal() {
        return precoUnitario.multiply(BigDecimal.valueOf(quantidade));
    }
}

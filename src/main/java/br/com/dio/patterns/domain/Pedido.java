package br.com.dio.patterns.domain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public class Pedido {

    private final UUID id;
    private final TipoCliente tipoCliente;
    private final List<ItemPedido> itens;
    private final LocalDateTime criadoEm;

    public Pedido(TipoCliente tipoCliente, List<ItemPedido> itens) {
        this.id = UUID.randomUUID();
        this.tipoCliente = tipoCliente;
        this.itens = List.copyOf(itens);
        this.criadoEm = LocalDateTime.now();
    }

    public BigDecimal totalBruto() {
        return itens.stream()
                .map(ItemPedido::subtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public UUID getId() {
        return id;
    }

    public TipoCliente getTipoCliente() {
        return tipoCliente;
    }

    public List<ItemPedido> getItens() {
        return itens;
    }

    public LocalDateTime getCriadoEm() {
        return criadoEm;
    }
}

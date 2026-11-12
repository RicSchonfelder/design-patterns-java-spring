package br.com.dio.patterns.web.dto;

import br.com.dio.patterns.facade.CheckoutFacade;

import java.math.BigDecimal;
import java.util.UUID;

public record CheckoutResponse(UUID pedidoId,
                               BigDecimal totalBruto,
                               BigDecimal totalLiquido,
                               BigDecimal descontoAplicado,
                               String politicaAplicada) {

    public static CheckoutResponse de(CheckoutFacade.ResultadoCheckout r) {
        return new CheckoutResponse(r.pedidoId(), r.totalBruto(), r.totalLiquido(),
                r.descontoAplicado(), r.politicaAplicada());
    }
}

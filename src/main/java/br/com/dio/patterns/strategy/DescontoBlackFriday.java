package br.com.dio.patterns.strategy;

import br.com.dio.patterns.domain.Pedido;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DescontoBlackFriday implements PoliticaDesconto {

    private static final BigDecimal PERCENTUAL = new BigDecimal("0.30");
    private static final BigDecimal TETO = new BigDecimal("200.00");

    @Override
    public BigDecimal aplicar(Pedido pedido) {
        BigDecimal desconto = pedido.totalBruto().multiply(PERCENTUAL);
        if (desconto.compareTo(TETO) > 0) {
            desconto = TETO;
        }
        return pedido.totalBruto().subtract(desconto).max(BigDecimal.ZERO);
    }

    @Override
    public String nome() {
        return "DESCONTO_BLACK_FRIDAY_30";
    }
}

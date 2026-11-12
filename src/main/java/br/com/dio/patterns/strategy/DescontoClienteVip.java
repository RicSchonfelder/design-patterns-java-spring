package br.com.dio.patterns.strategy;

import br.com.dio.patterns.domain.Pedido;
import br.com.dio.patterns.domain.TipoCliente;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class DescontoClienteVip implements PoliticaDesconto {

    private static final BigDecimal PERCENTUAL = new BigDecimal("0.10");

    @Override
    public BigDecimal aplicar(Pedido pedido) {
        if (pedido.getTipoCliente() != TipoCliente.VIP) {
            return pedido.totalBruto();
        }
        return pedido.totalBruto().subtract(pedido.totalBruto().multiply(PERCENTUAL));
    }

    @Override
    public String nome() {
        return "DESCONTO_VIP_10";
    }
}

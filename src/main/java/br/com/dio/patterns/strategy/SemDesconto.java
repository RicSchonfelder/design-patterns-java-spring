package br.com.dio.patterns.strategy;

import br.com.dio.patterns.domain.Pedido;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class SemDesconto implements PoliticaDesconto {

    @Override
    public BigDecimal aplicar(Pedido pedido) {
        return pedido.totalBruto();
    }

    @Override
    public String nome() {
        return "SEM_DESCONTO";
    }
}

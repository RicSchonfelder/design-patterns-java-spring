package br.com.dio.patterns.strategy;

import br.com.dio.patterns.domain.Pedido;
import br.com.dio.patterns.domain.TipoCliente;
import org.springframework.stereotype.Component;

import java.time.Month;
import java.time.MonthDay;

/**
 * Escolhe a Strategy adequada para o pedido. A regra de negocio fica
 * isolada da calculadora, e novas politicas entram sem alterar o consumidor.
 */
@Component
public class SeletorDeDesconto {

    private final PoliticaDesconto semDesconto;
    private final PoliticaDesconto clienteVip;
    private final PoliticaDesconto blackFriday;

    public SeletorDeDesconto(SemDesconto semDesconto,
                             DescontoClienteVip clienteVip,
                             DescontoBlackFriday blackFriday) {
        this.semDesconto = semDesconto;
        this.clienteVip = clienteVip;
        this.blackFriday = blackFriday;
    }

    public PoliticaDesconto para(Pedido pedido, boolean promocaoAtiva) {
        if (promocaoAtiva) {
            return blackFriday;
        }
        if (pedido.getTipoCliente() == TipoCliente.VIP) {
            return clienteVip;
        }
        return semDesconto;
    }

    public boolean promocaoDeBlackFridayAtiva() {
        MonthDay hoje = MonthDay.now();
        return hoje.getMonth() == Month.NOVEMBER
                && hoje.getDayOfMonth() >= 20
                && hoje.getDayOfMonth() <= 30;
    }
}

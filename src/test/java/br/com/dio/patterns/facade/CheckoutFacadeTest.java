package br.com.dio.patterns.facade;

import br.com.dio.patterns.domain.ItemPedido;
import br.com.dio.patterns.domain.Pedido;
import br.com.dio.patterns.domain.TipoCliente;
import br.com.dio.patterns.singleton.RegistroDePedidos;
import br.com.dio.patterns.strategy.DescontoBlackFriday;
import br.com.dio.patterns.strategy.DescontoClienteVip;
import br.com.dio.patterns.strategy.SemDesconto;
import br.com.dio.patterns.strategy.SeletorDeDesconto;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class CheckoutFacadeTest {

    private CheckoutFacade facade(RegistroDePedidos registro) {
        SeletorDeDesconto seletor = new SeletorDeDesconto(
                new SemDesconto(), new DescontoClienteVip(), new DescontoBlackFriday());
        return new CheckoutFacade(registro, seletor);
    }

    @Test
    void registraPedidoEDevolveTotais() {
        RegistroDePedidos registro = new RegistroDePedidos();
        CheckoutFacade facade = facade(registro);

        Pedido pedido = new Pedido(TipoCliente.VIP,
                List.of(new ItemPedido("Curso", new BigDecimal("100.00"), 2)));

        CheckoutFacade.ResultadoCheckout resultado = facade.finalizar(pedido, false);

        assertThat(resultado.totalBruto()).isEqualByComparingTo("200.00");
        assertThat(resultado.totalLiquido()).isEqualByComparingTo("180.00");
        assertThat(resultado.descontoAplicado()).isEqualByComparingTo("20.00");
        assertThat(resultado.politicaAplicada()).isEqualTo("DESCONTO_VIP_10");
        assertThat(registro.quantidade()).isEqualTo(1);
    }

    @Test
    void registraMultiplosPedidosNoMesmoSingleton() {
        RegistroDePedidos registro = new RegistroDePedidos();
        CheckoutFacade facade = facade(registro);

        for (int i = 0; i < 3; i++) {
            facade.finalizar(new Pedido(TipoCliente.COMUM,
                    List.of(new ItemPedido("Ebook", new BigDecimal("30.00"), 1))), false);
        }

        assertThat(registro.quantidade()).isEqualTo(3);
    }
}

package br.com.dio.patterns.strategy;

import br.com.dio.patterns.domain.ItemPedido;
import br.com.dio.patterns.domain.Pedido;
import br.com.dio.patterns.domain.TipoCliente;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class PoliticaDescontoTest {

    private Pedido pedido(TipoCliente tipo, String preco) {
        return new Pedido(tipo, List.of(new ItemPedido("Curso", new BigDecimal(preco), 1)));
    }

    @Test
    void semDescontoMantemTotalBruto() {
        Pedido pedido = pedido(TipoCliente.COMUM, "100.00");
        assertThat(new SemDesconto().aplicar(pedido)).isEqualByComparingTo("100.00");
    }

    @Test
    void vipRecebeDezPorCento() {
        Pedido pedido = pedido(TipoCliente.VIP, "100.00");
        assertThat(new DescontoClienteVip().aplicar(pedido)).isEqualByComparingTo("90.00");
    }

    @Test
    void vipNaoAplicaDescontoParaClienteComum() {
        Pedido pedido = pedido(TipoCliente.COMUM, "100.00");
        assertThat(new DescontoClienteVip().aplicar(pedido)).isEqualByComparingTo("100.00");
    }

    @Test
    void blackFridayRespeitaTetoDeDuzentos() {
        Pedido pedido = pedido(TipoCliente.COMUM, "1000.00");
        assertThat(new DescontoBlackFriday().aplicar(pedido)).isEqualByComparingTo("800.00");
    }

    @Test
    void seletorPreferePromocaoQuandoAtiva() {
        Pedido pedido = pedido(TipoCliente.VIP, "100.00");
        SeletorDeDesconto seletor = new SeletorDeDesconto(
                new SemDesconto(), new DescontoClienteVip(), new DescontoBlackFriday());

        assertThat(seletor.para(pedido, true).nome()).isEqualTo("DESCONTO_BLACK_FRIDAY_30");
        assertThat(seletor.para(pedido, false).nome()).isEqualTo("DESCONTO_VIP_10");
    }
}

package br.com.dio.patterns.facade;

import br.com.dio.patterns.domain.Pedido;
import br.com.dio.patterns.singleton.RegistroDePedidos;
import br.com.dio.patterns.strategy.PoliticaDesconto;
import br.com.dio.patterns.strategy.SeletorDeDesconto;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.UUID;

/**
 * Facade: esconde a coordenacao entre dominio, Strategy e registro,
 * expondo uma unica operacao de checkout para o controller.
 */
@Service
public class CheckoutFacade {

    private final RegistroDePedidos registro;
    private final SeletorDeDesconto seletor;

    public CheckoutFacade(RegistroDePedidos registro, SeletorDeDesconto seletor) {
        this.registro = registro;
        this.seletor = seletor;
    }

    public ResultadoCheckout finalizar(Pedido pedido, boolean promocaoForcada) {
        boolean promocao = promocaoForcada || seletor.promocaoDeBlackFridayAtiva();
        PoliticaDesconto politica = seletor.para(pedido, promocao);

        BigDecimal bruto = pedido.totalBruto();
        BigDecimal liquido = politica.aplicar(pedido);

        registro.registrar(pedido);

        return new ResultadoCheckout(pedido.getId(), bruto, liquido, bruto.subtract(liquido), politica.nome());
    }

    public record ResultadoCheckout(UUID pedidoId,
                                    BigDecimal totalBruto,
                                    BigDecimal totalLiquido,
                                    BigDecimal descontoAplicado,
                                    String politicaAplicada) {
    }
}

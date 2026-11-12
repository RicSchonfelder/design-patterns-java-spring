package br.com.dio.patterns.web;

import br.com.dio.patterns.domain.ItemPedido;
import br.com.dio.patterns.domain.Pedido;
import br.com.dio.patterns.facade.CheckoutFacade;
import br.com.dio.patterns.web.dto.CheckoutRequest;
import br.com.dio.patterns.web.dto.CheckoutResponse;
import br.com.dio.patterns.web.dto.ItemRequest;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/checkout")
public class CheckoutController {

    private final CheckoutFacade facade;

    public CheckoutController(CheckoutFacade facade) {
        this.facade = facade;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CheckoutResponse finalizar(@Valid @RequestBody CheckoutRequest request) {
        Pedido pedido = new Pedido(request.tipoCliente(), toItens(request.itens()));
        return CheckoutResponse.de(facade.finalizar(pedido, request.promocaoForcada()));
    }

    @GetMapping("/politicas")
    public List<String> politicas() {
        return List.of("SEM_DESCONTO", "DESCONTO_VIP_10", "DESCONTO_BLACK_FRIDAY_30");
    }

    private List<ItemPedido> toItens(List<ItemRequest> itens) {
        return itens.stream()
                .map(i -> new ItemPedido(i.descricao(), i.precoUnitario(), i.quantidade()))
                .toList();
    }
}

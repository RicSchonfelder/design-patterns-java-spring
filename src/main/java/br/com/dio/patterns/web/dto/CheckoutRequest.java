package br.com.dio.patterns.web.dto;

import br.com.dio.patterns.domain.TipoCliente;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record CheckoutRequest(
        @NotNull TipoCliente tipoCliente,
        @NotEmpty List<@Valid ItemRequest> itens,
        boolean promocaoForcada) {
}

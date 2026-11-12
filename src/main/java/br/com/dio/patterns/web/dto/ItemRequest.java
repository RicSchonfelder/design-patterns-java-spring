package br.com.dio.patterns.web.dto;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public record ItemRequest(
        @NotBlank String descricao,
        @NotNull @DecimalMin("0.0") BigDecimal precoUnitario,
        @Min(1) int quantidade) {
}

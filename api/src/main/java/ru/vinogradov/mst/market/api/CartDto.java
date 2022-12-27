package ru.vinogradov.mst.market.api;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.util.List;


@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CartDto {
    private List<CartItemDto> items;
    private BigDecimal totalPrice;
}

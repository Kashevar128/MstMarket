package ru.vinogradov.mst.market.cart.converters;

import org.springframework.stereotype.Component;
import ru.vinogradov.mst.market.api.CartItemDto;
import ru.vinogradov.mst.market.cart.utils.CartItem;

@Component
public class CartItemConverter {
    public CartItemDto entityToDto(CartItem c) {
        return new CartItemDto(c.getProductId(), c.getProductTitle(), c.getQuantity(), c.getPricePerProduct(), c.getPrice());
    }
}

package ru.vinogradov.mst.market.core.mappers;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import ru.vinogradov.mst.market.api.OrderDto;
import ru.vinogradov.mst.market.core.entities.Order;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class OrderMapper {
    private final OrderItemMapper orderItemMapper;

    public OrderDto mapOrderToOrderDto (Order order) {
        return OrderDto.builder()
                .id(order.getId())
                .items(order.getItems()
                        .stream()
                        .map(orderItemMapper::mapOrderItemToOrderItemDto)
                        .collect(Collectors.toList()))
                .totalPrice(order.getTotalPrice())
                .build();
    }

}

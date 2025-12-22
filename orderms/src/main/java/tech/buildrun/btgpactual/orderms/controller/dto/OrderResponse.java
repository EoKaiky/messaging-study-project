package tech.buildrun.btgpactual.orderms.controller.dto;

import tech.buildrun.btgpactual.orderms.entities.OrderEntity;

import java.math.BigDecimal;

public record OrderResponse(String orderId,
                            Long customerId,
                            BigDecimal total){

    public static OrderResponse fromEntity(OrderEntity orderEntity){
        return new OrderResponse(orderEntity.getOrderId(), orderEntity.getCustomerId(), orderEntity.getTotal());
    }
}

package tech.buildrun.btgpactual.orderms.listener.dto;

import java.util.List;

public record OrderCreatedEvent (Long codeOrder,
                                Long clientCode,
                                List<OrderItemEvent> itens){
}

package tech.buildrun.btgpactual.orderms.service;

import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import tech.buildrun.btgpactual.orderms.entities.OrderEntity;
import tech.buildrun.btgpactual.orderms.entities.OrderItem;
import tech.buildrun.btgpactual.orderms.listener.dto.OrderCreatedEvent;
import tech.buildrun.btgpactual.orderms.repository.OrderRepository;

import java.math.BigDecimal;
import java.util.List;

@Service
public class OrderService {

    private final OrderRepository orderRepository;

    public OrderService(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public void save(OrderCreatedEvent event) {

        var entity = new OrderEntity();
        entity.setOrderId(String.valueOf(event.codeOrder()));
        entity.setCustomerId(event.clientCode());
        entity.setTotal(getTotal(event));

        entity.setItems(getOrderItems(event));
        orderRepository.save(entity);
    }

    private BigDecimal getTotal(OrderCreatedEvent event) {
        return event.itens()
                .stream()
                .map(i -> i.price().multiply(BigDecimal.valueOf(i.quantity())))
                .reduce(BigDecimal::add)
                .orElse(BigDecimal.ZERO);
    }

    private static List<OrderItem> getOrderItems(OrderCreatedEvent event) {
        return event.itens().stream()
                .map(i -> new OrderItem(i.product(), i.quantity(), i.price()))
                .toList();

    }

}
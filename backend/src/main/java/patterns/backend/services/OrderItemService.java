package patterns.backend.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import patterns.backend.domain.OrderItem;
import patterns.backend.exception.OrderItemNotFoundException;
import patterns.backend.repositories.OrderItemRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Getter
@Setter
public class OrderItemService {

    @Autowired
    private OrderItemRepository orderItemRepository;

    public OrderItem findOrderItemById(Long id) {
        Optional<OrderItem> optionalOrderItem = orderItemRepository.findById(id);
        if (!optionalOrderItem.isPresent()) {
            throw new OrderItemNotFoundException(id);
        } else {
            return optionalOrderItem.get();
        }
    }

    public OrderItem saveOrderItem(OrderItem orderItem) {
        OrderItem savedOrderItem;
        if (orderItem != null) {
            savedOrderItem = orderItemRepository.save(orderItem);
        } else {
            throw new IllegalArgumentException();
        }
        return savedOrderItem;
    }

    public void deleteOrderItemById(Long id) {
        OrderItem orderItem = findOrderItemById(id);
        orderItemRepository.delete(orderItem);
    }

    public long countOrderItem() {
        return orderItemRepository.count();
    }

    public List<OrderItem> findAll() {
        return StreamSupport.stream(orderItemRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}

package patterns.backend.services;

import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import patterns.backend.domain.Orders;
import patterns.backend.exception.OrdersNotFoundException;
import patterns.backend.repositories.OrdersRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@Getter
@Setter
public class OrdersService {

    @Autowired
    private OrdersRepository ordersRepository;

    public Orders findOrdersById(Long id) {
        Optional<Orders> optionalOrders = ordersRepository.findById(id);
        if (!optionalOrders.isPresent()) {
            throw new OrdersNotFoundException(id);
        } else {
            return optionalOrders.get();
        }
    }

    public Orders saveOrders(Orders order) {
        Orders savedOrder;
        if (order != null) {
            savedOrder = ordersRepository.save(order);
        } else {
            throw new IllegalArgumentException();
        }
        return savedOrder;
    }

    public void deleteOrdersById(Long id) {
        Orders orders = findOrdersById(id);
        ordersRepository.delete(orders);
    }

    public long countOrders() {
        return ordersRepository.count();
    }

    public List<Orders> findAll() {
        return StreamSupport.stream(ordersRepository.findAll().spliterator(), false)
                .collect(Collectors.toList());
    }

}

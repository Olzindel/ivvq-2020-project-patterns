package patterns.backend.dto;

import lombok.Getter;
import lombok.Setter;
import patterns.backend.domain.Order;
import patterns.backend.domain.Product;

@Getter
@Setter
public class OrderItemDto {

    private int quantity;

    private Product product;

    private Order order;
}

package patterns.backend.model;

import lombok.Getter;
import lombok.Setter;
import patterns.backend.dto.OrderItemDto;

import java.util.List;

@Getter
@Setter
public class OrderForm {
    List<OrderItemDto> orders;
}

package patterns.backend.dto;

import lombok.Getter;
import lombok.Setter;
import patterns.backend.domain.OrderStatus;
import patterns.backend.domain.User;
import patterns.backend.dto.validation.EnumValue;

import java.time.LocalDate;


@Getter
@Setter
public class OrderDto {

    private Long id;

    @EnumValue(value = OrderStatus.class)
    private String orderStatus;

    private LocalDate createdAt;

    private User user;

}

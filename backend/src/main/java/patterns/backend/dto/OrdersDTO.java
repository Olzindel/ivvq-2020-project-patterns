package patterns.backend.dto;

import lombok.Getter;
import lombok.Setter;
import patterns.backend.domain.User;

import java.time.LocalDate;


@Getter
@Setter
public class OrdersDTO {

    private Long id;

    private String status;

    private LocalDate createdAt;

    private User user;

}

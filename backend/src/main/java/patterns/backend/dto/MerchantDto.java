package patterns.backend.dto;

import lombok.Getter;
import lombok.Setter;
import patterns.backend.domain.User;

import java.time.LocalDate;

@Setter
@Getter
public class MerchantDto {

    private Long id;

    private String name;

    private LocalDate createdAt;

    private User admin;

}

package patterns.backend.dto;

import lombok.Getter;
import lombok.Setter;
import patterns.backend.domain.Merchant;

import java.time.LocalDate;


@Getter
@Setter
public class ProductDto {

    private Long id;

    private String name;

    private double price;

    private String status;

    private LocalDate createdAt;

    private String imageLink;

    private Merchant merchant;

}

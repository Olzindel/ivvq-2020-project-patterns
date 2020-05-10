package patterns.backend.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@Entity(name = "products")
public class Product {
    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @DecimalMin("0.0")
    private double price;

    @NotEmpty
    private String status;

    private String description;

    @PastOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt;

    @OneToMany(mappedBy = "product", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    private List<ImageLink> imageLinks;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH})
    private Merchant merchant;

    public Product(String name, double price, String status, String description, LocalDate createdAt, Merchant merchant) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
        this.merchant = merchant;
        this.imageLinks = new ArrayList<>();
    }

    public void addImageLink(ImageLink imageLink) {
        if (!imageLinks.contains(imageLink))
            imageLinks.add(imageLink);
    }
}

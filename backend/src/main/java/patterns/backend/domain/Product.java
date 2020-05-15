package patterns.backend.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;
import patterns.backend.exception.NotEnoughStockException;

import javax.persistence.*;
import javax.validation.constraints.*;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Transactional
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

    private ProductStatus status;

    private String description;

    @NotNull
    @Min(0)
    private int stock;

    @PastOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "product", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<ImageLink> imageLinks = new HashSet<>();

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH})
    private Merchant merchant;

    public Product(String name, double price, ProductStatus status, String description, int stock, LocalDate createdAt, Merchant merchant) {
        this.name = name;
        this.price = price;
        this.status = status;
        this.createdAt = createdAt;
        this.merchant = merchant;
        this.description = description;
        this.stock = stock;
    }

    public void addImageLink(ImageLink imageLink) {
        imageLinks.add(imageLink);
    }

    public void decreaseStock(int value) {
        if (stock >= value) {
            this.stock = this.stock - value;
        } else {
            throw new NotEnoughStockException(id, name);
        }
    }
}

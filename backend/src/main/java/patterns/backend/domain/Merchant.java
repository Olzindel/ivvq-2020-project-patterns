package patterns.backend.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Transactional
@Entity(name = "merchants")
public class Merchant {

    @Id
    @GeneratedValue
    private Long id;

    @NotNull
    @NotEmpty
    private String name;

    @PastOrPresent
    @NotNull
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt;

    @ManyToOne(cascade = {CascadeType.MERGE, CascadeType.REFRESH, CascadeType.PERSIST, CascadeType.DETACH})
    @NotNull
    @Valid
    private User admin;

    @JsonIgnore
    @OneToMany(mappedBy = "merchant", cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<Product> products = new HashSet<>();

    public Merchant(String name, LocalDate createdAt, User admin) {
        this.name = name;
        this.createdAt = createdAt;
        this.admin = admin;
    }

    public void addProduct(Product product) {
        products.add(product);
    }
}

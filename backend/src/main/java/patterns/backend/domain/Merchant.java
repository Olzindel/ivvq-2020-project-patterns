package patterns.backend.domain;


import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;

@NoArgsConstructor
@Getter
@Setter
@Entity
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

    @OneToOne(cascade = CascadeType.ALL)
    @NotNull
    @Valid
    private User admin;

    public Merchant(String name, LocalDate createdAt, User admin) {
        this.name = name;
        this.createdAt = createdAt;
        this.admin = admin;
    }
}

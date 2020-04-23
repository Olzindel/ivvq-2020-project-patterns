package patterns.backend.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PastOrPresent;
import java.time.LocalDate;


@NoArgsConstructor
@Getter
@Setter
@Entity
public class Orders {
    @Id
    @GeneratedValue
    private Long id;

    @NotEmpty
    @NotNull
    private String status;

    @PastOrPresent
    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt;

    @OneToOne(cascade = CascadeType.ALL)
    private User user;

    public Orders(String status, LocalDate createdAt, User user) {
        this.status = status;
        this.createdAt = createdAt;
        this.user = user;
    }

}

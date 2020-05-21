package patterns.backend.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@Transactional
@Entity(name = "users")
public class User {

    @Id
    @GeneratedValue
    private Long id;

    private String username;

    private String password;

    private String firstName;

    private String lastName;

    @Email
    @NotNull
    private String email;

    @Pattern(regexp = "[MF]")
    @NotNull
    private String gender;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate dateOfBirth;

    private String street;

    @Pattern(regexp = "^(([0-8][0-9])|(9[0-5]))[0-9]{3}$")
    private String postalCode;

    private String city;

    @JsonFormat(pattern = "dd/MM/yyyy")
    private LocalDate createdAt;

    @JsonIgnore
    @OneToMany(mappedBy = "admin", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<Merchant> merchants = new HashSet<>();

    @JsonIgnore
    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.DETACH})
    private Set<Order> orders = new HashSet<>();

    public User(String username, String password, String firstName, String lastName, String email, String gender, LocalDate dateOfBirth, String street, String postalCode, String city, LocalDate createdAt) {
        this.username = username;
        this.password = password;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.gender = gender;
        this.dateOfBirth = dateOfBirth;
        this.createdAt = createdAt;
        this.street = street;
        this.postalCode = postalCode;
        this.city = city;
    }

    public void addMerchant(Merchant merchant) {
        merchants.add(merchant);
    }

    public void addOrder(Order order) {
        orders.add(order);
    }
}

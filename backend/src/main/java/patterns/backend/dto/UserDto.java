package patterns.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class UserDto {

    private Long id;

    private String fullName;

    private String email;

    private String gender;

    private LocalDate dateOfBirth;

    private LocalDate createdAt;

}

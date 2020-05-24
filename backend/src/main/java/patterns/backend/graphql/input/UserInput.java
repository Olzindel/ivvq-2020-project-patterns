package patterns.backend.graphql.input;

import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.transaction.annotation.Transactional;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Transactional
public class UserInput {
  String firstName;
  String lastName;
  String email;
  String gender;
  String street;
  String postalCode;
  String city;
  java.util.List<Long> merchantIds;
  List<Long> orderIds;
}

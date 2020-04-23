package patterns.backend.exception;

import lombok.Getter;

@Getter
public class UserNotFoundException extends RuntimeException {

    private final Long id;

    public UserNotFoundException(Long id) {
        super("User could not be found with id : " + id);
        this.id = id;
    }

}

package patterns.backend.exception;

public class OrdersNotFoundException extends RuntimeException {
    private final Long id;

    public OrdersNotFoundException(Long id) {
        super("Orders could not be found with id : " + id);
        this.id = id;
    }
}

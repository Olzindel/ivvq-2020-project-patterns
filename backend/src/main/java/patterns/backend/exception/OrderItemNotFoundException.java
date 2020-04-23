package patterns.backend.exception;

public class OrderItemNotFoundException extends RuntimeException {
    private final Long id;

    public OrderItemNotFoundException(Long id) {
        super("OrderItem could not be found with id : " + id);
        this.id = id;
    }
}

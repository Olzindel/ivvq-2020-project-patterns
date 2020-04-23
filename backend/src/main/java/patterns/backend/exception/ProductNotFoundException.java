package patterns.backend.exception;

public class ProductNotFoundException extends RuntimeException {
    private final Long id;

    public ProductNotFoundException(Long id) {
        super("Product could not be found with id : " + id);
        this.id = id;
    }
}

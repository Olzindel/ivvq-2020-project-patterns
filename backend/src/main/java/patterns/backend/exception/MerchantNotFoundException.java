package patterns.backend.exception;

public class MerchantNotFoundException extends RuntimeException {
    private final Long id;

    public MerchantNotFoundException(Long id) {
        super("Merchant could not be found with id : " + id);
        this.id = id;
    }
}

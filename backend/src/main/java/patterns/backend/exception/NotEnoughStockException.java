package patterns.backend.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class NotEnoughStockException extends RuntimeException implements GraphQLError {
    private final Long id;

    public NotEnoughStockException(Long id, String productName) {
        super("Not enough stock for this product : " + productName + " (" + id + ")");
        this.id = id;
    }

    @Override
    public List<SourceLocation> getLocations() {
        return null;
    }

    @Override
    public ErrorType getErrorType() {
        return null;
    }

    @Override
    public Map<String, Object> getExtensions() {
        return Collections.singletonMap("invalidId", id);
    }
}

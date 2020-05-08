package patterns.backend.exception;

import graphql.ErrorType;
import graphql.GraphQLError;
import graphql.language.SourceLocation;

import java.util.Collections;
import java.util.List;
import java.util.Map;

public class OrderItemNotFoundException extends RuntimeException implements GraphQLError {
    private final Long id;

    public OrderItemNotFoundException(Long id) {
        super("OrderItem could not be found with id : " + id);
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

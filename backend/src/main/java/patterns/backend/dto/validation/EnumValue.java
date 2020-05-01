package patterns.backend.dto.validation;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.ReportAsSingleViolation;
import java.lang.annotation.*;

@ReportAsSingleViolation
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EnumValueValidator.class)
@Target(ElementType.FIELD)
public @interface EnumValue {

    Class<? extends Enum> value();

    String message() default "The enum value enter is not valid.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

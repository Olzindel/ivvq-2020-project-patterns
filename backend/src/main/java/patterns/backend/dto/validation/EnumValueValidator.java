package patterns.backend.dto.validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

public class EnumValueValidator implements ConstraintValidator<EnumValue, String> {

   private Class<? extends Enum> enumClass;
   private String message;

   @Override
   public void initialize(final EnumValue constraintAnnotation) {
      this.enumClass = constraintAnnotation.value();
      this.message = constraintAnnotation.message();
   }

   public boolean isValid(final String value, final ConstraintValidatorContext context) {
      if (value == null) {
         return true;
      }

      for (final Enum enumValue : enumClass.getEnumConstants()) {
         if (enumValue.toString().equals(value)) {
            return true;
         }
      }
      return false;
   }
}

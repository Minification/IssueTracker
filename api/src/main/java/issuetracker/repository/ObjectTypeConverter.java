package issuetracker.repository;

import issuetracker.domain.model.ObjectType;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.stream.Stream;

@Converter(autoApply = true)
public class ObjectTypeConverter implements AttributeConverter<ObjectType, String> {

    @Override
    public String convertToDatabaseColumn(final ObjectType objectType) {
        return objectType.getUniqueId();
    }

    @Override
    public ObjectType convertToEntityAttribute(final String s) {
        return Stream.of(ObjectType.values())
                .filter(objectType -> objectType.getUniqueId().equals(s))
                .findFirst()
                .orElseThrow(IllegalArgumentException::new);
    }

}

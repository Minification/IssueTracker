package issuetracker.domain.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Enumerated;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ObjectPermission {

    private Long userId;

    private Long objectId;

    private ObjectType objectType;

}

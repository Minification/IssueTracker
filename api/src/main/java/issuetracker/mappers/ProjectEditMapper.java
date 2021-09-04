package issuetracker.mappers;

import issuetracker.domain.Project;
import issuetracker.dto.CreateProjectRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProjectEditMapper {

     public abstract Project create(CreateProjectRequest request);

}

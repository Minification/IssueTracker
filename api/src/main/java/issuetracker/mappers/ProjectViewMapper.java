package issuetracker.mappers;

import issuetracker.domain.Project;
import issuetracker.dto.ProjectView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class ProjectViewMapper {

    public abstract ProjectView projectToView(final Project project);

}

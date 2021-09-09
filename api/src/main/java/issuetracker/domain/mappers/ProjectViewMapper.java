package issuetracker.domain.mappers;

import issuetracker.domain.model.Project;
import issuetracker.domain.dto.ProjectView;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public abstract class ProjectViewMapper {

    public abstract ProjectView projectToView(final Project project);

}

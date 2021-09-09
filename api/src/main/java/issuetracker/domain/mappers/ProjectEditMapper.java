package issuetracker.domain.mappers;

import issuetracker.domain.model.Project;
import issuetracker.domain.dto.CreateProjectRequest;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.WARN)
public abstract class ProjectEditMapper {

     public abstract Project create(CreateProjectRequest request);

}

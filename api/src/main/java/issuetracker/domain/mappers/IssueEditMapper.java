package issuetracker.domain.mappers;

import issuetracker.domain.model.Issue;
import issuetracker.domain.dto.CreateIssueRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class IssueEditMapper {

    public abstract Issue create(final CreateIssueRequest request);

}

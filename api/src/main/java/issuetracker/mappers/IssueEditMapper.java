package issuetracker.mappers;

import issuetracker.domain.Issue;
import issuetracker.dto.CreateIssueRequest;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class IssueEditMapper {

    public abstract Issue create(final CreateIssueRequest request);

}

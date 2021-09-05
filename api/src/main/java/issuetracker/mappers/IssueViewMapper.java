package issuetracker.mappers;

import issuetracker.domain.Issue;
import issuetracker.dto.IssueView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class IssueViewMapper {

    public abstract IssueView toIssueView(final Issue issue);

}

package issuetracker.domain.mappers;

import issuetracker.domain.model.Issue;
import issuetracker.domain.dto.IssueView;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public abstract class IssueViewMapper {

    public abstract IssueView toIssueView(final Issue issue);

}

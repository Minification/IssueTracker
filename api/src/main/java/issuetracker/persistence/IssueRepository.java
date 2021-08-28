package issuetracker.persistence;

import issuetracker.domain.Issue;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface IssueRepository {

    default IssueRepository create(final Connection connection) {
        return new JdbcIssueRepository(connection);
    }

    Optional<Issue> findById(final long id);

    List<Issue> findByUser(final long userId);

    List<Issue> findByUsername(final String username);

    Issue create(final Issue issue);

}

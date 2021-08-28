package issuetracker.persistence;

import issuetracker.domain.Issue;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public class JdbcIssueRepository implements IssueRepository {

    private final Connection connection;

    JdbcIssueRepository(final Connection connection) {
        this.connection = connection;
    }

    @Override
    public Optional<Issue> findById(long id) {
        return Optional.empty();
    }

    @Override
    public List<Issue> findByUser(long userId) {
        return null;
    }

    @Override
    public List<Issue> findByUsername(String username) {
        return null;
    }

    @Override
    public Issue create(Issue issue) {
        return null;
    }
}

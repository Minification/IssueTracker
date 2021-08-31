package issuetracker.persistence;

import issuetracker.domain.Issue;
import issuetracker.domain.Project;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ProjectRepository  extends JpaRepository<Project, Long> {

    Optional<Project> getByName(String name);

}

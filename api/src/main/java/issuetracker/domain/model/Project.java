package issuetracker.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Entity
@Data
public class Project implements Serializable, Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "column_id")
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name="account_id", nullable=false)
    private Account owner;

    @OneToMany(mappedBy="project")
    private List<Issue> issues;

    public void addIssue(final Issue issue) {
        issues.add(issue);
        issue.setProject(this);
    }

}

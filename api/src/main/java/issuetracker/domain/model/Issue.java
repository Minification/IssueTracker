package issuetracker.domain.model;

import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;

@Entity
@Data
public class Issue implements Serializable, Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "issue_id")
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "account_id", nullable = false)
    private Account owner;

    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

}

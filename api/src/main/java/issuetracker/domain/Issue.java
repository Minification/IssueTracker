package issuetracker.domain;

import javax.persistence.*;

@Entity
public class Issue {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String title;

    private String content;

    @ManyToOne
    @JoinColumn(name = "OWNER_ID")
    private Account owner;

    @ManyToOne
    @JoinColumn(name = "PROJECT_ID")
    private Project project;

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getContent() {
        return content;
    }

    public Account getOwner() {
        return owner;
    }

    public Project getProject() {
        return project;
    }
}

package issuetracker.domain.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
public class Account implements UserDetails, Serializable, Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "account_id")
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @OneToMany(mappedBy="owner")
    private List<Project> projects;

    @OneToMany(mappedBy="owner")
    private List<Issue> issues;

    private boolean enabled = true;

    public void addProject(final Project project) {
        projects.add(project);
        project.setOwner(this);
    }

    public void addIssue(final Issue issue) {
        issues.add(issue);
        issue.setOwner(this);
    }

    public Account(final String email, final String password) {
        this.email = email;
        this.password = password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return enabled;
    }

    @Override
    public boolean isAccountNonLocked() {
        return enabled;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return enabled;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of();
    }
}

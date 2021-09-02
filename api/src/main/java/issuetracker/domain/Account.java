package issuetracker.domain;

import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Data
public class Account implements UserDetails, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    @ElementCollection(targetClass = Long.class)
    private List<Long> projectIds;

    @ElementCollection(targetClass = Long.class)
    private List<Long> issueIds;

    @ElementCollection(targetClass = Role.class)
    private final Set<Role> authorities = new HashSet<>();

    private boolean enabled = true;

    public Account() {

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

}

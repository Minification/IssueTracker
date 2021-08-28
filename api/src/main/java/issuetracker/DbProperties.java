package issuetracker;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@ConfigurationProperties(prefix = "db")
@Configuration("dbProperties")
public class DbProperties {

    private String user;

    private String password;

    public String getUser() {
        return user;
    }

    public String getPassword() {
        return password;
    }
}

package issuetracker.configuration.security;

import org.springframework.security.access.PermissionEvaluator;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
public class ObjectPermissionEvaluator implements PermissionEvaluator {

    @Override
    public boolean hasPermission(final Authentication authentication, final Object domainObject, final Object permission) {
        if (authentication == null) {
            return false;
        }

        return false;
    }

    @Override
    public boolean hasPermission(final Authentication authentication, final Serializable serializable, final String s, final Object o) {
        return false;
    }
}

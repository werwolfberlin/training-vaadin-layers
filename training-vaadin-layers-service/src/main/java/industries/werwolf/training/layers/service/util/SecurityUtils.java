package industries.werwolf.training.layers.service.util;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

public class SecurityUtils {

    public static User getUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User user? user : null;
    }
}

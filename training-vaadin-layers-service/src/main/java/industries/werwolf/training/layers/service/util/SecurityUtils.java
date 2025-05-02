package industries.werwolf.training.layers.service.util;

import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;

import java.util.function.Supplier;

public class SecurityUtils {

    public static User getUser() {
        return SecurityContextHolder.getContext().getAuthentication().getPrincipal() instanceof User user? user : null;
    }

    public static void doInContext(SecurityContext outerContext, Runnable runnable){
        SecurityContext threadContext = SecurityContextHolder.getContext();
        SecurityContextHolder.setContext(outerContext);
        try {
            // execute runnable
            runnable.run();
        }
        finally {
            SecurityContextHolder.setContext(threadContext);
        }
    }

    public static <T> T doInContext(SecurityContext outerContext, Supplier<T> supplier){
        SecurityContext threadContext = SecurityContextHolder.getContext();
        SecurityContextHolder.setContext(outerContext);
        try {
            // execute supplier
            return supplier.get();
        }
        finally {
            SecurityContextHolder.setContext(threadContext);
        }
    }

    public static SecurityContext getContext() {
        return SecurityContextHolder.getContext();
    }
}

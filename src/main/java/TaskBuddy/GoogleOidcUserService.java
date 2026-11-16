package TaskBuddy;

import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserService;
import org.springframework.security.oauth2.client.oidc.userinfo.OidcUserRequest;
import org.springframework.security.oauth2.core.oidc.user.OidcUser;
import org.springframework.security.oauth2.core.oidc.user.DefaultOidcUser;
import org.springframework.stereotype.Service;
import org.springframework.security.crypto.password.PasswordEncoder;
import java.util.UUID;

@Service
public class GoogleOidcUserService extends OidcUserService {
    private final UserRepository users;
    private final PasswordEncoder encoder;

    public GoogleOidcUserService(UserRepository users, PasswordEncoder encoder) {
        this.users = users;
        this.encoder = encoder;
    }

    @Override
    public OidcUser loadUser(OidcUserRequest request) {
        OidcUser googleUser = super.loadUser(request);
        String email = googleUser.getEmail();
        if (email == null || email.isBlank()) {
            throw new IllegalStateException("Google did not provide an email address.");
        }
        String name = googleUser.getFullName();
        if (name == null || name.isBlank()) name = email;
        final String displayName = name;
        users.findByEmailIgnoreCase(email).orElseGet(() ->
            users.save(new UserAccount(email.toLowerCase(), encoder.encode(UUID.randomUUID().toString()),
                displayName, "GOOGLE"))
        );
        // Use email as the authenticated principal name so task ownership is consistent.
        return new DefaultOidcUser(googleUser.getAuthorities(), googleUser.getIdToken(),
            googleUser.getUserInfo(), "email");
    }
}

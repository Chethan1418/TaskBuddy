package TaskBuddy;

import jakarta.persistence.*;

@Entity
@Table(name = "app_users")
public class UserAccount {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable=false, unique=true, length=190)
    private String email;
    @Column(nullable=false)
    private String password;
    @Column(nullable=false, length=80)
    private String displayName;
    @Column(nullable=false, length=20)
    private String provider = "LOCAL";

    protected UserAccount() {}
    public UserAccount(String email, String password, String displayName, String provider) {
        this.email=email; this.password=password; this.displayName=displayName; this.provider=provider;
    }
    public Long getId(){return id;}
    public String getEmail(){return email;}
    public String getPassword(){return password;}
    public String getDisplayName(){return displayName;}
    public String getProvider(){return provider;}
}

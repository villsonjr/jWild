package br.dev.ulk.animalz.domain.models;

import br.dev.ulk.animalz.domain.enumerations.StatusEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "`USERS`")
public class User extends AbstractEntity implements UserDetails {

    @Column(name = "FULLNAME",
            nullable = false)
    private String name;

    @Column(name = "EMAIL",
            nullable = false)
    private String email;

    @Column(name = "USERNAME",
            nullable = false)
    private String username;

    @Column(name = "PASSWORD",
            nullable = false)
    private String password;

    @ManyToMany(fetch = FetchType.EAGER,
            cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @JoinTable(
            name = "USER_ROLE",
            joinColumns = @JoinColumn(name = "USER_ID"),
            inverseJoinColumns = @JoinColumn(name = "ROLE_ID")
    )
    private Set<Role> roles;

    @Enumerated(EnumType.STRING)
    @Column(name = "STATUS",
            nullable = false)
    private StatusEnum status;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<SimpleGrantedAuthority> authorities = new HashSet<>();
        for (Role role : this.roles) {
            authorities.add(new SimpleGrantedAuthority("ROLE_" + role.getRole().getDescription()));
        }
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return status != StatusEnum.EXPIRED;
    }

    @Override
    public boolean isAccountNonLocked() {
        return status != StatusEnum.BLOCKED;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return status != StatusEnum.CREDENTIALS_EXPIRED;
    }

    @Override
    public boolean isEnabled() {
        return status == StatusEnum.ACTIVE;
    }

}
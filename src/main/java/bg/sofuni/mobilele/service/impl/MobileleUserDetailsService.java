package bg.sofuni.mobilele.service.impl;

import bg.sofuni.mobilele.model.entity.UserRoleEntity;
import bg.sofuni.mobilele.model.enums.UserRoleEnum;
import bg.sofuni.mobilele.model.user.MobileleUserDetails;
import bg.sofuni.mobilele.model.entity.UserEntity;
import bg.sofuni.mobilele.repository.UserRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;

public class MobileleUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

    public MobileleUserDetailsService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return   userRepository
                .findByEmail(email)
                .map(MobileleUserDetailsService::map)
                .orElseThrow(() -> new UsernameNotFoundException("User with email " + email + " not found!"));
    }

    private static UserDetails map(UserEntity userEntity) {
        return new MobileleUserDetails(
                userEntity.getUuid(),  // UUID instead of email for security reasons
                userEntity.getEmail(),
                userEntity.getPassword(),
                userEntity.getRoles().stream().map(UserRoleEntity::getRole).map(MobileleUserDetailsService::map).toList(),
                userEntity.getFirstName(),
                userEntity.getLastName()
        );
    }

    private static GrantedAuthority map(UserRoleEnum role) {
        return new SimpleGrantedAuthority(
                "ROLE_" + role
        );
    }
}

package bg.sofuni.mobilele.service.impl;

import bg.sofuni.mobilele.model.entity.UserEntity;
import bg.sofuni.mobilele.model.entity.UserRoleEntity;
import bg.sofuni.mobilele.model.enums.UserRoleEnum;
import bg.sofuni.mobilele.model.user.MobileleUserDetails;
import bg.sofuni.mobilele.repository.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.List;
import java.util.Optional;

class MobileleUserDetailServiceTest {
    private static final String TEST_EMAIL = "user@example.com";
    private static final String NOT_EXISTENT_EMAIL = "noone@example.com";

    private MobileleUserDetailsService toTest;
    @Mock
    private UserRepository mockUserRepository;

    @BeforeEach
    void setUp() {
        toTest = new MobileleUserDetailsService(mockUserRepository);
    }

    @Test
    void testLoadUserByUsername_UserFound() {

        UserEntity testUser = new UserEntity();
        testUser.setEmail(TEST_EMAIL);
        testUser.setFirstName("Pesho");
        testUser.setLastName("Petrov");

        Mockito.when(mockUserRepository.findByEmail(TEST_EMAIL))
                .thenReturn(Optional.of(testUser));


        UserDetails userDetails = toTest.loadUserByUsername(TEST_EMAIL);

        Assertions.assertInstanceOf(MobileleUserDetails.class, userDetails);

        MobileleUserDetails mobileleUserDetails = (MobileleUserDetails) userDetails;
        Assertions.assertEquals(TEST_EMAIL, userDetails.getUsername());
        Assertions.assertEquals(testUser.getFirstName(), mobileleUserDetails.getFirstName());
        Assertions.assertEquals(testUser.getLastName(), mobileleUserDetails.getLastName());

        Assertions.assertEquals(2, userDetails.getAuthorities().size());

        Optional<? extends GrantedAuthority> admin = userDetails.getAuthorities().stream()
                .filter(a -> "ROLE_ADMIN".equals(a.getAuthority())).findAny();
        Assertions.assertTrue(admin.isPresent());

        Optional<? extends GrantedAuthority> user = userDetails.getAuthorities().stream()
                .filter(a -> "ROLE_USER".equals(a.getAuthority())).findAny();
        Assertions.assertTrue(user.isPresent());
    }

    @Test
    void testLoadUserByUsername_UserNotFound() {
        Assertions.assertThrows(UsernameNotFoundException.class, () ->  {
            toTest.loadUserByUsername(NOT_EXISTENT_EMAIL);
        });
    }
}

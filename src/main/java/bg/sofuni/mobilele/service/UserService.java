package bg.sofuni.mobilele.service;

import bg.sofuni.mobilele.model.dto.UserLoginDTO;
import bg.sofuni.mobilele.model.dto.UserRegistrationDTO;
import bg.sofuni.mobilele.model.user.MobileleUserDetails;

import java.util.Optional;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistration);
    Optional<MobileleUserDetails> getCurrentUser();

}

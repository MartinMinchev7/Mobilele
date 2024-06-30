package bg.sofuni.mobilele.service;

import bg.sofuni.mobilele.model.dto.UserLoginDTO;
import bg.sofuni.mobilele.model.dto.UserRegistrationDTO;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistration);

}

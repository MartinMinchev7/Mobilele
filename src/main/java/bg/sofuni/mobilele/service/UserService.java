package bg.sofuni.mobilele.service;

import bg.sofuni.mobilele.model.UserLoginDTO;
import bg.sofuni.mobilele.model.UserRegistrationDTO;

public interface UserService {

    void registerUser(UserRegistrationDTO userRegistration);

    boolean login(UserLoginDTO userLoginDTO);

    void logout();
}

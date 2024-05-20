package pl.tumanski.discounthub.usermanagement;

import pl.tumanski.discounthub.usermanagement.model.dto.CreateUserDto;
import pl.tumanski.discounthub.usermanagement.model.dto.LoginDto;
import pl.tumanski.discounthub.usermanagement.model.dto.PatchUserDto;
import pl.tumanski.discounthub.usermanagement.model.entity.User;

import java.util.List;
import java.util.UUID;

public interface UserService {
    User registerUser(CreateUserDto createUserDto);

    User authenticateUser(LoginDto loginDto);

    User getUserById(UUID id);

    List<User> getAllUsers();

    User patchUser(UUID id, PatchUserDto patchUserDto);

    void deleteUser(UUID id);
}

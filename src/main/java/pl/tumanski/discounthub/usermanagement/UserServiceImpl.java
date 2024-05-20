package pl.tumanski.discounthub.usermanagement;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import pl.tumanski.discounthub.usermanagement.exception.InvalidCredentialsException;
import pl.tumanski.discounthub.usermanagement.exception.UserCreationException;
import pl.tumanski.discounthub.usermanagement.exception.UserNotFoundException;
import pl.tumanski.discounthub.usermanagement.model.dto.CreateUserDto;
import pl.tumanski.discounthub.usermanagement.model.dto.LoginDto;
import pl.tumanski.discounthub.usermanagement.model.dto.PatchUserDto;
import pl.tumanski.discounthub.usermanagement.model.entity.Role;
import pl.tumanski.discounthub.usermanagement.model.entity.User;

import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static pl.tumanski.discounthub.utils.PatchUtils.shouldUpdateValue;

@Service
@Slf4j
class UserServiceImpl implements UserService {
    @Autowired
    private final UserRepository userRepository;
    @Autowired
    private final RoleRepository roleRepository;

    public UserServiceImpl(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    @Override
    public User registerUser(CreateUserDto createUserDto) {
        User newUser = UserMapper.INSTANCE.toUser(createUserDto);

        Role role = roleRepository.findRoleByName("client")
                .orElseThrow(() -> new UserCreationException("Invalid role"));

        newUser.setRole(role);
        newUser.setLastActive(OffsetDateTime.now());

        try {
            User createdUser = userRepository.save(newUser);
            log.info("Saved new user in repository with id = {}", createdUser.getId());
            return createdUser;
        } catch (DataIntegrityViolationException e) {
            handleDataIntegrityViolation(e, newUser);
        } catch (Exception e) {
            log.error("Failed to save new user in repository", e);
            throw new UserCreationException("Failed to create new user", e);
        }
        return null;
    }

    @Override
    public User authenticateUser(LoginDto loginDto) {
        Optional<User> optionalUser = userRepository.findByUsernameAndPassword(loginDto.getIdentifier(), loginDto.getPassword())
                .or(() -> userRepository.findByEmailAndPassword(loginDto.getIdentifier(), loginDto.getPassword()));

        User user = optionalUser.orElseThrow(() -> new InvalidCredentialsException("Invalid identifier or password"));

        user.setLastActive(OffsetDateTime.now());
        userRepository.save(user);

        return user;
    }

    @Override
    public User getUserById(UUID id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));
    }

    @Override
    public List<User> getAllUsers() {
        return new ArrayList<>(userRepository.findAll());
    }

    @Override
    public User patchUser(UUID id, PatchUserDto patchUserDto) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new UserNotFoundException("User not found with id: " + id));

        if (shouldUpdateValue(patchUserDto.getEmail())) {
            user.setEmail(patchUserDto.getEmail());
        }
        if (shouldUpdateValue(patchUserDto.getUsername())) {
            user.setUsername(patchUserDto.getUsername());
        }
        if (shouldUpdateValue(patchUserDto.getPassword())) {
            user.setPassword(patchUserDto.getPassword());
        }

        try {
            User updatedUser = userRepository.save(user);
            log.info("Patched user with id = {}", updatedUser.getId());
            return updatedUser;
        } catch (DataIntegrityViolationException e) {
            handleDataIntegrityViolation(e, user);
        } catch (Exception e) {
            log.error("Failed to patch user in repository", e);
            throw new UserCreationException("Failed to patch user", e);
        }
        return null;
    }

    @Override
    public void deleteUser(UUID id) {
        if (userRepository.existsById(id)) {
            userRepository.deleteById(id);
            log.info("Deleted user with id = {}", id);
        } else {
            throw new UserNotFoundException("User not found with id: " + id);
        }
    }

    private void handleDataIntegrityViolation(DataIntegrityViolationException e, User newUser) {
        log.error("Failed to save new user due to data integrity violation", e);

        String causeMessage = (e.getCause() != null) ? e.getCause().getMessage() : "";

        if (causeMessage.contains("UNIQUE_EMAIL")) {
            throw new UserCreationException("Email already in use: " + newUser.getEmail(), e);
        } else if (causeMessage.contains("UNIQUE_USERNAME")) {
            throw new UserCreationException("Username already in use: " + newUser.getUsername(), e);
        } else {
            throw new UserCreationException("Data integrity violation occurred", e);
        }
    }
}

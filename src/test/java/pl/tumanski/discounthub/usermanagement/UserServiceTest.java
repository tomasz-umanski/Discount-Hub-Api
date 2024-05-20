package pl.tumanski.discounthub.usermanagement;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import pl.tumanski.discounthub.categorymanagement.exception.CategoryCreationException;
import pl.tumanski.discounthub.categorymanagement.exception.CategoryNotFoundException;
import pl.tumanski.discounthub.categorymanagement.model.dto.PatchCategoryDto;
import pl.tumanski.discounthub.categorymanagement.model.entity.Category;
import pl.tumanski.discounthub.usermanagement.UserService;
import pl.tumanski.discounthub.usermanagement.exception.InvalidCredentialsException;
import pl.tumanski.discounthub.usermanagement.exception.UserCreationException;
import pl.tumanski.discounthub.usermanagement.exception.UserNotFoundException;
import pl.tumanski.discounthub.usermanagement.model.dto.CreateUserDto;
import pl.tumanski.discounthub.usermanagement.model.dto.LoginDto;
import pl.tumanski.discounthub.usermanagement.model.dto.PatchUserDto;
import pl.tumanski.discounthub.usermanagement.model.dto.UserDto;
import pl.tumanski.discounthub.usermanagement.model.entity.User;
import pl.tumanski.discounthub.utils.UnitTest;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@UnitTest
class UserServiceTest {
    @Autowired
    private UserService userService;

    @Test
    void shouldRegisterNewUserSuccessfully() {
        final User user = createUser();
        assertTrue(Objects.nonNull(user) && Objects.nonNull(user.getId()) && Objects.nonNull(user.getRole()) && Objects.nonNull(user.getRole().getName()) && Objects.nonNull(user.getUsername()) && Objects.nonNull(user.getEmail()));
    }

    @Test
    void shouldThrowUserCreationExceptionWhenEmailAlreadyInUse() {
        final String email = "email@example.com";
        final String username = "username";
        final String password = "password";
        final CreateUserDto createUserDto = CreateUserDto.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
        final CreateUserDto createUserDtoWithSameEmail = CreateUserDto.builder()
                .username(String.valueOf(UUID.randomUUID()))
                .email(email)
                .password(password)
                .build();
        userService.registerUser(createUserDto);
        assertThrows(UserCreationException.class, () -> userService.registerUser(createUserDtoWithSameEmail));
    }

    @Test
    void shouldThrowUserCreationExceptionWhenUsernameAlreadyInUse() {
        final String email = "email@example.com";
        final String username = "username";
        final String password = "password";
        final CreateUserDto createUserDto = CreateUserDto.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
        final CreateUserDto createUserDtoWithSameUsername = CreateUserDto.builder()
                .username(username)
                .email(String.valueOf(UUID.randomUUID()))
                .password(password)
                .build();
        userService.registerUser(createUserDto);
        assertThrows(UserCreationException.class, () -> userService.registerUser(createUserDtoWithSameUsername));
    }

    @Test
    void shouldAuthenticateUserWithUsername() {
        final String email = "email@example.com";
        final String username = "username";
        final String password = "password";
        final CreateUserDto createUserDto = CreateUserDto.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
        final LoginDto loginDto = LoginDto.builder()
                .identifier(username)
                .password(password)
                .build();

        userService.registerUser(createUserDto);

        User authenticatedUser = userService.authenticateUser(loginDto);
        assertTrue(Objects.nonNull(authenticatedUser) && Objects.nonNull(authenticatedUser.getId()));
        assertEquals(loginDto.getIdentifier(), authenticatedUser.getUsername());
    }

    @Test
    void shouldAuthenticateUserWithEmail() {
        final String email = "email@example.com";
        final String username = "username";
        final String password = "password";
        final CreateUserDto createUserDto = CreateUserDto.builder()
                .username(username)
                .email(email)
                .password(password)
                .build();
        final LoginDto loginDto = LoginDto.builder()
                .identifier(email)
                .password(password)
                .build();

        userService.registerUser(createUserDto);

        User authenticatedUser = userService.authenticateUser(loginDto);
        assertTrue(Objects.nonNull(authenticatedUser) && Objects.nonNull(authenticatedUser.getId()));
        assertEquals(loginDto.getIdentifier(), authenticatedUser.getEmail());
    }

    @Test
    void shouldThrowInvalidCredentialsExceptionWhenAuthenticationFails() {
        final String email = "email@example.com";
        final String password = "password";
        final LoginDto loginDto = LoginDto.builder()
                .identifier(email)
                .password(password)
                .build();

        assertThrows(InvalidCredentialsException.class, () -> userService.authenticateUser(loginDto));
    }

    @Test
    void shouldGetUserById() {
        User user = createUser();
        User retrievedUser = userService.getUserById(user.getId());
        assertTrue(Objects.nonNull(retrievedUser) && Objects.nonNull(retrievedUser.getId()));
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenThereIsNoUserWithProvidedId() {
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(UUID.randomUUID()));
    }

    @Test
    void shouldGetAllUsers() {
        for (int i = 0; i < 10; i++) {
            createUser();
        }
        List<User> usersRetrieved = userService.getAllUsers();
        assertTrue(Objects.nonNull(usersRetrieved) && !usersRetrieved.isEmpty());
    }

    @Test
    void shouldUpdateUserSuccessfully() {
        User user = createUser();
        PatchUserDto patchUserDto = PatchUserDto.builder()
                .email("Updated Email")
                .username("Updated Username")
                .password("Updated Password")
                .build();

        User updatedUser = userService.patchUser(user.getId(), patchUserDto);

        assertTrue(Objects.nonNull(updatedUser));
        assertEquals("Updated Email", updatedUser.getEmail());
        assertEquals("Updated Username", updatedUser.getUsername());
        assertEquals("Updated Password", updatedUser.getPassword());
    }

    @Test
    void shouldNotUpdateFieldsWithBlankValues() {
        User user = createUser();
        PatchUserDto patchUserDto = PatchUserDto.builder()
                .email("")
                .username("")
                .password("")
                .build();

        User updatedUser = userService.patchUser(user.getId(), patchUserDto);

        assertTrue(Objects.nonNull(updatedUser));
        assertEquals(user.getEmail(), updatedUser.getEmail());
        assertEquals(user.getUsername(), updatedUser.getUsername());
        assertEquals(user.getPassword(), updatedUser.getPassword());
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenUpdatingNonExistentUser() {
        UUID nonExistentId = UUID.randomUUID();
        PatchUserDto patchUserDto = PatchUserDto.builder()
                .email("")
                .username("")
                .password("")
                .build();
        assertThrows(UserNotFoundException.class, () -> userService.patchUser(nonExistentId, patchUserDto));
    }

    @Test
    void shouldThrowUserCreationExceptionWhenValueAlreadyUsedWhenUpdating() {
        User user = createUser();
        User userToUpdate = createUser();
        PatchUserDto patchUserDto = PatchUserDto.builder()
                .email(user.getEmail())
                .build();

        assertThrows(UserCreationException.class, () -> userService.patchUser(userToUpdate.getId(), patchUserDto));
    }

    @Test
    void shouldDeleteUser() {
        User user = createUser();
        userService.deleteUser(user.getId());
        assertThrows(UserNotFoundException.class, () -> userService.getUserById(user.getId()));
    }

    @Test
    void shouldThrowUserNotFoundExceptionWhenThereIsNoUserToDeleteWithProvidedId() {
        assertThrows(UserNotFoundException.class, () -> userService.deleteUser(UUID.randomUUID()));
    }

    private User createUser() {
        CreateUserDto createUserDto = CreateUserDto.builder()
                .username(String.valueOf(UUID.randomUUID()))
                .email(String.valueOf(UUID.randomUUID()))
                .password("strongPassword")
                .build();
        return userService.registerUser(createUserDto);
    }
}
package pl.tumanski.discounthub.usermanagement;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;
import pl.tumanski.discounthub.usermanagement.model.dto.CreateUserDto;
import pl.tumanski.discounthub.usermanagement.model.dto.RoleDto;
import pl.tumanski.discounthub.usermanagement.model.dto.UserDto;
import pl.tumanski.discounthub.usermanagement.model.entity.Role;
import pl.tumanski.discounthub.usermanagement.model.entity.User;

@Mapper(componentModel = "spring")
interface UserMapper {
    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    User toUser(CreateUserDto createUserDto);

    UserDto toUserDto(User user);

    RoleDto toRoleDto(Role role);
}
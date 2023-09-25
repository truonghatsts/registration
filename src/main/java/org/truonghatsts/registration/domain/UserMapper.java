package org.truonghatsts.registration.domain;

import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserEntity toUserEntity(UserDTO userRequest);
    UserDTO toUserDto(UserEntity entity);
}

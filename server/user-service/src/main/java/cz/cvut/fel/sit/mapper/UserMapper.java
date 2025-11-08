package cz.cvut.fel.sit.mapper;

import cz.cvut.fel.sit.entity.User;
import cz.cvut.fel.sit.dto.response.UserResponse;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResponse toResponse (User user);

}

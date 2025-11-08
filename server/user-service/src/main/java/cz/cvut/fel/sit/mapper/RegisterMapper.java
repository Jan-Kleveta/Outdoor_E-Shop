package cz.cvut.fel.sit.mapper;

import cz.cvut.fel.sit.entity.User;
import cz.cvut.fel.sit.dto.request.RegisterRequest;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RegisterMapper {

    @Mapping(source = "email", target = "email")
    @Mapping(source = "password", target = "password")
    User toEntity(RegisterRequest request);
}

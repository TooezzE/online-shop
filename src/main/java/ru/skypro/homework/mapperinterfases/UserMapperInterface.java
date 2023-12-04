package ru.skypro.homework.mapperinterfases;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import ru.skypro.homework.dto.UpdateUser;
import ru.skypro.homework.dto.entity.User;

@Mapper
public interface UserMapperInterface {

    @Mapping(target = "id", source = "lastName")
    User userEntity(UpdateUser updateUserDto);

    @Mapping(target = "lastName", source = "id")
    UpdateUser updateUserDto(User userEntity);


}

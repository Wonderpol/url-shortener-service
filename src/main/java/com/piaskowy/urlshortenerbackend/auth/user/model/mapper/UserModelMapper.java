package com.piaskowy.urlshortenerbackend.auth.user.model.mapper;

import com.piaskowy.urlshortenerbackend.auth.user.model.dto.UserDto;
import com.piaskowy.urlshortenerbackend.auth.user.model.entity.User;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UserModelMapper {

    private final ModelMapper modelMapper;

    public UserModelMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UserDto toDto(User user) {
        return modelMapper.map(user, UserDto.class);
    }

}

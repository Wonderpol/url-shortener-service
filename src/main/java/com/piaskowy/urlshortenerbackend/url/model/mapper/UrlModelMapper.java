package com.piaskowy.urlshortenerbackend.url.model.mapper;

import com.piaskowy.urlshortenerbackend.url.model.Url;
import com.piaskowy.urlshortenerbackend.url.model.dto.UrlDto;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class UrlModelMapper {

    private final ModelMapper modelMapper;

    public UrlModelMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public UrlDto toDto(Url url) {
        return modelMapper.map(url, UrlDto.class);
    }

}

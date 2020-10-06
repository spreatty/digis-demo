package io.spreatty.digisdemo.converter;

public interface Converter<DTO, ENTITY> {
    DTO toDTO(ENTITY source);

    ENTITY fromDTO(DTO source);
}

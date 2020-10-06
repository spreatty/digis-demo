package io.spreatty.digisdemo.converter;

import io.spreatty.digisdemo.pojo.dto.GenderDTO;
import io.spreatty.digisdemo.pojo.entity.Gender;
import org.springframework.stereotype.Component;

@Component
public class GenderConverter implements Converter<GenderDTO, Gender> {
    @Override
    public GenderDTO toDTO(Gender source) {
        return source == null ? null : GenderDTO.valueOf(source.name());
    }

    @Override
    public Gender fromDTO(GenderDTO source) {
        return source == null ? null : Gender.valueOf(source.name());
    }
}

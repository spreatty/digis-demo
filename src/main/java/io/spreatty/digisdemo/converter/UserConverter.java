package io.spreatty.digisdemo.converter;

import io.spreatty.digisdemo.pojo.dto.GenderDTO;
import io.spreatty.digisdemo.pojo.dto.UserDTO;
import io.spreatty.digisdemo.pojo.entity.Gender;
import io.spreatty.digisdemo.pojo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserConverter implements Converter<UserDTO, User> {
    @Autowired
    private Converter<GenderDTO, Gender> genderConverter;

    @Override
    public UserDTO toDTO(User source) {
        UserDTO target = new UserDTO();
        target.setId(source.getId());
        target.setLogin(source.getLogin());
        target.setFullName(source.getFullName());
        target.setGender(genderConverter.toDTO(source.getGender()));
        target.setBirthDate(source.getBirthDate());
        return target;
    }

    @Override
    public User fromDTO(UserDTO source) {
        User target = new User();
        target.setId(source.getId());
        target.setLogin(source.getLogin());
        target.setFullName(source.getFullName());
        target.setGender(genderConverter.fromDTO(source.getGender()));
        target.setBirthDate(source.getBirthDate());
        return target;
    }

    public void setGenderConverter(Converter<GenderDTO, Gender> genderConverter) {
        this.genderConverter = genderConverter;
    }
}

package rut.miit.airportweb.mapper;

import lombok.experimental.UtilityClass;
import rut.miit.airportweb.config.security.CustomUserDetails;
import rut.miit.airportweb.dao.entity.UserEntity;

@UtilityClass
public class UserEntityToCustomUserDetailsMapper {

    public static CustomUserDetails map(UserEntity user) {
        return CustomUserDetails.builder()
                .id(user.getId())
                .username(user.getUsername())
                .password(user.getPassword())
                .role(user.getRole())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .createdAt(user.getCreatedAt())
                .build();
    }

}

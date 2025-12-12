package rut.miit.airportweb.mapper;

import lombok.experimental.UtilityClass;
import rut.miit.airportweb.dao.entity.UserEntity;
import rut.miit.airportweb.dto.UserDto;
import rut.miit.airportweb.dto.UserRegistrationDto;

@UtilityClass
public class UserMapper {

    public static UserDto map(UserEntity user) {
        UserDto.UserDtoBuilder builder = UserDto.builder()
                .username(user.getUsername())
                .passwordHash(user.getPassword())
                .firstName(user.getFirstName())
                .lastName(user.getLastName())
                .role(user.getRole())
                .createdAt(user.getCreatedAt());

        // Безопасное получение passportNumber
        if (user.getPassenger() != null) {
            builder.passportNumber(user.getPassenger().getPassportNumber());
        }

        return builder.build();
    }

    public static UserEntity map(UserRegistrationDto dto) {
        return UserEntity.builder()
                .username(dto.getUsername())
                .password(dto.getPassword())
                .role(UserEntity.Role.valueOf(dto.getRole()))
                .firstName(dto.getFirstName())
                .lastName(dto.getLastName())
                .build();
    }

    // В обратную сторону то же самое, билдеры везде есть, если что-то не будет получаться, писать мне

}

package rut.miit.airportweb.mapper;

import lombok.experimental.UtilityClass;
import rut.miit.airportweb.dao.entity.PassengerEntity;
import rut.miit.airportweb.dao.entity.TicketEntity;
import rut.miit.airportweb.dao.entity.UserEntity;
import rut.miit.airportweb.dto.PassengerCreateDto;
import rut.miit.airportweb.dto.PassengerDto;
import rut.miit.airportweb.dto.TicketDto;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@UtilityClass
public class PassengerMapper {

    public static PassengerDto map(PassengerEntity passenger) {
        return PassengerDto.builder()
                .id(passenger.getId())
                .user(UserMapper.map(passenger.getUser()))
                .passportNumber(passenger.getPassportNumber())
                .phone(passenger.getPhone())
                .email(passenger.getEmail())
                .luggageChecked(passenger.getLuggageChecked())
                .tickets(mapTicketsToDto(passenger.getTickets()))
                .build();
    }

    private static List<TicketDto> mapTicketsToDto(List<TicketEntity> ticketEntities) {
        if (ticketEntities == null || ticketEntities.isEmpty()) {
            return new ArrayList<>();
        }

        return ticketEntities.stream()
                .map(TicketMapper::map)
                .collect(Collectors.toList());
    }

    public static PassengerEntity map(PassengerCreateDto dto, UserEntity user) {
        return PassengerEntity.builder()
                .user(user)  // Теперь устанавливаем связь с UserEntity
                .passportNumber(dto.getPassportNumber())
                .phone(dto.getPhone())
                .email(dto.getEmail())
                .build();
    }

    // Метод для обновления существующего PassengerEntity
    public static void updateEntity(PassengerEntity entity, PassengerDto dto) {
        if (dto.getPhone() != null) {
            entity.setPhone(dto.getPhone());
        }
        if (dto.getEmail() != null) {
            entity.setEmail(dto.getEmail());
        }
        if (dto.getLuggageChecked() != null) {
            entity.setLuggageChecked(dto.getLuggageChecked());
        }
    }
}
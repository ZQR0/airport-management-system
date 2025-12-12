package rut.miit.airportweb.mapper;

import lombok.experimental.UtilityClass;
import rut.miit.airportweb.dao.entity.BoardingPassEntity;
import rut.miit.airportweb.dao.entity.TicketEntity;
import rut.miit.airportweb.dao.entity.UserEntity;
import rut.miit.airportweb.dto.BoardingPassCreateDto;
import rut.miit.airportweb.dto.BoardingPassDto;

@UtilityClass
public class BoardingPassMapper {

    public static BoardingPassDto map(BoardingPassEntity boardingPass) {
        BoardingPassDto.BoardingPassDtoBuilder builder = BoardingPassDto.builder()
                .id(boardingPass.getId())
                .checkInTime(boardingPass.getCheckInTime())
                .passportVerified(boardingPass.getPassportVerified())
                .luggageVerified(boardingPass.getLuggageVerified())
                .boarded(boardingPass.getBoarded());

        if (boardingPass.getTicket() != null) {
            builder.ticket(TicketMapper.map(boardingPass.getTicket()));
        }

        if (boardingPass.getVerifiedByBorderGuard() != null) {
            builder.verifiedByBorderGuard(UserMapper.map(boardingPass.getVerifiedByBorderGuard()));
        }

        if (boardingPass.getVerifiedByCustoms() != null) {
            builder.verifiedByCustoms(UserMapper.map(boardingPass.getVerifiedByCustoms()));
        }

        return builder.build();
    }

    public static BoardingPassEntity map(BoardingPassCreateDto dto, TicketEntity ticket,
                                         UserEntity borderGuard, UserEntity customsOfficer) {
        return BoardingPassEntity.builder()
                .ticket(ticket)
                .verifiedByBorderGuard(borderGuard)
                .verifiedByCustoms(customsOfficer)
                .build();
    }

    // Метод для обновления существующего BoardingPassEntity
    public static void updateEntity(BoardingPassEntity entity, BoardingPassDto dto) {
        if (dto.getPassportVerified() != null) {
            entity.setPassportVerified(dto.getPassportVerified());
        }
        if (dto.getLuggageVerified() != null) {
            entity.setLuggageVerified(dto.getLuggageVerified());
        }
        if (dto.getBoarded() != null) {
            entity.setBoarded(dto.getBoarded());
        }
    }
}
package rut.miit.airportweb.mapper;

import lombok.experimental.UtilityClass;
import rut.miit.airportweb.dao.entity.FlightEntity;
import rut.miit.airportweb.dao.entity.PassengerEntity;
import rut.miit.airportweb.dao.entity.TicketEntity;
import rut.miit.airportweb.dto.TicketCreateDto;
import rut.miit.airportweb.dto.TicketDto;
import rut.miit.airportweb.dto.TicketUpdateDto;

@UtilityClass
public class TicketMapper {

    public static TicketDto map(TicketEntity ticket) {
        return TicketDto.builder()
                .id(ticket.getId())
                .flightId(ticket.getFlight().getId())
                .passengerId(ticket.getPassenger().getId())
                .seatNumber(ticket.getSeatNumber())
                .price(ticket.getPrice())
                .ticketNumber(ticket.getTicketNumber())
                .status(ticket.getStatus().toString())
                .bookingDate(ticket.getBookingDate())
                .flightNumber(ticket.getFlight().getFlightNumber())
                .passengerName(getFullName(ticket))
                .passportNumber(ticket.getPassenger().getPassportNumber())
                .build();
    }

    private static String getFullName(TicketEntity ticket) {
        return ticket.getPassenger().getUser().getFirstName() + " " + ticket.getPassenger().getUser().getLastName();
    }

    // Новый метод для создания TicketEntity из DTO
    public static TicketEntity map(TicketCreateDto dto, FlightEntity flight, PassengerEntity passenger) {
        return TicketEntity.builder()
                .flight(flight)
                .passenger(passenger)
                .seatNumber(dto.getSeatNumber())
                .price(dto.getPrice())
                .ticketNumber(dto.getTicketNumber())
                .build();
    }

    // Метод для обновления существующего TicketEntity
    public static void updateEntity(TicketEntity entity, TicketUpdateDto dto) {
        if (dto.getSeatNumber() != null) {
            entity.setSeatNumber(dto.getSeatNumber());
        }
        if (dto.getPrice() != null) {
            entity.setPrice(dto.getPrice());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(TicketEntity.TicketStatus.valueOf(dto.getStatus()));
        }
    }
}
package rut.miit.airportweb.mapper;

import lombok.experimental.UtilityClass;
import rut.miit.airportweb.dao.entity.FlightEntity;
import rut.miit.airportweb.dto.FlightCreateDto;
import rut.miit.airportweb.dto.FlightDto;
import rut.miit.airportweb.dto.FlightUpdateDto;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

@UtilityClass
public class FlightMapper {

    private static final DateTimeFormatter DATE_TIME_FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public static FlightDto map(FlightEntity flight) {
        return FlightDto.builder()
                .id(flight.getId())
                .flightNumber(flight.getFlightNumber())
                .departureCity(flight.getDepartureCity())
                .arrivalCity(flight.getArrivalCity())
                .departureTime(flight.getDepartureTime())
                .arrivalTime(flight.getArrivalTime())
                .totalSeats(flight.getTotalSeats())
                .availableSeats(flight.getAvailableSeats())
                .status(flight.getStatus().toString())
                .createdBy(UserMapper.map(flight.getCreatedBy()))
                .build();
    }

    public static FlightEntity map(FlightCreateDto dto) {
        return FlightEntity.builder()
                .flightNumber(dto.getFlightNumber())
                .departureCity(dto.getDepartureCity())
                .arrivalCity(dto.getArrivalCity())
                .departureTime(parseDateTime(dto.getDepartureTime()))
                .arrivalTime(parseDateTime(dto.getArrivalTime()))
                .totalSeats(dto.getTotalSeats())
                .availableSeats(dto.getAvailableSeats())
                .status(FlightEntity.FlightStatus.valueOf(dto.getStatus()))
                .build();
    }

    public static void updateEntity(FlightEntity entity, FlightUpdateDto dto) {
        if (dto.getDepartureCity() != null) {
            entity.setDepartureCity(dto.getDepartureCity());
        }
        if (dto.getArrivalCity() != null) {
            entity.setArrivalCity(dto.getArrivalCity());
        }
        if (dto.getDepartureTime() != null) {
            entity.setDepartureTime(dto.getDepartureTime());
        }
        if (dto.getArrivalTime() != null) {
            entity.setArrivalTime(dto.getArrivalTime());
        }
        if (dto.getTotalSeats() != null) {
            entity.setTotalSeats(dto.getTotalSeats());
        }
        if (dto.getAvailableSeats() != null) {
            entity.setAvailableSeats(dto.getAvailableSeats());
        }
        if (dto.getStatus() != null) {
            entity.setStatus(FlightEntity.FlightStatus.valueOf(dto.getStatus()));
        }
    }

    private static LocalDateTime parseDateTime(LocalDateTime dateTime) {
        // Если уже LocalDateTime, возвращаем как есть
        return dateTime;
    }

    // Метод для парсинга String в LocalDateTime (если понадобится)
    public static LocalDateTime parseDateTimeString(String dateTimeString) {
        try {
            return LocalDateTime.parse(dateTimeString, DATE_TIME_FORMATTER);
        } catch (DateTimeParseException e) {
            throw new IllegalArgumentException("Invalid date-time format: " + dateTimeString +
                    ". Expected format: yyyy-MM-dd HH:mm:ss");
        }
    }
}
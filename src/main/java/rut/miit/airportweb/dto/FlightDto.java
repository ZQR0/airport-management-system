package rut.miit.airportweb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class FlightDto {

    private Integer id;

    @NotBlank(message = "Flight number is required")
    private String flightNumber;

    @NotBlank(message = "Departure city is required")
    private String departureCity;

    @NotBlank(message = "Arrival city is required")
    private String arrivalCity;

    @NotNull(message = "Departure time is required")
    private LocalDateTime departureTime;

    @NotNull(message = "Arrival time is required")
    private LocalDateTime arrivalTime;

    @Positive(message = "Total seats must be positive")
    private Integer totalSeats;

    @Positive(message = "Available seats must be positive")
    private Integer availableSeats;

    private String status;

    private UserDto createdBy;

    // Оставляем только один конструктор
    public FlightDto() {}
}
package rut.miit.airportweb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class TicketDto {

    private Integer id;

    @NotNull(message = "Flight ID is required")
    private Integer flightId;

    @NotNull(message = "Passenger ID is required")
    private Integer passengerId;

    @NotBlank(message = "Seat number is required")
    private String seatNumber;

    @Positive(message = "Price must be positive")
    private BigDecimal price;

    private String ticketNumber;
    private String status;
    private LocalDateTime bookingDate;

    // Additional fields for display
    private String flightNumber;
    private String passengerName;
    private String passportNumber;

    // Добавляем конструктор по умолчанию
    public TicketDto() {}
}
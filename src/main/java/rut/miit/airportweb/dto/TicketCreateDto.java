package rut.miit.airportweb.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@AllArgsConstructor
@Builder
@Getter
@Setter
public class TicketCreateDto {

    @NotNull(message = "Flight number of ticket cannot be null")
    @NotBlank(message = "Flight number of ticket cannot be blank")
    private String flightNumberOfTicket;

    @NotNull(message = "Passport number of passenger cannot be null")
    @NotBlank(message = "Passport number of passenger cannot be blank")
    private String passportNumberOfPassenger;

    @NotNull(message = "Seat number cannot be null")
    @NotBlank(message = "Seat number cannot be blank")
    private String seatNumber;

    @NotNull(message = "Price cannot be null")
    @Positive(message = "Price must be positive")
    private BigDecimal price;

    @NotNull(message = "Ticket number cannot be null")
    @NotBlank(message = "Ticket number cannot be blank")
    private String ticketNumber;


}

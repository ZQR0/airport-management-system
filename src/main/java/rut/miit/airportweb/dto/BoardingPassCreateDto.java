package rut.miit.airportweb.dto;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class BoardingPassCreateDto {

    @NotNull(message = "Ticket number is required")
    private String ticketNumber;

    @NotNull(message = "Border guard username is required")
    private String borderGuardUsername;

    @NotNull(message = "Customs officer username is required")
    private String customsOfficerUsername;

    public BoardingPassCreateDto() {}
}
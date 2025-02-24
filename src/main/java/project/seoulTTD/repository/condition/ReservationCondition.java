package project.seoulTTD.repository.condition;

import lombok.Data;
import project.seoulTTD.entity.ReservationStatus;

import java.time.LocalDateTime;

@Data
public class ReservationCondition {

    private LocalDateTime reservedDate;
    private LocalDateTime reservedTime;
    private Integer peopleCount;
    private ReservationStatus status;
}

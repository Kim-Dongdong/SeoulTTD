package project.seoulTTD.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import project.seoulTTD.entity.ReservationStatus;


import java.time.LocalDateTime;

@Getter @Setter
public class ReservationDto {

    private ReservationStatus status;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private LocalDateTime reservedDate;
    private LocalDateTime reservedTime;
    private Integer peopleCount;

    @QueryProjection
    public ReservationDto(ReservationStatus status, LocalDateTime createdAt,
                          LocalDateTime updatedAt, LocalDateTime reservedDate,
                          LocalDateTime reservedTime, Integer peopleCount) {
        this.status = status;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.reservedDate = reservedDate;
        this.reservedTime = reservedTime;
        this.peopleCount = peopleCount;
    }
}

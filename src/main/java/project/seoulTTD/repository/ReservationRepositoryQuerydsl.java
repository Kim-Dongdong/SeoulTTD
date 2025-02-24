package project.seoulTTD.repository;

import project.seoulTTD.entity.Reservation;
import project.seoulTTD.repository.condition.ReservationCondition;

import java.util.List;

public interface ReservationRepositoryQuerydsl {

//    List<Reservation> findByCond(ReservationCondition cond);

    List<Reservation> findByCondDto(ReservationCondition cond);
}

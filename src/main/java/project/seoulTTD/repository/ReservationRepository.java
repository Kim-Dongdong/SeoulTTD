package project.seoulTTD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.seoulTTD.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation, Long>, ReservationRepositoryQuerydsl {

    Reservation findReservationById(Long id);
}

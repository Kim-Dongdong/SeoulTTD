package project.seoulTTD.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import project.seoulTTD.entity.Reservation;
import project.seoulTTD.repository.condition.ReservationCondition;

import java.util.List;

public class ReservationRepositoryQuerydslImpl implements ReservationRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public ReservationRepositoryQuerydslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Reservation> findByCondDto(ReservationCondition cond) {
        return null;
    }



}

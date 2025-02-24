package project.seoulTTD.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import project.seoulTTD.entity.Review;

import java.util.List;

public class UserRepositoryQuerydslImpl implements UserRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public UserRepositoryQuerydslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public List<Review> findReview(Long userId) {
        return null;
    }


}

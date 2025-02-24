package project.seoulTTD.repository;

import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import project.seoulTTD.entity.Review;

import java.util.List;

import static project.seoulTTD.entity.QReview.review;
import static project.seoulTTD.entity.place.QPlace.place;

public class ReviewRepositoryQuerydslImpl implements ReviewRepositoryQuerydsl {

    private final JPAQueryFactory queryFactory;

    public ReviewRepositoryQuerydslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }


    @Override
    public List<Review> findReview(Long placeId, int rating) {

        return queryFactory
                .select(review)
                .from(place)
                .leftJoin(place.reviews, review)
                .where(
                        review.rating.eq(rating),
                        place.id.eq(placeId)
                )
                .fetch();
    }
}

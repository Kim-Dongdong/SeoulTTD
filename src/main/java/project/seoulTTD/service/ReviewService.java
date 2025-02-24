package project.seoulTTD.service;

import jakarta.persistence.PrePersist;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seoulTTD.entity.Review;
import project.seoulTTD.entity.User;
import project.seoulTTD.entity.place.Place;
import project.seoulTTD.repository.ReviewRepository;
import project.seoulTTD.web.form.ReviewForm;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ReviewService {

    private final ReviewRepository reviewRepository;

    public Long saveReview(Review review) {
        Review saved = reviewRepository.save(review);
//        setting(review);
        return saved.getId();
    }

    // Controller에서 넘어오는 Review, User, Place값 처리 로직
    public void handleReview(User user, Place place, Review review) {
        user.getReviews().add(review);
        place.getReviews().add(review);
        // Review review는 view에서 객체가 만들어져서 올것이기 때문에 생략
    }

    @Transactional
    public Long editReview(Long id, ReviewForm reviewForm) {
        Review findReview = reviewRepository.findById(id).get();

        findReview.setRating(reviewForm.getRating());
        findReview.setComment(reviewForm.getComment());

        return findReview.getId();
    }

    public Review findReviewById(Long id) {
        Review review = reviewRepository.findReviewById(id);
        return review;
    }

    @Transactional
    public void deleteReviewById(Long id) {
        reviewRepository.deleteById(id);

    }

    public void setting(Review review) {
        review.getUser().getReviews().add(review);
        review.getPlace().getReviews().add(review);
    }


}

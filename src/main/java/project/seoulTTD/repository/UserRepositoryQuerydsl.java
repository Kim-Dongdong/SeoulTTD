package project.seoulTTD.repository;

import project.seoulTTD.entity.Review;

import java.util.List;

public interface UserRepositoryQuerydsl {

    List<Review> findReview(Long userId);


}

package project.seoulTTD.repository;

import project.seoulTTD.entity.Review;

import java.util.List;



public interface ReviewRepositoryQuerydsl {

    List<Review> findReview(Long placeId, int rate);


}

package project.seoulTTD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.seoulTTD.entity.Review;

public interface ReviewRepository extends JpaRepository<Review, Long>, ReviewRepositoryQuerydsl {

    Review findReviewById(Long id);

    void deleteById(Long id);
}

package project.seoulTTD.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import project.seoulTTD.entity.User;
import project.seoulTTD.entity.place.Place;

import java.time.LocalDateTime;

@Getter @Setter
public class ReviewDto {

    private int rating; // 1~5
    private String comment;
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private User user;
    private Place place;

    @QueryProjection
    public ReviewDto(int rating, String comment, LocalDateTime createdDate, LocalDateTime lastModifiedDate, User user, Place place) {
        this.rating = rating;
        this.comment = comment;
        this.createdDate = createdDate;
        this.lastModifiedDate = lastModifiedDate;
        this.user = user;
        this.place = place;
    }
}

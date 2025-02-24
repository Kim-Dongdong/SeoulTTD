package project.seoulTTD.web.form;

import lombok.Data;
import project.seoulTTD.entity.place.Place;
import project.seoulTTD.entity.place.PlaceInfo;

import java.util.List;

@Data
public class CreateReviewForm {

    private Long resId;
    private Long placeId;
    private Long userId;
    private Long reviewId;
    private Integer rating;
    private String comment;
    private PlaceInfo placeInfo;
}

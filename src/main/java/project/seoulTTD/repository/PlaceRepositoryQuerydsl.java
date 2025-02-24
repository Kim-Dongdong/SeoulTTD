package project.seoulTTD.repository;

import project.seoulTTD.entity.Review;
import project.seoulTTD.entity.place.Place;
import project.seoulTTD.repository.condition.PlaceCondition;
import project.seoulTTD.repository.dto.PlaceDto;

import java.util.List;

public interface PlaceRepositoryQuerydsl {

    List<Place> findByCond(PlaceCondition cond);

    List<PlaceDto> findByCondDto(PlaceCondition cond);

    List<Review> findReviewAll(Long placeId, int rate);

    PlaceDto findPlaceDtoById(Long id);

    List<Place> findPlaceByRegion(String region);
}

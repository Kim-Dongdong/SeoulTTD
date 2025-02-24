package project.seoulTTD.service;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seoulTTD.entity.Review;
import project.seoulTTD.entity.place.Place;
import project.seoulTTD.repository.PlaceRepository;
import project.seoulTTD.repository.condition.PlaceCondition;
import project.seoulTTD.repository.dto.PlaceDto;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlaceService {

    @Autowired
    private final PlaceRepository placeRepository;

    // 장소 등록
    @Transactional
    public void join(Place place) {
//        validDuplicatePlace(place);
        Place savePlace = placeRepository.save(place);
    }

    // 장소 중복 검색
    public void validDuplicatePlace(Place place) {
        Optional<Place> findPlace = placeRepository.findById(place.getId());
        if (findPlace.isPresent()) {
            throw new IllegalStateException("중복 장소입니다.");
        }
    }

    public List<Place> findAll() {
        return placeRepository.findAll();
    }

    // ReviewSet
    @Transactional
    public void setReview(Long placeId, Review review) {
        Place findPlace = placeRepository.findById(placeId).get();
        findPlace.setReview(review);
        review.setPlace(findPlace);
    }

    public PlaceDto findPlaceDtoById(Long placeId) {
        PlaceDto findDto = placeRepository.findPlaceDtoById(placeId);
        return findDto;
    }

    public Place findPlaceById(Long id) {
        Place place = placeRepository.findById(id).get();
        return place;
    }

    public void removeFavorite(Long id) {

    }



    // 장소 조회(동적 쿼리)
    public List<Place> findPlaceByCond(PlaceCondition cond) {
        return placeRepository.findByCond(cond);
    }

    public List<Place> findPlaceByRegion(String location) {
        return placeRepository.findPlaceByRegion(location);
    }
}



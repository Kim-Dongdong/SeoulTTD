package project.seoulTTD.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.seoulTTD.entity.Review;
import project.seoulTTD.entity.place.Place;
import project.seoulTTD.entity.place.PlaceInfo;
import project.seoulTTD.repository.dto.PlaceDto;
import project.seoulTTD.service.PlaceService;
import project.seoulTTD.service.UserService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class PlaceController {

    private final PlaceService placeService;

    @GetMapping("/place/placeDetail/{id}")
    public String placeDetailForm(@PathVariable("id") Long placeId, Model model) {
        PlaceDto findPlaceDto = placeService.findPlaceDtoById(placeId);
//        model.addAttribute("place", findPlaceDto);
        Place findPlace = placeService.findPlaceById(placeId);
        List<Review> reviews = findPlace.getReviews();
        model.addAttribute("place", findPlace);
        model.addAttribute("reviews", reviews);
        List<PlaceInfo> placeInfos = findPlace.getPlaceInfos();
        model.addAttribute("placeInfos", placeInfos);

        return "/place/placeDetail3";
    }

    @GetMapping("/place/placeDetail")
    public String placeDetailForm2(@RequestParam Long userId, @RequestParam Long placeId, Model model) {
        PlaceDto findPlaceDto = placeService.findPlaceDtoById(placeId);
        System.out.println("findPlaceDto = " + findPlaceDto);

        Place findPlace = placeService.findPlaceById(placeId);
        List<Review> reviews = findPlace.getReviews();
        model.addAttribute("reviews", reviews);
        model.addAttribute("place", findPlace);

        List<PlaceInfo> placeInfos = findPlace.getPlaceInfos();

        model.addAttribute("placeInfos", placeInfos);
        model.addAttribute("userId", userId);
        model.addAttribute("placeId", placeId);

        return "/place/placeDetail3";
    }

    @PostMapping("/place/placeDetail")
    public String placeDetailForm(@RequestParam Long userId, @RequestParam Long placeId, Model model) {
        PlaceDto findPlaceDto = placeService.findPlaceDtoById(placeId);
//        model.addAttribute("place", findPlaceDto);
        System.out.println("findPlaceDto = " + findPlaceDto);

        Place findPlace = placeService.findPlaceById(placeId);
        List<Review> reviews = findPlace.getReviews();
        model.addAttribute("reviews", reviews);

        model.addAttribute("place", findPlace);
        List<PlaceInfo> placeInfos = findPlace.getPlaceInfos();

        model.addAttribute("placeInfos", placeInfos);
        model.addAttribute("userId", userId);
        model.addAttribute("placeId", placeId);

        return "/place/placeDetail2";
    }


    @GetMapping("/locations")
    public String locationsForm(@RequestParam("location") String location, @RequestParam("userId") Long userId,
                                Model model) {

        List<Place> findPlaces = placeService.findPlaceByRegion(location);

        System.out.println("findPlaces = " + findPlaces);

        model.addAttribute("userId", userId);
        model.addAttribute("placeList", findPlaces);

        return "place/showPlaces";
    }


}

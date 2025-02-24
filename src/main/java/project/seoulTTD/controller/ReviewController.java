package project.seoulTTD.controller;

import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.seoulTTD.entity.Reservation;
import project.seoulTTD.entity.Review;
import project.seoulTTD.entity.User;
import project.seoulTTD.entity.place.Place;
import project.seoulTTD.entity.place.PlaceInfo;
import project.seoulTTD.exception.DuplicateReviewException;
import project.seoulTTD.service.*;
import project.seoulTTD.web.form.CreateReviewForm;
import project.seoulTTD.web.form.ReviewForm;

import java.util.List;
import java.util.Objects;

@Controller
@RequiredArgsConstructor
public class ReviewController {

    private final ReviewService reviewService;
    private final PlaceService placeService;
    private final UserService userService;
    private final PlaceInfoService placeInfoService;
    private final ReservationService reservationService;

    @GetMapping("/reviews/new")
    public String showReviewForm(@PathVariable("placeId") Long placeId, Model model) {
        Place place = placeService.findPlaceById(placeId);
        ReviewForm reviewForm = new ReviewForm();
        reviewForm.setPlaceId(placeId);

        model.addAttribute("place", place);
        model.addAttribute("reviewForm", reviewForm);
        return "reviewForm";  // Thymeleaf template name
    }

    @PostMapping("/reviews/save")
    public String saveReview(ReviewForm reviewForm) {

        reviewService.editReview(reviewForm.getReviewId(), reviewForm);

//        Place findPlace = placeService.findPlaceById(placeId);
//        User findUser = userService.findUserById(userId);
//
//        Review review = new Review();
//        review.setRating(reviewForm.getRating());
//        review.setComment(reviewForm.getComment());
//
//        findPlace.setReview(review);
//        findUser.setReview(review);

        return "redirect:/user/" + reviewForm.getUserId();
    }

    @GetMapping("/review/edit/{id}")
    public String editReviewGet(@PathVariable("id") Long id, Model model) {
        Review findReview = reviewService.findReviewById(id);
        ReviewForm reviewForm = new ReviewForm();
        reviewForm.setRating(findReview.getRating());
        reviewForm.setComment(findReview.getComment());
        reviewForm.setReviewId(findReview.getId());
        reviewForm.setUserId(findReview.getUser().getId());
        PlaceInfo findPlaceInfo = findReview.getPlaceInfo();
        reviewForm.setPlaceInfo(findPlaceInfo);

        model.addAttribute("place", findReview.getPlace());
        model.addAttribute("reviewForm", reviewForm);
        model.addAttribute("placeInfoName", findPlaceInfo.getName());

        return "/review/reviewForm";
    }

    @GetMapping("/review/edit")
    public String editReviewButtonGet(@RequestParam("id") Long id, Model model) {
        Review findReview = reviewService.findReviewById(id);
        ReviewForm reviewForm = new ReviewForm();
        reviewForm.setRating(findReview.getRating());
        reviewForm.setComment(findReview.getComment());
        reviewForm.setReviewId(findReview.getId());
        reviewForm.setUserId(findReview.getUser().getId());
        PlaceInfo findPlaceInfo = findReview.getPlaceInfo();
        reviewForm.setPlaceInfo(findPlaceInfo);

        model.addAttribute("place", findReview.getPlace());
        model.addAttribute("reviewForm", reviewForm);
        model.addAttribute("placeInfoName", findPlaceInfo.getName());

        return "/review/reviewForm";
    }

    @PostMapping("/review/edit/{id}")
    public String editReviewPost(@PathVariable("id") Long id, @ModelAttribute("reviewForm") ReviewForm reviewForm) {

       reviewService.editReview(id, reviewForm);

       return "redirect:/";
    }

    @GetMapping("/{id}/review")
    public String reviewForm(@PathVariable("id") Long id, Model model) {

        Place place = new Place();
        Review review = new Review();
        model.addAttribute("review", review);
        model.addAttribute("place", place);

        return "/review/reviewForm";
    }

    @PostMapping("/{id}/review")
    public String saveReview(@PathVariable("id") Long id, @ModelAttribute("review") Review review) {

        Place findPlace = placeService.findPlaceById(id);
        findPlace.getReviews().add(review);

        return "redirect:" + "/place/placeDetail/" + id;
    }

    @GetMapping("/createReview")
    public String createReviewGet(@RequestParam("reservationId") Long resId,
                                  @RequestParam("userId") Long userId,
                                  @RequestParam("placeId") Long placeId,
                                  @RequestParam("placeInfoId") Long placeInfoId, Model model) {

        Place place = placeService.findPlaceById(placeId);
        Reservation findRes = reservationService.findResById(resId);
        PlaceInfo findPlaceInfo = placeInfoService.findPlaceInfoById(placeInfoId);
        User findUser = userService.findUserById(userId);

        System.out.println("findPlaceInfo = " + findPlaceInfo.getReviews());


        // 중복 방지 로직
        for (Review rev : findPlaceInfo.getReviews()) {
            System.out.println("rev.getUser() = " + rev.getUser());
            System.out.println("findUser = " + findUser);
            if (rev.getUser().equals(findUser)){
                System.out.println("rev.getUser() = " + rev.getUser());
                System.out.println("findUser = " + findUser);
                throw new DuplicateReviewException("중복 리뷰입니다.");
            }
        }

        Review review = new Review();

        ReviewForm reviewForm = new ReviewForm();
        reviewForm.setResId(resId);
        reviewForm.setUserId(userId);
        reviewForm.setPlaceId(placeId);
        reviewForm.setPlaceInfo(findRes.getPlaceInfo());

        model.addAttribute("review", review);
        model.addAttribute("place", place);
        model.addAttribute("reviewForm", reviewForm);
        model.addAttribute("resId", resId);

        return "/review/createReview";
    }

    @PostMapping("/createReview")
    public String createReviewPost(@ModelAttribute("reviewForm") ReviewForm reviewForm, Model model) {

        Place findPlace = placeService.findPlaceById(reviewForm.getPlaceId());
        User findUser = userService.findUserById(reviewForm.getUserId());
        Reservation findRes = reservationService.findResById(reviewForm.getResId());

        System.out.println("reviewForm.getPlaceId() = " + reviewForm.getPlaceId());

        Review review = new Review();
        review.setRating(reviewForm.getRating());
        review.setComment(reviewForm.getComment());
        review.setPlace(findPlace);
        review.setUser(findUser);
        review.setPlaceInfo(findRes.getPlaceInfo());

        reviewService.saveReview(review);

        return "redirect:user/" + reviewForm.getUserId();
    }

    @GetMapping("/deleteReview")
    public String deleteReview(@RequestParam("id") Long id, @RequestParam("userId") Long userId,
                               HttpServletRequest request) {

        System.out.println("userId = " + userId);
        System.out.println("id = " + id);

        Review findReview = reviewService.findReviewById(id);
        User findUser = userService.findUserById(userId);

        System.out.println("findReview = " + findReview);
        System.out.println("findUser = " + findUser);

        reviewService.deleteReviewById(id);

        return "redirect:user/" + userId;
    }
}

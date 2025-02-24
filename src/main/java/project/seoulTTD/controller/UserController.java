package project.seoulTTD.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.*;
import project.seoulTTD.entity.User;
import project.seoulTTD.entity.place.Place;
import project.seoulTTD.exception.DuplicateFavoriteException;
import project.seoulTTD.repository.UserRepository;
import project.seoulTTD.service.PlaceService;
import project.seoulTTD.service.UserService;
import project.seoulTTD.web.form.UserForm;

import java.util.List;
import java.util.Objects;

@Slf4j
@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final PlaceService placeService;
    private final UserService userService;

    @GetMapping("/user/{id}")
    public String getUserProfile(@PathVariable("id") Long id, Model model) {

        User findUser = userRepository.findUserById(id);
        List<Place> favorites = findUser.getFavorites();

        // User profile data fetching logic
        UserForm userForm = new UserForm();
        userForm.setId(findUser.getId());
        userForm.setName(findUser.getName());
        userForm.setFavorites(findUser.getFavorites());
        userForm.setReservations(findUser.getReservations());
        userForm.setReviews(findUser.getReviews());

        // Populate userForm with data
        model.addAttribute("userForm", userForm);



        return "user/userPage5";
    }

//    @GetMapping("/user/{id}")
//    public String userPage(@PathVariable("id") Long id, Model model) {
//
//        User findUser = userRepository.findById(id).get();
//        List<Place> favorites = findUser.getFavorites();
//
//        UserForm userForm = new UserForm();
//        userForm.setUserId(findUser.getId());
//        userForm.setName(findUser.getName());
//        userForm.setFavorites(findUser.getFavorites());
//        userForm.setReservations(findUser.getReservations());
//        userForm.setReviews(findUser.getReviews());
//
////        model.addAttribute("name", findUser.getName());
////        model.addAttribute("userId", findUser.getId());
////        model.addAttribute("reservations", findUser.getReservations());
////        model.addAttribute("favorites", findUser.getFavorites());
////        model.addAttribute("reviews", findUser.getReviews());
//
//        model.addAttribute("userForm", userForm);
//
////        System.out.println("model.getAttribute(\"userForm\") = " + model.getAttribute("userForm"));
//
//        System.out.println("userForm = " + userForm);
//
//        return "user/userPage4";
//    }

    @PostMapping("/removeFavorite")
    public String deleteFavorite(@RequestParam Long userId, @RequestParam Long placeId, Model model) {

        System.out.println("placeId = " + placeId);

        User findUser = userService.findUserById(userId);
        System.out.println("beforeRemove = " + findUser.getFavorites());
        Place findPlace = placeService.findPlaceById(placeId);
        userService.removeFavorite(findUser, findPlace);

        System.out.println("afterRemove = " + findUser.getFavorites());

        return "redirect:/user/" + userId;
    }

    @GetMapping("/addFavorite")
    public String addFavorite(@RequestParam("userId") Long userId, @RequestParam("placeId") Long placeId, Model model) {

        Place findPlace = placeService.findPlaceById(placeId);
        User findUser = userService.findUserById(userId);

        // 중복 방지 로직
        for (Place ple : findUser.getFavorites()) {
            if (Objects.equals(ple.getId(), placeId)) {
                throw new DuplicateFavoriteException("중복된 즐겨찾기입니다.");
//                bindingResult.addError(new ObjectError("place", "이미 즐겨찾기에 추가된 장소입니다."));
//                model.addAttribute("err", "이미 즐겨찾기에 추가된 장소입니다.");
            }
        }

        userService.addFavorite(userId, findPlace);

        return "redirect:user/" + userId;
    }
}

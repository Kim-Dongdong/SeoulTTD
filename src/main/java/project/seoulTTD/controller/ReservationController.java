package project.seoulTTD.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import project.seoulTTD.entity.Reservation;
import project.seoulTTD.entity.User;
import project.seoulTTD.entity.place.Place;
import project.seoulTTD.entity.place.PlaceInfo;
import project.seoulTTD.service.PlaceInfoService;
import project.seoulTTD.service.PlaceService;
import project.seoulTTD.service.ReservationService;
import project.seoulTTD.service.UserService;
import project.seoulTTD.web.form.ReserveForm;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Controller
@RequiredArgsConstructor
public class ReservationController {

    private final UserService userService;
    private final PlaceService placeService;
    private final PlaceInfoService placeInfoService;
    private final ReservationService reservationService;

    @GetMapping("/reserve")
    public String reserveForm(@RequestParam Long userId, @RequestParam Long placeId, Model model) {

        User findUser = userService.findUserById(userId);
        Place findPlace = placeService.findPlaceById(placeId);
        List<PlaceInfo> placeInfos = findPlace.getPlaceInfos();

        ReserveForm reserveForm = new ReserveForm();
        reserveForm.setUser(findUser);
        reserveForm.setPlace(findPlace);
        reserveForm.setPlaceInfo(placeInfos);

        model.addAttribute("userId", userId);
        model.addAttribute("placeId", placeId);
        model.addAttribute("placeInfos", placeInfos);
        model.addAttribute("reserveForm", reserveForm);
        model.addAttribute("placeName", findPlace.getName());
        model.addAttribute("operatingStart", findPlace.getOperatingStart());
        model.addAttribute("operatingEnd", findPlace.getOperatingEnd());

        return "/reserve/reserveDetail3";
    }

    @PostMapping("/reserveDetail")
    public String reserveDetail(@RequestParam Long userId, @RequestParam Long placeId,
                                @RequestParam PlaceInfo placeInfo,
                                Model model) {

        User findUser = userService.findUserById(userId);
        Place findPlace = placeService.findPlaceById(placeId);
        List<PlaceInfo> placeInfos = findPlace.getPlaceInfos();

        ReserveForm reserveForm = new ReserveForm();
        reserveForm.setUser(findUser);
        reserveForm.setPlace(findPlace);
        reserveForm.setPlaceInfo(placeInfos);

        model.addAttribute("userId", userId);
        model.addAttribute("placeId", placeId);
        model.addAttribute("placeInfos", placeInfos);
        model.addAttribute("reserveForm", reserveForm);
        model.addAttribute("placeName", findPlace.getName());
        model.addAttribute("operatingStart", findPlace.getOperatingStart());
        model.addAttribute("operatingEnd", findPlace.getOperatingEnd());
        model.addAttribute("defaultTime", placeInfo.getDefaultTime());
        model.addAttribute("placeInfoName", placeInfo.getName());
        model.addAttribute("placeInfoId", placeInfo.getId());

        return "/reserve/reserveDetail3-1";
    }

    @PostMapping("/reserve")
    public String createRes(@RequestParam("userId") Long userId, @RequestParam("placeId") Long placeId,
                            @RequestParam("placeInfoId") Long placeInfoId, @RequestParam("selectedTime") Long selectedTime,
                            @RequestParam("selectedMonth") Long selectedMonth, @RequestParam("selectedDay") Long selectedDay,
                            Model model) {
        System.out.println("selectedTime = " + selectedTime);
        User findUser = userService.findUserById(userId);
        Place findPlace = placeService.findPlaceById(placeId);
        PlaceInfo findPlaceInfo = placeInfoService.findPlaceInfoById(placeInfoId);

        System.out.println("startTime = " + selectedTime);
        Integer endTime = selectedTime.intValue() + findPlaceInfo.getDefaultTime();
        int month = selectedMonth.intValue();

        LocalDateTime ldt = LocalDateTime.of(2024, selectedMonth.intValue(),selectedDay.intValue(), selectedTime.intValue(), 0, 0);


        Reservation reservation = Reservation.createReservation(selectedTime.intValue(), endTime, ldt, findUser, findPlace, findPlaceInfo);

        reservationService.createRes(reservation);

        return "redirect:/user/" + userId;
    }

    @GetMapping("/editReserve")
    public String editResForm(@RequestParam("reservationId") Long reservationId, Model model) {

        Reservation findRes = reservationService.findResById(reservationId);
//        Integer endTime = selectedTime.intValue() + findRes.getPlaceInfo().getDefaultTime();
//        reservationService.updateReservation(findRes, selectedTime.intValue(), endTime);

        model.addAttribute("placeName", findRes.getPlace().getName());
        model.addAttribute("placeInfoName", findRes.getPlaceInfo().getName());
        model.addAttribute("startTime", findRes.getStartTime());
        model.addAttribute("endTime", findRes.getEndTime());
        model.addAttribute("operatingStart", findRes.getPlace().getOperatingStart());
        model.addAttribute("operatingEnd", findRes.getPlace().getOperatingEnd());
        model.addAttribute("reservationId", reservationId);

        return "/reserve/reserveEdit";
    }

    @PostMapping("/editReserve")
    public String editRes(@RequestParam("reservationId") Long reservationId, @RequestParam("selectedTime") Long selectedTime, Model model) {

        Reservation findRes = reservationService.findResById(reservationId);
        Integer endTime = selectedTime.intValue() + findRes.getPlaceInfo().getDefaultTime();
        System.out.println("selectedTime = " + selectedTime);
        System.out.println("endTime = " + endTime);
        reservationService.updateReservation(findRes, selectedTime.intValue(), endTime);

        System.out.println("findRes.getUser.getId = " + findRes.getUser().getId());

        return "redirect:/user/" + findRes.getUser().getId();
    }
}

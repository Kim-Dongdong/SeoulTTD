package project.seoulTTD.controller;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import project.seoulTTD.entity.place.Place;
import project.seoulTTD.service.PlaceService;

import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class MainController {

    private final PlaceService placeService;


    @GetMapping("/mainForm/{userId}")
    public String mainForm(@PathVariable("userId") Long userId, Model model) {
        List<Place> findAllPlace = placeService.findAll();
        model.addAttribute("placeList", findAllPlace);
        model.addAttribute("userId", userId);

        return "main/main2";
    }
}

package project.seoulTTD.web.form;

import lombok.Data;
import project.seoulTTD.entity.Reservation;
import project.seoulTTD.entity.Review;
import project.seoulTTD.entity.place.Place;

import java.util.ArrayList;
import java.util.List;

@Data
public class UserForm {

//    private Long userId;
//    private Long placeId;
//    private String name;
    private Long id;
    private String name;
    private List<Place> favorites = new ArrayList<>();
    private List<Reservation> reservations = new ArrayList<>();
    private List<Review> reviews = new ArrayList<>();
}

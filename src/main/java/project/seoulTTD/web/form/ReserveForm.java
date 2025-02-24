package project.seoulTTD.web.form;

import lombok.Data;
import project.seoulTTD.entity.User;
import project.seoulTTD.entity.place.Place;
import project.seoulTTD.entity.place.PlaceInfo;

import java.util.List;

@Data
public class ReserveForm {

    private User user;
    private Place place;
    private List<PlaceInfo> placeInfo;
    private List<Integer> operatingHours;

}

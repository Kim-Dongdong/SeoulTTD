package project.seoulTTD.repository.condition;

import lombok.Data;
import project.seoulTTD.entity.Regions;
import project.seoulTTD.entity.place.PlaceType;

@Data
public class PlaceCondition {

    private String name; // 이름
    private Regions regions; // 지역
    private Integer priceGoe; // 가격(이상)
    private Integer priceLoe; // 가격(이하)
    private Integer operatingHoursGoe; // 운영시간(이상)
    private Integer operatingHoursLoe; // 운영시간(이하)
    private PlaceType type;
}

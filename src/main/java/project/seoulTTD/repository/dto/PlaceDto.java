package project.seoulTTD.repository.dto;

import com.querydsl.core.annotations.QueryProjection;
import lombok.Getter;
import lombok.Setter;
import project.seoulTTD.entity.Regions;
import project.seoulTTD.entity.Review;
import project.seoulTTD.entity.place.PlaceType;

@Getter @Setter
public class PlaceDto {

    private String name; // 이름
    private Regions region; // 지역
    private Integer price;
    private Integer operatingStart;
    private Integer operatingEnd;
    private PlaceType type;
    private String website;
    private String address;
    private String postalCode;
    private String phoneNumber;
    private Review review;

    @QueryProjection
    public PlaceDto(String name, Regions region, Integer price, String website, String postalCode, String address,
                    String phoneNumber, Integer operatingStart, Integer operatingEnd,PlaceType type) {
        this.name = name;
        this.region = region;
        this.price = price;
        this.website = website;
        this.postalCode = postalCode;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.operatingStart = operatingStart;
        this.operatingEnd = operatingEnd;
        this.type = type;

    }
}

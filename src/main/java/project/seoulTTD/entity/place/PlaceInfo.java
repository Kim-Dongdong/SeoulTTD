package project.seoulTTD.entity.place;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.seoulTTD.entity.Review;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
public class PlaceInfo {

    @Id
    @GeneratedValue
    @Column(name = "placeInfo_id")
    private Long id;

    private String name; // 테마 또는 방 이름 등 다양한 이름

    private List<Integer> timeList = new ArrayList<>();

    private Integer defaultTime;

    @OneToMany(mappedBy = "placeInfo")
    private List<Review> reviews = new ArrayList<>();

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "place_id")
    private Place place;

    public PlaceInfo() {}

    // 테마 이름, 타임리스트 등 해당 Place에 대한 세부 정보를 필드값으로 가지면 된다

    public PlaceInfo(String name, Place place, Integer defaultTime) {

        place.getPlaceInfos().add(this);
        this.setPlace(place);

        this.name = name;

        for (int i = place.getOperatingStart(); i < place.getOperatingEnd(); i++) {
            this.timeList.add(i);
        }

        this.defaultTime = defaultTime;
    }

    // 편의 메서드
    public void setPlaceInfo(Place place) {
        place.getPlaceInfos().add(this);
        this.setPlace(place);
    }

    public void setReview(Review review) {
        this.reviews.add(review);
        review.setPlaceInfo(this);
    }



}

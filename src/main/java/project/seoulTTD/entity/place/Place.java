package project.seoulTTD.entity.place;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.seoulTTD.entity.Region;
import project.seoulTTD.entity.Reservation;
import project.seoulTTD.entity.Review;


import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "Place")
public class Place{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "place_id")
    private Long id;

    private String name;
    private String address;
    private Integer price;
    private String postalCode;
    private String phoneNumber;
    private Integer operatingStart;
    private Integer operatingEnd;

    private String website;
    private List<String> photos = new ArrayList<>();

    // 방 여유 count
    private Integer roomCount;

    // 방 타입
    @Enumerated
    private PlaceType type;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "region_id")
    private Region region;

    @OneToMany(mappedBy = "place")
    private List<Review> reviews = new ArrayList<>();

    @OneToMany(mappedBy = "place")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "place")
    private List<PlaceInfo> placeInfos = new ArrayList<>();

//    @OneToMany(mappedBy = "place")
//    private List<PlaceResInfo> resInfos = new ArrayList<>();

    // 기본 생성자
    public Place() {}

    // 생성자
    public Place(String name, String address, Integer price, String postalCode, String phoneNumber,
                 Integer operatingStart, Integer operatingEnd, String website, Integer roomCount,
                 PlaceType type, Region region, List<Review> reviews, List<Reservation> reservations) {
        this.name = name;
        this.address = address;
        this.price = price;
        this.postalCode = postalCode;
        this.phoneNumber = phoneNumber;
        this.operatingStart = operatingStart;
        this.operatingEnd = operatingEnd;
        this.website = website;
        this.roomCount = roomCount;
        this.type = type;
        this.region = region;
        this.reviews = reviews;
        this.reservations = reservations;
    }

    // 연관관계 편의 메서드
    public void setRegion(Region region) {
        region.getPlaces().add(this);
        this.region = region;
    }

    public void setReview(Review review) {
        review.setPlace(this);
        this.reviews.add(review);
    }

    // 비즈니스 로직
    public void addTimeList(Reservation reservation) {

    }

    public void cancelRes(Reservation reservation) {

    }

}

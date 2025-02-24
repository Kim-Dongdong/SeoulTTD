package project.seoulTTD.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import project.seoulTTD.entity.place.Place;
import project.seoulTTD.entity.place.PlaceInfo;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@Table(name = "Review")
public class Review {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "review_id")
    private Long id;

    private int rating; // 1~5
    private String comment;

    @CreatedDate
    private LocalDateTime createdDate;

    @LastModifiedDate
    private LocalDateTime lastModifiedDate;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user; // User 정보 필요

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "place_id")
    private Place place; // 사용자 리뷰 필요

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "placeInfo_id")
    private PlaceInfo placeInfo;

    // 비즈니스 로직
//    public void settingPlaceInfo(PlaceInfo placeInfo) {
//        placeInfo.setReview(this);
//        this.setPlaceInfo(placeInfo);
//    }
}

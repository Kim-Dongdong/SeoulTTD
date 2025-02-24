package project.seoulTTD.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.seoulTTD.entity.place.Place;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "Member")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    private Long id;

    private String loginId;

    private String name;

    @Column(unique = true)
    private String email;

    private String password;

    private String phoneNum;

    @OneToMany
    private List<Place> favorites = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations = new ArrayList<>();

    @OneToMany(mappedBy = "user")
    private List<Review> reviews = new ArrayList<>();


    // 생성자
//    public User(String name, String email, String password, List<Favorite> favorites, List<Reservation> reservations, List<Review> reviews) {
//        this.name = name;
//        this.email = email;
//        this.password = password;
//        this.favorites = favorites;
//        this.reservations = reservations;
//        this.reviews = reviews;
//    }

    // 연관관계 편의 메서드
    public void setFavorite(Place place) {
        this.favorites.add(place);
    }

    public void setReview(Review review) {
        this.reviews.add(review);
        review.setUser(this);
    }

    // 예약 취소 로직
    public void cancelReservation(Reservation reservation) {
        this.getReservations().remove(reservation);
    }
}


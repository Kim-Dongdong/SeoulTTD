package project.seoulTTD.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.validator.internal.util.privilegedactions.LoadClass;
import org.springframework.cglib.core.Local;
import project.seoulTTD.entity.place.Place;
import project.seoulTTD.entity.place.PlaceInfo;
import project.seoulTTD.exception.NoEnoughRoomException;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "Reservation")
@Slf4j
public class Reservation {

    static int TF;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "reservation_id")
    private Long id;

    private Integer startTime;
    private Integer endTime;

    private LocalDateTime reservedDate;

    private int tf;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "place_id")
    private Place place;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "placeInfo_id")
    private PlaceInfo placeInfo;

    public Reservation() {
    }

    // 생성자
    public Reservation (Integer startTime, Integer endTime, LocalDateTime reservedDate,
                        User user, Place place) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.reservedDate = reservedDate;
        this.user = user;
        this.place = place;
    }

    public static Reservation createReservation(Integer startTime, Integer endTime, LocalDateTime reservedDate,
                                                User user, Place place, PlaceInfo placeInfo) {
        Reservation reservation = new Reservation();
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);
        reservation.setReservedDate(reservedDate);
        reservation.setUser(user);
        reservation.setPlace(place);
        reservation.setPlaceInfo(placeInfo);

        LocalDateTime now = LocalDateTime.now();

        if (now.isAfter(reservedDate)) {
            reservation.setTf(1);
            System.out.println("reservation.getTf() = " + reservation.getTf());
        }

//        String year = now.substring(0, 4);
//        String month = now.substring(5, 7);
//        String day = now.substring(8, 10);
//        String hour = now.substring(11, 13);
////        System.out.println("year = " + year);
////        System.out.println("month = " + month);
////        System.out.println("day = " + day);
////        System.out.println("hour = " + hour);
////
////        System.out.println("reservedDate.substring(0, 4) = " + reservedDate.substring(0, 4));
////        System.out.println("Integer.parseInt(reservedDate.substring(5, 7) = " + Integer.parseInt(reservedDate.substring(5, 7)));
////        System.out.println("Integer.parseInt(reservedDate.substring(8, 10) = " + Integer.parseInt(reservedDate.substring(8, 10)));
////        System.out.println("Integer.parseInt(reservedDate.substring(11, 13) = " + Integer.parseInt(reservedDate.substring(11, 13)));
//        if (Integer.parseInt(reservedDate.substring(0, 4)) <= Integer.parseInt(year)) {
//            TF += 1;
//            log.info("year");
//            if (Integer.parseInt(reservedDate.substring(5, 7)) <= Integer.parseInt(month)) {
//                TF += 1;
//                log.info("month");
//                if (Integer.parseInt(reservedDate.substring(8, 10)) <= Integer.parseInt(day)) {
//                    TF += 1;
//                    log.info("day");
//                    if (Integer.parseInt(reservedDate.substring(11, 13)) <= Integer.parseInt(hour) + 1) {
//                        TF += 1;
//                        log.info("hour");
//                    }
//                }
//            }
//        }
//        2024 08 21 2024 09 01

        return reservation;
    }

    // 연관관계 편의 메서드
    public void setPlace(Place place) {
        this.place = place;
    }

    public void setUser(User user) {
        this.user = user;
        user.getReservations().add(this);
    }

    public void deleteUser(Reservation reservation) {
        this.user.getReservations().remove(this);
    }

    /**
     * 비즈니스 로직
     */


    // 초기 예약 생성
    public void createResFirst(User user, Place place) {

    }

    public void ableReservation() {
        if (this.getPlace().getRoomCount() == 0) {
            throw new NoEnoughRoomException("예약 가능한 방이 없습니다.");
        }
    }







}

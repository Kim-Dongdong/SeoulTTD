package project.seoulTTD;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import project.seoulTTD.entity.*;
import project.seoulTTD.entity.place.Place;
import project.seoulTTD.entity.place.PlaceInfo;
import project.seoulTTD.entity.place.PlaceType;
import project.seoulTTD.service.*;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Component
@RequiredArgsConstructor
public class InitDb {

    private final InitService initService;

    @PostConstruct
    public void init() {
        initService.dbInit1();
    }

    @Component
    @Transactional
    @RequiredArgsConstructor
    static class InitService {

        private final UserService userService;
        private final PlaceService placeService;
        private final ReservationService reservationService;
        private final ReviewService reviewService;
        private final PlaceInfoService placeInfoService;

        public void dbInit1() {

            User user1 = new User();
            user1.setName("김동섭");
            user1.setPhoneNum("01094506423");
            user1.setLoginId("test");
            user1.setPassword("12345");
            User user2 = new User();
            user2.setName("김동땡");
            user2.setPhoneNum("01062346612");
            User user3 = new User();
            user3.setName("김동화");
            user3.setPhoneNum("01056246724");
            userService.join(user1);
            userService.join(user2);
            userService.join(user3);



            Region region1 = new Region();
            region1.setRegions(Regions.강남);
            Region region2 = new Region();
            region2.setRegions(Regions.신촌);



            Place place1 = new Place("서울이스케이프룸 - 강남1호점", "서울 서초구 서초대로77길 11 S호텔 빌딩 지하1층", 15000, "23512", "1641361",
                    10, 22, "https://seoul-escape.com/", 5, PlaceType.EscapeRoom, region1, null, null);
            Place place2 = new Place("리얼 이스케이프 첼린지", "서울 마포구 와우산로27길 21 4층", 20000, "12525", "62346161",
                    10, 22, "https://www.rec-escape.com/", 5, PlaceType.EscapeRoom, region2, null, null);
            Place place3 = new Place("신촌 이스케이프존", "서울 강남구 강남대로 12-3", 18000, "23151", "2612467",
                    8, 24, "https://www.gananamescapezone.com/", 5, PlaceType.EscapeRoom, region2, null, null);
            Place place4 = new Place("단편선 방탈출", "서울 강남구 봉은사로4길 36 지하1층 02호", 25000, "06123", "07088660795",
                    10, 24, "http://www.dpsnnn.com/", 6, PlaceType.EscapeRoom, region1, null, null);
            Place place5 = new Place("비트포비아 던전루나", "서울 강남구 강남대로94길 20 삼오빌딩 지하1층", 26000, "06134", "025551008",
                    9, 24, "https://xdungeon.net/", 5, PlaceType.EscapeRoom, region1, null, null);


            place1.getPhotos().add("/photos/seoulescaperoomgangnam1.jpg");
            place2.getPhotos().add("/photos/realescapechallenge.jpg");
            place3.getPhotos().add("/photos/shinchonecsapezone.jpg");
            place4.getPhotos().add("/photos/dpsnnn.png");
            place5.getPhotos().add("/photos/dungeonluna.png");
            placeService.join(place1);
            placeService.join(place2);
            placeService.join(place3);
            placeService.join(place4);
            placeService.join(place5);




            Review review1 = new Review();
            review1.setRating(5);
            review1.setComment("사장이 맛있고, 음식이 친절해요");
            review1.setUser(user1);
            review1.setPlace(place1);

            List<Review> reviews1 = Arrays.asList(review1);
            Review review2 = new Review();
            review2.setRating(4);
            review2.setComment("맛있어서 저희집 개가 다먹었어요..");
            review2.setUser(user1);
            review2.setPlace(place2);
            List<Review> reviews2 = Arrays.asList(review2);
            Review review3 = new Review();
            review3.setRating(5);
            review3.setComment("맛있네영");
            review3.setUser(user3);
            review3.setPlace(place2);
            List<Review> reviews3 = Arrays.asList(review3);
            reviewService.saveReview(review1);
            reviewService.saveReview(review2);
            reviewService.saveReview(review3);



            PlaceInfo placeInfo1 = new PlaceInfo("카지노", place1, 1);
            PlaceInfo placeInfo2 = new PlaceInfo("준단화-봄을 잘라낸 꽃", place1, 1);
            PlaceInfo placeInfo3 = new PlaceInfo("팩토리", place1, 1);
            PlaceInfo placeInfo4 = new PlaceInfo("고문실", place2, 2);
            PlaceInfo placeInfo5 = new PlaceInfo("엘리베이터", place2, 1);
            PlaceInfo placeInfo6 = new PlaceInfo("파킹랏", place2, 1);
            PlaceInfo placeInfo7 = new PlaceInfo("JACOB", place3, 2);
            PlaceInfo placeInfo8 = new PlaceInfo("바라보다", place3, 1);
            PlaceInfo placeInfo9 = new PlaceInfo("방민정음", place3, 1);
            PlaceInfo placeInfo10 = new PlaceInfo("상자", place4, 2);
            PlaceInfo placeInfo11 = new PlaceInfo("죽음", place4, 1);
            PlaceInfo placeInfo12 = new PlaceInfo("행복", place4, 1);
            PlaceInfo placeInfo13 = new PlaceInfo("꿈의 공장", place4, 2);
            PlaceInfo placeInfo14 = new PlaceInfo("향", place4, 1);
            PlaceInfo placeInfo15 = new PlaceInfo("날씨의 신", place4, 1);
            review1.setPlaceInfo(placeInfo1);
            review2.setPlaceInfo(placeInfo9);
            placeInfo3.setReview(review1);
            placeInfo9.setReview(review2);



            placeInfoService.save(placeInfo1);
            placeInfoService.save(placeInfo2);
            placeInfoService.save(placeInfo3);
            placeInfoService.save(placeInfo4);
            placeInfoService.save(placeInfo5);
            placeInfoService.save(placeInfo6);
            placeInfoService.save(placeInfo7);
            placeInfoService.save(placeInfo8);
            placeInfoService.save(placeInfo9);
            placeInfoService.save(placeInfo10);
            placeInfoService.save(placeInfo11);
            placeInfoService.save(placeInfo12);
            placeInfoService.save(placeInfo13);
            placeInfoService.save(placeInfo14);
            placeInfoService.save(placeInfo15);




            List<Review> reviews = new ArrayList<>(Arrays.asList(review1, review2));
            place1.setReviews(reviews); // place1에 Review List 저장
            List<Place> favorites = new ArrayList<>();
            favorites.add(place1);
            favorites.add(place2);
            favorites.add(place3);
            user1.setFavorites(favorites);



            LocalDateTime ldt1 = LocalDateTime.of(2024, 8, 16, 12, 0, 0,0);
            LocalDateTime ldt2 = LocalDateTime.of(2024, 8, 19, 16, 0, 0,0);
            LocalDateTime ldt3 = LocalDateTime.of(2024, 9, 21, 11, 0, 0,0);
            Reservation reservation1 = Reservation.createReservation(12, 14, ldt1, user1, place1, placeInfo3);
            Reservation reservation2 = Reservation.createReservation(15, 17, ldt2, user1, place2, placeInfo9);
            Reservation reservation3 = Reservation.createReservation(18, 20, ldt3, user1, place3, placeInfo5);
            reservationService.createRes(reservation1);
            reservationService.createRes(reservation2);
            reservationService.createRes(reservation3);


        }
    }
}
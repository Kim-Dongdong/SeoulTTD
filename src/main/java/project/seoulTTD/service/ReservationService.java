package project.seoulTTD.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seoulTTD.entity.Reservation;
import project.seoulTTD.repository.ReservationRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ReservationService {

    private final ReservationRepository reservationRepository;
    private final PlaceInfoService placeInfoService;

    // 예약 생성
    @Transactional
    public Long createRes(Reservation reservation) { // controller에서 @ModelAttribute로 받아 실행할거임
//        validDuplicatePlace(reservation); // 중복 예약 방지
        reservation.ableReservation(); // 장소의 예약 가능 여부 확인

        placeInfoService.timeListCheck(reservation.getStartTime(), reservation.getEndTime(), reservation.getPlaceInfo()); // 장소 예약 시간 중복 확인
        Reservation savedReservation = reservationRepository.save(reservation);
        // 여기에 Place에도 예약 정보를 날려 중복 예약을 방지해야함
        placeInfoService.timeListMin(reservation); // timelistmin
        return savedReservation.getId();
    }

    // 모든 예약 조회
    public List<Reservation> findAll() {
        return reservationRepository.findAll();
    }

    public Reservation findResById(Long id) {
        return reservationRepository.findReservationById(id);
    }

    // 예약 취소
    @Transactional
    public void cancelReservation(Reservation reservation) {
        reservation.deleteUser(reservation);
        placeInfoService.timeListAdd(reservation);
    }

    // 예약 변경 (예약 id를 가지고 와서 findById한 다음 찾은 예약 정보를 수정)
    public void updateReservation(Reservation reservation, Integer startTime, Integer endTime) {


        for (int i = reservation.getStartTime(); i < reservation.getEndTime(); i++) { // 기존 시간을 추가
            reservation.getPlaceInfo().getTimeList().add(i);
        }

        placeInfoService.timeListCheck(startTime, endTime, reservation.getPlaceInfo()); // 시간이 중복되는지 확인

        List<Integer> removes = new ArrayList<>();
        for (int i = startTime; i < endTime; i++) {
            removes.add(i);
        }

        System.out.println("removes = " + removes);
        Iterator<Integer> iterator = removes.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.println("next = " + next);
            reservation.getPlaceInfo().getTimeList().remove(next);
        }

        // 값 변경 로직
        reservation.setStartTime(startTime);
        reservation.setEndTime(endTime);

        Collections.sort(reservation.getPlaceInfo().getTimeList()); // 오름차순 정렬
    }

}

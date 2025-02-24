package project.seoulTTD.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.seoulTTD.entity.Reservation;
import project.seoulTTD.entity.place.PlaceInfo;
import project.seoulTTD.exception.DuplicateTimeException;
import project.seoulTTD.repository.PlaceInfoRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class PlaceInfoService {

    private final PlaceInfoRepository placeInfoRepository;

    public PlaceInfo findPlaceByName(String name) {
        return placeInfoRepository.findByName(name);
    }

    public PlaceInfo findPlaceInfoById(Long id) {
        return placeInfoRepository.findPlaceInfoById(id);
    }

    public Long save(PlaceInfo placeInfo) {
        return placeInfoRepository.save(placeInfo).getId();
    }

    // 최종 timeList 정리
    public void timeListAdd(Reservation reservation) {
        for (int i = reservation.getStartTime(); i < reservation.getEndTime(); i++) {
            reservation.getPlaceInfo().getTimeList().add(i);
        }
        Collections.sort((reservation.getPlaceInfo().getTimeList())); // 오름차순 정렬
    }

    public void timeListMin(Reservation reservation) { // 예약 생성 시 해당 time 제거
        List<Integer> removes = new ArrayList<>();
        for (int i = reservation.getStartTime(); i < reservation.getEndTime(); i++) {
            removes.add(i);
        }

        System.out.println("removes = " + removes);

        Iterator<Integer> iterator = removes.iterator();
        while (iterator.hasNext()) {
            Integer next = iterator.next();
            System.out.println("next = " + next);
            reservation.getPlaceInfo().getTimeList().remove(next);
        }
        Collections.sort(reservation.getPlaceInfo().getTimeList()); // 오름차순 정렬
    }

    public void timeListCheck(Integer startTime, Integer endTime, PlaceInfo placeInfo) {

        for (int i = startTime; i < endTime; i++) {
            if (!placeInfo.getTimeList().contains(i)) {
                throw new DuplicateTimeException("해당 시간은 예약이 이미 되있습니다. 다른 시간을 선택해주세요.");
            }
        }
    }

}

package project.seoulTTD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.seoulTTD.entity.place.PlaceInfo;

public interface PlaceInfoRepository extends JpaRepository<PlaceInfo, Long> {

    public PlaceInfo findByName(String name);

    public PlaceInfo findPlaceInfoById(Long id);
}

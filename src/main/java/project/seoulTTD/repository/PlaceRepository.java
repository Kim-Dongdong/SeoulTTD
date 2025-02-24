package project.seoulTTD.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.seoulTTD.entity.Regions;
import project.seoulTTD.entity.place.Place;

import java.util.List;

public interface PlaceRepository extends JpaRepository<Place, Long>, PlaceRepositoryQuerydsl {


}

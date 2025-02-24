package project.seoulTTD.entity;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.seoulTTD.entity.place.Place;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter @Setter
@Table(name = "Region")
public class Region {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "region_id")
    private Long id;

    private Regions regions; // ex) 홍대, 건대, 신촌...

//    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
//    @JoinColumn(name = "parent_id")
//    private Region parent;
//
//    @OneToMany(mappedBy = "parent")
//    private List<Region> subRegions;

    @OneToMany(mappedBy = "region")
    private List<Place> places = new ArrayList<>();

    // 연관관계 편의 메서드


}

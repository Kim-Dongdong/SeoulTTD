package project.seoulTTD.repository;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import project.seoulTTD.entity.QRegion;
import project.seoulTTD.entity.Regions;
import project.seoulTTD.entity.Review;
import project.seoulTTD.entity.place.Place;
import project.seoulTTD.entity.place.PlaceType;
import project.seoulTTD.repository.condition.PlaceCondition;
import project.seoulTTD.repository.dto.PlaceDto;
import project.seoulTTD.repository.dto.QPlaceDto;

import java.util.List;

import static project.seoulTTD.entity.QRegion.region;
import static project.seoulTTD.entity.QReview.review;
import static project.seoulTTD.entity.place.QPlace.place;

public class PlaceRepositoryQuerydslImpl implements PlaceRepositoryQuerydsl {


    private final JPAQueryFactory queryFactory;

    public PlaceRepositoryQuerydslImpl(EntityManager em) {
        this.queryFactory = new JPAQueryFactory(em);
    }

    // 지역+가격 조건 검색
    @Override
    public List<Place> findByCond(PlaceCondition cond) {
        return queryFactory
                .selectFrom(place)
                .leftJoin(place.region)
                .where(
                        regionCond(cond.getRegions()),
                        priceCondGoe(cond.getPriceGoe()),
                        priceCondLoe((cond.getPriceLoe())))
                .fetch();
    }

    @Override
    public List<PlaceDto> findByCondDto(PlaceCondition cond) {
//        return null;
        return queryFactory
                .select(new QPlaceDto(
                        place.name,
                        place.region.regions,
                        place.price,
                        place.website,
                        place.postalCode,
                        place.address,
                        place.phoneNumber,
                        place.operatingStart,
                        place.operatingEnd,
                        place.type
                ))
                .from(place)
                .leftJoin(place.region, region)
                .where(
                        regionCond(cond.getRegions()),
                        priceCondGoe(cond.getPriceGoe()),
                        priceCondLoe(cond.getPriceLoe()),
                        nameCond(cond.getName()),
                        operatingStartCond(cond.getOperatingHoursGoe()),
                        operatingEndCond(cond.getOperatingHoursLoe()),
                        typeCond(cond.getType())
                )
                .fetch();
    } // 만약 단순 장소만 조회하고싶다? -> 그냥 조건에 Place만 넣으면 됨
    // 여러 Region을 선택했다면? -> 그만큼 이 로직을 실행시켜서 view로 반환하면 됨..


    @Override
    public PlaceDto findPlaceDtoById(Long id) {

//        return null;
        return queryFactory
                .select(new QPlaceDto(
                        place.name,
                        place.region.regions,
                        place.price,
                        place.website,
                        place.postalCode,
                        place.address,
                        place.phoneNumber,
                        place.operatingStart,
                        place.operatingEnd,
                        place.type
                ))
                .from(place)
                .leftJoin(place.region, region)
                .where(place.id.eq(id))
                .fetchOne();
    }

    @Override
    public List<Review> findReviewAll(Long placeId, int rating) {

        return queryFactory
                .select(review)
                .from(place)
                .leftJoin(place.reviews, review)
                .where(place.id.eq(placeId))
                .orderBy(review.rating.asc())
                .fetch();
    }

    @Override
    public List<Place> findPlaceByRegion(String region) {
        return queryFactory
                .select(place)
                .from(place)
                .leftJoin(place.region, QRegion.region)
                .where(regionCond2(region))
                .fetch();
    }

    private BooleanExpression regionCond(Regions regionCond) {
        return regionCond != null ? place.region.regions.eq(Regions.valueOf(regionCond.toString())) : null;
    }
    private BooleanExpression priceCondGoe(Integer priceCondGoe) {
        return priceCondGoe != null ? place.price.goe(priceCondGoe) : null;
    }
    private BooleanExpression priceCondLoe(Integer priceCondLoe) {
        return priceCondLoe != null ? place.price.loe(priceCondLoe) : null;
    }
    private BooleanExpression nameCond(String nameCond) {
        return nameCond != null ? place.name.contains(nameCond) : null;
    }
    private BooleanExpression operatingStartCond(Integer operatingStart) {
        return operatingStart != null ? place.operatingStart.loe(operatingStart) : null;
    } // 개장 시간을 검색하는건 그 이전에 문을 여는 매장을 찾는것이기 때문에 loe로 검색한다.
    private BooleanExpression operatingEndCond(Integer operatingEnd) {
        return operatingEnd != null ? place.operatingEnd.goe(operatingEnd) : null;
    } // 폐장 시간을 검색하는건 그 이후에 문을 여는 매장을 찾는것이기 때문에 goe로 검색한다.
    private BooleanExpression typeCond(PlaceType type) {
        return type != null ? place.type.eq(type) : null;
    }
    private BooleanExpression regionCond2(String regionCond) {
        return regionCond != null ? place.region.regions.eq(Regions.valueOf(regionCond)) : null;
    }

}








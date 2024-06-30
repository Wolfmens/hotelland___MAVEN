package com.study.hotelland.repository.specification;

import com.study.hotelland.entity.Hotel;
import com.study.hotelland.web.dto.filter.HotelFilter;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.util.Objects;

public interface HotelSpecification {

    static Specification<Hotel> filterHotelsBy(HotelFilter filter) {
        return Specification
                .where(findById(filter))
                .and(findByHotelName(filter))
                .and(findByHotelsheadline(filter))
                .and(findByCity(filter))
                .and(findByHotelsAdress(filter))
                .and(findByHotelsDistanceToCenter(filter))
                .and(findByHotelsRating(filter))
                .and(findByHotelsNumbersRatings(filter));
    }

    static Specification<Hotel> findByHotelsNumbersRatings(HotelFilter filter) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(filter.getNumberRatings())) {
                return null;
            }

            return criteriaBuilder.equal(root.get("numberRatings"), filter.getNumberRatings());
        };
    }

    static Specification<Hotel> findByHotelsRating(HotelFilter filter) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(filter.getHotelRating())) {
                return null;
            }
            if (filter.getNumberRatings() == 5) {
                return criteriaBuilder.equal(root.get("hotelRating"), filter.getHotelRating());
            }

            return criteriaBuilder.between(root.get("hotelRating"), filter.getHotelRating(), filter.getHotelRating() + 1);
        };
    }

    static Specification<Hotel> findByHotelsDistanceToCenter(HotelFilter filter) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(filter.getDistanceToCenter())) {
                return null;
            }

            return criteriaBuilder.equal(root.get("distanceToCenter"), filter.getDistanceToCenter());
        };

    }

    static Specification<Hotel> findByHotelsAdress(HotelFilter filter) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(filter.getAddress())) {
                return null;
            }

            return criteriaBuilder.equal(root.get("address"), filter.getAddress());
        };
    }

    static Specification<Hotel> findByCity(HotelFilter filter) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(filter.getCity())) {
                return null;
            }

            return criteriaBuilder.equal(root.get("city"), filter.getCity());
        };

    }

    static Specification<Hotel> findByHotelsheadline(HotelFilter filter) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(filter.getHeadline())) {
                return null;
            }

            return criteriaBuilder.equal(root.get("headline"), filter.getHeadline());
        };
    }

    static Specification<Hotel> findById(HotelFilter filter) {
        return ((root, query, criteriaBuilder) -> {
            if (filter.getId() == null) {
                return null;
            }

            return criteriaBuilder.equal(root.get("id"), filter.getId());
        });
    }

    static Specification<Hotel> findByHotelName(HotelFilter filter) {
        return ((root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(filter.getName())) {
                return null;
            }

            return criteriaBuilder.equal(root.get("name"), filter.getName());
        });
    }


}

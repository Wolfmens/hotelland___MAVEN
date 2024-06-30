package com.study.hotelland.repository.specification;

import com.study.hotelland.entity.Room;
import com.study.hotelland.exception.IncorrectRequestFilterFiledByArrivalOrDepartureDate;
import com.study.hotelland.web.dto.filter.RoomFilter;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

public interface RoomSpecification {

    static Specification<Room> filterBy(RoomFilter filter) {
        return Specification
                .where(filterByRoomId(filter))
                .and(filterByDescription(filter))
                .and(filterByMaxOrMinPrice(filter))
                .and(filterByMaxPeople(filter))
                .and(filterByArrivalAndDepartureDates(filter))
                .and(filterByHotelId(filter));
    }

    static Specification<Room> filterByHotelId(RoomFilter filter) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(filter.getHotelId())) {
                return null;
            }

            return criteriaBuilder.equal(root.get("hotel").get("id"), filter.getHotelId());
        };
    }

    static Specification<Room> filterByArrivalAndDepartureDates(RoomFilter filter) {
        return (root, query, criteriaBuilder) -> {
            LocalDate arrivalDate = filter.getArrival();
            LocalDate departureDate = filter.getDeparture();

            if (arrivalDate == null && departureDate == null) {
                return null;
            }

            if (arrivalDate == null && departureDate != null ||
                    arrivalDate != null && departureDate == null) {
                throw new IncorrectRequestFilterFiledByArrivalOrDepartureDate();
            }

            List<LocalDate> listDateForSearchAvailableRooms =
                    arrivalDate.datesUntil(departureDate.plusDays(1)).toList();

//            List<Predicate> predicate1 = listDateForSearchAvailableRooms.stream()
//                    .map(date -> criteriaBuilder.isNotMember(date, root.get("blockDates"))).toList();

            Predicate predicate = criteriaBuilder.conjunction();

            for (LocalDate date : listDateForSearchAvailableRooms) {
                predicate = criteriaBuilder.and(predicate, criteriaBuilder.isNotMember(date, root.get("blockDates")));
            }

            return predicate;
        };
    }

    static Specification<Room> filterByMaxPeople(RoomFilter filter) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(filter.getMaxPeople())) {
                return null;
            }

            return criteriaBuilder.equal(root.get("maxPeople"), filter.getMaxPeople());
        };
    }

    static Specification<Room> filterByMaxOrMinPrice(RoomFilter filter) {
        return (root, query, criteriaBuilder) -> {
            if (filter.getMinPrice() == null && filter.getMaxPrice() == null) {
                return null;
            }
            if (filter.getMinPrice() == null) {
                return criteriaBuilder.lessThanOrEqualTo(root.get("price"), filter.getMaxPrice());
            }
            if (filter.getMaxPrice() == null) {
                return criteriaBuilder.greaterThanOrEqualTo(root.get("price"), filter.getMinPrice());
            }

            return criteriaBuilder.between(root.get("price"), filter.getMinPrice(), filter.getMaxPrice());
        };
    }

    static Specification<Room> filterByDescription(RoomFilter filter) {
        return (root, query, criteriaBuilder) -> {
            if (!StringUtils.hasText(filter.getDescription())) {
                return null;
            }

            return criteriaBuilder.equal(root.get("description"), filter.getDescription());
        };
    }

    static Specification<Room> filterByRoomId(RoomFilter filter) {
        return (root, query, criteriaBuilder) -> {
            if (Objects.isNull(filter.getRoomId())) {
                return null;
            }

            return criteriaBuilder.equal(root.get("id"), filter.getRoomId());
        };
    }

}

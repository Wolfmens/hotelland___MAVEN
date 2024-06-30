package com.study.hotelland.service.impl;

import com.study.hotelland.entity.Hotel;
import com.study.hotelland.exception.ListIsEmptyByFilter;
import com.study.hotelland.exception.NotFoundEntityException;
import com.study.hotelland.mapper.HotelMapper;
import com.study.hotelland.repository.HotelRepository;
import com.study.hotelland.repository.specification.HotelSpecification;
import com.study.hotelland.service.HotelService;
import com.study.hotelland.util.NotNullCopyProperty;
import com.study.hotelland.web.dto.filter.HotelFilter;
import com.study.hotelland.web.dto.hotel.HotelRequest;
import com.study.hotelland.web.dto.hotel.HotelResponse;
import com.study.hotelland.web.dto.hotel.HotelResponseList;
import com.study.hotelland.web.dto.hotel.HotelResponseWithRatingAndNumberRatings;
import lombok.RequiredArgsConstructor;
import org.apache.commons.beanutils.BeanUtilsBean;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.MessageFormat;
import java.util.Arrays;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class HotelServiceImpl implements HotelService {

    private final HotelRepository repository;

    private final HotelMapper hotelMapper;

    @Override
    public HotelResponseList findAll(Integer pageSize, Integer pageNumber) {
        return hotelMapper.listHotelEntityToHotelResponseLIst
                (repository.findAll(PageRequest.of(pageNumber,pageSize)).getContent());
    }

    @Override
    public HotelResponse findById(Long id) {
        return hotelMapper.hotelEntityToHotelResponse(findHotelByIdFromBD(id));
    }

    @Override
    public HotelResponse create(HotelRequest request) {
        return hotelMapper.hotelEntityToHotelResponse(repository.save(hotelMapper.hotelRequestToHotelEntity(request)));
    }

    @Override
    public HotelResponse update(Long id, HotelRequest request) {
        Hotel hotelFromRequestForUpdate = hotelMapper.hotelRequestToHotelEntity(id, request);
        Hotel hotelFromDBForUpdate = findHotelByIdFromBD(hotelFromRequestForUpdate.getId());

        NotNullCopyProperty.copyNonNullProperties(hotelFromRequestForUpdate, hotelFromDBForUpdate, new String[]{});

        return hotelMapper.hotelEntityToHotelResponse(repository.save(hotelFromDBForUpdate));
    }

    @Override
    public void deleteById(Long id) {
        repository.deleteById(id);
    }

    @Override
    public HotelResponseWithRatingAndNumberRatings addHotelRating(Long newMark, Long hotelId) {
        Hotel hotelFromBD = findHotelByIdFromBD(hotelId);
        if (hotelFromBD.getHotelRating() == null) {
            hotelFromBD.setHotelRating(1D);
            hotelFromBD.setNumberRatings(1L);
        }
        double currentHotelRating = hotelFromBD.getHotelRating();
        long countRatings = hotelFromBD.getNumberRatings();

        double totalRatings = currentHotelRating * countRatings;
        totalRatings = totalRatings - currentHotelRating + newMark;

        MathContext context = new MathContext(2, RoundingMode.HALF_UP);
        BigDecimal estimatedRatingHotelRating = new BigDecimal(totalRatings / countRatings, context);

        double newCurrentHotelRating = Math.min(estimatedRatingHotelRating.doubleValue(), 5D);

        Long newCountRatings = ++countRatings;

        hotelFromBD.setHotelRating(newCurrentHotelRating);
        hotelFromBD.setNumberRatings(newCountRatings);

        return hotelMapper.hotelEntityToHotelResponseWithRatingAndNumberRatings(repository.save(hotelFromBD));
    }

    @Override
    public HotelResponseList filterBy(HotelFilter filter) {
        Page<Hotel> listByRequestFilterHotel = repository.findAll(
                HotelSpecification.filterHotelsBy(filter),
                PageRequest.of(filter.getPageNumber(), filter.getPageSize()));
        if (listByRequestFilterHotel.getContent().isEmpty()) {
            throw new ListIsEmptyByFilter("Hotels");
        }
        return hotelMapper.listHotelEntityToHotelResponseLIst(listByRequestFilterHotel.getContent());
    }

    @Override
    public Hotel findHotelByIdFromBD(Long id) {
        return repository.findById(id).orElseThrow(() ->
                new NotFoundEntityException(MessageFormat.format("Hotel with id {0} not found", id)));
    }
}

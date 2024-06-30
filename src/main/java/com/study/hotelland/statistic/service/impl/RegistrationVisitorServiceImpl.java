package com.study.hotelland.statistic.service.impl;

import com.study.hotelland.entity.Visitor;
import com.study.hotelland.exception.NotFoundEntityException;
import com.study.hotelland.service.VisitorService;
import com.study.hotelland.statistic.entity.RegistrationVisitor;
import com.study.hotelland.statistic.repository.RegistrationVisitorRepository;
import com.study.hotelland.statistic.service.RegistrationVisitorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.MessageFormat;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class RegistrationVisitorServiceImpl implements RegistrationVisitorService {

    private final RegistrationVisitorRepository registrationVisitorRepository;

    private final VisitorService visitorService;

    @Override
    public RegistrationVisitor findById(String id) {
        return registrationVisitorRepository.findById(id).orElseThrow((() ->
                new NotFoundEntityException(MessageFormat.format("RegistrationVisitor with id {0} not found", id))));
    }

    @Override
    public List<RegistrationVisitor> findAll() {
        return registrationVisitorRepository.findAll();
    }

    @Override
    public RegistrationVisitor create(RegistrationVisitor registrationVisitor) {
        Visitor visitor = visitorService.findVisitorByIdFromBD(registrationVisitor.getVisitorId());
        RegistrationVisitor registrationVisitorCreated = registrationVisitorRepository.save(registrationVisitor);
        registrationVisitorCreated.setVisitorName(visitor.getName());

        return registrationVisitorCreated;
    }

    @Override
    public RegistrationVisitor update(String id, RegistrationVisitor registrationVisitor) {
        RegistrationVisitor registrationVisitorFromBD = findById(id);
        if (Objects.nonNull(registrationVisitor.getVisitorId())) {
            registrationVisitorFromBD.setVisitorId(registrationVisitor.getVisitorId());
        }
        Visitor visitor = visitorService.findVisitorByIdFromBD(registrationVisitorFromBD.getVisitorId());
        RegistrationVisitor registrationVisitorUpdated = registrationVisitorRepository.save(registrationVisitorFromBD);
        registrationVisitorUpdated.setVisitorName(visitor.getName());


        return registrationVisitorUpdated;
    }

    @Override
    public void delete(String id) {
        registrationVisitorRepository.deleteById(id);
    }
}

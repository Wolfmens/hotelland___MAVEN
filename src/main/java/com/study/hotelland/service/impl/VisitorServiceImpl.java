package com.study.hotelland.service.impl;

import com.study.hotelland.entity.RoleType;
import com.study.hotelland.entity.Visitor;
import com.study.hotelland.exception.NotFoundEntityException;
import com.study.hotelland.mapper.VisitorMapper;
import com.study.hotelland.repository.VisitorRepository;
import com.study.hotelland.service.VisitorService;
import com.study.hotelland.statistic.event.RegistrationVisitorEvent;
import com.study.hotelland.util.NotNullCopyProperty;
import com.study.hotelland.web.dto.visitor.VisitorRequest;
import com.study.hotelland.web.dto.visitor.VisitorResponse;
import com.study.hotelland.web.dto.visitor.VisitorResponseList;
import jakarta.validation.ValidationException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.text.MessageFormat;
import java.util.List;

@Service
@RequiredArgsConstructor
public class VisitorServiceImpl implements VisitorService {

    private final VisitorRepository repository;

    private final KafkaTemplate<String, Object> kafkaTemplate;

    private final PasswordEncoder passwordEncoder;

    private final VisitorMapper mapper;

    @Value("${app.kafka.property.registrationVisitorTopic}")
    private String registrationVisitorTopic;

    @Override
    public VisitorResponseList findAll() {
        return mapper.visitorListToVisitorResponseList(repository.findAll());
    }

    @Override
    public VisitorResponse findById(Long id) {
        return mapper.visitorEntityToVisitorResponse(findVisitorByIdFromBD(id));
    }

    @Override
    public VisitorResponse create(VisitorRequest request, RoleType type) {
        Visitor visitorFromRequest = mapper.visitorRequestToVisitorEntity(request);
        if (repository.existsByNameAndEmail(visitorFromRequest.getName(), visitorFromRequest.getEmail())) {
            throw new ValidationException("Visitor with the specified name and email already exists!");
        }
        visitorFromRequest.setAuthority(type);
        visitorFromRequest.setPassword(passwordEncoder.encode(visitorFromRequest.getPassword()));

        Visitor createdVisitor = repository.save(visitorFromRequest);

        RegistrationVisitorEvent event = new RegistrationVisitorEvent();
        event.setVisitorId(createdVisitor.getId());
        kafkaTemplate.send(registrationVisitorTopic, event);

        return mapper.visitorEntityToVisitorResponse(createdVisitor);
    }

    @Override
    public VisitorResponse update(Long visitorId, VisitorRequest request) {
        Visitor visitorFromRequest = mapper.visitorRequestToVisitorEntity(visitorId, request);
        Visitor visitorFromDb = findVisitorByIdFromBD(visitorFromRequest.getId());

        NotNullCopyProperty.copyNonNullProperties(visitorFromRequest, visitorFromDb, new String[]{"password", "authority"});

//        if (StringUtils.hasText(visitorFromRequest.getName())) {
//            visitorFromDb.setName(visitorFromRequest.getName());
//        }
//        if (StringUtils.hasText(visitorFromRequest.getEmail())) {
//            visitorFromDb.setEmail(visitorFromRequest.getEmail());
//        }
        if (StringUtils.hasText(visitorFromRequest.getPassword())) {
            visitorFromDb.setPassword(passwordEncoder.encode(visitorFromRequest.getPassword()));
        }
        if (visitorFromRequest.getAuthority() != null && StringUtils.hasText(visitorFromRequest.getAuthority().name())) {
            visitorFromDb.setAuthority(visitorFromRequest.getAuthority());
        }

        return mapper.visitorEntityToVisitorResponse(repository.save(visitorFromDb));
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }

    @Override
    public Visitor findByName(String name) {
        return repository.findByName(name)
                .orElseThrow(() ->  new NotFoundEntityException(MessageFormat.format("Visitor with name {0} not found", name)));
    }

    @Override
    public Boolean existsByName(String name) {
        return repository.existsByName(name);
    }

    @Override
    public Visitor findVisitorByIdFromBD(Long id) {
        return repository.findById(id)
                .orElseThrow(() ->  new NotFoundEntityException(MessageFormat
                        .format("Visitor with id {0} not found", id)));
    }
}

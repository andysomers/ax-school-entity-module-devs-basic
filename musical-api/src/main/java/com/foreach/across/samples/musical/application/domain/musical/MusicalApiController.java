package com.foreach.across.samples.musical.application.domain.musical;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/musical")
@RequiredArgsConstructor
public class MusicalApiController {
    private final MusicalRepository musicalRepository;
    private final MusicalResponseBuilder musicalResponseBuilder;

    @RequestMapping(method = RequestMethod.GET)
    public List<MusicalResponse> getMusicals() {
        return musicalRepository.findAll().stream()
                .map(musicalResponseBuilder::build)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public Musical getMusicalById(Long id) {
        return musicalRepository.findOne(id);
    }
}

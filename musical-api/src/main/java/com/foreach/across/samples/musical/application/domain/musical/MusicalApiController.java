package com.foreach.across.samples.musical.application.domain.musical;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/musical")
@RequiredArgsConstructor
public class MusicalApiController {
    private final MusicalRepository musicalRepository;
    private final MusicalDtoBuilder musicalDtoBuilder;

    @RequestMapping(method = RequestMethod.GET)
    public List<MusicalDto> getMusicals() {
        return musicalRepository.findAll().stream()
                .map(musicalDtoBuilder::buildDto)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public MusicalDto getMusicalById(@PathVariable Long id) {
        Musical musical = musicalRepository.findOne(id);

        return musicalDtoBuilder.buildDto(musical);
    }

    @RequestMapping(method = RequestMethod.POST)
    public MusicalDto createMusical(@RequestBody MusicalDto musicalDto) {
        Musical musical = musicalDtoBuilder.buildEntity(musicalDto);

        musical = musicalRepository.saveAndFlush(musical);

        return musicalDtoBuilder.buildDto(musical);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public MusicalDto updateMusical(@RequestBody MusicalDto musicalDto) {
        Musical musical = musicalDtoBuilder.buildEntity(musicalDto);

        musicalRepository.save(musical);
        return musicalDto;
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public void deleteMusicalById(@PathVariable Long id) {
        musicalRepository.delete(id);
    }
}

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

    @RequestMapping(method = RequestMethod.GET)
    public List<MusicalDto> getMusicals() {
        return musicalRepository.findAll().stream()
                .map(MusicalDto::from)
                .collect(Collectors.toList());
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public MusicalDto getMusicalById(@PathVariable Long id) {
        Musical musical = musicalRepository.findOne(id);

        return MusicalDto.from(musical);
    }

    @RequestMapping(method = RequestMethod.POST)
    public MusicalDto createMusical(@RequestBody MusicalDto musicalDto) {
        Musical musical = musicalDto.toMusical();

        musical = musicalRepository.save(musical);

        return MusicalDto.from(musical);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public MusicalDto updateMusical(@RequestBody MusicalDto musicalDto) {
        Musical musical = musicalRepository.findOne(musicalDto.getId());
        musical.setName(musicalDto.getName());

        musical = musicalRepository.save(musical);

        return MusicalDto.from(musical);
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public void deleteMusicalById(@PathVariable Long id) {
        musicalRepository.delete(id);
    }
}

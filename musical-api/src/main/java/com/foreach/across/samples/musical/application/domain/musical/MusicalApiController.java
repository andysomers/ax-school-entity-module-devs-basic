package com.foreach.across.samples.musical.application.domain.musical;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/musical")
@RequiredArgsConstructor
public class MusicalApiController {
    private final MusicalRepository musicalRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<MusicalDto>> getMusicals() {
        return ResponseEntity.ok(
                musicalRepository.findAll().stream()
                        .map(MusicalDto::from)
                        .collect(Collectors.toList())
        );
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<MusicalDto> getMusicalById(@PathVariable Long id) {
        Musical musical = musicalRepository.findOne(id);

        return ResponseEntity.ok(
                MusicalDto.from(musical)
        );
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity<MusicalDto> createMusical(@RequestBody MusicalDto musicalDto) {
        Musical musical = musicalDto.toMusical();

        musical = musicalRepository.save(musical);

        return ResponseEntity.ok(
                MusicalDto.from(musical)
        );
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseEntity<MusicalDto> updateMusical(@RequestBody MusicalDto musicalDto) {
        Musical musical = musicalRepository.findOne(musicalDto.getId());
        musical.setName(musicalDto.getName());

        musical = musicalRepository.save(musical);

        return ResponseEntity.ok(
                MusicalDto.from(musical)
        );
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public void deleteMusicalById(@PathVariable Long id) {
        musicalRepository.delete(id);
    }
}

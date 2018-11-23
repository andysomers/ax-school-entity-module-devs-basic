package com.foreach.across.samples.musical.application.domain.musical;

import com.foreach.across.samples.musical.application.domain.show.ShowRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping(path = "/api/musicals")
@RequiredArgsConstructor
public class MusicalApiController {
    private final MusicalRepository musicalRepository;
    private final ShowRepository showRepository;

    @RequestMapping(method = RequestMethod.GET)
    public ResponseEntity<List<MusicalDto>> getMusicals() {
        return ResponseEntity.ok(
                musicalRepository.findAll().stream()
                        .map(MusicalDto::from)
                        .collect(Collectors.toList())
        );
    }

    @RequestMapping(method = RequestMethod.GET, path = "/{id}")
    public ResponseEntity<MusicalDto> getMusicalById(@PathVariable UUID id) {
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

    @RequestMapping(method = RequestMethod.PUT, path = "/{id}")
    public ResponseEntity<MusicalDto> updateMusical( @PathVariable("id") Musical existingMusical, @RequestBody MusicalDto musicalDto ) {
        existingMusical.setName(musicalDto.getName());
        existingMusical.setDescription(musicalDto.getDescription());

        existingMusical = musicalRepository.save(existingMusical);

        return ResponseEntity.ok(
                MusicalDto.from(existingMusical)
        );
    }

    @RequestMapping(method = RequestMethod.DELETE, path = "/{id}")
    public void deleteMusicalById(@PathVariable UUID id) {
        musicalRepository.delete(id);
    }
}

package com.foreach.across.samples.musical.application.domain.musical;

import com.foreach.across.samples.musical.application.domain.show.ShowDtoBuilder;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MusicalDtoBuilder {
    private final ShowDtoBuilder showDtoBuilder;

    public MusicalDto buildDto(Musical musical) {
        if (musical == null) {
            return null;
        }

        return MusicalDto.builder()
                .id(musical.getId())
                .name(musical.getName())
                .shows(
                        musical.getShows().stream()
                                .map(showDtoBuilder::buildDto)
                                .collect(Collectors.toList())
                )
                .build();

    }

    public Musical buildEntity(MusicalDto musicalDto) {
        if (musicalDto == null) {
            return null;
        }

        return Musical.builder()
                .id(musicalDto.getId())
                .name(musicalDto.getName())
                .shows(
                        musicalDto.getShows().stream()
                                .map(showDtoBuilder::buildEntity)
                                .collect(Collectors.toList())
                )
                .build();
    }
}

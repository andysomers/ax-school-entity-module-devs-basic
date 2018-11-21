package com.foreach.across.samples.musical.application.domain.musical;

import com.foreach.across.samples.musical.application.domain.show.Show;
import com.foreach.across.samples.musical.application.domain.show.ShowResponseBuilder;
import lombok.*;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
@RequiredArgsConstructor
public class MusicalResponseBuilder {
    private final ShowResponseBuilder showResponseBuilder;

    public MusicalResponse build(Musical musical) {
        return MusicalResponse.builder()
                .id(musical.getId())
                .name(musical.getName())
                .shows(
                        musical.getShows().stream()
                                .map(showResponseBuilder::build)
                                .collect(Collectors.toList())
                )
                .build();

    }
}

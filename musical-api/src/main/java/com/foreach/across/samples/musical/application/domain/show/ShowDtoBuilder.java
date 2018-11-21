package com.foreach.across.samples.musical.application.domain.show;

import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ShowDtoBuilder {
    public ShowDto buildDto(Show show) {
        return ShowDto.builder()
                .id(show.getId())
                .name(show.getName())
                .build();
    }

    public Show buildEntity(ShowDto showDto) {
        return Show.builder()
                .id(showDto.getId())
                .name(showDto.getName())
                .build();
    }
}

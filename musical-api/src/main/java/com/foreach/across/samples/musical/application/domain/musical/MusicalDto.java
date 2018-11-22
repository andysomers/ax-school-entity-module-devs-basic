package com.foreach.across.samples.musical.application.domain.musical;

import com.foreach.across.samples.musical.application.domain.show.ShowDto;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MusicalDto {
    private Long id;
    private String name;

    public static MusicalDto from(Musical musical) {
        if (musical == null) {
            return null;
        }

        return MusicalDto.builder()
                .id(musical.getId())
                .name(musical.getName())
                .build();

    }

    public Musical toMusical() {
        return Musical.builder()
                .id(this.getId())
                .name(this.getName())
                .build();
    }
}

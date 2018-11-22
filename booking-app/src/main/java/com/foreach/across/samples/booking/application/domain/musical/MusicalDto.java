package com.foreach.across.samples.booking.application.domain.musical;

import lombok.*;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MusicalDto {
    private Long id;
    private String name;
    private String description;

    public static MusicalDto from(Musical musical) {
        if (musical == null) {
            return null;
        }

        return MusicalDto.builder()
                .id(musical.getId())
                .name(musical.getName())
                .description(musical.getDescription())
                .build();

    }

    public Musical toMusical() {
        return Musical.builder()
                .id(this.getId())
                .name(this.getName())
                .description(this.getDescription())
                .build();
    }
}

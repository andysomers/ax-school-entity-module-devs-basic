package com.foreach.across.samples.musical.application.domain.show;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ShowDto {
    private Long id;
    private String name;

    public static ShowDto from(Show show) {
        return ShowDto.builder()
                .id(show.getId())
                .name(show.getName())
                .build();
    }

    public Show toShow() {
        return Show.builder()
                .id(this.getId())
                .name(this.getName())
                .build();
    }
}

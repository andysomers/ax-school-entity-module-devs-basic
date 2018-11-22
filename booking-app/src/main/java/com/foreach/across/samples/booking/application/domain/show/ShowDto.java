package com.foreach.across.samples.booking.application.domain.show;

import lombok.*;

import java.time.ZonedDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ShowDto {
    private Long id;
    private String name;
    private String location;
    private ZonedDateTime time;

    public static ShowDto from(Show show) {
        return ShowDto.builder()
                .id(show.getId())
                .name(show.getName())
                .location(show.getLocation())
                .time(show.getTime())
                .build();
    }

    public Show toShow() {
        return Show.builder()
                .id(this.getId())
                .name(this.getName())
                .location(this.getLocation())
                .time(this.getTime())
                .build();
    }
}

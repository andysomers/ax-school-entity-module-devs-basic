package com.foreach.across.samples.booking.application.domain.show;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.*;

import java.time.ZonedDateTime;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ShowDto {
    private Long id;

    private String location;

    private String city;

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss.SSSZ")
    private ZonedDateTime time;

    private Long musicalId;

    public static ShowDto from( Show show ) {
        return ShowDto.builder()
                      .id( show.getId() )
                      .location( show.getLocation() )
                      .city( show.getCity() )
                      .time( show.getTime() )
                      .musicalId( show.getMusical().getId() )
                      .build();
    }

    public Show toShow() {
        return Show.builder()
                   .id( this.getId() )
                   .location( this.getLocation() )
                   .city( city )
                   .time( this.getTime() )
                   .build();
    }
}

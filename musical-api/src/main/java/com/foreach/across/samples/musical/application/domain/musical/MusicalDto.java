package com.foreach.across.samples.musical.application.domain.musical;

import com.foreach.across.samples.musical.application.domain.show.ShowDto;
import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MusicalDto {
    private Long id;
    private String name;
    private List<ShowDto> shows;
}

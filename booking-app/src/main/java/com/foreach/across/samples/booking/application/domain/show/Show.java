package com.foreach.across.samples.booking.application.domain.show;

import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.time.ZonedDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Show {
    private Long id;
    private String name;
    private String location;
    private ZonedDateTime time;
}

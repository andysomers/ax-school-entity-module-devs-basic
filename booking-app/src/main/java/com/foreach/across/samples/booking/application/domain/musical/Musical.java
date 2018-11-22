package com.foreach.across.samples.booking.application.domain.musical;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Musical {
    private Long id;
    private String name;
    private String description;
}

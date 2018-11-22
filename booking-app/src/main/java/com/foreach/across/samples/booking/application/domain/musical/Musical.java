package com.foreach.across.samples.booking.application.domain.musical;

import com.foreach.across.samples.booking.application.domain.show.Show;
import lombok.*;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Musical {
    private Long id;
    private String name;
    private String description;
    private List<Show> shows;
}

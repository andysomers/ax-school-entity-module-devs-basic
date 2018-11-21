package com.foreach.across.samples.musical.application.domain.show;

import lombok.*;

import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class ShowResponse {
    private Long id;
    private String name;
}

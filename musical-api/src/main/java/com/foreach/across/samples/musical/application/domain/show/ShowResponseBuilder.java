package com.foreach.across.samples.musical.application.domain.show;

import com.foreach.across.samples.musical.application.domain.musical.Musical;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@NoArgsConstructor
public class ShowResponseBuilder {
    public ShowResponse build(Show show) {
        return ShowResponse.builder()
                .id(show.getId())
                .name(show.getName())
                .build();
    }
}

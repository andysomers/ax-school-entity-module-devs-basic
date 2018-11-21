package com.foreach.across.samples.musical.application.domain.musical;

import com.foreach.across.modules.hibernate.business.SettableIdBasedEntity;
import com.foreach.across.modules.hibernate.id.AcrossSequenceGenerator;
import com.foreach.across.samples.musical.application.domain.show.Show;
import com.foreach.across.samples.musical.application.domain.show.ShowResponse;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class MusicalResponse {
    private Long id;
    private String name;
    private List<ShowResponse> shows;
}

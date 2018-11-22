package com.foreach.across.samples.musical.application.domain.musical;

import com.foreach.across.modules.hibernate.business.SettableIdBasedEntity;
import com.foreach.across.modules.hibernate.id.AcrossSequenceGenerator;
import com.foreach.across.samples.musical.application.domain.show.Show;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "sample_music")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Musical extends SettableIdBasedEntity<Musical> {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_sample_music_id")
    @GenericGenerator(
            name = "seq_sample_music_id",
            strategy = AcrossSequenceGenerator.STRATEGY,
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "sequenceName", value = "seq_sample_music_id"),
                    @org.hibernate.annotations.Parameter(name = "allocationSize", value = "1")
            }
    )
    private Long id;

    @NotBlank
    @Length(max = 200)
    @Column(name = "name")
    private String name;

    @Length(max = 2000)
    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "musical")
    private List<Show> shows;
}

package com.foreach.across.samples.musical.application.domain.musical;

import com.foreach.across.modules.hibernate.business.EntityWithDto;
import com.foreach.across.modules.hibernate.business.SettableIdBasedEntity;
import com.foreach.across.modules.hibernate.id.AcrossSequenceGenerator;
import com.foreach.across.modules.hibernate.util.DtoUtils;
import com.foreach.across.samples.musical.application.domain.show.Show;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "sample_musical")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Musical implements Persistable<UUID> {
    @Id
    @GeneratedValue(generator = "seq_sample_music_uuid")
    @GenericGenerator(
            name = "seq_sample_music_uuid",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Column(name = "id", length=16, updatable = false, nullable = false)
    private UUID id;

    @NotBlank
    @Length(max = 200)
    @Column(name = "name")
    private String name;

    @Length(max = 2000)
    @Column(name = "description")
    private String description;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "musical")
    private List<Show> shows;

    public final boolean isNew() {
        return getId() == null || getId().equals("");
    }

    public boolean equals( Musical o ) {
        if ( this == o ) {
            return true;
        }

        if ( isNew() ) {
            return false;
        }

        return Objects.equals( getId(), o.getId() );
    }

    @Override
    public int hashCode() {
        return isNew() ? super.hashCode() : Objects.hash( getId() );
    }
}

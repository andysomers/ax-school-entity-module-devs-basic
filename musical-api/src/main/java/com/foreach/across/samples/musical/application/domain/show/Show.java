package com.foreach.across.samples.musical.application.domain.show;

import com.foreach.across.samples.musical.application.domain.musical.Musical;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "sample_show")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Show implements Persistable<UUID>
{
	@Id
	@GeneratedValue(generator = "sample_show")
	@GenericGenerator(
			name = "sample_show",
			strategy = "org.hibernate.id.UUIDGenerator"
	)
	@Column(name = "id", length = 16, updatable = false, nullable = false)
	private UUID id;

	@NotBlank
	@Length(max = 10000)
	@Column(name = "location")
	private String location;

	@NotBlank
	@Column
	private String city;

	@NotNull
	@Column(name = "time")
	private ZonedDateTime time;

	@NotNull
	@ManyToOne
	@JoinColumn(name = "musicalId")
	private Musical musical;

	@OneToMany(cascade = CascadeType.ALL, mappedBy = "musical")
	private List<Show> shows;

	public final boolean isNew() {
		return getId() == null || getId().equals( "" );
	}

	public boolean equals( Show o ) {
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

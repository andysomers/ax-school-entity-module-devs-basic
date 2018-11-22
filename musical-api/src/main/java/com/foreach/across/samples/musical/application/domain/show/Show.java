package com.foreach.across.samples.musical.application.domain.show;

import com.foreach.across.modules.hibernate.business.SettableIdBasedEntity;
import com.foreach.across.modules.hibernate.id.AcrossSequenceGenerator;
import com.foreach.across.samples.musical.application.domain.musical.Musical;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotBlank;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.time.ZonedDateTime;

@Entity
@Table(name = "sample_show")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder(toBuilder = true)
public class Show extends SettableIdBasedEntity<Show>
{
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO, generator = "seq_sample_show_id")
	@GenericGenerator(
			name = "seq_sample_show_id",
			strategy = AcrossSequenceGenerator.STRATEGY,
			parameters = {
					@org.hibernate.annotations.Parameter(name = "sequenceName", value = "seq_sample_show_id"),
					@org.hibernate.annotations.Parameter(name = "allocationSize", value = "1")
			}
	)
	private Long id;

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
}

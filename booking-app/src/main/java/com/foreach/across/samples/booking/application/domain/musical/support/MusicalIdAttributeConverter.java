package com.foreach.across.samples.booking.application.domain.musical.support;

import com.foreach.across.samples.booking.application.domain.musical.MusicalId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class MusicalIdAttributeConverter implements AttributeConverter<MusicalId, Long>
{
	@Override
	public Long convertToDatabaseColumn( MusicalId attribute ) {
		return attribute.getId();
	}

	@Override
	public MusicalId convertToEntityAttribute( Long dbData ) {
		return MusicalId.of( dbData );
	}
}

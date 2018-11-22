package com.foreach.across.samples.booking.application.domain.show.support;

import com.foreach.across.samples.booking.application.domain.show.ShowId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ShowIdAttributeConverter implements AttributeConverter<ShowId, Long>
{
	@Override
	public Long convertToDatabaseColumn( ShowId attribute ) {
		return attribute.getId();
	}

	@Override
	public ShowId convertToEntityAttribute( Long dbData ) {
		return ShowId.of( dbData );
	}
}

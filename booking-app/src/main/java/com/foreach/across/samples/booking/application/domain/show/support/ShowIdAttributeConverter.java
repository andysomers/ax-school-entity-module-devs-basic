package com.foreach.across.samples.booking.application.domain.show.support;

import com.foreach.across.samples.booking.application.domain.show.ShowId;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;

@Converter(autoApply = true)
public class ShowIdAttributeConverter implements AttributeConverter<ShowId, String>
{
	@Override
	public String convertToDatabaseColumn( ShowId attribute ) {
		return attribute.getId();
	}

	@Override
	public ShowId convertToEntityAttribute( String dbData ) {
		return ShowId.of( dbData );
	}
}

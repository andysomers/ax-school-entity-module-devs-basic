package com.foreach.across.samples.booking.application.domain.musical;

import com.foreach.across.modules.entity.validators.EntityValidatorSupport;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;

/**
 * Custom validator that checks there is no other musical yet with that name.
 * Because this is the only validator which supports musical, and it is registered
 * in the same module which provides the musical registry, it will be automatically
 * attached to the {@link com.foreach.across.modules.entity.registry.EntityConfiguration}.
 */
@Component
@RequiredArgsConstructor
public class MusicalValidator extends EntityValidatorSupport<Musical>
{
	private final MusicalClient musicalClient;

	@Override
	public boolean supports( Class<?> clazz ) {
		return Musical.class.isAssignableFrom( clazz );
	}

	@Override
	protected void postValidation( Musical musical, Errors errors, Object... validationHints ) {
		if ( !errors.hasFieldErrors( "name" ) ) {
			musicalClient.getAllMusicals()
			             .stream()
			             .filter( other -> !other.getId().equals( musical.getId() ) )
			             .filter( other -> musical.getName().equalsIgnoreCase( other.getName() ) )
			             .findAny()
			             .ifPresent( other -> errors.rejectValue( "name", "duplicateName", "Duplicate musical name." ) );
		}
	}
}

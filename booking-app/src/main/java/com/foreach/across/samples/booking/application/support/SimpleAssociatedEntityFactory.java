package com.foreach.across.samples.booking.application.support;

import com.foreach.across.modules.entity.registry.EntityFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;

@RequiredArgsConstructor
public final class SimpleAssociatedEntityFactory<T, U> implements EntityFactory<U>
{
	@NonNull
	private final Function<T, U> constructor;

	@NonNull
	private final Function<U, U> dtoFunction;

	@Override
	@SuppressWarnings("unchecked")
	public U createNew( Object... args ) {
		return constructor.apply( (T) args[0] );
	}

	@Override
	public U createDto( U entity ) {
		return dtoFunction.apply( entity );
	}
}

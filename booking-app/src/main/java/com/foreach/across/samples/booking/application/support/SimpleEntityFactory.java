package com.foreach.across.samples.booking.application.support;

import com.foreach.across.modules.entity.registry.EntityFactory;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.util.function.Function;
import java.util.function.Supplier;

/**
 * Simple implementation of {@link EntityFactory} which takes a {@link Supplier} to create a new instance
 * of a type, and a {@link Function} to create a DTO of an instance.
 *
 * @param <T> entity type
 */
@RequiredArgsConstructor
public final class SimpleEntityFactory<T> implements EntityFactory<T>
{
	@NonNull
	private final Supplier<T> constructor;

	@NonNull
	private final Function<T, T> dtoFunction;

	@Override
	public T createNew( Object... arguments ) {
		return constructor.get();
	}

	@Override
	public T createDto( T original ) {
		return dtoFunction.apply( original );
	}
}

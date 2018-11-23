package com.foreach.across.samples.booking.application.support;

import com.foreach.across.modules.entity.config.AttributeRegistrar;
import com.foreach.across.modules.entity.query.EntityQuery;
import com.foreach.across.modules.entity.query.EntityQueryExecutor;
import com.foreach.across.modules.entity.query.collections.CollectionEntityQueryExecutor;
import com.foreach.across.modules.entity.registry.EntityConfiguration;
import com.foreach.across.modules.entity.registry.properties.EntityPropertyRegistry;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import java.util.List;
import java.util.function.Supplier;

/**
 * Helper which implements a custom {@link EntityQueryExecutor} which queries a collection that is in turned fetched from a {@link Supplier}.
 * This is a workaround for the limitation of the {@link CollectionEntityQueryExecutor} which only supports a fixed collection.
 * See {@link #register(Supplier)} function for easy registration on an {@link EntityConfiguration}.
 *
 * @param <T>
 */
@SuppressWarnings("unchecked")
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public class CollectionSupplierEntityQueryExecutor<T> implements EntityQueryExecutor<T>
{
	private final EntityPropertyRegistry propertyRegistry;
	private final Supplier<List<T>> collectionSupplier;

	@Override
	public List<T> findAll( EntityQuery query ) {
		return new CollectionEntityQueryExecutor( collectionSupplier.get(), propertyRegistry ).findAll( query );
	}

	@Override
	public List<T> findAll( EntityQuery query, Sort sort ) {
		return new CollectionEntityQueryExecutor( collectionSupplier.get(), propertyRegistry ).findAll( query, sort );
	}

	@Override
	public Page<T> findAll( EntityQuery query, Pageable pageable ) {
		return new CollectionEntityQueryExecutor( collectionSupplier.get(), propertyRegistry ).findAll( query, pageable );
	}

	/**
	 * Helper function which returns an {@link AttributeRegistrar} that registers the {@link EntityQueryExecutor} on the
	 * {@link EntityConfiguration} which owns the attributes collection.
	 *
	 * @param supplier for the list of entities which should be queried
	 * @param <U>      entity type
	 * @return attribute registrar
	 */
	public static <U> AttributeRegistrar<EntityConfiguration<U>> register( @NonNull Supplier<List<U>> supplier ) {
		return ( configuration, attributes ) -> {
			attributes.setAttribute( EntityQueryExecutor.class, new CollectionSupplierEntityQueryExecutor<>( configuration.getPropertyRegistry(), supplier ) );
		};
	}
}

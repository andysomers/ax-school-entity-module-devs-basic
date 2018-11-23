package com.foreach.across.samples.booking.application.domain.show.web.backend;

import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.modules.entity.query.AssociatedEntityQueryExecutor;
import com.foreach.across.modules.entity.query.EntityQueryExecutor;
import com.foreach.across.modules.entity.registry.EntityAssociation;
import com.foreach.across.modules.entity.registry.EntityFactory;
import com.foreach.across.modules.entity.registry.MutableEntityAssociation;
import com.foreach.across.samples.booking.application.domain.musical.Musical;
import com.foreach.across.samples.booking.application.domain.show.Show;
import com.foreach.across.samples.booking.application.support.SimpleAssociatedEntityFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
class ShowsForMusicalUiConfiguration implements EntityConfigurer
{
	@Override
	public void configure( EntitiesConfigurationBuilder entities ) {
		entities.withType( Musical.class )
		        .association(
				        association -> association
						        .name( "shows" )
						        .show()
						        .targetEntityType( Show.class )
						        .targetProperty( "musical" )
						        .associationType( EntityAssociation.Type.EMBEDDED )
						        .parentDeleteMode( EntityAssociation.ParentDeleteMode.WARN )
						        .listView()
						        .createFormView()
						        .updateFormView()
						        .deleteFormView()
						        .attribute(
								        EntityFactory.class,
								        new SimpleAssociatedEntityFactory<Musical, Show>(
										        musical -> Show.builder().musicalId( musical.getId() ).build(),
										        show -> show.toBuilder().build()
								        )
						        )
		        )
		        .postProcessor(
				        entityConfiguration -> {
					        MutableEntityAssociation association = entityConfiguration.association( "shows" );
					        EntityQueryExecutor<Show> showQueryExecutor = association.getTargetEntityConfiguration().getAttribute( EntityQueryExecutor.class );

					        association.setAttribute(
							        AssociatedEntityQueryExecutor.class,
							        new AssociatedEntityQueryExecutor<>( association.getTargetProperty(), showQueryExecutor )
					        );
				        }
		        );
	}
}

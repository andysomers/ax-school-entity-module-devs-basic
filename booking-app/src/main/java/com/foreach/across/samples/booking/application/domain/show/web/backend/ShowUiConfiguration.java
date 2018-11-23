package com.foreach.across.samples.booking.application.domain.show.web.backend;

import com.foreach.across.core.context.info.AcrossModuleInfo;
import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.modules.entity.registry.properties.ConfigurableEntityPropertyController;
import com.foreach.across.modules.entity.support.EntityMessageCodeResolver;
import com.foreach.across.samples.booking.application.domain.musical.Musical;
import com.foreach.across.samples.booking.application.domain.musical.MusicalClient;
import com.foreach.across.samples.booking.application.domain.show.Show;
import com.foreach.across.samples.booking.application.domain.show.ShowClient;
import com.foreach.across.samples.booking.application.domain.show.ShowId;
import com.foreach.across.samples.booking.application.support.CollectionSupplierEntityQueryExecutor;
import com.foreach.across.samples.booking.application.support.SimpleEntityFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.core.support.ReflectionEntityInformation;
import org.springframework.format.support.FormattingConversionService;

import java.time.format.DateTimeFormatter;
import java.util.Locale;

@Configuration
@RequiredArgsConstructor
class ShowUiConfiguration implements EntityConfigurer
{
	private final AcrossModuleInfo moduleInfo;
	private final ShowClient showClient;
	private final MusicalClient musicalClient;

	@Override
	public void configure( EntitiesConfigurationBuilder entities ) {
		entities.create()
		        .entityType( Show.class, true )
		        .as( Show.class )
		        .attribute( AcrossModuleInfo.class, moduleInfo )
		        .attribute( CollectionSupplierEntityQueryExecutor.register( showClient::getAllShows ) )
		        .properties(
				        props -> props.property( "id" ).hidden( true ).and()
				                      .property( "musicalId" ).hidden( true ).and()
				                      .property( "musical" )
				                      .propertyType( Musical.class )
				                      .controller( this::configureMusicalPropertyController )
				                      .readable( true )
				                      .writable( true )
		        )
		        .entityModel(
				        model -> model.labelPrinter( this::createLabelForShow )
				                      .entityInformation( new ReflectionEntityInformation<>( Show.class ) )
				                      .findOneMethod( s -> showClient.getShow( ShowId.of( s.toString() ) ) )
				                      .entityFactory( new SimpleEntityFactory<>( Show::new, show -> show.toBuilder().build() ) )
				                      .saveMethod(
						                      show -> {
							                      if ( show.getId() == null ) {
								                      return showClient.createShowForMusical( show.getMusicalId(), show );
							                      }
							                      return showClient.updateShowForMusical( show.getMusicalId(), show );
						                      }
				                      )
				                      .deleteMethod( show -> showClient.deleteShow( show.getId() ) )
		        )
		        .show()
		        .listView(/* lvb -> lvb.showProperties( "location" )*/ )
		        .createFormView()
		        .updateFormView()
		        .deleteFormView()
		        .postProcessor( configuration -> {
			        EntityMessageCodeResolver messageCodeResolver = configuration.getEntityMessageCodeResolver();
			        messageCodeResolver.setPrefixes( "booking.show" );
			        messageCodeResolver.setFallbackCollections( "booking", "EntityModule.entities" );
		        } );

	}

	private String createLabelForShow( Show show, Locale locale ) {
		Musical musical = musicalClient.getMusical( show.getMusicalId() );
		return musical.getName() + " - " + show.getTime().format( DateTimeFormatter.ofPattern( "dd MMM yyyy HH:mm" ) ) + " - " + show.getCity();
	}

	private void configureMusicalPropertyController( ConfigurableEntityPropertyController<Object, Object> controller ) {
		controller.withTarget( Show.class, Musical.class )
		          .applyValueConsumer( ( show, musicalValue ) -> {
			          Musical musical = musicalValue.getNewValue();
			          show.setMusicalId( musical != null ? musical.getId() : null );
		          } )
		          .valueFetcher( show -> {
			          if ( show.getMusicalId() != null ) {
				          return musicalClient.getMusical( show.getMusicalId() );
			          }
			          return null;
		          } );
	}

	@Autowired
	public void registerConverter( FormattingConversionService mvcConversionService ) {
		mvcConversionService.addConverter( ShowId.class, String.class, ShowId::toString );
	}
}

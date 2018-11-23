package com.foreach.across.samples.booking.application.domain.musical.web.backend;

import com.foreach.across.core.context.info.AcrossModuleInfo;
import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.modules.entity.registry.EntityFactory;
import com.foreach.across.samples.booking.application.domain.musical.Musical;
import com.foreach.across.samples.booking.application.domain.musical.MusicalClient;
import com.foreach.across.samples.booking.application.domain.musical.MusicalId;
import com.foreach.across.samples.booking.application.support.CollectionSupplierEntityQueryExecutor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.repository.core.support.ReflectionEntityInformation;
import org.springframework.format.support.FormattingConversionService;

@Configuration
@RequiredArgsConstructor
class MusicalUiConfiguration implements EntityConfigurer
{
	private final AcrossModuleInfo moduleInfo;
	private final MusicalClient musicalClient;

	@Override
	public void configure( EntitiesConfigurationBuilder entities ) {
		entities.create()
		        .entityType( Musical.class, true )
		        .as( Musical.class )
		        .attribute( AcrossModuleInfo.class, moduleInfo )
		        .attribute( CollectionSupplierEntityQueryExecutor.register( musicalClient::getAllMusicals ) )
		        .entityModel(
				        model -> model.labelPrinter( ( musical, locale ) -> musical.getName() )
				                      .entityInformation( new ReflectionEntityInformation<>( Musical.class ) )
				                      .findOneMethod( s -> musicalClient.getMusical( MusicalId.of( s.toString() ) ) )
				                      .entityFactory( new EntityFactory<Musical>()
				                      {
					                      @Override
					                      public Musical createNew( Object... args ) {
						                      return new Musical();
					                      }

					                      @Override
					                      public Musical createDto( Musical entity ) {
						                      return entity;
					                      }
				                      } )

		        )
		        .show()
		        .listView()
		        .createFormView()
		        .updateFormView( fvb -> fvb.showProperties( "name", "description", "id" ))
		        .deleteFormView();
	}

	@Autowired
	public void registerConverter( FormattingConversionService mvcConversionService ) {
		mvcConversionService.addConverter( MusicalId.class, String.class, MusicalId::getId );
	}
}

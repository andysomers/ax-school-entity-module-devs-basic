package com.foreach.across.samples.booking.application.domain.musical.web.backend;

import com.foreach.across.core.context.info.AcrossModuleInfo;
import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.samples.booking.application.domain.musical.Musical;
import com.foreach.across.samples.booking.application.domain.musical.MusicalClient;
import com.foreach.across.samples.booking.application.domain.musical.MusicalId;
import com.foreach.across.samples.booking.application.support.CollectionSupplierEntityQueryExecutor;
import com.foreach.across.samples.booking.application.support.SimpleEntityFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
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
		        .properties(
				        props -> props.property( "id" ).hidden( true ).and()
				                      .property( "name" ).attribute( Sort.Order.class, new Sort.Order( "name" ) )
		        )
		        .entityModel(
				        model -> model.labelPrinter( ( musical, locale ) -> musical.getName() )
				                      .entityInformation( new ReflectionEntityInformation<>( Musical.class ) )
				                      .findOneMethod( s -> musicalClient.getMusical( MusicalId.of( s.toString() ) ) )
				                      .entityFactory( new SimpleEntityFactory<>( Musical::new, musical -> musical.toBuilder().build() ) )
				                      .saveMethod(
						                      musical -> {
							                      if ( musical.getId() == null ) {
								                      return musicalClient.createMusical( musical );
							                      }
							                      musicalClient.updateMusical( musical );
							                      return musical;
						                      }
				                      )
				                      .deleteMethod( musical -> musicalClient.deleteMusical( musical.getId() ) )
		        )
		        .show()
		        .listView( lvb -> lvb.showProperties( "name" ).defaultSort( "name" ) )
		        .createFormView()
		        .updateFormView( fvb -> fvb.showProperties( "name", "description" ) )
		        .deleteFormView();
	}

	@Autowired
	public void registerConverter( FormattingConversionService mvcConversionService ) {
		mvcConversionService.addConverter( MusicalId.class, String.class, MusicalId::getId );
	}
}

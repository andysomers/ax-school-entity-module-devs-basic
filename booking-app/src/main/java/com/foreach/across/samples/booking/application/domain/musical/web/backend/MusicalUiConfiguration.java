package com.foreach.across.samples.booking.application.domain.musical.web.backend;

import com.foreach.across.core.context.info.AcrossModuleInfo;
import com.foreach.across.modules.bootstrapui.elements.TextareaFormElement;
import com.foreach.across.modules.bootstrapui.elements.TextboxFormElement;
import com.foreach.across.modules.entity.config.EntityConfigurer;
import com.foreach.across.modules.entity.config.builders.EntitiesConfigurationBuilder;
import com.foreach.across.modules.entity.support.EntityMessageCodeResolver;
import com.foreach.across.modules.entity.views.ViewElementMode;
import com.foreach.across.modules.web.resource.WebResource;
import com.foreach.across.modules.web.resource.WebResourceRegistry;
import com.foreach.across.modules.web.ui.ViewElementPostProcessor;
import com.foreach.across.samples.booking.application.domain.musical.Musical;
import com.foreach.across.samples.booking.application.domain.musical.MusicalClient;
import com.foreach.across.samples.booking.application.domain.musical.MusicalId;
import com.foreach.across.samples.booking.application.domain.musical.MusicalValidator;
import com.foreach.across.samples.booking.application.support.CollectionSupplierEntityQueryExecutor;
import com.foreach.across.samples.booking.application.support.SimpleEntityFactory;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.core.support.ReflectionEntityInformation;
import org.springframework.format.support.FormattingConversionService;
import org.springframework.validation.Validator;

@Configuration
@RequiredArgsConstructor
class MusicalUiConfiguration implements EntityConfigurer
{
	private final AcrossModuleInfo moduleInfo;
	private final MusicalClient musicalClient;
	private final MusicalValidator musicalValidator;

	@Override
	public void configure( EntitiesConfigurationBuilder entities ) {
		entities.create()
		        .entityType( Musical.class, true )
		        .as( Musical.class )
		        .attribute( AcrossModuleInfo.class, moduleInfo )
		        .attribute( CollectionSupplierEntityQueryExecutor.register( musicalClient::getAllMusicals ) )
		        .attribute( Validator.class, musicalValidator )
		        .properties(
				        props -> props.property( "id" ).hidden( true ).and()
				                      .property( "name" ).attribute( Sort.Order.class, new Sort.Order( "name" ) ).and()
				                      .property( "description" ).viewElementPostProcessor( ViewElementMode.CONTROL, makeRichText() )
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
		        .deleteFormView()
		        .postProcessor( configuration -> {
			        EntityMessageCodeResolver messageCodeResolver = configuration.getEntityMessageCodeResolver();
			        messageCodeResolver.setPrefixes( "booking.musical" );
			        messageCodeResolver.setFallbackCollections( "booking", "EntityModule.entities" );
		        } );
	}

	/**
	 * Example post processor that adds rich text on the musical description.
	 */
	private ViewElementPostProcessor<TextboxFormElement> makeRichText() {
		return ( builderContext, textbox ) -> {
			WebResourceRegistry resourceRegistry = builderContext.getAttribute( WebResourceRegistry.class );
			resourceRegistry.add( WebResource.JAVASCRIPT_PAGE_END, "https://cdn.ckeditor.com/ckeditor5/11.1.1/classic/ckeditor.js" );
			resourceRegistry.add( WebResource.JAVASCRIPT_PAGE_END, "/static/booking/js/rich-text.js", WebResource.VIEWS );

			textbox.removeCssClass( TextareaFormElement.CSS_AUTOSIZE );
			textbox.setAttribute( "rich-text", true );
		};
	}

	@Autowired
	public void registerConverter( FormattingConversionService mvcConversionService ) {
		mvcConversionService.addConverter( MusicalId.class, String.class, MusicalId::toString );
	}
}

package com.foreach.across.samples.booking.application.domain.musical;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestClientException;

import java.util.List;
import java.util.Objects;
import java.util.UUID;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = "musicalService.url=http://localhost:8099")
@ContextConfiguration
@Slf4j
public class TestMusicalClient
{
	private final ObjectMapper mapper = new ObjectMapper();

	@Autowired
	private Environment environment;

	private MusicalClient musicalClient;

	private Musical existingMusical;

	@Before
	public void setup() {
		musicalClient = new MusicalClient( environment.getProperty( "musicalService.url" ) );

		existingMusical = musicalClient.createMusical(
				Musical.builder()
				       .id( new MusicalId( UUID.randomUUID().toString() ) )
				       .name( "Winter wonderland" )
				       .description( "Winterland description" )
				       .build()
		);
	}

	@After
	public void cleanUp(){
		musicalClient.deleteMusical( existingMusical.getId() );
	}

	@Test
	public void getAllMusicals() throws JsonProcessingException {
		List<Musical> musicals = musicalClient.getAllMusicals();
		assertThat( musicals ).isNotEmpty();
		Musical existing = musicals.stream()
		                           .filter( musical -> Objects.equals( musical.getId(), existingMusical.getId() ) )
		                           .findFirst().orElse( null );
		assertForExistingMusical( existing );
		LOG.debug( mapper.writeValueAsString( musicals ) );
	}

	@Test
	public void getMusical() throws JsonProcessingException {
		Musical musical = musicalClient.getMusical( existingMusical.getId() );
		assertForExistingMusical( musical );
		LOG.debug( mapper.writeValueAsString( musical ) );
	}

	@Test
	public void createAndDeleteMusical() throws JsonProcessingException {
		Musical musicalDto = Musical.builder()
		                            .name( "New musical" )
		                            .description( "New musical description" )
		                            .build();
		Musical musical = musicalClient.createMusical( musicalDto );
		assertForMusical( musical, musicalDto.getName(), musicalDto.getDescription() );
		LOG.debug( mapper.writeValueAsString( musical ) );

		musicalClient.deleteMusical( musical.getId() );
		assertThatThrownBy( () -> musicalClient.getMusical( musical.getId() ) )
				.isInstanceOf( RestClientException.class );
	}

	@Test
	public void updateMusical() throws JsonProcessingException {
		Musical musicalDto = Musical.builder()
		                            .id( existingMusical.getId() )
		                            .name( "Updated musical" )
		                            .description( "Newly updated description" )
		                            .build();
		Musical musical = musicalClient.updateMusical( musicalDto );
		assertThat( musical )
				.hasFieldOrPropertyWithValue( "id", musicalDto.getId() );
		assertForMusical( musical, musicalDto.getName(), musicalDto.getDescription() );
		LOG.debug( mapper.writeValueAsString( musical ) );
	}

	private void assertForExistingMusical( Musical musical ) {
		assertForMusical( musical, existingMusical.getName(), existingMusical.getDescription() );
		assertThat( musical )
				.hasFieldOrPropertyWithValue( "id", existingMusical.getId() );
	}

	private void assertForMusical( Musical musical, String name, String description ) {
		assertThat( musical )
				.hasFieldOrPropertyWithValue( "name", name )
				.hasFieldOrPropertyWithValue( "description", description );
	}

}

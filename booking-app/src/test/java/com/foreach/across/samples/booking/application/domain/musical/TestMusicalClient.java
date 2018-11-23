package com.foreach.across.samples.booking.application.domain.musical;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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

import java.util.List;
import java.util.UUID;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = "musicalService.url=http://localhost:8099")
@ContextConfiguration
@Slf4j
public class TestMusicalClient
{
	private MusicalClient musicalClient;

	@Autowired
	private Environment environment;

	private Musical existingMusical;

	@Before
	public void setup() {
		musicalClient = new MusicalClient( environment.getProperty( "musicalService.url" ) );

		existingMusical = musicalClient.createMusical(
				Musical.builder()
				       .id( MusicalId.of( UUID.randomUUID().toString() ) )
				       .name( "Winter wonderland" )
				       .description( "Winterland description" )
				       .build()
		);
	}

	@After
	public void cleanUp() {
		musicalClient.deleteMusical( existingMusical.getId() );
	}

	@Test
	public void getAllMusicals() throws JsonProcessingException {
		List<Musical> musicals = musicalClient.getAllMusicals();
		ObjectMapper mapper = new ObjectMapper();

		LOG.debug( mapper.writeValueAsString( musicals ) );
	}

	@Test
	public void getMusical() throws JsonProcessingException {
		Musical musical = musicalClient.getMusical( existingMusical.getId() );
		ObjectMapper mapper = new ObjectMapper();

		LOG.debug( mapper.writeValueAsString( musical ) );
	}

	@Test
	public void createMusical() throws JsonProcessingException {
		Musical musicalDto = Musical.builder()
		                            .name( "New musical" )
		                            .description( "New musical description" )
		                            .build();
		Musical musical = musicalClient.createMusical( musicalDto );
		ObjectMapper mapper = new ObjectMapper();

		LOG.debug( mapper.writeValueAsString( musical ) );
		musicalClient.deleteMusical( musical.getId() );
	}

	@Test
	public void updateMusical() throws JsonProcessingException {
		Musical musicalDto = Musical.builder()
		                            .id( existingMusical.getId() )
		                            .name( "Updated musical" )
		                            .description( "Newly updated description" )
		                            .build();
		Musical musical = musicalClient.updateMusical( musicalDto );
		ObjectMapper mapper = new ObjectMapper();

		LOG.debug( mapper.writeValueAsString( musical ) );
	}

	@Test
	public void deleteMusical() throws JsonProcessingException {
		musicalClient.deleteMusical( existingMusical.getId() );
	}
}

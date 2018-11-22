package com.foreach.across.samples.booking.application.domain.musical;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = "musicalService.url=http://localhost:8099")
@ContextConfiguration
@Slf4j
public class TestMusicalClient
{
	private MusicalClient musicalClient;

	@Autowired
	private Environment environment;

	@Before
	public void setup() {
		RestTemplate restTemplate = new RestTemplate();
		musicalClient = new MusicalClient( restTemplate, environment.getProperty( "musicalService.url" ) );
	}

	@Test
	public void testGetAllMusicals() throws JsonProcessingException {
		HttpEntity<List<Musical>> musicals = musicalClient.getAllMusicals();
		ObjectMapper mapper = new ObjectMapper();

		LOG.debug( mapper.writeValueAsString( musicals.getBody() ) );
	}

	@Test
	public void testGetMusical() throws JsonProcessingException {
		HttpEntity<Musical> musical = musicalClient.getMusical( MusicalId.of( 1L ) );
		ObjectMapper mapper = new ObjectMapper();

		LOG.debug( mapper.writeValueAsString( musical.getBody() ) );
	}

	@Test
	public void testCreateMusical() throws JsonProcessingException {
		Musical musicalDto = Musical.builder()
		                            .name( "New musical" )
		                            .description( "New musical description" )
		                            .build();
		HttpEntity<Musical> musical = musicalClient.createMusical( musicalDto );
		ObjectMapper mapper = new ObjectMapper();

		LOG.debug( mapper.writeValueAsString( musical.getBody() ) );
	}

	@Test
	public void updateMusical() throws JsonProcessingException {
		Musical musicalDto = Musical.builder()
		                            .id( MusicalId.of( 1L ) )
		                            .name( "Updated musical" )
		                            .description( "Newly updated description" )
		                            .build();
		HttpEntity<Musical> musical = musicalClient.updateMusical( musicalDto );
		ObjectMapper mapper = new ObjectMapper();

		LOG.debug( mapper.writeValueAsString( musical.getBody() ) );
	}

	@Test
	public void deleteMusical() throws JsonProcessingException {
		musicalClient.deleteMusical( MusicalId.of( 1L ) );
	}
}

package com.foreach.across.samples.booking.application.domain.show;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foreach.across.samples.booking.application.domain.musical.Musical;
import com.foreach.across.samples.booking.application.domain.musical.MusicalClient;
import com.foreach.across.samples.booking.application.domain.musical.MusicalId;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Objects;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.assertj.core.api.Java6Assertions.assertThatThrownBy;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = "musicalService.url=http://localhost:8099")
@ContextConfiguration
@Slf4j
public class TestShowClient
{
	private final ObjectMapper mapper = new ObjectMapper();
	private MusicalClient musicalClient;
	private ShowClient showClient;

	@Autowired
	private Environment environment;

	private Musical existingMusical;
	private Show existingShow;

	@Before
	public void setup() {
		musicalClient = new MusicalClient( environment.getProperty( "musicalService.url" ) );
		showClient = new ShowClient( environment.getProperty( "musicalService.url" ) );

		existingMusical = musicalClient.createMusical(
				Musical.builder()
				       .name( "Winter wonderland" )
				       .build()
		);
		existingShow = showClient.createShowForMusical( existingMusical.getId(),
		                                                Show.builder()
		                                                    .city( "Antwerpen" )
		                                                    .location( "Stadsschouwburg Bourla" )
		                                                    .time( ZonedDateTime.now() )
		                                                    .build()
		);
	}

	@After
	public void cleanUp() {
		musicalClient.deleteMusical( existingMusical.getId() );
	}

	@Test
	public void getAllShows() throws JsonProcessingException {
		List<Show> shows = showClient.getAllShows();
		assertThat( shows ).isNotEmpty();
		Show fetched = shows.stream()
		                    .filter( show -> Objects.equals( show.getId(), existingShow.getId() ) )
		                    .findFirst().orElse( null );
		assertForExistingShow( fetched );
		LOG.debug( mapper.writeValueAsString( shows ) );
	}

	@Test
	public void getASingleShow() throws JsonProcessingException {
		Show show = showClient.getShow( existingShow.getId() );
		assertForExistingShow( show );
		LOG.debug( mapper.writeValueAsString( show ) );
	}

	@Test
	public void getAllShowsForMusical() throws JsonProcessingException {
		List<Show> shows = showClient.getAllShowsForMusical( existingMusical.getId() );
		assertThat( shows ).hasSize( 1 );
		assertForExistingShow( shows.get( 0 ) );
		LOG.debug( mapper.writeValueAsString( shows ) );
	}

	@Test
	public void getSingleShowForMusical() throws JsonProcessingException {
		Show show = showClient.getShowForMusical( existingMusical.getId(), existingShow.getId() );
		assertForExistingShow( show );
		LOG.debug( mapper.writeValueAsString( show ) );
	}

	@Test
	public void createShowForMusical() throws JsonProcessingException {
		Show show = Show.builder()
		                .musicalId( existingMusical.getId() )
		                .city( "Brussel" )
		                .location( "Carré" )
		                .time( ZonedDateTime.of( LocalDate.of( 2018, 9, 12 ), LocalTime.of( 12, 0 ), ZoneId.of( "UTC" ) ) )
		                .build();
		Show created = showClient.createShowForMusical( existingMusical.getId(), show );
		assertForShow( created, show.getCity(), show.getLocation(), show.getMusicalId(), show.getTime() );
		LOG.debug( mapper.writeValueAsString( created ) );
	}

	@Test
	public void updateShowForMusical() throws JsonProcessingException {
		Show show = existingShow;
		show.setCity( "Brussel" );
		show.setLocation( "Carré" );
		Show updated = showClient.updateShowForMusical( existingMusical.getId(), show );
		assertForShow( updated, show.getCity(), show.getLocation(), show.getMusicalId(), show.getTime() );
		assertThat( updated )
				.hasFieldOrPropertyWithValue( "id", show.getId() );
		LOG.debug( mapper.writeValueAsString( updated ) );
	}

	@Test
	public void deleteShow() throws JsonProcessingException {
		showClient.deleteShow( existingShow.getId() );
		assertThatThrownBy( () -> showClient.getShow( existingShow.getId() ) )
				.isInstanceOf( RestClientException.class );
	}

	private void assertForExistingShow( Show show ) {
		assertForShow( show, existingShow.getCity(), existingShow.getLocation(), existingShow.getMusicalId(),
		               existingShow.getTime() );
		assertThat( show )
				.hasFieldOrPropertyWithValue( "id", existingShow.getId() );
	}

	private void assertForShow( Show show, String city, String location, MusicalId musicalId, ZonedDateTime time ) {
		assertThat( show )
				.hasFieldOrPropertyWithValue( "city", city )
				.hasFieldOrPropertyWithValue( "location", location )
				.hasFieldOrPropertyWithValue( "musicalId", musicalId )
				.hasFieldOrPropertyWithValue( "time", time );
	}

}

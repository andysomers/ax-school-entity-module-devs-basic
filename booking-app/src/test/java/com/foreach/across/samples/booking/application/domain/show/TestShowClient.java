package com.foreach.across.samples.booking.application.domain.show;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foreach.across.samples.booking.application.domain.musical.Musical;
import com.foreach.across.samples.booking.application.domain.musical.MusicalClient;
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

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = "musicalService.url=http://localhost:8099")
@ContextConfiguration
@Slf4j
public class TestShowClient {
    private final ObjectMapper mapper = new ObjectMapper();
    private MusicalClient musicalClient;
    private ShowClient showClient;

    @Autowired
    private Environment environment;

    private Musical existingMusical;
    private Show existingShow;

    @Before
    public void setup() {
        musicalClient = new MusicalClient(environment.getProperty("musicalService.url"));
        showClient = new ShowClient(environment.getProperty("musicalService.url"));

        existingMusical = musicalClient.createMusical(
                Musical.builder()
                        .name("Winter wonderland")
                        .build()
        );
        existingShow = showClient.createShowForMusical(existingMusical.getId(),
                Show.builder()
                        .city("Antwerpen")
                        .location("Stadsschouwburg Bourla")
                        .time(ZonedDateTime.now())
                        .build()
        );
    }

    @Test
    public void getAllShows() throws JsonProcessingException {
        List<Show> shows = showClient.getAllShows();
        LOG.debug(mapper.writeValueAsString(shows));
    }

    @Test
    public void getASingleShow() throws JsonProcessingException {
        Show show = showClient.getShow( existingShow.getId() );
        LOG.debug( mapper.writeValueAsString( show ) );
    }

    @Test
    public void getAllShowsForMusical() throws JsonProcessingException {
        List<Show> shows = showClient.getAllShowsForMusical( existingMusical.getId() );
        LOG.debug( mapper.writeValueAsString( shows ) );
    }

    @Test
    public void getSingleShowForMusical() throws JsonProcessingException {
        Show show = showClient.getShowForMusical( existingMusical.getId(), existingShow.getId() );
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
        LOG.debug( mapper.writeValueAsString( created ) );
    }

    @Test
    public void updateShowForMusical() throws JsonProcessingException {
        Show show = existingShow;
        show.setCity( "Brussel" );
        show.setLocation( "Carré" );
        Show updated = showClient.updateShowForMusical( existingMusical.getId(), show );
        LOG.debug( mapper.writeValueAsString( updated ) );
    }

    @Test
    public void deleteShow() throws JsonProcessingException {
        showClient.deleteShow( existingShow.getId() );
    }

    @After
    public void cleanUp() {
        musicalClient.deleteMusical(existingMusical.getId());
    }

}

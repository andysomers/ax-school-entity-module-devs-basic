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
    public void getAllMusicals() throws JsonProcessingException {
        List<Show> shows = showClient.getAllShows();

        LOG.debug(mapper.writeValueAsString(shows));
    }

    @After
    public void cleanUp() {
        musicalClient.deleteMusical(existingMusical.getId());
    }

}

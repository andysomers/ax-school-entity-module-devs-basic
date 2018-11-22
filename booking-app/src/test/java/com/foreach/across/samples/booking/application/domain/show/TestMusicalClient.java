package com.foreach.across.samples.booking.application.domain.show;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.foreach.across.samples.booking.BookingApplication;
import com.foreach.across.samples.booking.application.domain.musical.MusicalClient;
import com.foreach.across.samples.booking.application.domain.musical.MusicalDto;
import lombok.extern.slf4j.Slf4j;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.EnvironmentAware;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RunWith(SpringRunner.class)
@TestPropertySource(properties = "musicalService.url=http://localhost:8099")
@ContextConfiguration
@Slf4j
public class TestMusicalClient {
    private MusicalClient musicalClient;

    @Autowired
    private Environment environment;

    @Before
    public void setup() {
        RestTemplate restTemplate = new RestTemplate();
        musicalClient = new MusicalClient(restTemplate, environment.getProperty("musicalService.url"));
    }

    @Test
    public void testGetAllMusicals() throws JsonProcessingException {
        HttpEntity<List<MusicalDto>> musicals = musicalClient.getAllMusicals();
        ObjectMapper mapper = new ObjectMapper();

        LOG.debug(mapper.writeValueAsString(musicals.getBody()));
    }

    @Test
    public void testGetMusical() throws JsonProcessingException {
        HttpEntity<MusicalDto> musical = musicalClient.getMusical(1L);
        ObjectMapper mapper = new ObjectMapper();

        LOG.debug(mapper.writeValueAsString(musical.getBody()));
    }

    @Test
    public void testCreateMusical() throws JsonProcessingException {
        MusicalDto musicalDto = MusicalDto.builder()
                .name("New musical")
                .description("New musical description")
                .build();
        HttpEntity<MusicalDto> musical = musicalClient.createMusical(musicalDto);
        ObjectMapper mapper = new ObjectMapper();

        LOG.debug(mapper.writeValueAsString(musical.getBody()));
    }

    @Test
    public void updateMusical() throws JsonProcessingException {
        MusicalDto musicalDto = MusicalDto.builder()
                .id(1L)
                .name("Updated musical")
                .description("Newly updated description")
                .build();
        HttpEntity<MusicalDto> musical = musicalClient.updateMusical(musicalDto);
        ObjectMapper mapper = new ObjectMapper();

        LOG.debug(mapper.writeValueAsString(musical.getBody()));
    }


    @Test
    public void deleteMusical() throws JsonProcessingException {
        musicalClient.deleteMusical(1L);
    }
}

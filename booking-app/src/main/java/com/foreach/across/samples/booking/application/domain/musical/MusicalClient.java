package com.foreach.across.samples.booking.application.domain.musical;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
public class MusicalClient {
    private final String musicalServiceUrl;
    private RestTemplate restTemplate;

    @Autowired
    public MusicalClient(@Value("${musicalService.url}") String musicalServiceUrl) {
        this.restTemplate = new RestTemplate();
        this.musicalServiceUrl = musicalServiceUrl;
    }

    public List<Musical> getAllMusicals() {
        try {
            return restTemplate.exchange(
                    buildMusicalBaseUrl(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Musical>>() {
                    }).getBody();
        } catch (RestClientException e) {
            LOG.error("Get all musicals from api failed.");
        }

        return null;
    }

    public Musical getMusical(MusicalId musicalId) {
        try {
            return restTemplate.exchange(
                    String.format(buildMusicalBaseUrl().concat("/%s"), musicalId.getId()),
                    HttpMethod.GET,
                    null,
                    Musical.class).getBody();
        } catch (RestClientException e) {
            LOG.error(String.format("Get musical detail from api failed for id %s .", musicalId.getId()));
        }

        return null;
    }

    public Musical createMusical(Musical musical) {
        try {
            HttpEntity<Musical> request = new HttpEntity<>(musical);

            return restTemplate.exchange(
                    buildMusicalBaseUrl(),
                    HttpMethod.POST,
                    request,
                    Musical.class).getBody();
        } catch (RestClientException e) {
            LOG.error("Create a musical api failed.");
        }

        return null;
    }

    public Musical updateMusical(Musical musical) {
        try {
            HttpEntity<Musical> request = new HttpEntity<>(musical);

            return restTemplate.exchange(
                    String.format(buildMusicalBaseUrl().concat("/%s"), musical.getId().getId()),
                    HttpMethod.PUT,
                    request,
                    Musical.class).getBody();
        } catch (RestClientException e) {
            LOG.error(String.format("Update musical api failed for id %s .", musical.getId().getId()));
        }

        return null;
    }

    public void deleteMusical(MusicalId musicalId) {
        try {
            restTemplate.exchange(
                    String.format(buildMusicalBaseUrl().concat("/%s"), musicalId.getId()),
                    HttpMethod.DELETE,
                    null,
                    Musical.class);
        } catch (RestClientException e) {
            LOG.error(String.format("Delete musical api failed for id %s .", musicalId.getId()));
        }
    }

    private String buildMusicalBaseUrl() {
        return musicalServiceUrl.concat("/api/musicals");
    }
}

package com.foreach.across.samples.booking.application.domain.musical;

import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class MusicalClient {
    private final RestTemplate restTemplate;
    private final String musicalServiceUrl;

    @Autowired
    public MusicalClient(RestTemplate restTemplate, @Value("${musicalService.url}") String musicalServiceUrl) {
        this.restTemplate = restTemplate;
        this.musicalServiceUrl = musicalServiceUrl;
    }

    public ResponseEntity<List<MusicalDto>> getAllMusicals() {
        try {
            return restTemplate.exchange(
                    buildMusicalBaseUrl(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<MusicalDto>>() {
                    });
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResponseEntity<MusicalDto> getMusical(Long id) {
        try {
            return restTemplate.exchange(
                    String.format(buildMusicalBaseUrl().concat("/%s"), id),
                    HttpMethod.GET,
                    null,
                    MusicalDto.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResponseEntity<MusicalDto> createMusical(MusicalDto musicalDto) {
        try {
            HttpEntity<MusicalDto> request = new HttpEntity<>(musicalDto);

            return restTemplate.exchange(
                    buildMusicalBaseUrl(),
                    HttpMethod.POST,
                    request,
                    MusicalDto.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResponseEntity<MusicalDto> updateMusical(MusicalDto musicalDto) {
        try {
            HttpEntity<MusicalDto> request = new HttpEntity<>(musicalDto);

            return restTemplate.exchange(
                    buildMusicalBaseUrl(),
                    HttpMethod.PUT,
                    request,
                    MusicalDto.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResponseEntity deleteMusical(Long id) {
        try {
            return restTemplate.exchange(
                    String.format(buildMusicalBaseUrl().concat("/%s"), id),
                    HttpMethod.DELETE,
                    null,
                    ResponseEntity.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String buildMusicalBaseUrl() {
        return musicalServiceUrl.concat("/api/musical");
    }
}

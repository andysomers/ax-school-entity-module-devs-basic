package com.foreach.across.samples.booking.application.domain.show;

import lombok.RequiredArgsConstructor;
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
@RequiredArgsConstructor
public class ShowClient {
    private final RestTemplate restTemplate;

    @Value("${musicalService.url}")
    private String musicalServiceUrl;

    public ResponseEntity<List<ShowDto>> getAllShows() {
        try {
            return restTemplate.exchange(
                    buildShowBaseUrl(),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ShowDto>>() {
                    });
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResponseEntity<List<ShowDto>> getShow(Long id) {
        try {
            return restTemplate.exchange(
                    String.format(buildShowBaseUrl().concat("/%s"), id),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<ShowDto>>() {
                    });
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResponseEntity<List<ShowDto>> createShow(ShowDto ShowDto) {
        try {
            HttpEntity<ShowDto> request = new HttpEntity<>(ShowDto);

            return restTemplate.exchange(
                    buildShowBaseUrl(),
                    HttpMethod.POST,
                    request,
                    new ParameterizedTypeReference<List<ShowDto>>() {
                    });
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResponseEntity<List<ShowDto>> updateShow(ShowDto ShowDto) {
        try {
            HttpEntity<ShowDto> request = new HttpEntity<>(ShowDto);

            return restTemplate.exchange(
                    buildShowBaseUrl(),
                    HttpMethod.PUT,
                    request,
                    new ParameterizedTypeReference<List<ShowDto>>() {
                    });
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return null;
    }

    public ResponseEntity<List<ShowDto>> deleteShow(Long id) {
        try {
            return restTemplate.exchange(
                    String.format(buildShowBaseUrl().concat("/%s"), id),
                    HttpMethod.DELETE,
                    null,
                    new ParameterizedTypeReference<List<ShowDto>>() {
                    });
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String buildShowBaseUrl() {
        return musicalServiceUrl.concat("/api/show");
    }
}

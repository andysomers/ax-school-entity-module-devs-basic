package com.foreach.across.samples.booking.application.domain.musical;

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

	public ResponseEntity<List<Musical>> getAllMusicals() {
        try {
            return restTemplate.exchange(
		            buildMusicalBaseUrl(),
		            HttpMethod.GET,
		            null,
		            new ParameterizedTypeReference<List<Musical>>()
		            {
                    });
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return null;
    }

	public ResponseEntity<Musical> getMusical( MusicalId musicalId ) {
        try {
            return restTemplate.exchange(
		            String.format( buildMusicalBaseUrl().concat( "/%s" ), musicalId.getId() ),
		            HttpMethod.GET,
		            null,
		            Musical.class );
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return null;
    }

	public ResponseEntity<Musical> createMusical( Musical musical ) {
        try {
	        HttpEntity<Musical> request = new HttpEntity<>( musical );

            return restTemplate.exchange(
		            buildMusicalBaseUrl(),
		            HttpMethod.POST,
		            request,
		            Musical.class );
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return null;
    }

	public ResponseEntity<Musical> updateMusical( Musical musical ) {
        try {
	        HttpEntity<Musical> request = new HttpEntity<>( musical );

            return restTemplate.exchange(
		            buildMusicalBaseUrl(),
		            HttpMethod.PUT,
		            request,
		            Musical.class );
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return null;
    }

	public ResponseEntity deleteMusical( MusicalId musicalId ) {
        try {
            return restTemplate.exchange(
		            String.format( buildMusicalBaseUrl().concat( "/%s" ), musicalId.getId() ),
		            HttpMethod.DELETE,
		            null,
		            ResponseEntity.class);
        } catch (RestClientException e) {
            e.printStackTrace();
        }

        return null;
    }

    private String buildMusicalBaseUrl() {
	    return musicalServiceUrl.concat( "/api/musicals" );
    }
}

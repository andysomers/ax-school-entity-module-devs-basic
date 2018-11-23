package com.foreach.across.samples.booking.application.domain.musical;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Component
public class MusicalClient
{
	private RestTemplate restTemplate;
	private final String musicalServiceUrl;

	@Autowired
	public MusicalClient( @Value("${musicalService.url}") String musicalServiceUrl ) {
		this.restTemplate = new RestTemplate();
		this.musicalServiceUrl = musicalServiceUrl;
	}

	public List<Musical> getAllMusicals() {
		try {
			return restTemplate.exchange(
					buildMusicalBaseUrl(),
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<Musical>>()
					{
					} ).getBody();
		}
		catch ( RestClientException e ) {
			e.printStackTrace();
		}

		return null;
	}

	public Musical getMusical( MusicalId musicalId ) {
		try {
			return restTemplate.exchange(
					String.format( buildMusicalBaseUrl().concat( "/%s" ), musicalId.getId() ),
					HttpMethod.GET,
					null,
					Musical.class ).getBody();
		}
		catch ( RestClientException e ) {
			e.printStackTrace();
		}

		return null;
	}

	public Musical createMusical( Musical musical ) {
		try {
			HttpEntity<Musical> request = new HttpEntity<>( musical );

			return restTemplate.exchange(
					buildMusicalBaseUrl(),
					HttpMethod.POST,
					request,
					Musical.class ).getBody();
		}
		catch ( RestClientException e ) {
			e.printStackTrace();
		}

		return null;
	}

	public Musical updateMusical( Musical musical ) {
		try {
			HttpEntity<Musical> request = new HttpEntity<>( musical );

			return restTemplate.exchange(
					String.format( buildMusicalBaseUrl().concat( "/%s" ), musical.getId().getId() ),
					HttpMethod.PUT,
					request,
					Musical.class ).getBody();
		}
		catch ( RestClientException e ) {
			e.printStackTrace();
		}

		return null;
	}

	public void deleteMusical( MusicalId musicalId ) {
		try {
			restTemplate.exchange(
					String.format( buildMusicalBaseUrl().concat( "/%s" ), musicalId.getId() ),
					HttpMethod.DELETE,
					null,
					Musical.class );
		}
		catch ( RestClientException e ) {
			e.printStackTrace();
		}
	}

	private String buildMusicalBaseUrl() {
		return musicalServiceUrl.concat( "/api/musicals" );
	}
}

package com.foreach.across.samples.booking.application.domain.show;

import com.foreach.across.samples.booking.application.domain.musical.MusicalId;
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
public class ShowClient
{
	private final RestTemplate restTemplate;
	private final String musicalServiceUrl;

	@Autowired
	public ShowClient( RestTemplate restTemplate, @Value("${musicalService.url}") String musicalServiceUrl ) {
		this.restTemplate = restTemplate;
		this.musicalServiceUrl = musicalServiceUrl;
	}

	public List<Show> getAllShows() {
		try {
			return restTemplate.exchange(
					buildShowBaseUrl(),
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<Show>>()
					{
					} ).getBody();
		}
		catch ( RestClientException e ) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Show> getShow( ShowId showId ) {
		try {
			return restTemplate.exchange(
					String.format( buildShowBaseUrl().concat( "/%s" ), showId.getId() ),
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<Show>>()
					{
					} ).getBody();
		}
		catch ( RestClientException e ) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Show> deleteShow( ShowId showId ) {
		try {
			return restTemplate.exchange(
					String.format( buildShowBaseUrl().concat( "/%s" ), showId ),
					HttpMethod.DELETE,
					null,
					new ParameterizedTypeReference<List<Show>>()
					{
					} ).getBody();
		}
		catch ( RestClientException e ) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Show> getAllShowsForMusical( MusicalId musicalId ) {
		try {
			return restTemplate.exchange(
					buildMusicalShowsBaseUrl( musicalId ),
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<Show>>()
					{
					} ).getBody();
		}
		catch ( RestClientException e ) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Show> getShowForMusical( MusicalId musicalId, ShowId showId ) {
		try {
			return restTemplate.exchange(
					String.format( buildMusicalShowsBaseUrl( musicalId ).concat( "/%s" ), showId ),
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<Show>>()
					{
					} ).getBody();
		}
		catch ( RestClientException e ) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Show> createShowForMusical( Show show ) {
		try {
			HttpEntity<Show> request = new HttpEntity<>( show );

			return restTemplate.exchange(
					buildMusicalShowsBaseUrl( show.getMusicalId() ).concat( "/shows" ),
					HttpMethod.POST,
					request,
					new ParameterizedTypeReference<List<Show>>()
					{
					} ).getBody();
		}
		catch ( RestClientException e ) {
			e.printStackTrace();
		}

		return null;
	}

	public List<Show> updateShowForMusical( Show show ) {
		try {
			HttpEntity<Show> request = new HttpEntity<>( show );

			return restTemplate.exchange(
					buildMusicalShowsBaseUrl( show.getMusicalId() ).concat( "/shows" ),
					HttpMethod.PUT,
					request,
					new ParameterizedTypeReference<List<Show>>()
					{
					} ).getBody();
		}
		catch ( RestClientException e ) {
			e.printStackTrace();
		}

		return null;
	}

	private String buildShowBaseUrl() {
		return musicalServiceUrl.concat( "/api/shows" );
	}

	private String buildMusicalShowsBaseUrl( MusicalId musicalId ) {
		return String.format( musicalServiceUrl.concat( "/api/musicals/%s" ), musicalId.getId() );
	}
}

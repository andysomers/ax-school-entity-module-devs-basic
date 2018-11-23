package com.foreach.across.samples.booking.application.domain.show;

import com.foreach.across.samples.booking.application.domain.musical.MusicalId;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Component
public class ShowClient
{
	private final String musicalServiceUrl;
	private RestTemplate restTemplate;

	@Autowired
	public ShowClient( @Value("${musicalService.url}") String musicalServiceUrl ) {
		this.restTemplate = new RestTemplate();
		this.musicalServiceUrl = musicalServiceUrl;
	}

	public List<Show> getAllShows() {
		return restTemplate.exchange(
				buildShowBaseUrl(),
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Show>>()
				{
				} ).getBody();
	}

	public Show getShow( ShowId showId ) {
		return restTemplate.exchange(
				String.format( buildShowBaseUrl().concat( "/%s" ), showId.getId() ),
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<Show>()
				{
				} ).getBody();
	}

	public void deleteShow( ShowId showId ) {
		restTemplate.exchange(
				String.format( buildShowBaseUrl().concat( "/%s" ), showId.getId() ),
				HttpMethod.DELETE,
				null,
				new ParameterizedTypeReference<List<Show>>()
				{
				} );
	}

	public List<Show> getAllShowsForMusical( MusicalId musicalId ) {
		return restTemplate.exchange(
				buildMusicalShowsBaseUrl( musicalId ),
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<List<Show>>()
				{
				} ).getBody();
	}

	public Show getShowForMusical( MusicalId musicalId, ShowId showId ) {
		return restTemplate.exchange(
				String.format( buildMusicalShowsBaseUrl( musicalId ).concat( "/%s" ), showId.getId() ),
				HttpMethod.GET,
				null,
				new ParameterizedTypeReference<Show>()
				{
				} ).getBody();
	}

	public Show createShowForMusical( MusicalId musicalId, Show show ) {
		HttpEntity<Show> request = new HttpEntity<>( show );
		return restTemplate.exchange(
				buildMusicalShowsBaseUrl( musicalId ),
				HttpMethod.POST,
				request,
				new ParameterizedTypeReference<Show>()
				{
				} ).getBody();
	}

	public Show updateShowForMusical( MusicalId musicalId, Show show ) {
		HttpEntity<Show> request = new HttpEntity<>( show );
		return restTemplate.exchange(
				String.format( buildMusicalShowsBaseUrl( musicalId ).concat( "/%s" ), show.getId().getId() ),
				HttpMethod.PUT,
				request,
				new ParameterizedTypeReference<Show>()
				{
				} ).getBody();
	}

	private String buildShowBaseUrl() {
		return musicalServiceUrl.concat( "/api/shows" );
	}

	private String buildMusicalShowsBaseUrl( MusicalId musicalId ) {
		return String.format( musicalServiceUrl.concat( "/api/musicals/%s" ), musicalId.getId() ).concat( "/shows" );
	}
}

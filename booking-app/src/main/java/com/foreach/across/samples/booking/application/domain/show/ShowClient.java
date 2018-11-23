package com.foreach.across.samples.booking.application.domain.show;

import com.foreach.across.samples.booking.application.domain.musical.MusicalId;
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
		try {
			return restTemplate.exchange(
					buildShowBaseUrl(),
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<Show>>() {
					}).getBody();
		} catch (RestClientException e) {
			throw new RestClientException("Get all shows  failed.", e);
		}
	}

	public Show getShow(ShowId showId) {
		try {
			return restTemplate.exchange(
					String.format(buildShowBaseUrl().concat("/%s"), showId.getId()),
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<Show>() {
					}).getBody();
		} catch (RestClientException e) {
			throw new RestClientException(String.format("Get show %s detail failed.", showId.getId()), e);
		}
	}

	public void deleteShow(ShowId showId) {
		try {
			restTemplate.exchange(
					String.format(buildShowBaseUrl().concat("/%s"), showId.getId()),
					HttpMethod.DELETE,
					null,
					new ParameterizedTypeReference<List<Show>>() {
					});
		} catch (RestClientException e) {
			throw new RestClientException(String.format("Delete show %s failed.", showId.getId()), e);
		}
	}

	public List<Show> getAllShowsForMusical(MusicalId musicalId) {
		try {
			return restTemplate.exchange(
					buildMusicalShowsBaseUrl(musicalId),
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<List<Show>>() {
					}).getBody();
		} catch (RestClientException e) {
			throw new RestClientException(String.format("Get shows for musical %s failed.", musicalId.getId()), e);
		}
	}

	public Show getShowForMusical(MusicalId musicalId, ShowId showId) {
		try {
			return restTemplate.exchange(
					String.format(buildMusicalShowsBaseUrl(musicalId).concat("/%s"), showId.getId()),
					HttpMethod.GET,
					null,
					new ParameterizedTypeReference<Show>() {
					}).getBody();
		} catch (RestClientException e) {
			throw new RestClientException(String.format(String.format("Get show detail for musical failed for musical %s and show %s .", musicalId.getId(), showId.getId()), musicalId.getId()), e);
		}
	}

	public Show createShowForMusical(MusicalId musicalId, Show show) {
		try {
			HttpEntity<Show> request = new HttpEntity<>(show);

			return restTemplate.exchange(
					buildMusicalShowsBaseUrl(musicalId),
					HttpMethod.POST,
					request,
					new ParameterizedTypeReference<Show>() {
					}).getBody();
		} catch (RestClientException e) {
			throw new RestClientException(String.format("Create show %s for musical %s failed.", musicalId.getId(), show.getId().getId(), musicalId.getId()), e);
		}
	}

	public Show updateShowForMusical(MusicalId musicalId, Show show) {
		try {
			HttpEntity<Show> request = new HttpEntity<>(show);

			return restTemplate.exchange(
					String.format(buildMusicalShowsBaseUrl(musicalId).concat("/%s"), show.getId().getId()),
					HttpMethod.PUT,
					request,
					new ParameterizedTypeReference<Show>() {
					}).getBody();
		} catch (RestClientException e) {
			throw new RestClientException(String.format("Update show %s for musical %s failed.", musicalId.getId(), show.getId().getId()), e);
		}
	}

	private String buildShowBaseUrl() {
		return musicalServiceUrl.concat( "/api/shows" );
	}

	private String buildMusicalShowsBaseUrl( MusicalId musicalId ) {
		return String.format( musicalServiceUrl.concat( "/api/musicals/%s" ), musicalId.toString() ).concat( "/shows" );
	}
}

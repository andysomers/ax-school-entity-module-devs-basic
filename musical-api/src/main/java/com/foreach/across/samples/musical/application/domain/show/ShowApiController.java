package com.foreach.across.samples.musical.application.domain.show;

import com.foreach.across.samples.musical.application.domain.musical.Musical;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class ShowApiController
{
	private final ShowRepository showRepository;

	@GetMapping("/shows/{showId}")
	public ResponseEntity<ShowDto> getShow( @PathVariable("showId") Show show ) {
		if ( show == null ) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok( ShowDto.from( show ) );
	}

	@GetMapping("/shows")
	public ResponseEntity<List<ShowDto>> getAllShows() {
		List<ShowDto> response = showRepository.findAll()
		                                       .stream()
		                                       .map( ShowDto::from )
		                                       .collect( Collectors.toList() );
		return ResponseEntity.ok( response );
	}

	@DeleteMapping("/shows/{show}")
	public ResponseEntity deleteShow( @PathVariable("show") UUID showId ) {
		showRepository.delete( showId );
		return ResponseEntity.ok().build();
	}

	@GetMapping("/musicals/{musical}/shows/{showId}")
	public ResponseEntity<ShowDto> getShowByMusical( @PathVariable("musical") Musical musical, @PathVariable("showId") UUID showId ) {
		Show show = showRepository.findOneByIdAndMusical( showId, musical );
		if ( show == null ) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok( ShowDto.from( show ) );
	}

	@GetMapping("/musicals/{musical}/shows")
	public ResponseEntity<List<ShowDto>> getAllShowsByMusical( @PathVariable("musical") Musical musical ) {
		if ( musical == null ) {
			return ResponseEntity.notFound().build();
		}
		List<ShowDto> response = musical.getShows()
		                                .stream()
		                                .map( ShowDto::from )
		                                .collect( Collectors.toList() );
		return ResponseEntity.ok( response );
	}

	@PostMapping("/musicals/{musical}/shows")
	public ResponseEntity<ShowDto> createShow( @PathVariable("musical") Musical musical, @RequestBody ShowDto showDto ) {
		Show show = showDto.toShow();
		show.setMusical( musical );
		showRepository.save( show );
		return ResponseEntity.ok( ShowDto.from( show ) );
	}

	@PutMapping("/musicals/{musical}/shows/{show}")
	public ResponseEntity<ShowDto> updateShow( @PathVariable("musical") Musical musical, @PathVariable("show") UUID showId, @RequestBody ShowDto showDto ) {
		Show show = showRepository.findOneByIdAndMusical( showId, musical );
		if ( show == null ) {
			return ResponseEntity.notFound().build();
		}
		show.setLocation( showDto.getLocation() );
		show.setTime( showDto.getTime() );
		showRepository.save( show );
		return ResponseEntity.ok( ShowDto.from( show ) );
	}
}

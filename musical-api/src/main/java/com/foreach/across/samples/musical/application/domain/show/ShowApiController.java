package com.foreach.across.samples.musical.application.domain.show;

import com.foreach.across.samples.musical.application.domain.musical.Musical;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RequestMapping("/api")
@RequiredArgsConstructor
@RestController
public class ShowApiController
{
	private final ShowRepository showRepository;

	@GetMapping("/musicals/{musical}/shows/{showId}")
	public ResponseEntity<ShowDto> getShow( @PathVariable("musical") Musical musical, @PathVariable("showId") Long showId ) {
		Show show = showRepository.findOneByIdAndMusical( showId, musical );
		if ( show == null ) {
			return ResponseEntity.notFound().build();
		}
		return ResponseEntity.ok( ShowDto.from( show ) );
	}

	@GetMapping("/musicals/{musical}/shows")
	public ResponseEntity<List<ShowDto>> getShows( @PathVariable("musical") Musical musical ) {
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
	public ResponseEntity<ShowDto> createShow( @PathVariable("musical") Musical musical, ShowDto showDto ) {
		Show show = showDto.toShow();
		return ResponseEntity.ok( ShowDto.from( show ) );
	}

	@PutMapping("/musicals/{musical}/shows/{show}")
	public ResponseEntity<ShowDto> updateShow( @PathVariable("musical") Musical musical, @PathVariable("show") ShowDto showDto ) {
		Show show = showRepository.findOneByIdAndMusical( showDto.getId(), musical );
		if ( show == null ) {
			return ResponseEntity.notFound().build();
		}
		show.setName( showDto.getName() );
		showRepository.save( show );
		return ResponseEntity.ok( ShowDto.from( show ) );
	}

	@DeleteMapping("/musicals/{musical}/shows/{show}")
	public ResponseEntity deleteShow( @PathVariable("musical") Musical musical, @PathVariable("show") Long showId ) {
		Show show = showRepository.findOneByIdAndMusical( showId, musical );
		if ( show == null ) {
			return ResponseEntity.notFound().build();
		}
		showRepository.delete( show );
		return ResponseEntity.ok().build();
	}
}

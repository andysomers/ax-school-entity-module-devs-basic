package com.foreach.across.samples.musical.application.installers;

import com.foreach.across.core.annotations.Installer;
import com.foreach.across.core.annotations.InstallerMethod;
import com.foreach.across.core.installers.InstallerPhase;
import com.foreach.across.samples.musical.application.domain.musical.Musical;
import com.foreach.across.samples.musical.application.domain.musical.MusicalRepository;
import com.foreach.across.samples.musical.application.domain.show.Show;
import com.foreach.across.samples.musical.application.domain.show.ShowRepository;
import lombok.RequiredArgsConstructor;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Installer(
		name = "Default musical data installer",
		description = "Installs some default musicals and shows",
		version = 1,
		phase = InstallerPhase.AfterModuleBootstrap
)
@RequiredArgsConstructor
public class MusicalDataInstaller
{
	private final MusicalRepository musicalRepository;
	private final ShowRepository showRepository;

	@InstallerMethod
	public void install() {

		Musical peterPanMusical = Musical.builder()
		                                 .name( "Neverland, the adventures of Peter Pan" )
		                                 .description(
				                                 "In February 2019 Music Hall brings the timeless story of Peter Pan, 'the boy who did not want to grow up', to the halls again in a brand new, enchanting version. An international cast, countless surprises, magical images and amazing choreographies, provide an unforgettable theater experience." )
		                                 .build();

		List<Show> peterPanShows = new ArrayList<Show>();
		peterPanShows.add(
				Show.builder().location( "Stadsschouwburg Antwerpen" )
				    .time( ZonedDateTime.parse( "2019-02-20T20:00+01:00[Europe/Brussels]" ) )
				    .city( "Antwerpen" )
				    .build()
		);
		peterPanShows.add(
				Show.builder().location( "Stadsschouwburg Antwerpen" )
				    .time( ZonedDateTime.parse( "2019-02-21T20:00+01:00[Europe/Brussels]" ) )
				    .city( "Antwerpen" )
				    .build()
		);
		peterPanShows.add(
				Show.builder().location( "Stadsschouwburg Antwerpen" )
				    .time( ZonedDateTime.parse( "2019-02-22T20:00+01:00[Europe/Brussels]" ) )
				    .city( "Antwerpen" )
				    .build()
		);
		peterPanShows.add(
				Show.builder().location( "Stadsschouwburg Antwerpen" )
				    .time( ZonedDateTime.parse( "2019-02-23T20:00+01:00[Europe/Brussels]" ) )
				    .city( "Antwerpen" )
				    .build()
		);
		peterPanShows.add(
				Show.builder().location( "Stadsschouwburg Antwerpen" )
				    .time( ZonedDateTime.parse( "2019-02-24T14:30+01:00[Europe/Brussels]" ) )
				    .city( "Antwerpen" )
				    .build()
		);
		peterPanShows.forEach( show -> show.setMusical( peterPanMusical ) );
		peterPanMusical.setShows( peterPanShows );
		musicalRepository.save( peterPanMusical );

		Musical aladinMusical = Musical.builder()
		                               .name( "Aladdin - De family musical" )
		                               .description(
				                               "Who does not know this world famous fairy tale from 'A thousand and one night'? After the great success of the musical 'Pippi Longstocking we present you another grand family musical: Aladdin. It is a spectacular musical journey through the Middle East, laced with humor, a lot of (belly) dance, some spells, a flying carpet with a princess on it and ... a surprising Spirit from a wonder lamp." )
		                               .build();

		List<Show> aladinShows = new ArrayList<Show>();
		aladinShows.add(
				Show.builder().location( "Stadsschouwburg Antwerpen" )
				    .time( ZonedDateTime.parse( "2017-09-05T10:00+01:00[Europe/Brussels]" ) )
				    .city( "Antwerpen" )
				    .build()
		);
		aladinShows.add(
				Show.builder().location( "Stadsschouwburg Antwerpen" )
				    .time( ZonedDateTime.parse( "2019-08-05T10:00+01:00[Europe/Brussels]" ) )
				    .city( "Antwerpen" )
				    .build()
		);
		aladinShows.add(
				Show.builder().location( "Capitole Gent" )
				    .time( ZonedDateTime.parse( "2019-08-06T11:00+01:00[Europe/Brussels]" ) )
				    .city( "Gent" )
				    .build()
		);
		aladinShows.add(
				Show.builder().location( "Capitole Gent" )
				    .time( ZonedDateTime.parse( "2019-08-06T14:00+01:00[Europe/Brussels]" ) )
				    .city( "Gent" )
				    .build()
		);
		aladinShows.add(
				Show.builder().location( "Expo Hasselt" )
				    .time( ZonedDateTime.parse( "2019-06-01T14:00+01:00[Europe/Brussels]" ) )
				    .city( "Hasselt" )
				    .build()
		);
		aladinShows.add(
				Show.builder().location( "Expo Hasselt" )
				    .time( ZonedDateTime.parse( "2019-06-02T14:00+01:00[Europe/Brussels]" ) )
				    .city( "Hasselt" )
				    .build()
		);
		aladinShows.forEach( show -> show.setMusical( aladinMusical ) );
		aladinMusical.setShows( aladinShows );
		musicalRepository.save( aladinMusical );

		Musical smurfenDeMusical = Musical.builder()
		                                  .name( "Smurfen de Musical" )
		                                  .description(
				                                  "Smurfs the Musical has received praise from all over the world, but the Smurfs have never been on stage in Belgium. In March 2019 this international production will finally comes to our country." )
		                                  .build();
		musicalRepository.save( smurfenDeMusical );

		List<Show> smurfenShows = new ArrayList<Show>();
		smurfenShows.add(
				Show.builder().location( "Stadsschouwburg Antwerpen" )
				    .time( ZonedDateTime.parse( "2019-06-03T13:30+01:00[Europe/Brussels]" ) )
				    .city( "Antwerpen" )
				    .build()
		);
		smurfenShows.add(
				Show.builder().location( "Expo Hasselt" )
				    .time( ZonedDateTime.parse( "2019-03-02T13:30+01:00[Europe/Brussels]" ) )
				    .city( "Hasselt" )
				    .build()
		);
		smurfenShows.add(
				Show.builder().location( "Capitole Gent" )
				    .time( ZonedDateTime.parse( "2019-03-03T13:30+01:00[Europe/Brussels]" ) )
				    .city( "Gent" )
				    .build()
		);
		smurfenShows.forEach( show -> show.setMusical( smurfenDeMusical ) );
		smurfenDeMusical.setShows( smurfenShows );
		showRepository.save( smurfenShows );
	}
}

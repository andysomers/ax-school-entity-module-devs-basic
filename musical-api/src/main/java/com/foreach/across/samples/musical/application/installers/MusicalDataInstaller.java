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
import java.util.Collections;
import java.util.List;

@Installer(
        name = "Default musical data installer",
        description = "Installs some default musicals and shows",
        version = 1,
        phase = InstallerPhase.AfterModuleBootstrap
)
@RequiredArgsConstructor
public class MusicalDataInstaller {
    private final MusicalRepository musicalRepository;

    @InstallerMethod
    public void install(){
        List<Show> zombieKidShows = new ArrayList<Show>();

        zombieKidShows.add(Show.builder().name("Show 1").location("Brussel").time(ZonedDateTime.now().plusDays(5)).build());
        zombieKidShows.add(Show.builder().name("Show 2").location("Antwerpen").time(ZonedDateTime.now().plusDays(5)).build());

        Musical zombieKids = Musical.builder()
                .name("Smurfen de Musical")
                .description("Musical about 'the smurfen' with several shows in all major cities!")
                .shows(zombieKidShows)
                .build();

        musicalRepository.save(zombieKids);
    }
}

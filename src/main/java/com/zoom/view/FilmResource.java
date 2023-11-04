package com.zoom.view;

import com.zoom.model.FilmEntity;
import com.zoom.repository.FilmRepository;
import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

import java.util.Optional;
import java.util.stream.Collectors;

@Path("/")
public class FilmResource {

    @Inject
    FilmRepository filmRepository;

    @GET
    @Path("/hello_world")
    @Produces(MediaType.TEXT_PLAIN)
    public String helloWorld() {
        return "Hello World";
    }

    @GET
    @Path("/file/{film_id}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getFilm(@PathParam("film_id") short filmId) {
        final Optional<FilmEntity> film = filmRepository.getFilm(filmId);
        return film.isPresent() ? film.get().getTitle() : "NO Film Present";
    }

    @GET
    @Path("/paged/{page}/{minLength}")
    @Produces(MediaType.TEXT_PLAIN)
    public String getPagedFilms(@PathParam("page") long page, @PathParam("minLength") short minLength ) {
        return filmRepository.paged(page, minLength)
            .map(f -> String.format("%s (%d min)", f.getTitle(), f.getLength()))
            .collect(Collectors.joining("\n"));

    }


}

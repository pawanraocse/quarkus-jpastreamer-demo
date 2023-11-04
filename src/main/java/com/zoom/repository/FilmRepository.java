package com.zoom.repository;

import com.speedment.jpastreamer.application.JPAStreamer;
import com.speedment.jpastreamer.projection.Projection;
import com.zoom.model.FilmEntity;
import com.zoom.model.FilmEntity$;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;

import java.util.Optional;
import java.util.stream.Stream;

@ApplicationScoped
public class FilmRepository {

    private static final int PAGE_SIZE = 10;

    @Inject
    JPAStreamer jpaStreamer;

    public Optional<FilmEntity> getFilm(short id) {
        return jpaStreamer.stream(FilmEntity.class)
            .filter(FilmEntity$.filmId.equal(id))
            .findFirst();
    }

    public Stream<FilmEntity> paged(long page, short pageLength) {
        return jpaStreamer.stream(Projection.select(FilmEntity$.filmId, FilmEntity$.title, FilmEntity$.length))
            .filter(FilmEntity$.length.greaterThan(pageLength))
            .sorted(FilmEntity$.length)
            .skip(page * PAGE_SIZE)
            .limit(PAGE_SIZE);
    }

}

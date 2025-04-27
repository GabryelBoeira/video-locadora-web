package io.github.gabryel.videolocadora.repository;

import io.github.gabryel.videolocadora.entity.MovieEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MovieRepository extends JpaRepository<MovieEntity, Long> {
}

package dev.team.githubtrendanalysis.repositories;

import dev.team.githubtrendanalysis.models.License;
import dev.team.githubtrendanalysis.models.Repository;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface LicenseRepository extends Neo4jRepository<License, Long> {
    Optional<License> findById(Long id);
}

package dev.team.githubtrendanalysis.repositories;

import dev.team.githubtrendanalysis.models.Repository;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface RepositoryRepository extends Neo4jRepository<Repository, Long> {
    Optional<Repository> findById(Long id);
}


package dev.team.githubtrendanalysis.repositories;

import dev.team.githubtrendanalysis.models.User;
import org.springframework.data.neo4j.repository.Neo4jRepository;

import java.util.Optional;

public interface UserRepository extends Neo4jRepository<User, Long> {
    Optional<User> findByUsername(String username);
}
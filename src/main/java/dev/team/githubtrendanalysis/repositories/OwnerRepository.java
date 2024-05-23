package dev.team.githubtrendanalysis.repositories;

import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.models.License;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
@Repository
public interface OwnerRepository extends Neo4jRepository<License, Long> {
    @Query("MATCH (repo:GithubRepo)-[:OWNED_BY]->(owner:Owner {login: $ownerLogin}) RETURN repo")
    List<GithubRepo> findByOwner_Login(@Param("ownerLogin") String ownerLogin);

}

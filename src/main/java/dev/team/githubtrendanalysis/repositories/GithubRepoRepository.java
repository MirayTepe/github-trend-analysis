package dev.team.githubtrendanalysis.repositories;

import dev.team.githubtrendanalysis.models.GithubRepo;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;
import java.util.Optional;

public interface GithubRepoRepository extends Neo4jRepository<GithubRepo, Long> {
    Optional<GithubRepo> findById(Long id);
    @Query("MATCH (repo:GithubRepo) WHERE repo.created_at >= $startDate AND repo.created_at <= $endDate RETURN repo")
    List<GithubRepo> findByCreatedAtBetween(String startDate, String endDate);


}


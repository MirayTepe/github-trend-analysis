package dev.team.githubtrendanalysis.repositories;

import dev.team.githubtrendanalysis.models.GithubRepo;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.time.LocalDate;
import java.util.List;

public interface GithubRepoRepository extends Neo4jRepository<GithubRepo, Long> {
    @Query("MATCH (repo:GithubRepo) WHERE repo.created_at >= $startDate AND repo.created_at <= $endDate RETURN repo")
    List<GithubRepo> findByCreatedAtBetween(String startDate, String endDate);
    @Query("MATCH (repo:GithubRepo) RETURN repo SKIP $skip LIMIT $size")
    List<GithubRepo> findGithubReposByPageAndSize(int skip, int size);

    // En çok yıldız alan repoları bulma
    List<GithubRepo> findTop10ByOrderByStargazersCountDesc();

    // Belirli bir dilde yazılmış repoları bulma
    List<GithubRepo> findByLanguageName(String languageName);

    // Fork sayısı belirli bir değerden fazla olan repoları getirme
    List<GithubRepo> findByForksCountGreaterThan(Integer forksCount); // Değişiklik burada


    // Özel sorgu: Belirli bir lisansa sahip repoları getirme
    @Query("MATCH (r:GithubRepo)-[:HAS_LICENSE]->(l:License) WHERE l.name = $licenseName RETURN r")
    List<GithubRepo> findByLicenseName(String licenseName);
}

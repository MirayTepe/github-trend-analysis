package dev.team.githubtrendanalysis.repositories;

import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.models.Language;
import dev.team.githubtrendanalysis.models.LanguageRepoCount;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface LanguageRepository extends Neo4jRepository<Language, Long> {
    Optional<Language> findByName(String name);
    @Query("MATCH (l:Language)<-[:WRITTEN_IN]-(r:GithubRepo) WHERE l.name = $name RETURN count(r)")
    int countRepositoriesByLanguage(String languageName);

    // Dil adı ve depo sayısını döndüren sorgu
    @Query("MATCH (repo:GithubRepo)-[:WRITTEN_IN]->(lang:Language) " +
            "RETURN lang.name AS language, COUNT(repo) AS repoCount " +
            "ORDER BY repoCount DESC " +
            "LIMIT 25")
    List<LanguageRepoCount> countReposByLanguage();
    @Query("MATCH (repo:GithubRepo)-[:WRITTEN_IN]->(lang:Language {name: $languageName}) RETURN repo")
    List<GithubRepo> findByLanguage_Name(@Param("languageName") String languageName);
}


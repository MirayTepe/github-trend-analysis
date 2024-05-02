package dev.team.githubtrendanalysis.repositories;

import dev.team.githubtrendanalysis.models.Language;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface LanguageRepository extends Neo4jRepository<Language, String> {

    // Dile göre repo sayısını getirme
    @Query("MATCH (l:Language)<-[:WRITTEN_IN]-(r:GithubRepo) WHERE l.name = $languageName RETURN count(r)")
    int countRepositoriesByLanguage(String languageName);
}

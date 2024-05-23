package dev.team.githubtrendanalysis.repositories;

import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.models.License;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
@Repository
public interface LicenseRepository extends Neo4jRepository<License, Long> {
    License findByName(String name);

    @Query("MATCH (repo:GithubRepo)-[:HAS_LICENSE]->(:License {name: $licenseName}) RETURN repo")
    List<GithubRepo> findByLicense_Name(@Param("licenseName") String licenseName);

    @Query("MATCH (repo:GithubRepo)-[:HAS_LICENSE]->(:License {key: $licenseKey}) RETURN repo")
    List<GithubRepo> findByLicense_Key(@Param("licenseKey") String licenseKey);

    @Query("MATCH (repo:GithubRepo)-[:HAS_LICENSE]->(:License {spdxId: $spdxId}) RETURN repo")
    List<GithubRepo> findByLicense_SpdxId(@Param("spdxId") String spdxId);
}

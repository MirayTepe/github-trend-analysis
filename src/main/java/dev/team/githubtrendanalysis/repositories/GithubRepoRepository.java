package dev.team.githubtrendanalysis.repositories;

import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.models.Language;
import dev.team.githubtrendanalysis.objects.Owner;
import dev.team.githubtrendanalysis.models.TopicNode;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;
@Repository
public interface GithubRepoRepository extends Neo4jRepository<GithubRepo, Long> {

    @Query("MATCH (repo:GithubRepo) " +
            "OPTIONAL MATCH (repo)-[written:WRITTEN_IN]->(language:Language) " +
            "OPTIONAL MATCH (repo)-[hasLicense:HAS_LICENSE]->(license:License) " +
            "OPTIONAL MATCH (repo)-[ownedBy:OWNED_BY]->(owner:Owner) " +
            "OPTIONAL MATCH (repo)-[hasTopic:HAS_TOPIC]->(topic:TopicNode) " +
            "WHERE " +
            "  (repo.name CONTAINS $query OR " +
            "  language.name IN $languageNames OR " +
            "  owner.login IN $ownerLogins OR " +
            "  license.name IN $licenseNames OR " +
            "  topic.topic IN $topics) AND " +
            "  ($fork IS NULL OR $fork = false OR repo.fork = true) " +
            "WITH repo, written, language, hasLicense, license, ownedBy, owner, collect(hasTopic) as topicRelations, collect(topic) as topics " +
            "ORDER BY repo.stargazers_count DESC, repo.forks_count DESC " +
            "SKIP $skip LIMIT $size " +
            "RETURN repo, written, language, hasLicense, license, ownedBy, owner, topicRelations, topics ")
    List<GithubRepo> searchGithubReposByPageAndSize(
            @Param("query") String query,
            @Param("filterBy") String filterBy,
            @Param("isPrivate") Boolean isPrivate,
            @Param("fork") Boolean fork,
            @Param("languageNames") List<String> languageNames,
            @Param("ownerLogins") List<String> ownerLogins,
            @Param("licenseNames") List<String> licenseNames,
            @Param("topics") List<String> topics,
            @Param("skip") int skip,
            @Param("size") int size);

    @Query("MATCH (repo:GithubRepo)-[written:WRITTEN_IN]->(language:Language) " +
            "MATCH (repo)-[hasLicense:HAS_LICENSE]->(license:License) " +
            "MATCH (repo)-[ownedBy:OWNED_BY]->(owner:Owner) " +
            "OPTIONAL MATCH (repo)-[hasTopic:HAS_TOPIC]->(topic:TopicNode) " +
            "RETURN repo, written, language, hasLicense, license, ownedBy, owner, collect(topic) as topics, collect(hasTopic) as topicRelations " +
            "ORDER BY repo.stargazersCount DESC, repo.forksCount DESC " +
            "SKIP $skip LIMIT $size")
    List<GithubRepo> findGithubReposByPageAndSize(@Param("skip") int skip, @Param("size") int size);

    @Query("MATCH (repo:GithubRepo) WHERE toLower(repo.fullName) CONTAINS toLower($fullName) RETURN repo")
    List<GithubRepo> findByFullNameContainingIgnoreCase(@Param("fullName") String fullName);

    @Query("MATCH (repo:GithubRepo) " +
            "WHERE toLower(repo.name) CONTAINS toLower($name) AND (" +
            "toLower($filterBy) IN [toLower(repo.language.name), toLower(repo.owner.login), toLower(repo.visibility)] OR " +
            "toLower(repo.description) CONTAINS toLower($filterBy) OR " +
            "EXISTS ((repo)-[:HAS_TOPIC]->(:TopicNode {topic: toLower($filterBy)}))) " +
            "RETURN repo")
    List<GithubRepo> findByFullNameContainingIgnoreCaseAndFilterBy(@Param("name") String fullName, @Param("filterBy") String filterBy);

    @Query("MATCH (repo:GithubRepo) WHERE repo.createdAt >= $startDate AND repo.createdAt <= $endDate RETURN repo")
    List<GithubRepo> findByCreatedAtBetween(@Param("startDate") String startDate, @Param("endDate") String endDate);

    @Query("MATCH (repo:GithubRepo) RETURN repo ORDER BY repo.stargazersCount DESC LIMIT 10")
    List<GithubRepo> findTop10ByOrderByStargazersCountDesc();

    @Query("MATCH (repo:GithubRepo) WHERE toLower(repo.description) CONTAINS toLower($keyword) RETURN repo")
    List<GithubRepo> findByDescriptionContaining(@Param("keyword") String keyword);

    @Query("MATCH (repo:GithubRepo {defaultBranch: $branch}) RETURN repo")
    List<GithubRepo> findByDefaultBranch(@Param("branch") String branch);

    @Query("MATCH (repo:GithubRepo {createdAt: $createdAt}) RETURN repo")
    List<GithubRepo> findByCreatedAt(@Param("createdAt") String createdAt);

    @Query("MATCH (repo:GithubRepo {updatedAt: $updatedAt}) RETURN repo")
    List<GithubRepo> findByUpdatedAt(@Param("updatedAt") String updatedAt);

    @Query("MATCH (repo:GithubRepo {pushedAt: $pushedAt}) RETURN repo")
    List<GithubRepo> findByPushedAt(@Param("pushedAt") String pushedAt);

    @Query(value = "MATCH (repo:GithubRepo {fullName: $fullName}) RETURN repo SKIP $skip LIMIT $limit",
            countQuery = "MATCH (repo:GithubRepo {fullName: $fullName}) RETURN count(repo)")
    Page<GithubRepo> findPageByFullName(@Param("fullName") String fullName, Pageable pageable);

    @Query("MATCH (repo:GithubRepo {isPrivate: $isPrivate}) RETURN repo")
    List<GithubRepo> findByIsPrivate(@Param("isPrivate") boolean isPrivate);

    @Query("MATCH (repo:GithubRepo {fork: $fork}) RETURN repo")
    List<GithubRepo> findByFork(@Param("fork") boolean fork);

    @Query("MATCH (repo:GithubRepo) WHERE repo.size >= $size RETURN repo")
    List<GithubRepo> findBySizeGreaterThanEqual(@Param("size") int size);

    @Query("MATCH (repo:GithubRepo) WHERE repo.stargazersCount >= $stargazersCount RETURN repo")
    List<GithubRepo> findByStargazersCountGreaterThanEqual(@Param("stargazersCount") int stargazersCount);

    @Query("MATCH (repo:GithubRepo) WHERE repo.watchersCount >= $watchersCount RETURN repo")
    List<GithubRepo> findByWatchersCountGreaterThanEqual(@Param("watchersCount") int watchersCount);

    @Query("MATCH (repo:GithubRepo) WHERE repo.hasIssues = $hasIssues RETURN repo")
    List<GithubRepo> findByHasIssues(@Param("hasIssues") boolean hasIssues);

    @Query("MATCH (repo:GithubRepo) WHERE repo.hasProjects = $hasProjects RETURN repo")
    List<GithubRepo> findByHasProjects(@Param("hasProjects") boolean hasProjects);

    @Query("MATCH (repo:GithubRepo) WHERE repo.hasDownloads = $hasDownloads RETURN repo")
    List<GithubRepo> findByHasDownloads(@Param("hasDownloads") boolean hasDownloads);

    @Query("MATCH (repo:GithubRepo) WHERE repo.hasWiki = $hasWiki RETURN repo")
    List<GithubRepo> findByHasWiki(@Param("hasWiki") boolean hasWiki);

    @Query("MATCH (repo:GithubRepo) WHERE repo.hasPages = $hasPages RETURN repo")
    List<GithubRepo> findByHasPages(@Param("hasPages") boolean hasPages);

    @Query("MATCH (repo:GithubRepo) WHERE repo.hasDiscussions = $hasDiscussions RETURN repo")
    List<GithubRepo> findByHasDiscussions(@Param("hasDiscussions") boolean hasDiscussions);

    @Query("MATCH (repo:GithubRepo) WHERE repo.forksCount >= $forksCount RETURN repo")
    List<GithubRepo> findByForksCountGreaterThanEqual(@Param("forksCount") int forksCount);

    @Query("MATCH (repo:GithubRepo) WHERE repo.archived = $archived RETURN repo")
    List<GithubRepo> findByArchived(@Param("archived") boolean archived);

    @Query("MATCH (repo:GithubRepo) WHERE repo.disabled = $disabled RETURN repo")
    List<GithubRepo> findByDisabled(@Param("disabled") boolean disabled);

    @Query("MATCH (repo:GithubRepo) WHERE repo.openIssuesCount >= $openIssuesCount RETURN repo")
    List<GithubRepo> findByOpenIssuesCountGreaterThanEqual(@Param("openIssuesCount") int openIssuesCount);

    @Query("MATCH (repo:GithubRepo) WHERE repo.isTemplate = $isTemplate RETURN repo")
    List<GithubRepo> findByIsTemplate(@Param("isTemplate") boolean isTemplate);

    @Query("MATCH (repo:GithubRepo) WHERE repo.webCommitSignoffRequired = $webCommitSignoffRequired RETURN repo")
    List<GithubRepo> findByWebCommitSignoffRequired(@Param("webCommitSignoffRequired") boolean webCommitSignoffRequired);

    @Query("MATCH (repo:GithubRepo) WHERE repo.visibility = $visibility RETURN repo")
    List<GithubRepo> findByVisibility(@Param("visibility") String visibility);




}

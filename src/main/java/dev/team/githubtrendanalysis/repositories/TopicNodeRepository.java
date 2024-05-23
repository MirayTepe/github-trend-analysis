package dev.team.githubtrendanalysis.repositories;

import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.models.TopicNode;
import dev.team.githubtrendanalysis.models.TopicRepoCount;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TopicNodeRepository extends Neo4jRepository<TopicNode, Long> {
    @Query("MATCH (repo:GithubRepo)-[:HAS_TOPIC]->(topic:TopicNode) RETURN topic.topic AS topic, COUNT(repo) AS repoCount DESC")
    List<TopicRepoCount> countReposByTopic();

    @Query("MATCH (repo:GithubRepo)-[:HAS_TOPIC]->(topic:TopicNode {topic: $topic}) RETURN repo")
    List<GithubRepo> findByTopics_Topic(@Param("topic") String topic);
}
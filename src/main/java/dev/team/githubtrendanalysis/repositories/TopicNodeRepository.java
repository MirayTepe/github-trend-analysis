package dev.team.githubtrendanalysis.repositories;

import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.models.TopicNode;
import dev.team.githubtrendanalysis.models.TopicRepoCount;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TopicNodeRepository extends Neo4jRepository<TopicNode, Long> {
    Optional<TopicNode> findByTopic(String topic);
    @Query("MATCH (t:Topic)<-[:HAS_TOPIC]-(r:GithubRepo) WHERE t.topic = $topic RETURN count(r)")
    int countRepositoriesByTopic(String topic);
    @Query("MATCH (repo:GithubRepo)-[:HAS_TOPIC]->(topic:TopicNode) " +
            "RETURN topic.topic AS topic, COUNT(repo) AS repoCount " +
            "ORDER BY repoCount DESC " +
            "LIMIT 25")
    List<TopicRepoCount> countReposByTopic();

    @Query("MATCH (repo:GithubRepo)-[:HAS_TOPIC]->(topic:TopicNode {topic: $topic}) RETURN repo")
    List<GithubRepo> findByTopics_Topic(@Param("topic") String topic);
}
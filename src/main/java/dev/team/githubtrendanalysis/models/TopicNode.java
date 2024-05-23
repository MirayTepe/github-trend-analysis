package dev.team.githubtrendanalysis.models;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Node("TopicNode")
public class TopicNode {
    @Id
    @GeneratedValue
    @Index(unique = true)
    private Long id;
    private String topic;
    public TopicNode(String topic) {
        this.topic = topic;
    }



}

package dev.team.githubtrendanalysis.models;
import org.neo4j.ogm.annotation.Index;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Node;


@Data
@NoArgsConstructor
@AllArgsConstructor

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

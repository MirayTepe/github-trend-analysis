package dev.team.githubtrendanalysis.models;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Index;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.RelationshipProperties;
import org.springframework.data.neo4j.core.schema.TargetNode;

@RelationshipProperties
@Data
@NoArgsConstructor
@AllArgsConstructor
public class OriginalRepoFork {
    @Id
    @GeneratedValue
    private Long id;
    @TargetNode
    private ForksData forkedRepo;

    public OriginalRepoFork(ForksData forkedRepo) {
        this.forkedRepo = forkedRepo;
    }
}

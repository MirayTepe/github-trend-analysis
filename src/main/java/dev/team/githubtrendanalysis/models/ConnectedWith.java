package dev.team.githubtrendanalysis.models;
import dev.team.githubtrendanalysis.models.GithubRepo;
import lombok.AllArgsConstructor;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Relationship;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ConnectedWith {

    @Relationship(type = "CONNECTED_WITH", direction = Relationship.Direction.OUTGOING)
    private GithubRepo sourceRepo;

    @Relationship(type = "CONNECTED_WITH", direction = Relationship.Direction.INCOMING)
    private GithubRepo targetRepo;
}

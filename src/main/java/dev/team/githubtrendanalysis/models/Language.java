package dev.team.githubtrendanalysis.models;

import lombok.*;
import org.neo4j.ogm.annotation.Index;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;

@Node("Language")
@Data
@NoArgsConstructor

public class Language {

    @Id
    @Index(unique = true)
    @GeneratedValue
    private Long id;
    private String name;

    public Language(String name) {
        this.name = name;
    }
}

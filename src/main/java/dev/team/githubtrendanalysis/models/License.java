package dev.team.githubtrendanalysis.models;

import lombok.*;

import org.neo4j.ogm.annotation.GeneratedValue;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;


import java.util.List;


@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class License {

    @Id
    @GeneratedValue
    @Index(unique = true)
    private Long id;

    @Property(name = "key")
    private String key;

    @Property(name = "name")
    private String name;

    @Property(name = "spdx_id")
    private String spdxId;

    @Property(name = "url")
    private String url;

    @Property(name = "node_id")
    private String nodeId;

    @Property(name = "html_url")
    private String htmlUrl;

    @Property(name = "description")
    private String description;

    @Property(name = "implementation")
    private String implementation;

    @Property(name = "permissions")

    private List<String> permissions;

    @Property(name = "conditions")

    private List<String> conditions;

    @Property(name = "limitations")

    private List<String> limitations;

    @Property(name = "body")
    private String body;

    @Property(name = "featured")
    private boolean featured;

    @Relationship(type = "HAS_LICENSE", direction = Relationship.Direction.INCOMING)
    private List<GithubRepo> repositories;
}
package dev.team.githubtrendanalysis.models;
import org.neo4j.ogm.annotation.Index;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Node;

import java.util.List;


@Node("License")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class License {
    @Id
    @GeneratedValue
    @Index(unique = true)
    private Long id;
    private String key;
    private String name;
    private String spdx_id;
    private String url;
    private String node_id;
    private String html_url;
    private String description;
    private String implementation;
    private List<String> permissions;
    private List<String> conditions;
    private List<String> limitations;
    private String body;
    private boolean featured;
}

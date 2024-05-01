package dev.team.githubtrendanalysis.models;

import dev.team.githubtrendanalysis.objects.Owner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Index;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Node
public class GithubRepo {

    @Id
    @Index(unique = true)
    private Long id;

    private String node_id;

    private String name;

    @Property(name = "full_name")
    private String fullName;

    @Property(name = "private")
    private Boolean isPrivate;

    @Relationship(type = "OWNED_BY")
    private Owner owner;

    @Relationship(type = "HAS_LICENSE")
    private License license;

    private String html_url;

    private String description;

    private Boolean fork;

    private String url;

    private String git_url;

    private Integer size;

    private Integer stargazers_count;

    private Integer watchers_count;

    @Relationship(type = "WRITTEN_IN")
    private Language language;

    private Boolean has_issues;

    private Boolean has_projects;

    private Boolean has_downloads;

    private Boolean has_wiki;

    private Boolean has_pages;

    private Boolean has_discussions;

    private Integer forks_count;

    private Boolean archived;

    private Boolean disabled;

    private Integer open_issues_count;

    private Boolean allow_forking;

    private Boolean is_template;

    private Boolean web_commit_signoff_required;

    private String visibility;

    private Integer forks;

    private Integer open_issues;

    private Integer watchers;

    private String default_branch;

    private Double score;

    @Relationship(type = "HAS_TOPIC")
    private List<TopicNode> topics;

    private String created_at;

    private String updated_at;

    private String pushed_at;


}

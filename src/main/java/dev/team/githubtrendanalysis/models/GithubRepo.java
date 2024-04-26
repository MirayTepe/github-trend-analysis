package dev.team.githubtrendanalysis.models;

import dev.team.githubtrendanalysis.objects.Owner;
import lombok.*;
import org.neo4j.ogm.annotation.Relationship;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

import java.util.List;


@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class GithubRepo {

    @Id
    private Long id;

    private String node_id;

    private String name;

    private String full_name;
    @Property (name = "private")
    private Boolean _private;

    @Relationship(type = "OWNED_BY" , direction = Relationship.Direction.OUTGOING)
    private Owner owner;

    @Relationship(type = "HAS_LICENSE", direction = Relationship.Direction.OUTGOING)
    private License license;



    private String html_url;

    private String description;

    private Boolean fork;

    private String url;

    private String forks_url;

    private String collaborators_url;

    private String teams_url;

    private String hooks_url;

    private String issue_events_url;

    private String events_url;

    private String assignees_url;

    private String branches_url;

    private String tags_url;

    private String blobs_url;

    private String git_tags_url;

    private String git_refs_url;

    private String trees_url;

    private String statuses_url;

    private String languages_url;

    private String stargazers_url;

    private String contributors_url;

    private String subscribers_url;

    private String subscription_url;

    private String commits_url;

    private String git_commits_url;

    private String comments_url;

    private String issue_comment_url;

    private String contents_url;

    private String compare_url;

    private String merges_url;

    private String archive_url;

    private String downloads_url;

    private String issues_url;

    private String pulls_url;

    private String milestones_url;

    private String notifications_url;

    private String labels_url;

    private String releases_url;

    private String deployments_url;

    private String created_at;

    private String updated_at;

    private String pushed_at;

    private String git_url;

    private String ssh_url;

    private String clone_url;

    private String svn_url;

    private Integer size;

    private Integer stargazers_count;

    private Integer watchers_count;

    @Relationship(type = "WRITTEN_IN", direction = Relationship.Direction.OUTGOING)
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

    private List<String> topics;

    private String visibility;

    private Integer forks;

    private Integer open_issues;

    private Integer watchers;

    private String default_branch;

    private Double score;






}

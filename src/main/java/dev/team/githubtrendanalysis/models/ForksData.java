package dev.team.githubtrendanalysis.models;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import dev.team.githubtrendanalysis.objects.Owner;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Index;
import org.springframework.data.neo4j.core.schema.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class ForksData {
    @Id
    @Index(unique = true)
    private Long id;
    private String keys_url;
    private String node_id;
    private String collaborators_url;
    private String teams_url;
    private String hooks_url;
    private String assignees_url;
    private String name;
    private String issue_events_url;
    private String full_name;
    private String events_url;
    private String issue_comment_url;
    private  String contents_url;
    private  String compare_url;
    @JsonProperty("private")
    private Boolean isRepositoryPrivate;
    private String branches_url;
    private String tags_url;
    private String blobs_url;
    private String git_tags_url;
    private String git_refs_url;
    private String trees_url;
    private String statuses_url;
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
    private Integer  watchers_count;
    private String stargazers_url;
    private String contributors_url;
    private String subscribers_url;
    private String subscription_url;
    private String commits_url;
    private String merges_url;
    private String archive_url;
    private String git_commits_url;
    private String comments_url;
    private String downloads_url;
    private String issues_url;
    private String pulls_url;
    private String milestones_url;
    private String notifications_url;
    private String releases_url;
    private String deployments_url;
    private String ssh_url;
    private String clone_url;
    private String homepage;
    private String svn_url;
    @Relationship(type = "WRITTEN_IN")
    private Language language;
    private Language languages_url;
    private Language labels_url;
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
    private String forks_url;
    private Double score;
    @Relationship(type = "HAS_TOPIC")
    private List<TopicNode> topics;
    private String created_at;
    private String updated_at;
    private String pushed_at;

}
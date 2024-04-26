package dev.team.githubtrendanalysis.queryresults;
import dev.team.githubtrendanalysis.models.GithubRepo;
import lombok.Data;
import java.util.List;


@Data
public class RepositoryResult {
    private Integer total_count;
    private Boolean incomplete_results;
    private List<GithubRepo> items;

    // Getter ve Setter'lar
}
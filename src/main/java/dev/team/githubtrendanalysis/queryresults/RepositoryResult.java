package dev.team.githubtrendanalysis.queryresults;
import dev.team.githubtrendanalysis.models.Repository;
import lombok.Data;
import java.util.List;


@Data
public class RepositoryResult {
    private Integer total_count;
    private Boolean incomplete_results;
    private List<Repository> items;

    // Getter ve Setter'lar
}
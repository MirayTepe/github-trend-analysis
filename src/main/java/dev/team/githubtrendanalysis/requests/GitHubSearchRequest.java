package dev.team.githubtrendanalysis.requests;

import lombok.Data;

@Data
public class GitHubSearchRequest {
    private String query;
    private String sortType;
    private String sortOrder;
    private int page;

    public boolean isValidSortType() {
        return sortType != null && (sortType.equals("stars") || sortType.equals("forks") || sortType.equals("updated"));
    }

    public boolean isValidSortOrder() {
        return sortOrder != null && (sortOrder.equals("asc") || sortOrder.equals("desc"));
    }
}
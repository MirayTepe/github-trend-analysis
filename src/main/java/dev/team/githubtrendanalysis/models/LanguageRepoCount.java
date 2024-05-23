package dev.team.githubtrendanalysis.models;

public class LanguageRepoCount {
    private String language;
    private long repoCount;

    // Getters and setters
    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public long getRepoCount() {
        return repoCount;
    }

    public void setRepoCount(long repoCount) {
        this.repoCount = repoCount;
    }
}

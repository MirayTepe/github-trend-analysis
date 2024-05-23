package dev.team.githubtrendanalysis.models;

public class TopicRepoCount {
    private String topic;
    private long repoCount;

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public long getRepoCount() {
        return repoCount;
    }

    public void setRepoCount(long repoCount) {
        this.repoCount = repoCount;
    }
}

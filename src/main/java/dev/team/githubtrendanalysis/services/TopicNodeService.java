package dev.team.githubtrendanalysis.services;

import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.models.TopicRepoCount;
import dev.team.githubtrendanalysis.repositories.TopicNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class TopicNodeService {

    @Autowired
    private TopicNodeRepository topicRepository;

    public int countRepositoriesByTopic(String topic) {
        return topicRepository.countRepositoriesByTopic(topic);
    }

    public List<TopicRepoCount> getRepoCountByTopic() {
        return topicRepository.countReposByTopic();
    }

    public List<GithubRepo> getGithubReposByTopic(String topic) {
        return topicRepository.findByTopics_Topic(topic);
    }
}

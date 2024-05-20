package dev.team.githubtrendanalysis.services;

import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.models.TopicRepoCount;
import dev.team.githubtrendanalysis.repositories.LicenseRepository;
import dev.team.githubtrendanalysis.repositories.TopicRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Service
public class TopicService {

    @Autowired
    private TopicRepository topicRepository;

    public TopicService(TopicRepository topicRepository) {
        this.topicRepository = topicRepository;
    }

    public Map<String, Long> getRepoCountByTopic() {
        List<TopicRepoCount> topicDataList = topicRepository.countReposByTopic();
        return groupByTopic(topicDataList);
    }

    private Map<String, Long> groupByTopic(List<TopicRepoCount> topicDataList) {
        Map<String, Long> topicMap = new HashMap<>();
        for (TopicRepoCount topicData : topicDataList) {
            topicMap.put(topicData.getTopic(), topicData.getRepoCount());
        }
        return topicMap;
    }

    public List<GithubRepo> getGithubReposByTopic(String topic) {
        return topicRepository.findByTopics_Topic(topic);
    }
}

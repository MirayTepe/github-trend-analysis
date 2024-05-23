package dev.team.githubtrendanalysis.controllers;

import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.services.TopicNodeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1")
public class TopicNodeController {

    private TopicNodeService topicService;

    public TopicNodeController(TopicNodeService topicService) {
        this.topicService = topicService;
    }

    @GetMapping("/count-by-topic")
    public Map<String, Long> getRepoCountByTopic() {
        return topicService.getRepoCountByTopic();
    }

    @GetMapping("/topic/{topic}")
    public List<GithubRepo> getReposByTopic(@PathVariable String topic) {
        return topicService.getGithubReposByTopic(topic);
    }
}

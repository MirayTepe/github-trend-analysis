package dev.team.githubtrendanalysis.services;
import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.models.Language;
import dev.team.githubtrendanalysis.models.License;
import dev.team.githubtrendanalysis.models.TopicNode;
import dev.team.githubtrendanalysis.repositories.GithubRepoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class GithubRepoService {

    @Autowired
    private GithubRepoRepository githubRepoRepository;

    @Autowired
    private MetadataService metadataService;

    public GithubRepo saveGithubRepo(GithubRepo githubRepo) {
        // Check and set language
        if (githubRepo.getLanguage() != null) {
            Language language = metadataService.findOrCreateLanguage(githubRepo.getLanguage().getName());
            githubRepo.setLanguage(language);
        }

        // Check and set license
        if (githubRepo.getLicense() != null) {
            License license = metadataService.findOrCreateLicense(githubRepo.getLicense().getName());
            githubRepo.setLicense(license);
        }

        // Check and set topics
        if (githubRepo.getTopics() != null && !githubRepo.getTopics().isEmpty()) {
            List<TopicNode> topics = new ArrayList<>();
            for (TopicNode topic : githubRepo.getTopics()) {
                TopicNode existingTopic = metadataService.findOrCreateTopic(topic.getTopic());
                topics.add(existingTopic);
            }
            githubRepo.setTopics(topics);
        }

        return githubRepoRepository.save(githubRepo);
    }
}


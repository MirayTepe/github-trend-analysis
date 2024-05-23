package dev.team.githubtrendanalysis.services;

import dev.team.githubtrendanalysis.models.Language;
import dev.team.githubtrendanalysis.models.License;
import dev.team.githubtrendanalysis.models.TopicNode;
import dev.team.githubtrendanalysis.repositories.LanguageRepository;
import dev.team.githubtrendanalysis.repositories.LicenseRepository;
import dev.team.githubtrendanalysis.repositories.TopicNodeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MetadataService {

    @Autowired
    private LanguageRepository languageRepository;

    @Autowired
    private LicenseRepository licenseRepository;

    @Autowired
    private TopicNodeRepository topicNodeRepository;

    public Language findOrCreateLanguage(String name) {
        return languageRepository.findByName(name)
                .orElseGet(() -> languageRepository.save(new Language(name)));
    }

    public License findOrCreateLicense(String name) {
        return licenseRepository.findByName(name)
                .orElseGet(() -> licenseRepository.save(new License(name)));
    }

    public TopicNode findOrCreateTopic(String topic) {
        return topicNodeRepository.findByTopic(topic)
                .orElseGet(() -> topicNodeRepository.save(new TopicNode(topic)));
    }
}
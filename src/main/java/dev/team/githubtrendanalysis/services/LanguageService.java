package dev.team.githubtrendanalysis.services;

import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.models.Language;
import dev.team.githubtrendanalysis.models.LanguageRepoCount;
import dev.team.githubtrendanalysis.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    public int countRepositoriesByLanguage(String languageName) {
        return languageRepository.countRepositoriesByLanguage(languageName);
    }

    public List<GithubRepo> getGithubReposByLanguage(String languageName) {
        return languageRepository.findByLanguage_Name(languageName);
    }

    public List<LanguageRepoCount> getRepoCountByLanguage() {
        return languageRepository.countReposByLanguage();
    }


}
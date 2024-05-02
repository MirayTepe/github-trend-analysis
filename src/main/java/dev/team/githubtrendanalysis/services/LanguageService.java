package dev.team.githubtrendanalysis.services;

import dev.team.githubtrendanalysis.models.Language;
import dev.team.githubtrendanalysis.repositories.LanguageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LanguageService {

    @Autowired
    private LanguageRepository languageRepository;

    public int countRepositoriesByLanguage(String languageName) {
        return languageRepository.countRepositoriesByLanguage(languageName);
    }
}
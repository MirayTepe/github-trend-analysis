package dev.team.githubtrendanalysis.controllers;

import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.models.LanguageRepoCount;
import dev.team.githubtrendanalysis.repositories.LanguageRepository;
import dev.team.githubtrendanalysis.services.GitHubService;
import dev.team.githubtrendanalysis.services.LanguageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/api/v1")
public class LanguageController {

    @Autowired
    private LanguageService languageService;

    @GetMapping("/repo-counts")
    public List<LanguageRepoCount> getRepoCountByLanguage() {
        return languageService.getRepoCountByLanguage();
    }

    @GetMapping("/language/{languageName}")
    public List<GithubRepo> getReposByLanguageName(@PathVariable String languageName) {
        return languageService.getGithubReposByLanguage(languageName);
    }
}

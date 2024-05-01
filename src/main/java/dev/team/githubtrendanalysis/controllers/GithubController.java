package dev.team.githubtrendanalysis.controllers;

import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.services.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
public class GithubController {

    @Autowired
    private GitHubService githubService;

    @GetMapping("/fetch-and-save")
    public ResponseEntity<String> fetchAndSaveData(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        try {
            githubService.fetchAllDataAndSaveToNeo4j(startDate, endDate);
            return ResponseEntity.ok("Veriler başarıyla çekildi ve Neo4j'e kaydedildi.");
        } catch (InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Bir hata oluştu: " + e.getMessage());
        }
    }



    @GetMapping("/getAllGithubRepos")
    public ResponseEntity<List<GithubRepo>> getAllGithubRepos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size
    ) {
        List<GithubRepo> repos = githubService.getGithubReposByPageAndSize(page, size);
        return ResponseEntity.ok(repos);
    }



    @GetMapping("/getFilteredGithubRepos")
    public ResponseEntity<List<GithubRepo>> getFilteredGithubRepos(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        List<GithubRepo> repos = githubService.getFilteredRepos(startDate, endDate);
        return ResponseEntity.ok(repos);
    }

    @GetMapping("/getBatchGithubRepos")
    public ResponseEntity<List<GithubRepo>> getBatchGithubRepos(
            @RequestParam(defaultValue = "20") int batchSize
    ) {
        List<GithubRepo> repos = githubService.getBatchRepos(batchSize);
        return ResponseEntity.ok(repos);
    }
}

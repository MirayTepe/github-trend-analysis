package dev.team.githubtrendanalysis.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.services.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class GithubController {

    @Autowired
    private GitHubService githubService;

    @GetMapping("/fetch-and-save")
    public ResponseEntity<String> fetchAndSaveData(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) throws ExecutionException, InterruptedException {
        Future<Void> future = githubService.fetchAllDataAndSaveToNeo4j(startDate, endDate);

        // Asenkron işlemin tamamlanmasını bekleyin
        future.get();

        return ResponseEntity.ok("Veriler başarıyla çekildi ve Neo4j'e kaydedildi.");
    }
    @GetMapping("/repos")
    public ResponseEntity<List<GithubRepo>> getAllGithubRepos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "50") int size
    ) {
        Pageable pageable = PageRequest.of(page, size);
        Page<GithubRepo> repos = githubService.getAllGithubRepos(pageable);
        return new ResponseEntity<>(repos.getContent(), HttpStatus.OK);
    }
    @GetMapping("/reposs")
    public ResponseEntity<List<GithubRepo>> getFilteredGithubRepos(
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate
    ) {
        List<GithubRepo> repos = githubService.getFilteredRepos(startDate, endDate);
        return new ResponseEntity<>(repos, HttpStatus.OK);
    }

    @GetMapping("/reposss")
    public ResponseEntity<List<GithubRepo>> getBatchGithubRepos(
            @RequestParam(defaultValue = "0") int batchSize
    ) {
        List<GithubRepo> repos = githubService.getBatchRepos(batchSize);
        return new ResponseEntity<>(repos, HttpStatus.OK);
    }





}

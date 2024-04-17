package dev.team.githubtrendanalysis.controllers;

import dev.team.githubtrendanalysis.services.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

@RestController
public class GithubController {
    @Autowired
    private GitHubService gitHubService;

    @GetMapping("/fetch-and-save")
    public ResponseEntity<String> fetchAndSaveData() throws ExecutionException, InterruptedException {
        Future<Void> future = gitHubService.fetchAllDataAndSaveToNeo4j();

        // Asenkron işlemin tamamlanmasını bekleyin
        future.get();

        return ResponseEntity.ok("Veriler başarıyla çekildi ve Neo4j'e kaydedildi.");
    }
}

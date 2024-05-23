package dev.team.githubtrendanalysis.controllers;

import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.services.GitHubService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/repo")
public class GithubController {

    @Autowired
    private GitHubService gitHubService;

    public GithubController(GitHubService gitHubService) {
        this.gitHubService = gitHubService;
    }

    @GetMapping("/fetch-and-save")
    public ResponseEntity<String> fetchAndSaveData(
            @RequestParam LocalDate startDate,
            @RequestParam LocalDate endDate
    ) {
        try {
            gitHubService.fetchAllDataAndSaveToNeo4j(startDate, endDate);
            return ResponseEntity.ok("Veriler başarıyla çekildi ve Neo4j'e kaydedildi.");
        } catch (InterruptedException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Bir hata oluştu: " + e.getMessage());
        }
    }


    @GetMapping("/createdat/range")
    public List<GithubRepo> getReposByCreatedAtBetween(@RequestParam LocalDate startDate, @RequestParam LocalDate endDate) {
        return gitHubService.getFilteredRepos(startDate, endDate);
    }

    @GetMapping("/page")
    public ResponseEntity<Map<String, Object>> getAllGithubRepos(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {

        Map<String, Object> response = gitHubService.getGithubReposByPageAndSize(page, size);
        List<GithubRepo> repos = (List<GithubRepo>) response.get("repos");

        for (GithubRepo repo : repos) {
            String description = repo.getDescription();
            if (description != null) {
                repo.setDescription(EmojiConverter.convertToEmoji(description));
            }
        }

        return ResponseEntity.ok(response);
    }

    @GetMapping("/search")
    public ResponseEntity<List<GithubRepo>> searchRepositories(
            @RequestParam(required = false) String query,
            @RequestParam(required = false) String filterBy,
            @RequestParam(required = false, defaultValue = "false") boolean isPrivate,
            @RequestParam(required = false, defaultValue = "false") boolean fork,
            @RequestParam(required = false) List<String> languageNames,
            @RequestParam(required = false) List<String> ownerLogins,
            @RequestParam(required = false) List<String> licenseNames,
            @RequestParam(required = false) List<String> topics,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        List<GithubRepo> repositories = gitHubService.searchRepositories(query, filterBy, isPrivate, fork, languageNames, ownerLogins, licenseNames, topics, page, size);

        if (repositories.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }

        for (GithubRepo repo : repositories) {
            String description = repo.getDescription();
            if (description != null) {
                repo.setDescription(EmojiConverter.convertToEmoji(description));
            }
        }

        return new ResponseEntity<>(repositories, HttpStatus.OK);
    }


    @GetMapping("/top10stargazers")
    public List<GithubRepo> getTop10ByStargazersCountDesc() {
        return gitHubService.findTop10ByOrderByStargazersCountDesc();
    }


    @GetMapping("/forks")
    public List<GithubRepo> getReposByFork(@RequestParam boolean isFork) {
        return gitHubService.getGithubReposByFork(isFork);
    }

    @GetMapping("/archived")
    public List<GithubRepo> getReposByArchived(@RequestParam boolean isArchived) {
        return gitHubService.getGithubReposByArchived(isArchived);
    }

    @GetMapping("/private")
    public List<GithubRepo> getReposByPrivate(@RequestParam boolean isPrivate) {
        return gitHubService.getGithubReposByPrivate(isPrivate);
    }

    @GetMapping("/description")
    public List<GithubRepo> getReposByDescriptionContaining(@RequestParam String keyword) {
        return gitHubService.getGithubReposByDescriptionContaining(keyword);
    }


    @GetMapping("/size")
    public List<GithubRepo> getReposBySizeGreaterThan(@RequestParam int size) {
        return gitHubService.getGithubReposBySizeGreaterThan(size);
    }

    @GetMapping("/stargazers")
    public List<GithubRepo> getReposByStargazersCountGreaterThan(@RequestParam int count) {
        return gitHubService.getGithubReposByStargazersCountGreaterThan(count);
    }

    @GetMapping("/watchers")
    public List<GithubRepo> getReposByWatchersCountGreaterThan(@RequestParam int count) {
        return gitHubService.getGithubReposByWatchersCountGreaterThan(count);
    }

    @GetMapping("/openissues")
    public List<GithubRepo> getReposByOpenIssuesCountGreaterThan(@RequestParam int count) {
        return gitHubService.getGithubReposByOpenIssuesCountGreaterThan(count);
    }

    @GetMapping("/defaultbranch")
    public List<GithubRepo> getReposByDefaultBranch(@RequestParam String branch) {
        return gitHubService.getGithubReposByDefaultBranch(branch);
    }

    @GetMapping("/visibility")
    public List<GithubRepo> getReposByVisibility(@RequestParam String visibility) {
        return gitHubService.getGithubReposByVisibility(visibility);
    }

    @GetMapping("/createdat")
    public List<GithubRepo> getReposByCreatedAt(@RequestParam String createdAt) {
        return gitHubService.getGithubReposByCreatedAt(createdAt);
    }

    @GetMapping("/updatedat")
    public List<GithubRepo> getReposByUpdatedAt(@RequestParam String updatedAt) {
        return gitHubService.getGithubReposByUpdatedAt(updatedAt);
    }

    @GetMapping("/pushedat")
    public List<GithubRepo> getReposByPushedAt(@RequestParam String pushedAt) {
        return gitHubService.getGithubReposByPushedAt(pushedAt);
    }

    @GetMapping("/webcommitsignoffrequired")
    public List<GithubRepo> getReposByWebCommitSignoffRequired(@RequestParam boolean required) {
        return gitHubService.getGithubReposByWebCommitSignoffRequired(required);
    }

    @GetMapping("/template")
    public List<GithubRepo> getReposByIsTemplate(@RequestParam boolean isTemplate) {
        return gitHubService.getGithubReposByIsTemplate(isTemplate);
    }



    @PostMapping("/repo")
    public ResponseEntity<GithubRepo> saveRepo(@RequestBody GithubRepo githubRepo) {
        GithubRepo savedRepo = gitHubService.saveGithubRepo(githubRepo);
        return ResponseEntity.ok(savedRepo);
    }

    @DeleteMapping("/repo/{id}")
    public ResponseEntity<Void> deleteRepoById(@PathVariable Long id) {
        gitHubService.deleteGithubRepoById(id);
        return ResponseEntity.noContent().build();
    }
}

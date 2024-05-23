package dev.team.githubtrendanalysis.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.queryresults.RepositoryResult;
import dev.team.githubtrendanalysis.repositories.GithubRepoRepository;
import dev.team.githubtrendanalysis.requests.GitHubSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.Future;

@Service
public class GitHubService {

    @Autowired
    private GithubRepoRepository gitHubRepoRepository;

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private GithubRepoService githubRepoService;

    @Value("${http.client.base-url}")
    private String baseUrl;

    @Value("${github.api.token}")
    private String githubApiToken;

    @Autowired
    private ObjectMapper objectMapper;

    private static final int TOTAL_PAGES = 3;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public Future<Void> fetchAllDataAndSaveToNeo4j(LocalDate startDate, LocalDate endDate) throws InterruptedException {
        LocalDate currentDate = startDate;

        while (currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) {
            for (int page = 1; page <= TOTAL_PAGES; page++) {
                GitHubSearchRequest request = createGitHubSearchRequest(currentDate, page);
                RepositoryResult result = fetchDataFromGitHub(request);

                if (result != null && result.getItems() != null) {
                    List<GithubRepo> repositories = result.getItems();
                    for (GithubRepo repo : repositories) {
                        githubRepoService.saveGithubRepo(repo);
                    }
                    Thread.sleep(1000);
                }
            }
            currentDate = currentDate.plusDays(1);
        }
        return null;
    }

    private GitHubSearchRequest createGitHubSearchRequest(LocalDate date, int page) {
        GitHubSearchRequest request = new GitHubSearchRequest();
        request.setQuery("created:" + date.format(dateFormatter));
        request.setSortType("stars");
        request.setSortOrder("desc");
        request.setPage(page);

        return request;
    }

    private RepositoryResult fetchDataFromGitHub(GitHubSearchRequest request) {
        WebClient webClient = webClientBuilder.baseUrl(baseUrl)
                .defaultHeader("Authorization", githubApiToken)
                .build();

        return webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", request.getQuery())
                        .queryParam("sort", request.getSortType())
                        .queryParam("order", request.getSortOrder())
                        .queryParam("page", request.getPage())
                        .build())
                .retrieve()
                .bodyToMono(RepositoryResult.class)
                .block();
    }

    public Page<GithubRepo> findGithubReposByFullName(String fullName, Pageable pageable) {
        return gitHubRepoRepository.findPageByFullName(fullName, pageable);
    }

    public List<GithubRepo> getAllGithubRepos() {
        return gitHubRepoRepository.findAll();
    }

    public Optional<GithubRepo> getGithubRepoById(Long id) {
        return gitHubRepoRepository.findById(id);
    }

    public List<GithubRepo> getGithubReposByFork(boolean isFork) {
        return gitHubRepoRepository.findByFork(isFork);
    }

    public List<GithubRepo> getGithubReposByArchived(boolean isArchived) {
        return gitHubRepoRepository.findByArchived(isArchived);
    }

    public List<GithubRepo> getGithubReposByPrivate(boolean isPrivate) {
        return gitHubRepoRepository.findByIsPrivate(isPrivate);
    }

    public List<GithubRepo> getGithubReposByDescriptionContaining(String keyword) {
        return gitHubRepoRepository.findByDescriptionContaining(keyword);
    }

    public List<GithubRepo> getGithubReposBySizeGreaterThan(int size) {
        return gitHubRepoRepository.findBySizeGreaterThanEqual(size);
    }

    public List<GithubRepo> getGithubReposByStargazersCountGreaterThan(int count) {
        return gitHubRepoRepository.findByStargazersCountGreaterThanEqual(count);
    }

    public List<GithubRepo> getGithubReposByWatchersCountGreaterThan(int count) {
        return gitHubRepoRepository.findByWatchersCountGreaterThanEqual(count);
    }

    public List<GithubRepo> getGithubReposByOpenIssuesCountGreaterThan(int count) {
        return gitHubRepoRepository.findByOpenIssuesCountGreaterThanEqual(count);
    }

    public List<GithubRepo> getGithubReposByDefaultBranch(String branch) {
        return gitHubRepoRepository.findByDefaultBranch(branch);
    }

    public List<GithubRepo> getGithubReposByVisibility(String visibility) {
        return gitHubRepoRepository.findByVisibility(visibility);
    }

    public List<GithubRepo> getGithubReposByCreatedAt(String createdAt) {
        return gitHubRepoRepository.findByCreatedAt(createdAt);
    }

    public List<GithubRepo> getGithubReposByUpdatedAt(String updatedAt) {
        return gitHubRepoRepository.findByUpdatedAt(updatedAt);
    }

    public List<GithubRepo> getGithubReposByPushedAt(String pushedAt) {
        return gitHubRepoRepository.findByPushedAt(pushedAt);
    }

    public List<GithubRepo> getGithubReposByWebCommitSignoffRequired(boolean required) {
        return gitHubRepoRepository.findByWebCommitSignoffRequired(required);
    }

    public List<GithubRepo> getGithubReposByIsTemplate(boolean isTemplate) {
        return gitHubRepoRepository.findByIsTemplate(isTemplate);
    }

    public GithubRepo saveGithubRepo(GithubRepo githubRepo) {
        return githubRepoService.saveGithubRepo(githubRepo);
    }

    public List<GithubRepo> saveAllGithubRepos(List<GithubRepo> githubRepos) {
        return gitHubRepoRepository.saveAll(githubRepos);
    }

    public void deleteGithubRepoById(Long id) {
        gitHubRepoRepository.deleteById(id);
    }

    public List<GithubRepo> getFilteredRepos(LocalDate startDate, LocalDate endDate) {
        return gitHubRepoRepository.findByCreatedAtBetween(startDate.toString(), endDate.toString());
    }

    public Map<String, Object> getGithubReposByPageAndSize(int page, int size) {
        int skip = page * size;
        List<GithubRepo> repos = gitHubRepoRepository.findGithubReposByPageAndSize(skip, size);
        long totalCount = gitHubRepoRepository.count();
        int totalPages = (int) Math.ceil((double) totalCount / size);

        Map<String, Object> response = new HashMap<>();
        response.put("repos", repos);
        response.put("totalPages", totalPages);

        return response;
    }

    public List<GithubRepo> searchRepositories(String query, String filterBy, boolean isPrivate, boolean fork,
                                               List<String> languageNames, List<String> ownerLogins,
                                               List<String> licenseNames, List<String> topics,
                                               int page, int size) {
        int skip = page * size;
        return gitHubRepoRepository.searchGithubReposByPageAndSize(query, filterBy, isPrivate, fork,
                languageNames, ownerLogins, licenseNames, topics, skip, size);
    }

    public List<GithubRepo> findTop10ByOrderByStargazersCountDesc() {
        return gitHubRepoRepository.findTop10ByOrderByStargazersCountDesc();
    }
}

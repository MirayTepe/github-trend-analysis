package dev.team.githubtrendanalysis.services;

import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.repositories.GithubRepoRepository;
import dev.team.githubtrendanalysis.queryresults.RepositoryResult;
import dev.team.githubtrendanalysis.requests.GitHubSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

@Service
public class GitHubService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private GithubRepoRepository githubRepoRepository;

    @Value("${http.client.base-url}")
    private String baseUrl;

    @Value("${github.api.token}")
    private String githubApiToken;

    private static final int TOTAL_PAGES = 10;
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public GitHubService(GithubRepoRepository githubRepoRepository) {
        this.githubRepoRepository = githubRepoRepository;
    }

    public Future<Void> fetchAllDataAndSaveToNeo4j(LocalDate startDate, LocalDate endDate) throws InterruptedException {
        LocalDate currentDate = startDate;

        while (currentDate.isBefore(endDate) || currentDate.isEqual(endDate)) {
            for (int page = 1; page <= TOTAL_PAGES; page++) {
                GitHubSearchRequest request = createGitHubSearchRequest(currentDate, page);
                RepositoryResult result = fetchDataFromGitHub(request);

                if (result != null && result.getItems() != null) {
                    List<GithubRepo> repositories = result.getItems();
                    githubRepoRepository.saveAll(repositories);
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
        return githubRepoRepository.findPageByFullName(fullName, pageable);
    }
    public List<GithubRepo> getAllGithubRepos() {
        return githubRepoRepository.findAll();
    }

    public Optional<GithubRepo> getGithubRepoById(Long id) {
        return githubRepoRepository.findById(id);
    }


    public List<GithubRepo> getGithubReposByFork(boolean isFork) {
        return githubRepoRepository.findByFork(isFork);
    }

    public List<GithubRepo> getGithubReposByArchived(boolean isArchived) {
        return githubRepoRepository.findByArchived(isArchived);
    }

    public List<GithubRepo> getGithubReposByPrivate(boolean isPrivate) {
        return githubRepoRepository.findByIsPrivate(isPrivate);
    }

    public List<GithubRepo> getGithubReposByDescriptionContaining(String keyword) {
        return githubRepoRepository.findByDescriptionContaining(keyword);
    }


    public List<GithubRepo> getGithubReposBySizeGreaterThan(int size) {
        return githubRepoRepository.findBySizeGreaterThanEqual(size);
    }

    public List<GithubRepo> getGithubReposByStargazersCountGreaterThan(int count) {
        return githubRepoRepository.findByStargazersCountGreaterThanEqual(count);
    }

    public List<GithubRepo> getGithubReposByWatchersCountGreaterThan(int count) {
        return githubRepoRepository.findByWatchersCountGreaterThanEqual(count);
    }

    public List<GithubRepo> getGithubReposByOpenIssuesCountGreaterThan(int count) {
        return githubRepoRepository.findByOpenIssuesCountGreaterThanEqual(count);
    }

    public List<GithubRepo> getGithubReposByDefaultBranch(String branch) {
        return githubRepoRepository.findByDefaultBranch(branch);
    }

    public List<GithubRepo> getGithubReposByVisibility(String visibility) {
        return githubRepoRepository.findByVisibility(visibility);
    }

    public List<GithubRepo> getGithubReposByCreatedAt(String createdAt) {
        return githubRepoRepository.findByCreatedAt(createdAt);
    }

    public List<GithubRepo> getGithubReposByUpdatedAt(String updatedAt) {
        return githubRepoRepository.findByUpdatedAt(updatedAt);
    }

    public List<GithubRepo> getGithubReposByPushedAt(String pushedAt) {
        return githubRepoRepository.findByPushedAt(pushedAt);
    }

    public List<GithubRepo> getGithubReposByWebCommitSignoffRequired(boolean required) {
        return githubRepoRepository.findByWebCommitSignoffRequired(required);
    }

    public List<GithubRepo> getGithubReposByIsTemplate(boolean isTemplate) {
        return githubRepoRepository.findByIsTemplate(isTemplate);
    }

    // Veritabanına ekleme işlevleri
    // Diğer sorgu işlevleriyle entegre edilebilir

    public GithubRepo saveGithubRepo(GithubRepo githubRepo) {
        return githubRepoRepository.save(githubRepo);
    }

    public List<GithubRepo> saveAllGithubRepos(List<GithubRepo> githubRepos) {
        return githubRepoRepository.saveAll(githubRepos);
    }

    public void deleteGithubRepoById(Long id) {
        githubRepoRepository.deleteById(id);
    }

    public List<GithubRepo> getFilteredRepos(LocalDate startDate, LocalDate endDate) {
        return githubRepoRepository.findByCreatedAtBetween(startDate.toString(), endDate.toString());
    }
    public Map<String, Object> getGithubReposByPageAndSize(int page, int size) {
        int skip = page * size;
        List<GithubRepo> repos = githubRepoRepository.findGithubReposByPageAndSize(skip, size);
        long totalCount = githubRepoRepository.count(); // Assuming this method exists to count total repositories
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
        return githubRepoRepository.searchGithubReposByPageAndSize(query, filterBy, isPrivate, fork,
                languageNames, ownerLogins, licenseNames, topics, skip, size);
    }



    public List<GithubRepo> findTop10ByOrderByStargazersCountDesc() {
        return githubRepoRepository.findTop10ByOrderByStargazersCountDesc();
    }


}

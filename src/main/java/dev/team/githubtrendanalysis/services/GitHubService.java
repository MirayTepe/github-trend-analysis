package dev.team.githubtrendanalysis.services;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.queryresults.RepositoryResult;
import dev.team.githubtrendanalysis.repositories.GithubRepoRepository;
import dev.team.githubtrendanalysis.requests.GitHubSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.concurrent.Future;

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


    public List<GithubRepo> getGithubReposByPageAndSize(int page, int size) {
        int skip = page * size;
        return githubRepoRepository.findGithubReposByPageAndSize(skip, size);
    }
    public List<GithubRepo> getFilteredRepos(LocalDate startDate, LocalDate endDate) {
        return githubRepoRepository.findByCreatedAtBetween(startDate.toString(), endDate.toString());
    }

    public List<GithubRepo> getBatchRepos(int batchSize) {
        Pageable pageable = PageRequest.of(0, batchSize);
        return githubRepoRepository.findAll(pageable).getContent();
    }
    public List<GithubRepo> getTop10StarredRepositories() {
        return githubRepoRepository.findTop10ByOrderByStargazersCountDesc();
    }

    public List<GithubRepo> getRepositoriesByLanguage(String languageName) {
        return githubRepoRepository.findByLanguageName(languageName);
    }

    public List<GithubRepo> getRepositoriesByForksCount(int minimumForks) {
        return githubRepoRepository.findByForksCountGreaterThan(minimumForks);
    }

    public List<GithubRepo> getRepositoriesByLicenseName(String licenseName) {
        return githubRepoRepository.findByLicenseName(licenseName);
    }





}

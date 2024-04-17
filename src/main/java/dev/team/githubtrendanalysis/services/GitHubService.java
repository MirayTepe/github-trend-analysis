package dev.team.githubtrendanalysis.services;

import dev.team.githubtrendanalysis.models.Repository;
import dev.team.githubtrendanalysis.queryresults.RepositoryResult;
import dev.team.githubtrendanalysis.repositories.RepositoryRepository;
import dev.team.githubtrendanalysis.requests.GitHubSearchRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.List;
import java.util.concurrent.Future;

@Service
public class GitHubService {

    @Autowired
    private WebClient.Builder webClientBuilder;

    @Autowired
    private RepositoryRepository repositoryRepository;

    @Value("${http.client.base-url}")
    private String baseUrl;

    @Value("${github.api.token}")
    private String githubApiToken;

    private static final int TOTAL_PAGES = 10; // Toplam sayfa sayısı, ihtiyaca göre değiştirilebilir

    public Future<Void> fetchAllDataAndSaveToNeo4j() throws InterruptedException {
        for (int page = 1; page <= TOTAL_PAGES; page++) {
            GitHubSearchRequest request = createGitHubSearchRequest(page);

            RepositoryResult result = fetchDataFromGitHub(request);

            if (result != null && result.getItems() != null) {
                List<Repository> repositories = result.getItems();
                repositoryRepository.saveAll(repositories);

                // Küçük bir gecikme ekleyerek GitHub API sınırlamalarına uymaya çalışıyoruz
                Thread.sleep(1000);
            }
        }
        return null;
    }

    private GitHubSearchRequest createGitHubSearchRequest(int page) {
        GitHubSearchRequest request = new GitHubSearchRequest();
        request.setQuery("created:>2022-01-01");
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
}

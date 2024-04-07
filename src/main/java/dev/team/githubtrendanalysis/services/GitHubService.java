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
import java.util.stream.Collectors;

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

    public void fetchDataAndSaveToNeo4j() {
        GitHubSearchRequest request = new GitHubSearchRequest();
        request.setQuery("created:>2022-01-01");
        request.setSortType("stars");
        request.setSortOrder("desc");

        WebClient webClient = webClientBuilder.baseUrl(baseUrl)
                .defaultHeader("Authorization", githubApiToken)  // Authorization başlığını ekliyoruz
                .build();

        RepositoryResult result = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .queryParam("q", request.getQuery())
                        .queryParam("sort", request.getSortType())
                        .queryParam("order", request.getSortOrder())
                        .build())
                .retrieve()
                .bodyToMono(RepositoryResult.class)
                .block();

        if (result != null && result.getItems() != null) {
            List<Repository> first20Items = result.getItems().stream().limit(20).collect(Collectors.toList());
            repositoryRepository.saveAll(first20Items);
        }
    }
}

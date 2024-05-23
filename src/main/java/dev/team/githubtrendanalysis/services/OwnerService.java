package dev.team.githubtrendanalysis.services;

import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class OwnerService {
    @Autowired
    private OwnerRepository ownerRepository;

    public OwnerService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public List<GithubRepo> getGithubReposByOwner(String ownerLogin) {
        return ownerRepository.findByOwner_Login(ownerLogin);
    }
}

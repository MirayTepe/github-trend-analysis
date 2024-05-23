package dev.team.githubtrendanalysis.services;

import dev.team.githubtrendanalysis.models.GithubRepo;
import dev.team.githubtrendanalysis.models.LanguageRepoCount;
import dev.team.githubtrendanalysis.models.License;
import dev.team.githubtrendanalysis.models.LicenseRepoCount;
import dev.team.githubtrendanalysis.repositories.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    public LicenseService(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    public Optional<License> getLicenseByName(String name) {
        return licenseRepository.findByName(name);
    }
    public List<LicenseRepoCount> getRepoCountByLicense() {
        return licenseRepository.countReposByLicense();
    }

}

package dev.team.githubtrendanalysis.controllers;

import dev.team.githubtrendanalysis.models.LanguageRepoCount;
import dev.team.githubtrendanalysis.models.LicenseRepoCount;
import dev.team.githubtrendanalysis.services.LicenseService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
public class LicenseController {

    @Autowired
    private LicenseService licenseService;
    @GetMapping("/repo-counts-license")
    public List<LicenseRepoCount> getRepoCountByLanguage() {
        return licenseService.getRepoCountByLicense();
    }
}

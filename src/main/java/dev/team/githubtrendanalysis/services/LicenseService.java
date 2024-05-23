package dev.team.githubtrendanalysis.services;

import dev.team.githubtrendanalysis.models.License;
import dev.team.githubtrendanalysis.repositories.LicenseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LicenseService {

    @Autowired
    private LicenseRepository licenseRepository;

    public LicenseService(LicenseRepository licenseRepository) {
        this.licenseRepository = licenseRepository;
    }

    public License getLicenseByName(String name) {
        return licenseRepository.findByName(name);
    }

}

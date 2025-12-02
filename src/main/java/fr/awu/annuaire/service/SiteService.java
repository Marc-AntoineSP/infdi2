package fr.awu.annuaire.service;

import java.util.List;

import fr.awu.annuaire.model.Site;
import fr.awu.annuaire.repository.SiteRepository;

public class SiteService {
    private SiteRepository siteRepository;

    public SiteService() {
        this.siteRepository = new SiteRepository();
    }

    public void save(Site site) throws IllegalArgumentException {
        if(site.getVille() == null || site.getVille().isBlank()) {
            throw new IllegalArgumentException("CreationError: Site name cannot be null");
        }
        siteRepository.save(site);
    }

    public List<Site> getAll() {
        return siteRepository.getAll();
    }

    public void update(Site site) throws IllegalArgumentException {
        if(site.getVille() == null || site.getVille().isBlank()) {
            throw new IllegalArgumentException("CreationError: Site name cannot be null");
        }
        siteRepository.update(site);
    }

    public void delete (Site site) {
        siteRepository.delete(site);
    }
}

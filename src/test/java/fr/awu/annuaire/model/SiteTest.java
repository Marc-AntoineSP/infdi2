package fr.awu.annuaire.model;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import fr.awu.annuaire.service.SiteService;

class SiteTest {
    private final SiteService siteService = new SiteService();

    @Test
    void createSiteTestShouldSuccess() {
        Site site = new Site();
        Site site2 = new Site("Sans setter");
        site.setVille("Paris");
        assert site.getVille().equals("Paris");
        assert site2.getVille().equals("Sans setter");
    }

    @Test
    void addSiteToDatabaseTestShouldSuccess() {
        Site site = new Site();
        site.setVille("Paris");
        siteService.save(site);
        assert siteService.getAll().contains(site);
    }

    @Test
    void addSiteToDatabaseTestShouldFail() {
        Site site = new Site("");
        assertThrows(IllegalArgumentException.class,
                () -> siteService.save(site));
    }

    @Test
    void updateSiteTestShouldSuccess() {
        Site site = new Site("Lyon");
        siteService.save(site);
        site.setVille("Marseille");
        siteService.update(site);
        boolean find = siteService.getAll().contains(site);
        assertTrue(find);
    }

    @Test
    void updateSiteTestShouldFail() {
        Site site = new Site("Lyon");
        siteService.save(site);
        site.setVille("");
        assertThrows(IllegalArgumentException.class,
                () -> siteService.update(site));
    }
}

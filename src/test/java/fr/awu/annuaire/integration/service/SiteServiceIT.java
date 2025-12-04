package fr.awu.annuaire.integration.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.Test;

import fr.awu.annuaire.model.Site;
import fr.awu.annuaire.service.SiteService;

class SiteServiceIT {
    private final SiteService siteService = new SiteService();

    @Test
    void addSiteToDatabaseTestShouldSuccess() {
        Site site = new Site("Paris");
        siteService.save(site);
        assertTrue(siteService.getAll().contains(site));
    }

    @Test
    void addSiteToDatabaseTestShouldFail() {
        Site site = new Site("");
        assertThrows(IllegalArgumentException.class,
                () -> siteService.save(site));
    }

    @Test
    void addNullSiteVilleShouldFail() {
        Site site = new Site("Valid");
        site.setVille(null);
        assertThrows(IllegalArgumentException.class,
                () -> siteService.save(site));
    }

    @Test
    void updateSiteTestShouldSuccess() {
        Site site = new Site("Lyon");
        siteService.save(site);

        site.setVille("Marseille");
        siteService.update(site);

        Site retrieved = siteService.getById(site.getId());
        assertEquals("Marseille", retrieved.getVille());
    }

    @Test
    void updateSiteTestShouldFail() {
        Site site = new Site("Lyon");
        siteService.save(site);

        site.setVille("");
        assertThrows(IllegalArgumentException.class,
                () -> siteService.update(site));
    }

    @Test
    void updateSiteWithNullVilleShouldFail() {
        Site site = new Site("Lyon");
        siteService.save(site);

        site.setVille(null);
        assertThrows(IllegalArgumentException.class,
                () -> siteService.update(site));
    }

    @Test
    void deleteSiteShouldSuccess() {
        Site site = new Site("ToDelete");
        siteService.save(site);
        int id = site.getId();

        siteService.delete(site);

        assertNull(siteService.getById(id));
    }

    @Test
    void getByIdShouldReturnCorrectSite() {
        Site site = new Site("FindMe");
        siteService.save(site);
        int id = site.getId();

        Site found = siteService.getById(id);
        assertNotNull(found);
        assertEquals("FindMe", found.getVille());
        assertEquals(id, found.getId());
    }

    @Test
    void getByIdWithInvalidIdShouldReturnNull() {
        Site found = siteService.getById(99999);
        assertNull(found);
    }

    @Test
    void getAllShouldReturnAllSites() {
        int initialSize = siteService.getAll().size();

        Site site1 = new Site("Paris");
        Site site2 = new Site("Lyon");
        siteService.save(site1);
        siteService.save(site2);

        assertEquals(initialSize + 2, siteService.getAll().size());
    }

    @Test
    void siteGetIdShouldReturnValidId() {
        Site site = new Site("Paris");
        siteService.save(site);

        assertTrue(site.getId() > 0);
    }
}

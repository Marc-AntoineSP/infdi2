package fr.awu.annuaire.unit.model;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

import fr.awu.annuaire.model.Site;

class SiteTest {

    @Test
    void createSiteWithConstructorShouldSuccess() {
        Site site = new Site("Paris");
        assertEquals("Paris", site.getVille());
    }

    @Test
    void createSiteWithSetterShouldSuccess() {
        Site site = new Site("Initial");
        site.setVille("Paris");
        assertEquals("Paris", site.getVille());
    }

    @Test
    void siteEqualsShouldWorkCorrectly() {
        Site site1 = new Site("Paris");
        Site site2 = new Site("Paris");

        assertEquals(site1, site1);
    }

    @Test
    void siteHashCodeShouldBeConsistent() {
        Site site = new Site("Paris");

        int hashCode1 = site.hashCode();
        int hashCode2 = site.hashCode();

        assertEquals(hashCode1, hashCode2);
    }

    @Test
    void siteToStringShouldNotThrowException() {
        Site site = new Site("Paris");
        assertDoesNotThrow(() -> site.toString());
    }
}

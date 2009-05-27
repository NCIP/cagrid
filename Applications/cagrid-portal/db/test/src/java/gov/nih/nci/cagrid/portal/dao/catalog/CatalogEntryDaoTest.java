package gov.nih.nci.cagrid.portal.dao.catalog;

import gov.nih.nci.cagrid.portal.DaoTestBase;
import gov.nih.nci.cagrid.portal.dao.AbstractDao;
import gov.nih.nci.cagrid.portal.domain.PortalUser;
import gov.nih.nci.cagrid.portal.domain.catalog.*;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * User: kherm
 *
 * @author kherm manav.kher@semanticbits.com
 */
public class CatalogEntryDaoTest extends DaoTestBase<AboutCatalogEntryDao> {

    @Test
    public void save() {
        CatalogEntry catalog = new CatalogEntry();
        catalog.setNumViews(122);
        catalog.setPublished(true);
        getDao().save(catalog);

    }

    @Test
    public void savePersonCatalog() {
        PersonCatalogEntry pCatalog = new PersonCatalogEntry();
        getDao().save(pCatalog);

    }

    @Test
    public void saveGraph() {
        CatalogEntry catalog = new CatalogEntry();
        catalog.setNumViews(2222);

        Term term = new Term();
        ((AbstractDao) getDao()).save(term);
        List<Term> terms = new ArrayList<Term>();
        terms.add(term);

        catalog.setAreasOfFocus(terms);

        PortalUser user = new PortalUser();
        ((AbstractDao) getDao()).save(user);
        catalog.setAuthor(user);

        Citation citation = new Citation();
        ((AbstractDao) getDao()).save(citation);
        List<Citation> citations = new ArrayList<Citation>();
        citations.add(citation);
        catalog.setCitations(citations);

        Comment comment = new Comment();
        ((AbstractDao) getDao()).save(comment);
        List<Comment> comments = new ArrayList<Comment>();
        comments.add(comment);
        catalog.setComments(comments);

        catalog.setCreatedAt(new Date());

        catalog.setDescription("");

        FavoriteOfRole fav = new FavoriteOfRole();
        ((AbstractDao) getDao()).save(fav);
        List<FavoriteOfRole> favs = new ArrayList<FavoriteOfRole>();
        favs.add(fav);
        catalog.setFavoriteOfRole(favs);

        File file = new File();
        ((AbstractDao) getDao()).save(file);
        List<File> files = new ArrayList<File>();
        files.add(file);
        catalog.setFiles(files);

        Hyperlink href = new Hyperlink();
        ((AbstractDao) getDao()).save(href);
        List<Hyperlink> hrefs = new ArrayList<Hyperlink>();
        hrefs.add(href);
        catalog.setHyperlinks(hrefs);

        catalog.setLastViewedAt(new Date());
        catalog.setName("");
        catalog.setNumViews(1212);
        catalog.setPublished(true);

        Rating rating = new Rating();
        ((AbstractDao) getDao()).save(rating);
        List<Rating> ratings = new ArrayList<Rating>();
        ratings.add(rating);
        catalog.setRatings(ratings);


        getDao().save(catalog);
    }
}

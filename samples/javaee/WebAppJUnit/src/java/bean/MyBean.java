/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package bean;

import java.util.Collection;
import javax.annotation.security.PermitAll;
import javax.ejb.Stateless;
import javax.ejb.LocalBean;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author nb
 */
@Stateless
@LocalBean
public class MyBean {

    @PersistenceContext(unitName = "WebAppJUnitPU")
    private EntityManager em;

    public int addNumbers(int numberA, int numberB) {
        return numberA + numberB;
    }

    public void persist(Object object) {
        em.persist(object);
    }

    @PermitAll
    public int verify() {
        String result = null;
        Query q = em.createNamedQuery("SimpleEntity.findAll");
        Collection entities = q.getResultList();
        int s = entities.size();
        for (Object o : entities) {
            SimpleEntity se = (SimpleEntity) o;
            System.out.println("Found: " + se.getName());
        }

        return s;
    }

    @PermitAll
    public void insert(int num) {
        for (int i = 1; i <= num; i++) {
            System.out.println("Inserting # " + i);
            SimpleEntity e = new SimpleEntity(i);
            em.persist(e);
        }
    }
}

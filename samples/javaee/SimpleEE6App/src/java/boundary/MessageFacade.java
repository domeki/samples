/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package boundary;

import entities.Message;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author nb
 */
@Stateless
public class MessageFacade {
    @PersistenceContext(unitName = "SimpleEE6AppPU")
    private EntityManager em;

    public void create(Message message) {
        em.persist(message);
    }

    public void edit(Message message) {
        em.merge(message);
    }

    public void remove(Message message) {
        em.remove(em.merge(message));
    }

    public Message find(Object id) {
        return em.find(Message.class, id);
    }

    public List<Message> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Message.class));
        return em.createQuery(cq).getResultList();
    }

    public List<Message> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(Message.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<Message> rt = cq.from(Message.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}

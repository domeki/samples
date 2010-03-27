/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package session;

import entity.CustomerOrder;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

/**
 *
 * @author troy
 */
@Stateless
public class CustomerOrderFacade {
    @PersistenceContext(unitName = "AffableBeanPU")
    private EntityManager em;

    public void create(CustomerOrder customerOrder) {
        em.persist(customerOrder);
    }

    public void edit(CustomerOrder customerOrder) {
        em.merge(customerOrder);
    }

    public void remove(CustomerOrder customerOrder) {
        em.remove(em.merge(customerOrder));
    }

//    public CustomerOrder find(Object id) {
//        return em.find(CustomerOrder.class, id);
//    }

    // manually altered
    public CustomerOrder find(Object id) {
        CustomerOrder order = em.find(CustomerOrder.class, id);
        em.refresh(order);
        return order;
    }

    public List<CustomerOrder> findAll() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(CustomerOrder.class));
        return em.createQuery(cq).getResultList();
    }

    public List<CustomerOrder> findRange(int[] range) {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        cq.select(cq.from(CustomerOrder.class));
        Query q = em.createQuery(cq);
        q.setMaxResults(range[1] - range[0]);
        q.setFirstResult(range[0]);
        return q.getResultList();
    }

    public int count() {
        CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
        Root<CustomerOrder> rt = cq.from(CustomerOrder.class);
        cq.select(em.getCriteriaBuilder().count(rt));
        Query q = em.createQuery(cq);
        return ((Long) q.getSingleResult()).intValue();
    }

}

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package jpa.controllers;

import java.util.List;
import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceUnit;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.transaction.UserTransaction;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.controllers.exceptions.RollbackFailureException;
import jpa.entities.Billable;
import jpa.entities.Project;
import jpa.entities.Consultant;

/**
 *
 * @author nb
 */
public class BillableJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "ConsultingAgencyPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Billable billable) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Project project = billable.getProject();
            if (project != null) {
                project = em.getReference(project.getClass(), project.getProjectPK());
                billable.setProject(project);
            }
            Consultant consultantId = billable.getConsultantId();
            if (consultantId != null) {
                consultantId = em.getReference(consultantId.getClass(), consultantId.getConsultantId());
                billable.setConsultantId(consultantId);
            }
            em.persist(billable);
            if (project != null) {
                project.getBillableCollection().add(billable);
                project = em.merge(project);
            }
            if (consultantId != null) {
                consultantId.getBillableCollection().add(billable);
                consultantId = em.merge(consultantId);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Billable billable) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Billable persistentBillable = em.find(Billable.class, billable.getBillableId());
            Project projectOld = persistentBillable.getProject();
            Project projectNew = billable.getProject();
            Consultant consultantIdOld = persistentBillable.getConsultantId();
            Consultant consultantIdNew = billable.getConsultantId();
            if (projectNew != null) {
                projectNew = em.getReference(projectNew.getClass(), projectNew.getProjectPK());
                billable.setProject(projectNew);
            }
            if (consultantIdNew != null) {
                consultantIdNew = em.getReference(consultantIdNew.getClass(), consultantIdNew.getConsultantId());
                billable.setConsultantId(consultantIdNew);
            }
            billable = em.merge(billable);
            if (projectOld != null && !projectOld.equals(projectNew)) {
                projectOld.getBillableCollection().remove(billable);
                projectOld = em.merge(projectOld);
            }
            if (projectNew != null && !projectNew.equals(projectOld)) {
                projectNew.getBillableCollection().add(billable);
                projectNew = em.merge(projectNew);
            }
            if (consultantIdOld != null && !consultantIdOld.equals(consultantIdNew)) {
                consultantIdOld.getBillableCollection().remove(billable);
                consultantIdOld = em.merge(consultantIdOld);
            }
            if (consultantIdNew != null && !consultantIdNew.equals(consultantIdOld)) {
                consultantIdNew.getBillableCollection().add(billable);
                consultantIdNew = em.merge(consultantIdNew);
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0) {
                Long id = billable.getBillableId();
                if (findBillable(id) == null) {
                    throw new NonexistentEntityException("The billable with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Long id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Billable billable;
            try {
                billable = em.getReference(Billable.class, id);
                billable.getBillableId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The billable with id " + id + " no longer exists.", enfe);
            }
            Project project = billable.getProject();
            if (project != null) {
                project.getBillableCollection().remove(billable);
                project = em.merge(project);
            }
            Consultant consultantId = billable.getConsultantId();
            if (consultantId != null) {
                consultantId.getBillableCollection().remove(billable);
                consultantId = em.merge(consultantId);
            }
            em.remove(billable);
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public List<Billable> findBillableEntities() {
        return findBillableEntities(true, -1, -1);
    }

    public List<Billable> findBillableEntities(int maxResults, int firstResult) {
        return findBillableEntities(false, maxResults, firstResult);
    }

    private List<Billable> findBillableEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Billable as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Billable findBillable(Long id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Billable.class, id);
        } finally {
            em.close();
        }
    }

    public int getBillableCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Billable as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

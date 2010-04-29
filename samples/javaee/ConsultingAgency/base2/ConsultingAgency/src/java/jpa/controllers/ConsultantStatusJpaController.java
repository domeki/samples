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
import jpa.controllers.exceptions.IllegalOrphanException;
import jpa.controllers.exceptions.NonexistentEntityException;
import jpa.controllers.exceptions.PreexistingEntityException;
import jpa.controllers.exceptions.RollbackFailureException;
import jpa.entities.Consultant;
import java.util.ArrayList;
import java.util.Collection;
import jpa.entities.ConsultantStatus;

/**
 *
 * @author nb
 */
public class ConsultantStatusJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "ConsultingAgencyPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(ConsultantStatus consultantStatus) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (consultantStatus.getConsultantCollection() == null) {
            consultantStatus.setConsultantCollection(new ArrayList<Consultant>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Collection<Consultant> attachedConsultantCollection = new ArrayList<Consultant>();
            for (Consultant consultantCollectionConsultantToAttach : consultantStatus.getConsultantCollection()) {
                consultantCollectionConsultantToAttach = em.getReference(consultantCollectionConsultantToAttach.getClass(), consultantCollectionConsultantToAttach.getConsultantId());
                attachedConsultantCollection.add(consultantCollectionConsultantToAttach);
            }
            consultantStatus.setConsultantCollection(attachedConsultantCollection);
            em.persist(consultantStatus);
            for (Consultant consultantCollectionConsultant : consultantStatus.getConsultantCollection()) {
                ConsultantStatus oldStatusIdOfConsultantCollectionConsultant = consultantCollectionConsultant.getStatusId();
                consultantCollectionConsultant.setStatusId(consultantStatus);
                consultantCollectionConsultant = em.merge(consultantCollectionConsultant);
                if (oldStatusIdOfConsultantCollectionConsultant != null) {
                    oldStatusIdOfConsultantCollectionConsultant.getConsultantCollection().remove(consultantCollectionConsultant);
                    oldStatusIdOfConsultantCollectionConsultant = em.merge(oldStatusIdOfConsultantCollectionConsultant);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findConsultantStatus(consultantStatus.getStatusId()) != null) {
                throw new PreexistingEntityException("ConsultantStatus " + consultantStatus + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(ConsultantStatus consultantStatus) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConsultantStatus persistentConsultantStatus = em.find(ConsultantStatus.class, consultantStatus.getStatusId());
            Collection<Consultant> consultantCollectionOld = persistentConsultantStatus.getConsultantCollection();
            Collection<Consultant> consultantCollectionNew = consultantStatus.getConsultantCollection();
            List<String> illegalOrphanMessages = null;
            for (Consultant consultantCollectionOldConsultant : consultantCollectionOld) {
                if (!consultantCollectionNew.contains(consultantCollectionOldConsultant)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Consultant " + consultantCollectionOldConsultant + " since its statusId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Collection<Consultant> attachedConsultantCollectionNew = new ArrayList<Consultant>();
            for (Consultant consultantCollectionNewConsultantToAttach : consultantCollectionNew) {
                consultantCollectionNewConsultantToAttach = em.getReference(consultantCollectionNewConsultantToAttach.getClass(), consultantCollectionNewConsultantToAttach.getConsultantId());
                attachedConsultantCollectionNew.add(consultantCollectionNewConsultantToAttach);
            }
            consultantCollectionNew = attachedConsultantCollectionNew;
            consultantStatus.setConsultantCollection(consultantCollectionNew);
            consultantStatus = em.merge(consultantStatus);
            for (Consultant consultantCollectionNewConsultant : consultantCollectionNew) {
                if (!consultantCollectionOld.contains(consultantCollectionNewConsultant)) {
                    ConsultantStatus oldStatusIdOfConsultantCollectionNewConsultant = consultantCollectionNewConsultant.getStatusId();
                    consultantCollectionNewConsultant.setStatusId(consultantStatus);
                    consultantCollectionNewConsultant = em.merge(consultantCollectionNewConsultant);
                    if (oldStatusIdOfConsultantCollectionNewConsultant != null && !oldStatusIdOfConsultantCollectionNewConsultant.equals(consultantStatus)) {
                        oldStatusIdOfConsultantCollectionNewConsultant.getConsultantCollection().remove(consultantCollectionNewConsultant);
                        oldStatusIdOfConsultantCollectionNewConsultant = em.merge(oldStatusIdOfConsultantCollectionNewConsultant);
                    }
                }
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
                Character id = consultantStatus.getStatusId();
                if (findConsultantStatus(id) == null) {
                    throw new NonexistentEntityException("The consultantStatus with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Character id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            ConsultantStatus consultantStatus;
            try {
                consultantStatus = em.getReference(ConsultantStatus.class, id);
                consultantStatus.getStatusId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The consultantStatus with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Consultant> consultantCollectionOrphanCheck = consultantStatus.getConsultantCollection();
            for (Consultant consultantCollectionOrphanCheckConsultant : consultantCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This ConsultantStatus (" + consultantStatus + ") cannot be destroyed since the Consultant " + consultantCollectionOrphanCheckConsultant + " in its consultantCollection field has a non-nullable statusId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(consultantStatus);
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

    public List<ConsultantStatus> findConsultantStatusEntities() {
        return findConsultantStatusEntities(true, -1, -1);
    }

    public List<ConsultantStatus> findConsultantStatusEntities(int maxResults, int firstResult) {
        return findConsultantStatusEntities(false, maxResults, firstResult);
    }

    private List<ConsultantStatus> findConsultantStatusEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from ConsultantStatus as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public ConsultantStatus findConsultantStatus(Character id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(ConsultantStatus.class, id);
        } finally {
            em.close();
        }
    }

    public int getConsultantStatusCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from ConsultantStatus as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

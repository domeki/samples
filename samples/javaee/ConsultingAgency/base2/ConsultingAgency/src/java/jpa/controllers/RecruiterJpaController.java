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
import jpa.entities.Client;
import jpa.entities.Consultant;
import java.util.ArrayList;
import java.util.Collection;
import jpa.entities.Recruiter;

/**
 *
 * @author nb
 */
public class RecruiterJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "ConsultingAgencyPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Recruiter recruiter) throws RollbackFailureException, Exception {
        if (recruiter.getConsultantCollection() == null) {
            recruiter.setConsultantCollection(new ArrayList<Consultant>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Client client = recruiter.getClient();
            if (client != null) {
                client = em.getReference(client.getClass(), client.getClientPK());
                recruiter.setClient(client);
            }
            Collection<Consultant> attachedConsultantCollection = new ArrayList<Consultant>();
            for (Consultant consultantCollectionConsultantToAttach : recruiter.getConsultantCollection()) {
                consultantCollectionConsultantToAttach = em.getReference(consultantCollectionConsultantToAttach.getClass(), consultantCollectionConsultantToAttach.getConsultantId());
                attachedConsultantCollection.add(consultantCollectionConsultantToAttach);
            }
            recruiter.setConsultantCollection(attachedConsultantCollection);
            em.persist(recruiter);
            if (client != null) {
                Recruiter oldRecruiterOfClient = client.getRecruiter();
                if (oldRecruiterOfClient != null) {
                    oldRecruiterOfClient.setClient(null);
                    oldRecruiterOfClient = em.merge(oldRecruiterOfClient);
                }
                client.setRecruiter(recruiter);
                client = em.merge(client);
            }
            for (Consultant consultantCollectionConsultant : recruiter.getConsultantCollection()) {
                Recruiter oldRecruiterIdOfConsultantCollectionConsultant = consultantCollectionConsultant.getRecruiterId();
                consultantCollectionConsultant.setRecruiterId(recruiter);
                consultantCollectionConsultant = em.merge(consultantCollectionConsultant);
                if (oldRecruiterIdOfConsultantCollectionConsultant != null) {
                    oldRecruiterIdOfConsultantCollectionConsultant.getConsultantCollection().remove(consultantCollectionConsultant);
                    oldRecruiterIdOfConsultantCollectionConsultant = em.merge(oldRecruiterIdOfConsultantCollectionConsultant);
                }
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

    public void edit(Recruiter recruiter) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Recruiter persistentRecruiter = em.find(Recruiter.class, recruiter.getRecruiterId());
            Client clientOld = persistentRecruiter.getClient();
            Client clientNew = recruiter.getClient();
            Collection<Consultant> consultantCollectionOld = persistentRecruiter.getConsultantCollection();
            Collection<Consultant> consultantCollectionNew = recruiter.getConsultantCollection();
            if (clientNew != null) {
                clientNew = em.getReference(clientNew.getClass(), clientNew.getClientPK());
                recruiter.setClient(clientNew);
            }
            Collection<Consultant> attachedConsultantCollectionNew = new ArrayList<Consultant>();
            for (Consultant consultantCollectionNewConsultantToAttach : consultantCollectionNew) {
                consultantCollectionNewConsultantToAttach = em.getReference(consultantCollectionNewConsultantToAttach.getClass(), consultantCollectionNewConsultantToAttach.getConsultantId());
                attachedConsultantCollectionNew.add(consultantCollectionNewConsultantToAttach);
            }
            consultantCollectionNew = attachedConsultantCollectionNew;
            recruiter.setConsultantCollection(consultantCollectionNew);
            recruiter = em.merge(recruiter);
            if (clientOld != null && !clientOld.equals(clientNew)) {
                clientOld.setRecruiter(null);
                clientOld = em.merge(clientOld);
            }
            if (clientNew != null && !clientNew.equals(clientOld)) {
                Recruiter oldRecruiterOfClient = clientNew.getRecruiter();
                if (oldRecruiterOfClient != null) {
                    oldRecruiterOfClient.setClient(null);
                    oldRecruiterOfClient = em.merge(oldRecruiterOfClient);
                }
                clientNew.setRecruiter(recruiter);
                clientNew = em.merge(clientNew);
            }
            for (Consultant consultantCollectionOldConsultant : consultantCollectionOld) {
                if (!consultantCollectionNew.contains(consultantCollectionOldConsultant)) {
                    consultantCollectionOldConsultant.setRecruiterId(null);
                    consultantCollectionOldConsultant = em.merge(consultantCollectionOldConsultant);
                }
            }
            for (Consultant consultantCollectionNewConsultant : consultantCollectionNew) {
                if (!consultantCollectionOld.contains(consultantCollectionNewConsultant)) {
                    Recruiter oldRecruiterIdOfConsultantCollectionNewConsultant = consultantCollectionNewConsultant.getRecruiterId();
                    consultantCollectionNewConsultant.setRecruiterId(recruiter);
                    consultantCollectionNewConsultant = em.merge(consultantCollectionNewConsultant);
                    if (oldRecruiterIdOfConsultantCollectionNewConsultant != null && !oldRecruiterIdOfConsultantCollectionNewConsultant.equals(recruiter)) {
                        oldRecruiterIdOfConsultantCollectionNewConsultant.getConsultantCollection().remove(consultantCollectionNewConsultant);
                        oldRecruiterIdOfConsultantCollectionNewConsultant = em.merge(oldRecruiterIdOfConsultantCollectionNewConsultant);
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
                Integer id = recruiter.getRecruiterId();
                if (findRecruiter(id) == null) {
                    throw new NonexistentEntityException("The recruiter with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Recruiter recruiter;
            try {
                recruiter = em.getReference(Recruiter.class, id);
                recruiter.getRecruiterId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The recruiter with id " + id + " no longer exists.", enfe);
            }
            Client client = recruiter.getClient();
            if (client != null) {
                client.setRecruiter(null);
                client = em.merge(client);
            }
            Collection<Consultant> consultantCollection = recruiter.getConsultantCollection();
            for (Consultant consultantCollectionConsultant : consultantCollection) {
                consultantCollectionConsultant.setRecruiterId(null);
                consultantCollectionConsultant = em.merge(consultantCollectionConsultant);
            }
            em.remove(recruiter);
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

    public List<Recruiter> findRecruiterEntities() {
        return findRecruiterEntities(true, -1, -1);
    }

    public List<Recruiter> findRecruiterEntities(int maxResults, int firstResult) {
        return findRecruiterEntities(false, maxResults, firstResult);
    }

    private List<Recruiter> findRecruiterEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Recruiter as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Recruiter findRecruiter(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Recruiter.class, id);
        } finally {
            em.close();
        }
    }

    public int getRecruiterCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Recruiter as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

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
import jpa.controllers.exceptions.RollbackFailureException;
import jpa.entities.Consultant;
import jpa.entities.Recruiter;
import jpa.entities.ConsultantStatus;
import jpa.entities.Project;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;
import jpa.entities.Billable;

/**
 *
 * @author nb
 */
public class ConsultantJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "ConsultingAgencyPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Consultant consultant) throws RollbackFailureException, Exception {
        if (consultant.getProjectCollection() == null) {
            consultant.setProjectCollection(new ArrayList<Project>());
        }
        if (consultant.getBillableCollection() == null) {
            consultant.setBillableCollection(new ArrayList<Billable>());
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Recruiter recruiterId = consultant.getRecruiterId();
            if (recruiterId != null) {
                recruiterId = em.getReference(recruiterId.getClass(), recruiterId.getRecruiterId());
                consultant.setRecruiterId(recruiterId);
            }
            ConsultantStatus statusId = consultant.getStatusId();
            if (statusId != null) {
                statusId = em.getReference(statusId.getClass(), statusId.getStatusId());
                consultant.setStatusId(statusId);
            }
            Collection<Project> attachedProjectCollection = new ArrayList<Project>();
            for (Project projectCollectionProjectToAttach : consultant.getProjectCollection()) {
                projectCollectionProjectToAttach = em.getReference(projectCollectionProjectToAttach.getClass(), projectCollectionProjectToAttach.getProjectPK());
                attachedProjectCollection.add(projectCollectionProjectToAttach);
            }
            consultant.setProjectCollection(attachedProjectCollection);
            Collection<Billable> attachedBillableCollection = new ArrayList<Billable>();
            for (Billable billableCollectionBillableToAttach : consultant.getBillableCollection()) {
                billableCollectionBillableToAttach = em.getReference(billableCollectionBillableToAttach.getClass(), billableCollectionBillableToAttach.getBillableId());
                attachedBillableCollection.add(billableCollectionBillableToAttach);
            }
            consultant.setBillableCollection(attachedBillableCollection);
            em.persist(consultant);
            if (recruiterId != null) {
                recruiterId.getConsultantCollection().add(consultant);
                recruiterId = em.merge(recruiterId);
            }
            if (statusId != null) {
                statusId.getConsultantCollection().add(consultant);
                statusId = em.merge(statusId);
            }
            for (Project projectCollectionProject : consultant.getProjectCollection()) {
                projectCollectionProject.getConsultantCollection().add(consultant);
                projectCollectionProject = em.merge(projectCollectionProject);
            }
            for (Billable billableCollectionBillable : consultant.getBillableCollection()) {
                Consultant oldConsultantIdOfBillableCollectionBillable = billableCollectionBillable.getConsultantId();
                billableCollectionBillable.setConsultantId(consultant);
                billableCollectionBillable = em.merge(billableCollectionBillable);
                if (oldConsultantIdOfBillableCollectionBillable != null) {
                    oldConsultantIdOfBillableCollectionBillable.getBillableCollection().remove(billableCollectionBillable);
                    oldConsultantIdOfBillableCollectionBillable = em.merge(oldConsultantIdOfBillableCollectionBillable);
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

    public void edit(Consultant consultant) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Consultant persistentConsultant = em.find(Consultant.class, consultant.getConsultantId());
            Recruiter recruiterIdOld = persistentConsultant.getRecruiterId();
            Recruiter recruiterIdNew = consultant.getRecruiterId();
            ConsultantStatus statusIdOld = persistentConsultant.getStatusId();
            ConsultantStatus statusIdNew = consultant.getStatusId();
            Collection<Project> projectCollectionOld = persistentConsultant.getProjectCollection();
            Collection<Project> projectCollectionNew = consultant.getProjectCollection();
            Collection<Billable> billableCollectionOld = persistentConsultant.getBillableCollection();
            Collection<Billable> billableCollectionNew = consultant.getBillableCollection();
            List<String> illegalOrphanMessages = null;
            for (Billable billableCollectionOldBillable : billableCollectionOld) {
                if (!billableCollectionNew.contains(billableCollectionOldBillable)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Billable " + billableCollectionOldBillable + " since its consultantId field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (recruiterIdNew != null) {
                recruiterIdNew = em.getReference(recruiterIdNew.getClass(), recruiterIdNew.getRecruiterId());
                consultant.setRecruiterId(recruiterIdNew);
            }
            if (statusIdNew != null) {
                statusIdNew = em.getReference(statusIdNew.getClass(), statusIdNew.getStatusId());
                consultant.setStatusId(statusIdNew);
            }
            Collection<Project> attachedProjectCollectionNew = new ArrayList<Project>();
            for (Project projectCollectionNewProjectToAttach : projectCollectionNew) {
                projectCollectionNewProjectToAttach = em.getReference(projectCollectionNewProjectToAttach.getClass(), projectCollectionNewProjectToAttach.getProjectPK());
                attachedProjectCollectionNew.add(projectCollectionNewProjectToAttach);
            }
            projectCollectionNew = attachedProjectCollectionNew;
            consultant.setProjectCollection(projectCollectionNew);
            Collection<Billable> attachedBillableCollectionNew = new ArrayList<Billable>();
            for (Billable billableCollectionNewBillableToAttach : billableCollectionNew) {
                billableCollectionNewBillableToAttach = em.getReference(billableCollectionNewBillableToAttach.getClass(), billableCollectionNewBillableToAttach.getBillableId());
                attachedBillableCollectionNew.add(billableCollectionNewBillableToAttach);
            }
            billableCollectionNew = attachedBillableCollectionNew;
            consultant.setBillableCollection(billableCollectionNew);
            consultant = em.merge(consultant);
            if (recruiterIdOld != null && !recruiterIdOld.equals(recruiterIdNew)) {
                recruiterIdOld.getConsultantCollection().remove(consultant);
                recruiterIdOld = em.merge(recruiterIdOld);
            }
            if (recruiterIdNew != null && !recruiterIdNew.equals(recruiterIdOld)) {
                recruiterIdNew.getConsultantCollection().add(consultant);
                recruiterIdNew = em.merge(recruiterIdNew);
            }
            if (statusIdOld != null && !statusIdOld.equals(statusIdNew)) {
                statusIdOld.getConsultantCollection().remove(consultant);
                statusIdOld = em.merge(statusIdOld);
            }
            if (statusIdNew != null && !statusIdNew.equals(statusIdOld)) {
                statusIdNew.getConsultantCollection().add(consultant);
                statusIdNew = em.merge(statusIdNew);
            }
            for (Project projectCollectionOldProject : projectCollectionOld) {
                if (!projectCollectionNew.contains(projectCollectionOldProject)) {
                    projectCollectionOldProject.getConsultantCollection().remove(consultant);
                    projectCollectionOldProject = em.merge(projectCollectionOldProject);
                }
            }
            for (Project projectCollectionNewProject : projectCollectionNew) {
                if (!projectCollectionOld.contains(projectCollectionNewProject)) {
                    projectCollectionNewProject.getConsultantCollection().add(consultant);
                    projectCollectionNewProject = em.merge(projectCollectionNewProject);
                }
            }
            for (Billable billableCollectionNewBillable : billableCollectionNew) {
                if (!billableCollectionOld.contains(billableCollectionNewBillable)) {
                    Consultant oldConsultantIdOfBillableCollectionNewBillable = billableCollectionNewBillable.getConsultantId();
                    billableCollectionNewBillable.setConsultantId(consultant);
                    billableCollectionNewBillable = em.merge(billableCollectionNewBillable);
                    if (oldConsultantIdOfBillableCollectionNewBillable != null && !oldConsultantIdOfBillableCollectionNewBillable.equals(consultant)) {
                        oldConsultantIdOfBillableCollectionNewBillable.getBillableCollection().remove(billableCollectionNewBillable);
                        oldConsultantIdOfBillableCollectionNewBillable = em.merge(oldConsultantIdOfBillableCollectionNewBillable);
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
                Integer id = consultant.getConsultantId();
                if (findConsultant(id) == null) {
                    throw new NonexistentEntityException("The consultant with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Consultant consultant;
            try {
                consultant = em.getReference(Consultant.class, id);
                consultant.getConsultantId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The consultant with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Billable> billableCollectionOrphanCheck = consultant.getBillableCollection();
            for (Billable billableCollectionOrphanCheckBillable : billableCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Consultant (" + consultant + ") cannot be destroyed since the Billable " + billableCollectionOrphanCheckBillable + " in its billableCollection field has a non-nullable consultantId field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Recruiter recruiterId = consultant.getRecruiterId();
            if (recruiterId != null) {
                recruiterId.getConsultantCollection().remove(consultant);
                recruiterId = em.merge(recruiterId);
            }
            ConsultantStatus statusId = consultant.getStatusId();
            if (statusId != null) {
                statusId.getConsultantCollection().remove(consultant);
                statusId = em.merge(statusId);
            }
            Collection<Project> projectCollection = consultant.getProjectCollection();
            for (Project projectCollectionProject : projectCollection) {
                projectCollectionProject.getConsultantCollection().remove(consultant);
                projectCollectionProject = em.merge(projectCollectionProject);
            }
            em.remove(consultant);
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

    public List<Consultant> findConsultantEntities() {
        return findConsultantEntities(true, -1, -1);
    }

    public List<Consultant> findConsultantEntities(int maxResults, int firstResult) {
        return findConsultantEntities(false, maxResults, firstResult);
    }

    private List<Consultant> findConsultantEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Consultant as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Consultant findConsultant(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Consultant.class, id);
        } finally {
            em.close();
        }
    }

    public int getConsultantCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Consultant as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

    // Code added

    public List<Consultant> findConsultantEntities(int maxResults, int firstResult, String query, Map<String, Object> parameters) {
        return findConsultantEntities(false, maxResults, firstResult, query, parameters);
    }

    private List<Consultant> findConsultantEntities(boolean all, int maxResults, int firstResult, String query, Map<String, Object> parameters) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery(query);
            if (parameters != null) {
                for (String name : parameters.keySet()) {
                    q.setParameter(name, parameters.get(name));
                }
            }
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        }
        finally {
            em.close();
        }
    }


}

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
import jpa.entities.Client;
import jpa.entities.Consultant;
import java.util.ArrayList;
import java.util.Collection;
import jpa.entities.Billable;
import jpa.entities.Project;
import jpa.entities.ProjectPK;

/**
 *
 * @author nb
 */
public class ProjectJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "ConsultingAgencyPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Project project) throws PreexistingEntityException, RollbackFailureException, Exception {
        if (project.getProjectPK() == null) {
            project.setProjectPK(new ProjectPK());
        }
        if (project.getConsultantCollection() == null) {
            project.setConsultantCollection(new ArrayList<Consultant>());
        }
        if (project.getBillableCollection() == null) {
            project.setBillableCollection(new ArrayList<Billable>());
        }
        project.getProjectPK().setClientDepartmentNumber(project.getClient().getClientPK().getClientDepartmentNumber());
        project.getProjectPK().setClientName(project.getClient().getClientPK().getClientName());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Client client = project.getClient();
            if (client != null) {
                client = em.getReference(client.getClass(), client.getClientPK());
                project.setClient(client);
            }
            Collection<Consultant> attachedConsultantCollection = new ArrayList<Consultant>();
            for (Consultant consultantCollectionConsultantToAttach : project.getConsultantCollection()) {
                consultantCollectionConsultantToAttach = em.getReference(consultantCollectionConsultantToAttach.getClass(), consultantCollectionConsultantToAttach.getConsultantId());
                attachedConsultantCollection.add(consultantCollectionConsultantToAttach);
            }
            project.setConsultantCollection(attachedConsultantCollection);
            Collection<Billable> attachedBillableCollection = new ArrayList<Billable>();
            for (Billable billableCollectionBillableToAttach : project.getBillableCollection()) {
                billableCollectionBillableToAttach = em.getReference(billableCollectionBillableToAttach.getClass(), billableCollectionBillableToAttach.getBillableId());
                attachedBillableCollection.add(billableCollectionBillableToAttach);
            }
            project.setBillableCollection(attachedBillableCollection);
            em.persist(project);
            if (client != null) {
                client.getProjectCollection().add(project);
                client = em.merge(client);
            }
            for (Consultant consultantCollectionConsultant : project.getConsultantCollection()) {
                consultantCollectionConsultant.getProjectCollection().add(project);
                consultantCollectionConsultant = em.merge(consultantCollectionConsultant);
            }
            for (Billable billableCollectionBillable : project.getBillableCollection()) {
                Project oldProjectOfBillableCollectionBillable = billableCollectionBillable.getProject();
                billableCollectionBillable.setProject(project);
                billableCollectionBillable = em.merge(billableCollectionBillable);
                if (oldProjectOfBillableCollectionBillable != null) {
                    oldProjectOfBillableCollectionBillable.getBillableCollection().remove(billableCollectionBillable);
                    oldProjectOfBillableCollectionBillable = em.merge(oldProjectOfBillableCollectionBillable);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findProject(project.getProjectPK()) != null) {
                throw new PreexistingEntityException("Project " + project + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Project project) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        project.getProjectPK().setClientDepartmentNumber(project.getClient().getClientPK().getClientDepartmentNumber());
        project.getProjectPK().setClientName(project.getClient().getClientPK().getClientName());
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Project persistentProject = em.find(Project.class, project.getProjectPK());
            Client clientOld = persistentProject.getClient();
            Client clientNew = project.getClient();
            Collection<Consultant> consultantCollectionOld = persistentProject.getConsultantCollection();
            Collection<Consultant> consultantCollectionNew = project.getConsultantCollection();
            Collection<Billable> billableCollectionOld = persistentProject.getBillableCollection();
            Collection<Billable> billableCollectionNew = project.getBillableCollection();
            List<String> illegalOrphanMessages = null;
            for (Billable billableCollectionOldBillable : billableCollectionOld) {
                if (!billableCollectionNew.contains(billableCollectionOldBillable)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Billable " + billableCollectionOldBillable + " since its project field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientNew != null) {
                clientNew = em.getReference(clientNew.getClass(), clientNew.getClientPK());
                project.setClient(clientNew);
            }
            Collection<Consultant> attachedConsultantCollectionNew = new ArrayList<Consultant>();
            for (Consultant consultantCollectionNewConsultantToAttach : consultantCollectionNew) {
                consultantCollectionNewConsultantToAttach = em.getReference(consultantCollectionNewConsultantToAttach.getClass(), consultantCollectionNewConsultantToAttach.getConsultantId());
                attachedConsultantCollectionNew.add(consultantCollectionNewConsultantToAttach);
            }
            consultantCollectionNew = attachedConsultantCollectionNew;
            project.setConsultantCollection(consultantCollectionNew);
            Collection<Billable> attachedBillableCollectionNew = new ArrayList<Billable>();
            for (Billable billableCollectionNewBillableToAttach : billableCollectionNew) {
                billableCollectionNewBillableToAttach = em.getReference(billableCollectionNewBillableToAttach.getClass(), billableCollectionNewBillableToAttach.getBillableId());
                attachedBillableCollectionNew.add(billableCollectionNewBillableToAttach);
            }
            billableCollectionNew = attachedBillableCollectionNew;
            project.setBillableCollection(billableCollectionNew);
            project = em.merge(project);
            if (clientOld != null && !clientOld.equals(clientNew)) {
                clientOld.getProjectCollection().remove(project);
                clientOld = em.merge(clientOld);
            }
            if (clientNew != null && !clientNew.equals(clientOld)) {
                clientNew.getProjectCollection().add(project);
                clientNew = em.merge(clientNew);
            }
            for (Consultant consultantCollectionOldConsultant : consultantCollectionOld) {
                if (!consultantCollectionNew.contains(consultantCollectionOldConsultant)) {
                    consultantCollectionOldConsultant.getProjectCollection().remove(project);
                    consultantCollectionOldConsultant = em.merge(consultantCollectionOldConsultant);
                }
            }
            for (Consultant consultantCollectionNewConsultant : consultantCollectionNew) {
                if (!consultantCollectionOld.contains(consultantCollectionNewConsultant)) {
                    consultantCollectionNewConsultant.getProjectCollection().add(project);
                    consultantCollectionNewConsultant = em.merge(consultantCollectionNewConsultant);
                }
            }
            for (Billable billableCollectionNewBillable : billableCollectionNew) {
                if (!billableCollectionOld.contains(billableCollectionNewBillable)) {
                    Project oldProjectOfBillableCollectionNewBillable = billableCollectionNewBillable.getProject();
                    billableCollectionNewBillable.setProject(project);
                    billableCollectionNewBillable = em.merge(billableCollectionNewBillable);
                    if (oldProjectOfBillableCollectionNewBillable != null && !oldProjectOfBillableCollectionNewBillable.equals(project)) {
                        oldProjectOfBillableCollectionNewBillable.getBillableCollection().remove(billableCollectionNewBillable);
                        oldProjectOfBillableCollectionNewBillable = em.merge(oldProjectOfBillableCollectionNewBillable);
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
                ProjectPK id = project.getProjectPK();
                if (findProject(id) == null) {
                    throw new NonexistentEntityException("The project with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ProjectPK id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Project project;
            try {
                project = em.getReference(Project.class, id);
                project.getProjectPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The project with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Billable> billableCollectionOrphanCheck = project.getBillableCollection();
            for (Billable billableCollectionOrphanCheckBillable : billableCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Project (" + project + ") cannot be destroyed since the Billable " + billableCollectionOrphanCheckBillable + " in its billableCollection field has a non-nullable project field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Client client = project.getClient();
            if (client != null) {
                client.getProjectCollection().remove(project);
                client = em.merge(client);
            }
            Collection<Consultant> consultantCollection = project.getConsultantCollection();
            for (Consultant consultantCollectionConsultant : consultantCollection) {
                consultantCollectionConsultant.getProjectCollection().remove(project);
                consultantCollectionConsultant = em.merge(consultantCollectionConsultant);
            }
            em.remove(project);
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

    public List<Project> findProjectEntities() {
        return findProjectEntities(true, -1, -1);
    }

    public List<Project> findProjectEntities(int maxResults, int firstResult) {
        return findProjectEntities(false, maxResults, firstResult);
    }

    private List<Project> findProjectEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Project as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Project findProject(ProjectPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Project.class, id);
        } finally {
            em.close();
        }
    }

    public int getProjectCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Project as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

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
import jpa.entities.ClientPK;
import jpa.entities.Recruiter;
import jpa.entities.Address;
import jpa.entities.Project;
import java.util.ArrayList;
import java.util.Collection;

/**
 *
 * @author nb
 */
public class ClientJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "ConsultingAgencyPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Client client) throws IllegalOrphanException, PreexistingEntityException, RollbackFailureException, Exception {
        if (client.getClientPK() == null) {
            client.setClientPK(new ClientPK());
        }
        if (client.getProjectCollection() == null) {
            client.setProjectCollection(new ArrayList<Project>());
        }
        List<String> illegalOrphanMessages = null;
        Address billingAddressOrphanCheck = client.getBillingAddress();
        if (billingAddressOrphanCheck != null) {
            Client oldClientOfBillingAddress = billingAddressOrphanCheck.getClient();
            if (oldClientOfBillingAddress != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("The Address " + billingAddressOrphanCheck + " already has an item of type Client whose billingAddress column cannot be null. Please make another selection for the billingAddress field.");
            }
        }
        if (illegalOrphanMessages != null) {
            throw new IllegalOrphanException(illegalOrphanMessages);
        }
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Recruiter recruiter = client.getRecruiter();
            if (recruiter != null) {
                recruiter = em.getReference(recruiter.getClass(), recruiter.getRecruiterId());
                client.setRecruiter(recruiter);
            }
            Address billingAddress = client.getBillingAddress();
            if (billingAddress != null) {
                billingAddress = em.getReference(billingAddress.getClass(), billingAddress.getAddressId());
                client.setBillingAddress(billingAddress);
            }
            Collection<Project> attachedProjectCollection = new ArrayList<Project>();
            for (Project projectCollectionProjectToAttach : client.getProjectCollection()) {
                projectCollectionProjectToAttach = em.getReference(projectCollectionProjectToAttach.getClass(), projectCollectionProjectToAttach.getProjectPK());
                attachedProjectCollection.add(projectCollectionProjectToAttach);
            }
            client.setProjectCollection(attachedProjectCollection);
            em.persist(client);
            if (recruiter != null) {
                Client oldClientOfRecruiter = recruiter.getClient();
                if (oldClientOfRecruiter != null) {
                    oldClientOfRecruiter.setRecruiter(null);
                    oldClientOfRecruiter = em.merge(oldClientOfRecruiter);
                }
                recruiter.setClient(client);
                recruiter = em.merge(recruiter);
            }
            if (billingAddress != null) {
                billingAddress.setClient(client);
                billingAddress = em.merge(billingAddress);
            }
            for (Project projectCollectionProject : client.getProjectCollection()) {
                Client oldClientOfProjectCollectionProject = projectCollectionProject.getClient();
                projectCollectionProject.setClient(client);
                projectCollectionProject = em.merge(projectCollectionProject);
                if (oldClientOfProjectCollectionProject != null) {
                    oldClientOfProjectCollectionProject.getProjectCollection().remove(projectCollectionProject);
                    oldClientOfProjectCollectionProject = em.merge(oldClientOfProjectCollectionProject);
                }
            }
            utx.commit();
        } catch (Exception ex) {
            try {
                utx.rollback();
            } catch (Exception re) {
                throw new RollbackFailureException("An error occurred attempting to roll back the transaction.", re);
            }
            if (findClient(client.getClientPK()) != null) {
                throw new PreexistingEntityException("Client " + client + " already exists.", ex);
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void edit(Client client) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Client persistentClient = em.find(Client.class, client.getClientPK());
            Recruiter recruiterOld = persistentClient.getRecruiter();
            Recruiter recruiterNew = client.getRecruiter();
            Address billingAddressOld = persistentClient.getBillingAddress();
            Address billingAddressNew = client.getBillingAddress();
            Collection<Project> projectCollectionOld = persistentClient.getProjectCollection();
            Collection<Project> projectCollectionNew = client.getProjectCollection();
            List<String> illegalOrphanMessages = null;
            if (billingAddressNew != null && !billingAddressNew.equals(billingAddressOld)) {
                Client oldClientOfBillingAddress = billingAddressNew.getClient();
                if (oldClientOfBillingAddress != null) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("The Address " + billingAddressNew + " already has an item of type Client whose billingAddress column cannot be null. Please make another selection for the billingAddress field.");
                }
            }
            for (Project projectCollectionOldProject : projectCollectionOld) {
                if (!projectCollectionNew.contains(projectCollectionOldProject)) {
                    if (illegalOrphanMessages == null) {
                        illegalOrphanMessages = new ArrayList<String>();
                    }
                    illegalOrphanMessages.add("You must retain Project " + projectCollectionOldProject + " since its client field is not nullable.");
                }
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (recruiterNew != null) {
                recruiterNew = em.getReference(recruiterNew.getClass(), recruiterNew.getRecruiterId());
                client.setRecruiter(recruiterNew);
            }
            if (billingAddressNew != null) {
                billingAddressNew = em.getReference(billingAddressNew.getClass(), billingAddressNew.getAddressId());
                client.setBillingAddress(billingAddressNew);
            }
            Collection<Project> attachedProjectCollectionNew = new ArrayList<Project>();
            for (Project projectCollectionNewProjectToAttach : projectCollectionNew) {
                projectCollectionNewProjectToAttach = em.getReference(projectCollectionNewProjectToAttach.getClass(), projectCollectionNewProjectToAttach.getProjectPK());
                attachedProjectCollectionNew.add(projectCollectionNewProjectToAttach);
            }
            projectCollectionNew = attachedProjectCollectionNew;
            client.setProjectCollection(projectCollectionNew);
            client = em.merge(client);
            if (recruiterOld != null && !recruiterOld.equals(recruiterNew)) {
                recruiterOld.setClient(null);
                recruiterOld = em.merge(recruiterOld);
            }
            if (recruiterNew != null && !recruiterNew.equals(recruiterOld)) {
                Client oldClientOfRecruiter = recruiterNew.getClient();
                if (oldClientOfRecruiter != null) {
                    oldClientOfRecruiter.setRecruiter(null);
                    oldClientOfRecruiter = em.merge(oldClientOfRecruiter);
                }
                recruiterNew.setClient(client);
                recruiterNew = em.merge(recruiterNew);
            }
            if (billingAddressOld != null && !billingAddressOld.equals(billingAddressNew)) {
                billingAddressOld.setClient(null);
                billingAddressOld = em.merge(billingAddressOld);
            }
            if (billingAddressNew != null && !billingAddressNew.equals(billingAddressOld)) {
                billingAddressNew.setClient(client);
                billingAddressNew = em.merge(billingAddressNew);
            }
            for (Project projectCollectionNewProject : projectCollectionNew) {
                if (!projectCollectionOld.contains(projectCollectionNewProject)) {
                    Client oldClientOfProjectCollectionNewProject = projectCollectionNewProject.getClient();
                    projectCollectionNewProject.setClient(client);
                    projectCollectionNewProject = em.merge(projectCollectionNewProject);
                    if (oldClientOfProjectCollectionNewProject != null && !oldClientOfProjectCollectionNewProject.equals(client)) {
                        oldClientOfProjectCollectionNewProject.getProjectCollection().remove(projectCollectionNewProject);
                        oldClientOfProjectCollectionNewProject = em.merge(oldClientOfProjectCollectionNewProject);
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
                ClientPK id = client.getClientPK();
                if (findClient(id) == null) {
                    throw new NonexistentEntityException("The client with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally {
            if (em != null) {
                em.close();
            }
        }
    }

    public void destroy(ClientPK id) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Client client;
            try {
                client = em.getReference(Client.class, id);
                client.getClientPK();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The client with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Collection<Project> projectCollectionOrphanCheck = client.getProjectCollection();
            for (Project projectCollectionOrphanCheckProject : projectCollectionOrphanCheck) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Client (" + client + ") cannot be destroyed since the Project " + projectCollectionOrphanCheckProject + " in its projectCollection field has a non-nullable client field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            Recruiter recruiter = client.getRecruiter();
            if (recruiter != null) {
                recruiter.setClient(null);
                recruiter = em.merge(recruiter);
            }
            Address billingAddress = client.getBillingAddress();
            if (billingAddress != null) {
                billingAddress.setClient(null);
                billingAddress = em.merge(billingAddress);
            }
            em.remove(client);
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

    public List<Client> findClientEntities() {
        return findClientEntities(true, -1, -1);
    }

    public List<Client> findClientEntities(int maxResults, int firstResult) {
        return findClientEntities(false, maxResults, firstResult);
    }

    private List<Client> findClientEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Client as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Client findClient(ClientPK id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Client.class, id);
        } finally {
            em.close();
        }
    }

    public int getClientCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Client as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

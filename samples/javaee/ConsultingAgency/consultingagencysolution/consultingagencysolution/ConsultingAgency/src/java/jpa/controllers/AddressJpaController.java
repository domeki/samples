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
import jpa.entities.Address;
import jpa.entities.Client;
import java.util.ArrayList;

/**
 *
 * @author nb
 */
public class AddressJpaController {
    @Resource
    private UserTransaction utx = null;
    @PersistenceUnit(unitName = "ConsultingAgencyPU")
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager() {
        return emf.createEntityManager();
    }

    public void create(Address address) throws RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Client client = address.getClient();
            if (client != null) {
                client = em.getReference(client.getClass(), client.getClientPK());
                address.setClient(client);
            }
            em.persist(address);
            if (client != null) {
                Address oldBillingAddressOfClient = client.getBillingAddress();
                if (oldBillingAddressOfClient != null) {
                    oldBillingAddressOfClient.setClient(null);
                    oldBillingAddressOfClient = em.merge(oldBillingAddressOfClient);
                }
                client.setBillingAddress(address);
                client = em.merge(client);
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

    public void edit(Address address) throws IllegalOrphanException, NonexistentEntityException, RollbackFailureException, Exception {
        EntityManager em = null;
        try {
            utx.begin();
            em = getEntityManager();
            Address persistentAddress = em.find(Address.class, address.getAddressId());
            Client clientOld = persistentAddress.getClient();
            Client clientNew = address.getClient();
            List<String> illegalOrphanMessages = null;
            if (clientOld != null && !clientOld.equals(clientNew)) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("You must retain Client " + clientOld + " since its billingAddress field is not nullable.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            if (clientNew != null) {
                clientNew = em.getReference(clientNew.getClass(), clientNew.getClientPK());
                address.setClient(clientNew);
            }
            address = em.merge(address);
            if (clientNew != null && !clientNew.equals(clientOld)) {
                Address oldBillingAddressOfClient = clientNew.getBillingAddress();
                if (oldBillingAddressOfClient != null) {
                    oldBillingAddressOfClient.setClient(null);
                    oldBillingAddressOfClient = em.merge(oldBillingAddressOfClient);
                }
                clientNew.setBillingAddress(address);
                clientNew = em.merge(clientNew);
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
                Integer id = address.getAddressId();
                if (findAddress(id) == null) {
                    throw new NonexistentEntityException("The address with id " + id + " no longer exists.");
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
            Address address;
            try {
                address = em.getReference(Address.class, id);
                address.getAddressId();
            } catch (EntityNotFoundException enfe) {
                throw new NonexistentEntityException("The address with id " + id + " no longer exists.", enfe);
            }
            List<String> illegalOrphanMessages = null;
            Client clientOrphanCheck = address.getClient();
            if (clientOrphanCheck != null) {
                if (illegalOrphanMessages == null) {
                    illegalOrphanMessages = new ArrayList<String>();
                }
                illegalOrphanMessages.add("This Address (" + address + ") cannot be destroyed since the Client " + clientOrphanCheck + " in its client field has a non-nullable billingAddress field.");
            }
            if (illegalOrphanMessages != null) {
                throw new IllegalOrphanException(illegalOrphanMessages);
            }
            em.remove(address);
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

    public List<Address> findAddressEntities() {
        return findAddressEntities(true, -1, -1);
    }

    public List<Address> findAddressEntities(int maxResults, int firstResult) {
        return findAddressEntities(false, maxResults, firstResult);
    }

    private List<Address> findAddressEntities(boolean all, int maxResults, int firstResult) {
        EntityManager em = getEntityManager();
        try {
            Query q = em.createQuery("select object(o) from Address as o");
            if (!all) {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally {
            em.close();
        }
    }

    public Address findAddress(Integer id) {
        EntityManager em = getEntityManager();
        try {
            return em.find(Address.class, id);
        } finally {
            em.close();
        }
    }

    public int getAddressCount() {
        EntityManager em = getEntityManager();
        try {
            return ((Long) em.createQuery("select count(o) from Address as o").getSingleResult()).intValue();
        } finally {
            em.close();
        }
    }

}

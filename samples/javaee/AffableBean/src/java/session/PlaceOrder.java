/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package session;

import cart.ShoppingCart;
import cart.ShoppingCartItem;
import entity.Customer;
import entity.CustomerOrder;
import entity.OrderHasProduct;
import entity.OrderHasProductPK;
import java.math.BigDecimal;
import java.util.Iterator;
import java.util.Random;
import javax.annotation.Resource;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.ejb.TransactionManagement;
import javax.ejb.TransactionManagementType;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author troy
 */
@Stateless
@TransactionManagement(TransactionManagementType.CONTAINER)
public class PlaceOrder implements PlaceOrderLocal {

    @PersistenceContext(unitName = "AffableBeanPU")
    private EntityManager em;
    @Resource
    private SessionContext context;

    @TransactionAttribute(TransactionAttributeType.REQUIRED)
    public int placeOrder(String name, String email, String phone, String address, String cityRegion, String ccNumber, ShoppingCart cart) {

        try {
            Customer customer = addCustomer(name, email, phone, address, cityRegion, ccNumber);
            CustomerOrder order = addOrder(customer, cart);
            addOrderedItems(order, cart);
            return order.getId();
        } catch (Exception e) {
            context.setRollbackOnly();
            return 0;
        }
    }

    public Customer addCustomer(String name, String email, String phone, String address, String cityRegion, String ccNumber) {

        Customer customer = new Customer();
        customer.setName(name);
        customer.setEmail(email);
        customer.setPhone(phone);
        customer.setAddress(address);
        customer.setCityRegion(cityRegion);
        customer.setCcNumber(ccNumber);

        em.persist(customer);
        return customer;
    }

    public CustomerOrder addOrder(Customer customer, ShoppingCart cart) {

        // set up customer order
        CustomerOrder order = new CustomerOrder();
        order.setCustomerId(customer);
        order.setAmount(BigDecimal.valueOf(cart.getTotal()));

//        Random random = new Random();
//        int i = random.nextInt();
//        order.setConfirmationNumber(i);

        em.persist(order);
        return order;
    }

    public void addOrderedItems(CustomerOrder order, ShoppingCart cart) {

        em.flush();

        Iterator it = cart.getItems().keySet().iterator();

        // iterate through shopping cart and add items to OrderHasProduct
        while(it.hasNext()) {

            String s = (String)it.next();
            int productId = Integer.parseInt(s);

            // set up primary key object
            OrderHasProductPK orderedItemPK = new OrderHasProductPK();
            orderedItemPK.setOrderId(order.getId());
            orderedItemPK.setProductId(productId);

            // create ordered item using PK object
            OrderHasProduct orderedItem = new OrderHasProduct(orderedItemPK);

            // set quantity
            ShoppingCartItem item = (ShoppingCartItem) cart.getItems().get(String.valueOf(productId));
            orderedItem.setQuantity(String.valueOf(item.getQuantity()));

            em.persist(orderedItem);
        }
    }
}
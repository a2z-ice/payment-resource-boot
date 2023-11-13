package com.spring.rest.curd.dao;

import com.spring.rest.curd.model.Payment;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class PaymentDaoImpl implements PaymentDao {

	@PersistenceContext
	private EntityManager entityManager;

	@Override
	public String payNow(Payment payment) {
		entityManager.persist(payment);
		return "Payment successfull with amount : " + payment.getAmount();
	}



	@SuppressWarnings("unchecked")
	@Override
	public List<Payment> getTransactionInfo(String vendor) {
		return findPaymentsByVendor(vendor);

	}

	public List<Payment> findPaymentsByVendor(String vendor) {
		// Obtain an instance of CriteriaBuilder from the EntityManager
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		// Create an instance of CriteriaQuery for the Payment entity
		CriteriaQuery<Payment> cq = cb.createQuery(Payment.class);

		// Define the root of the query (i.e., Payment)
		Root<Payment> payment = cq.from(Payment.class);



		// Build the query
		cq.select(payment).where(cb.equal(payment.get("vendor"), vendor));

		// Execute the query
		return entityManager.createQuery(cq).getResultList();
	}


}

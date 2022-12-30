package com.waiyanhtet.entity;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import javax.persistence.PersistenceException;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.waiyanhtet.entity.Member.Role;

@TestMethodOrder(OrderAnnotation.class)
public class PersistOpertaionTest extends OperationTest {

	@Order(1)
	@Test
	void transient_to_persit_test() {
		var em = emf.createEntityManager();
		em.getTransaction().begin();
		
		var member = new Member("William", "william", "william");
		em.persist(member);
		
		assertTrue(em.contains(member));
		
		em.getTransaction().commit();
	}
	
	@Order(2)
	@Test
	void manage_to_persit_test() {
		var em = emf.createEntityManager();
		em.getTransaction().begin();
				
		var  member = em.find(Member.class, 2);
		member.setRole(Role.Admin);
		
		em.persist(member);
		assertTrue(em.contains(member));

		em.getTransaction().commit();
	}
	
	@Order(3)
	@Test
	void detached_to_persit_test() {
		var em = emf.createEntityManager();
		em.getTransaction().begin();
				
		var  member = em.find(Member.class, 2);
		em.detach(member);
		member.setRole(Role.Member);
		
		assertThrows(PersistenceException.class, () -> em.persist(member));

		em.getTransaction().commit();
	}
	
	@Order(4)
	@Test
	void removed_to_persit_test() {
		var em = emf.createEntityManager();
		em.getTransaction().begin();
				
		var  member = em.find(Member.class, 2);
		em.remove(member);
		member.setRole(Role.Member);
		
		//assertThrows(PersistenceException.class, () -> em.persist(member));
		
		em.persist(member);
		assertTrue(em.contains(member));

		em.getTransaction().commit();
	}
}

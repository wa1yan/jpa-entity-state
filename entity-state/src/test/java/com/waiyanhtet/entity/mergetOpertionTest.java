package com.waiyanhtet.entity;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;

import com.waiyanhtet.entity.Member.Role;

public class mergetOpertionTest extends OperationTest {
	@Order(1)
	@Test
	void transient_to_merge_test() {
		var em = emf.createEntityManager();
		em.getTransaction().begin();
		
		var member = new Member("William", "william", "william");
		member = em.merge(member);
		
		assertTrue(em.contains(member));
		
		em.getTransaction().commit();
	}
	
	@Order(2)
	@Test
	void manage_to_merge_test() {
		var em = emf.createEntityManager();
		em.getTransaction().begin();
				
		var  member = em.find(Member.class, 1);
		member.setRole(Role.Admin);
		
		member = em.merge(member);
		assertTrue(em.contains(member));

		em.getTransaction().commit();
	}
	
	@Order(3)
	@Test
	void detached_to_merge_test() {
		var em = emf.createEntityManager();
		
		var  member = em.find(Member.class, 1);
		em.detach(member);
		
		assertFalse(em.contains(member));


		em.getTransaction().begin();
		member = em.merge(member);
		em.getTransaction().commit();
		
		assertTrue(em.contains(member));

	}
	
	@Order(4)
	@Test
	void removed_to_merge_test() {
		var em = emf.createEntityManager();
		em.getTransaction().begin();
				
		var  member = em.find(Member.class, 1);
		em.remove(member);
		member.setRole(Role.Member);
				
		assertThrows(IllegalArgumentException.class, ()-> em.merge(member));

		em.getTransaction().commit();
	}
}

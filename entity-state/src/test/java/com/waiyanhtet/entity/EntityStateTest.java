package com.waiyanhtet.entity;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.hibernate.LazyInitializationException;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.waiyanhtet.entity.Member.Role;

@TestMethodOrder(OrderAnnotation.class)
public class EntityStateTest {
	
	private static EntityManagerFactory emf;

	@BeforeAll
	static void init() {
		emf = Persistence.createEntityManagerFactory("entity-state");
	}
	
	@AfterAll
	static void close() {
		if(null != emf && emf.isOpen()) {
			emf.close();
		}
	}
	
	@Order(1)
	@Test
	void test() {
		var em = emf.createEntityManager();
		var member = new Member("William", "william", "william");

		em.getTransaction().begin();
		
		em.persist(member);
		
		assertEquals(2, member.getId());
		assertEquals("Member", member.getRole().toString().toString());
		
		em.detach(member);
		member = em.merge(member);

		member.setRole(Role.Admin);
		assertEquals("Admin", member.getRole().toString());

		
		em.getTransaction().commit();
	}
	
	@Order(2)
	@Test
	void find_test() {
		var em = emf.createEntityManager();
		var member = em.find(Member.class, 1);
		assertNotNull(member);
		em.detach(member);
		
		assertNotNull(member.getId());
	}
	
	@Order(3)
	@Test
	void find_reference_test() {
		var em = emf.createEntityManager();
		var member = em.getReference(Member.class, 1);
		assertNotNull(member);
		em.detach(member);
		
		assertThrows(LazyInitializationException.class, () -> member.getName());
	}
	
	@Order(4)
	@Test
	void fetch_mode_test() {
		var em = emf.createEntityManager();
		var member = em.getReference(Member.class, 1);
		assertNotNull(member);
		em.detach(member);
		
		assertThrows(LazyInitializationException.class, () -> member.getName());
	}
}

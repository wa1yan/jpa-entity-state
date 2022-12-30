package com.waiyanhtet.entity;

import org.junit.jupiter.api.Test;

public class CascadeOperationTest extends OperationTest {

//	@Test
//	void test_parent_persist() {
//		var em = emf.createEntityManager();
//		em.getTransaction().begin();
//		
//		var member = new Member("Mg Mg", "mgmg","mgmg");
//		var contact = new Contact("0948593843","mgmg@gmail.com");
//		member.addContacts(contact);
//		
//		em.persist(member);
//		em.getTransaction().commit();
//	}
	
	@Test
	void test_child_persist() {
		var em = emf.createEntityManager();
		em.getTransaction().begin();
		
		var member = new Member("Mg Mg", "mgmg","mgmg");
		var contact = new Contact("0948593843","mgmg@gmail.com");
		member.addContacts(contact);
		
		em.persist(contact);
		em.getTransaction().commit();
	}
}

package com.waiyanhtet.entity;

import org.junit.jupiter.api.Test;

public class FlushAndRefreshTest extends OperationTest {

	@Test
	void test1() {
	
		var thread1 = getThreadOne();
		var thread2 = getThreadTwo();
		
		thread1.start();
		thread2.start();

		try {
			Thread.sleep(1500);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Thread getThreadOne() {
		return new Thread(() -> {
			System.out.println("Start Thread 1");

			var em = emf.createEntityManager();
			var account = em.find(Account.class, 1);

			try {
				Thread.sleep(500);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Before Operation 1 : " + account.getName() + " " + account.getBalance());
			em.getTransaction().begin();

			
			account.setBalance(account.getBalance() + 10000);
			em.flush();
			
			System.out.println("After Operation 1 : " + account.getName() + " " +  account.getBalance());

			em.getTransaction().commit();
		}

		);
	}

	private Thread getThreadTwo() {
		return new Thread(() -> {
			System.out.println("Start Thread 2");

			var em = emf.createEntityManager();
			var account = em.find(Account.class, 1);

			try {
				Thread.sleep(800);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}

			System.out.println("Before Operation 2 : " + account.getName()  + " " + account.getBalance());
			em.getTransaction().begin();

			em.refresh(account);
			System.out.println("Before Operation 2 After refresh: " + account.getName()  + " " + account.getBalance());

			account.setBalance(account.getBalance() - 5000);
			System.out.println("After Operation 2 : " + account.getName()  + " " +  account.getBalance());

			em.getTransaction().commit();
		}

		);
	}

}

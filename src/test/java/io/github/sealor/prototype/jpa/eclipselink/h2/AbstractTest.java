package io.github.sealor.prototype.jpa.eclipselink.h2;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.Persistence;

import org.junit.After;
import org.junit.Before;

public class AbstractTest {

	protected EntityManagerFactory emFactory;
	protected EntityManager em;
	protected EntityTransaction transaction;

	@Before
	public void setUp() throws Exception {
		this.emFactory = Persistence.createEntityManagerFactory("test");
		this.em = this.emFactory.createEntityManager();
	}

	@After
	public void tearDown() throws Exception {
		this.em.close();
		this.emFactory.close();
	}

	protected void beginTransaction() {
		this.transaction = this.em.getTransaction();
		this.transaction.begin();
	}

	protected void commitTransaction() {
		this.transaction.commit();
		this.transaction = null;
	}

	protected void rollbackTransaction() {
		this.transaction.rollback();
		this.transaction = null;
	}
}

package io.github.sealor.prototype.jpa.eclipselink.h2;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.persistence.RollbackException;

import org.junit.Test;

public class SimpleEntityTest extends AbstractTest {

	@Test
	public void testPersist() {
		beginTransaction();

		SimpleEntity simpleEntity1 = new SimpleEntity();
		simpleEntity1.setText("stefan");

		this.em.persist(simpleEntity1);

		commitTransaction();
		beginTransaction();

		String jpql = "SELECT se FROM SimpleEntity se WHERE se.id = 1";
		SimpleEntity simpleEntity2 = this.em.createQuery(jpql, SimpleEntity.class).getSingleResult();

		assertEquals(simpleEntity1.getId(), simpleEntity2.getId());
		assertEquals("stefan", simpleEntity2.getText());

		commitTransaction();
	}

	@Test
	public void testTextLength() {
		beginTransaction();
		SimpleEntity simpleEntity1 = new SimpleEntity();
		simpleEntity1.setText("123456789");
		this.em.persist(simpleEntity1);
		commitTransaction();

		try {
			beginTransaction();
			SimpleEntity simpleEntity2 = new SimpleEntity();
			simpleEntity2.setText("1234567890");
			this.em.persist(simpleEntity2);
			commitTransaction();

			fail();
		} catch (RollbackException e) {
			assertFalse(this.transaction.isActive());
			assertTrue(e.getMessage().contains("Value too long for column"));
		}
	}
}

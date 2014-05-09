package io.github.sealor.prototype.jpa.eclipselink.h2;

import static org.junit.Assert.assertEquals;

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
}

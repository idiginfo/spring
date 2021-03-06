package org.idiginfo.docsvc.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import javax.annotation.Resource;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.idiginfo.docsvc.jpa.citagora.PersonImpl;
import org.idiginfo.docsvc.jpa.citagora.ReferenceImpl;
import org.idiginfo.docsvc.model.citagora.Reference;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.transaction.annotation.Transactional;

@ContextConfiguration
@RunWith(SpringJUnit4ClassRunner.class)
@Service
public class ReferenceInjectionTests {

	// @PersistenceContext
	// private EntityManager entityManager;

	// @Autowired
	// @Qualifier("referenceImplTest")
	@Resource(name = "referenceImplTest")
	private Reference ref;

	public Reference getRef() {
		return ref;
	}

	public void setRef(Reference ref) {
		this.ref = ref;
	}

	@Test
	public void testInjection() throws Exception {

		assertEquals(true, ref instanceof ReferenceImplTest);

		// assertEquals(1, other.getItems().size());
		// arrayEquals(other, other.getItems().iterator().next().getOrder());
	}

}

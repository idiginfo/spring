package org.idiginfo.docsvc.test;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.idiginfo.docsvc.factories.CitagoraFactory;
import org.idiginfo.docsvc.jpa.citagora.ReferenceImpl;
import org.idiginfo.docsvc.model.citagora.Author;
import org.idiginfo.docsvc.model.citagora.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ReferencePersistenceTest {

	@PersistenceContext
	private EntityManager entityManager;
	// @PersistenceContext
	private EntityManager entityManager2;

	@Autowired
	CitagoraFactory factory;

	public static void main(String[] args) throws Exception {

		ApplicationContext context = new ClassPathXmlApplicationContext(
				"META-INF/spring/app-context.xml");

		ReferencePersistenceTest test = (ReferencePersistenceTest) context
				.getBean("referencePersistenceTest");
		//test = new ReferencePersistenceTest();
		test.testSaveOrderWithItems();
		// test.testSaveAndGet();

	}

	@Transactional
	public void testSaveOrderWithItems() throws Exception {
		Reference ref = factory.createReference();
		System.out.println("id before persist: "+ ref.getMyId());
		entityManager.persist(ref);
		//entityManager.flush();
		if (ref.getMyId() == null) {
			System.out.println("id is null");
		} else {
			System.out.println("id is " + ref.getMyId());
		}
		if (entityManager == entityManager2) {
			System.out.println("entity managers are the same");
		}
	}

	@Transactional
	public void testSaveAndGet() throws Exception {
		Reference ref = factory.createReference();
		Author author = factory.createAuthor();
		ref.addAuthor(author);
		entityManager.persist(ref);
		entityManager.flush();
		// Otherwise the query returns the existing order (and we didn't set the
		// parent in the item)...
		entityManager.clear();
		Reference other = (Reference) entityManager.find(ReferenceImpl.class,
				ref.getMyId());
		if (other.getAuthors().size() != 1) {

		}
		if (other == other.getAuthors().iterator().next().getAuthorReferences()
				.get(0)) {

		}
	}

}

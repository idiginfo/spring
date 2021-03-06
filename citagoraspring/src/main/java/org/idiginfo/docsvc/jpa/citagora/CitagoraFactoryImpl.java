package org.idiginfo.docsvc.jpa.citagora;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.Persistence;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceException;
import javax.persistence.Query;
import javax.persistence.RollbackException;

import org.idiginfo.docsvc.factories.CitagoraFactory;
import org.idiginfo.docsvc.model.citagora.Author;
import org.idiginfo.docsvc.model.citagora.CitagoraAgent;
import org.idiginfo.docsvc.model.citagora.CitagoraObject;
import org.idiginfo.docsvc.model.citagora.Comment;
import org.idiginfo.docsvc.model.citagora.Container;
import org.idiginfo.docsvc.model.citagora.HarvestResult;
import org.idiginfo.docsvc.model.citagora.Person;
import org.idiginfo.docsvc.model.citagora.RatingType;
import org.idiginfo.docsvc.model.citagora.Reference;
import org.idiginfo.docsvc.model.citagora.Reply;
import org.idiginfo.docsvc.model.citagora.Review;
import org.idiginfo.docsvc.model.citagora.Tag;
import org.idiginfo.docsvc.model.citagora.UriObject;
import org.springframework.stereotype.Service;

/**
 * Class to support Citagora object creation and persistence management
 * 
 * @author griccardi
 * 
 */
@Service
public class CitagoraFactoryImpl extends CitagoraFactory {
	//private EntityManagerFactory emf;
	@PersistenceContext
	private EntityManager em;

	public CitagoraFactoryImpl() {
		factory = this;
	}

	public CitagoraFactoryImpl(boolean initEntityManager) {
		this();
		if (initEntityManager)
			init();
	}

	@Override
	public boolean commitTransaction() {
		EntityTransaction t = em.getTransaction();
		if (!t.isActive())
			return false;
		try {
			t.commit();
			return true;
		} catch (RollbackException e) {
			return false;
		}
	}

	@Override
	public Author createAuthor() {
		Author person = new PersonImpl(Author.class);
		return person;
	}

	@Override
	public CitagoraAgent createCitagoraAgent() {
		CitagoraAgent agent = new PersonImpl(CitagoraAgent.class);
		return agent;
	}

	@Override
	public Container createContainer() {
		return new ContainerImpl();
	}

	@Override
	public Comment createComment() {
		return new CommentImpl();
	}

	@Override
	public Person createPerson() {
		return new PersonImpl();
	}

	@Override
	public Person createPerson(Class<?> subclass) {
		// TODO Auto-generated method stub
		return new PersonImpl(subclass);
	}

	@Override
	public RatingType createRatingType(String type) {
		return new RatingTypeImpl(type);
	}

	@Override
	public Reference createReference() {
		return new ReferenceImpl();
	}

	@Override
	public Reply createReply() {
		return new ReplyImpl();
	}

	@Override
	public Review createReview() {
		return new ReviewImpl();
	}

	@Override
	public Tag createTag() {
		return new TagImpl();
	}

	@Override
	public Author findAuthor(int key) {
		PersonImpl person = (PersonImpl) findPerson(key);
		if (person == null || !person.getIsAuthor())
			return null;
		return person;
	}

	@Override
	public CitagoraAgent findCitagoraAgent(int key) {
		Person person = findPerson(key);
		if (person == null)
			return null;
		if (person.getIsAgent())
			return (CitagoraAgent) person;
		return null;
	}

	@Override
	public Container findContainer(int key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	/**
	 * Find an object in the repository of unknown subtype of CitagoraObject
	 */
	public CitagoraObject findCitagoraObject(int key) {
		CitagoraObject obj = findObject(CitagoraObjectImpl.class, key);
		return obj;
	}

	@Override
	public Comment findComment(int key) {
		// TODO Auto-generated method stub
		return null;
	}

	/**
	 * Get an object from the repository of a specific type.
	 */
	protected <K> K findObject(Class<K> type, int key) {
		try {
			getEntityManager();
			K obj = em.find(type, key);
			return obj;
		} catch (PersistenceException e) {
			return null;
		}
	}

	@Override
	public Person findPerson(int key) {
		PersonImpl obj = findObject(PersonImpl.class, key);
		return obj;
	}

	@Override
	public RatingType findRatingType(int key) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Reference findReference(int key) {
		return findObject(ReferenceImpl.class, key);
	}

	public static final String REFERENCE_CLASS_NAME = ReferenceImpl.class
			.getCanonicalName();

	@SuppressWarnings("unchecked")
	// we know that the query returns the correct class
	@Override
	public List<Reference> findReferencesByDoi(String doi) {
		getEntityManager();
		Query q = em.createQuery("SELECT e FROM " + REFERENCE_CLASS_NAME
				+ " e WHERE e.doi=:doi");
		q.setParameter("doi", doi);
		List<Reference> references = q.getResultList();
		return references;
	}

	@Override
	public Reference findReferenceBySourceDoi(String source, String doi) {
		try {
			getEntityManager();
			Query q = em.createQuery("SELECT e FROM " + REFERENCE_CLASS_NAME
					+ " e WHERE e.doi=:doi and e.source=:refSource");
			q.setParameter("doi", doi);
			q.setParameter("refSource", source);
			Reference reference = (Reference) q.getSingleResult();
			return reference;
		} catch (NoResultException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	// we know that the query returns the correct class
	@Override
	public List<Reference> findReferencesById(String id) {
		getEntityManager();
		Query q = em.createQuery("SELECT e FROM " + REFERENCE_CLASS_NAME
				+ " e WHERE e.id=:id");
		q.setParameter("id", id);
		List<Reference> references = q.getResultList();
		return references;
	}

	@Override
	public Reply findReply(int key) {
		return findObject(ReplyImpl.class, key);
	}

	@Override
	public Review findReview(int key) {
		return findObject(ReviewImpl.class, key);
	}

	@Override
	public Tag findTag(int key) {
		return findObject(TagImpl.class, key);
	}

	public EntityManager getEntityManager() {
		if (em == null)
			init();
		return em;
	}

	@Override
	public void init() {
		// if (emf == null)
		// emf = Persistence.createEntityManagerFactory(persistence);
		// if (em != null)
		// em.close();
		// em = emf.createEntityManager();
	}

	@Override
	public void renewPersistence() {
		// if (em != null)
		// em.close();
		// em = null;
		// if (emf != null)
		// emf.close();
		// emf = null;
		init();
	}

	@Override
	public boolean openTransaction() {
		getEntityManager();
		EntityTransaction t = em.getTransaction();
		if (!t.isActive()) {
			t.begin();
		}
		return true;
	}

	/**
	 * Persist the object. If the transaction is not active, open it
	 */
	@Override
	public boolean merge(UriObject obj) {
		// test to see if object is already persistent

		boolean localTransaction = false;
		EntityManager em = getEntityManager();
		// if already managed, nothing required
		if (em.contains(obj))
			return true;
		if (obj.getMyId() != null) {
			em.merge(obj);
			return true;
		}
		// object is not managed and has no primary id
		EntityTransaction t = em.getTransaction();
		if (!t.isActive()) {
			localTransaction = true;
			t.begin();
		}
		if (em.contains(obj)) {
			em.merge(obj);
		} else {
			em.persist(obj);
			obj.getUri();
		}
		if (localTransaction) {
			try {
				t.commit();
			} catch (Exception e) {
				if (t.isActive())
					t.rollback();
			}
		}
		return true;
	}

	@Override
	public boolean rollbackTransaction() {
		EntityTransaction t = em.getTransaction();
		if (!t.isActive())
			return false;
		try {
			t.rollback();
			return true;
		} catch (PersistenceException e) {
			return false;
		}
	}

	@Override
	public void refresh(Object obj) {
		try {
			getEntityManager().refresh(obj);
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}
	}

	@Override
	public void flush() {
		try {
			getEntityManager().flush();
		} catch (Exception e) {
			e.printStackTrace(System.err);
		}

	}

	@Override
	public boolean isTransactionActive() {
		getEntityManager();
		EntityTransaction to = em.getTransaction();
		if (to == null)
			return false;
		return to.isActive();
	}

	@Override
	public Container createContainer(Container containerFields) {
		return new ContainerImpl(containerFields);
	}

	@Override
	/**
	 * Get the standard agent for the service
	 * create an agent if one is not present
	 */
	public CitagoraAgent getServiceAgent(String serviceName) {
		Person agent = getPerson(serviceName);
		if (agent instanceof CitagoraAgent)
			return (CitagoraAgent) agent;
		// create service agent and add it to database
		CitagoraAgent newAgent = createCitagoraAgent();
		newAgent.setName(serviceName);
		newAgent.setAccount(serviceName);
		merge(newAgent);
		return newAgent;
	}

	@Override
	public Person getPerson(String name) {
		try {
			getEntityManager();
			Query q = em.createQuery("SELECT p FROM "
					+ PersonImpl.class.getCanonicalName()
					+ " p WHERE p.name=:name");
			q.setParameter("name", name);
			Person person = (Person) q.getSingleResult();
			return person;
		} catch (PersistenceException e) {
			return null;
		}
	}

	@Override
	public CitagoraObject findCitagoraObjectByURI(String uri) {
		try {
			getEntityManager();
			Query q = em
					.createQuery("SELECT c FROM CitagoraObjectImpl c WHERE c.uri=:uri");
			q.setParameter("uri", uri);
			CitagoraObject obj = (CitagoraObject) q.getSingleResult();
			return obj;
		} catch (PersistenceException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HarvestResult> findHarvestResults(String doi) {
		try {
			getEntityManager();
			Query q = em
					.createQuery("SELECT h FROM HarvestImpl h WHERE h.doi=:doi");
			q.setParameter("doi", doi);
			List<HarvestResult> obj = q.getResultList();
			return obj;
		} catch (PersistenceException e) {
			return null;
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<HarvestResult> findHarvestResults(String source, String doi) {
		try {
			getEntityManager();
			Query q = em
					.createQuery("SELECT h FROM HarvestResultImpl h WHERE h.doi=:doi and h.source=:refSource");
			q.setParameter("doi", doi);
			q.setParameter("refSource", source);
			List<HarvestResult> obj = q.getResultList();
			return obj;
		} catch (PersistenceException e) {
			return null;
		}
	}

	@Override
	public HarvestResult createHarvestResult() {
		return new HarvestResultImpl();
	}

	@Override
	public HarvestResult createHarvestResult(String source, String identifier,
			Reference ref, String description, boolean success) {
		return HarvestResultImpl.createHarvestResult(source, identifier, ref,
				description, success);
	}

	@Override
	public boolean persist(Object entity) {
		try {
			em.persist(entity);
		} catch (Exception e) {
			return false;
		}
		return true;
	}

	@Override
	public boolean contains(Object entity) {
		try {
			return em.contains(entity);
		} catch (Exception e) {
			return false;
		}
	}

	@Override
	public int getNumMissingDois() {
		String query = "SELECT e FROM "
				+ CitagoraFactoryImpl.REFERENCE_CLASS_NAME
				+ " e WHERE e.doi is null and e.biboType='article' and e.authorString is not null and e.title is not null ";
		Long count = factory.getLongResult(query);
		if (count != null)
			return count.intValue();
		return 0;
	}

	@Override
	public String getReferenceClassName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Long getLongResult(String query) {
		Query q = em.createQuery(query);
		return (long) q.getResultList().size();
	}

	@Override
	public List<Reference> getMissingDois(int firstResult, int maxResults) {
		Query q = em.createQuery("SELECT e FROM "
				+ CitagoraFactoryImpl.REFERENCE_CLASS_NAME
				+ " e WHERE e.doi is null and e.biboType='article' and e.authorString is not null and e.title is not null ");
		q.setFirstResult(firstResult);
		q.setMaxResults(maxResults);
		@SuppressWarnings("unchecked")
		List<Reference> references = q.getResultList();
		return references;
	}

}

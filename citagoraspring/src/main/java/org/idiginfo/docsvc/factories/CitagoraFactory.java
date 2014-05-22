package org.idiginfo.docsvc.factories;

import java.util.List;

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
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Factory to create citagora objects
 * 
 * @author griccardi
 * @param <K>
 * 
 */

public abstract class CitagoraFactory {

	protected static String persistence = "local";

	static class FactoryObject {
		@Autowired
		public CitagoraFactory factory;
	}

	static FactoryObject factoryObject = new FactoryObject();
	protected static CitagoraFactory factory = factoryObject.factory;

	// static {
	// try {
	// Class<?> factoryClass = Class
	// .forName("org.idiginfo.docsvc.jpa.citagora.CitagoraFactoryImpl");
	// factory = (CitagoraFactory) factoryClass.getConstructor()
	// .newInstance();
	// } catch (Exception e) {
	// e.printStackTrace();
	// }
	// }

	public static CitagoraFactory getFactory() {
		return factory;
	}

	@Autowired
	public static void setFactory(CitagoraFactory factory) {
		CitagoraFactory.factory = factory;
		Boolean.getBoolean("use.nosql.storage");

	}

	public abstract void renewPersistence();

	public abstract Author createAuthor();

	public abstract Author findAuthor(int key);

	public abstract CitagoraAgent createCitagoraAgent();

	public abstract CitagoraAgent findCitagoraAgent(int key);

	public abstract Container createContainer();

	public abstract Container findContainer(int key);

	public abstract Comment createComment();

	public abstract Comment findComment(int key);

	public abstract Person createPerson();

	public abstract RatingType createRatingType(String type);

	public abstract RatingType findRatingType(int key);

	public abstract Reference createReference();

	public abstract Reference findReference(int key);

	public abstract Reply createReply();

	public abstract Reply findReply(int key);

	public abstract Review createReview();

	public abstract Review findReview(int key);

	public abstract Tag createTag();

	public abstract Tag findTag(int key);

	public abstract Person createPerson(Class<?> subclass);

	public abstract List<Reference> findReferencesByDoi(String doi);

	public abstract void init();

	public abstract boolean merge(UriObject obj);

	public abstract boolean isTransactionActive();

	public abstract boolean openTransaction();

	public abstract boolean commitTransaction();

	public abstract boolean rollbackTransaction();

	public abstract Person findPerson(int key);

	public abstract CitagoraObject findCitagoraObject(int key);

	public abstract void refresh(Object obj);

	public abstract void flush();

	public abstract Container createContainer(Container containerFields);

	public abstract CitagoraAgent getServiceAgent(String serviceName);

	public abstract Person getPerson(String name);

	public List<Reference> findReferencesById(String doi) {
		// TODO Auto-generated method stub
		return null;
	}

	public abstract CitagoraObject findCitagoraObjectByURI(String uri);

	public static void setPersistence(String persistence) {
		CitagoraFactory.persistence = persistence;
	}

	public static String getPersistence() {
		return persistence;
	}

	public abstract Reference findReferenceBySourceDoi(String source, String doi);

	public abstract List<HarvestResult> findHarvestResults(String doi);

	public abstract List<HarvestResult> findHarvestResults(String source,
			String doi);

	public abstract HarvestResult createHarvestResult();

	public abstract HarvestResult createHarvestResult(String source,
			String identifier, Reference ref, String description,
			boolean success);

	public abstract boolean persist(Object entity);

	public abstract boolean contains(Object entity);

	public abstract String getReferenceClassName();

	public abstract Long getLongResult(String query);

	public abstract List<Reference> getMissingDois(int firstResult,
			int numResults);

	public abstract int getNumMissingDois();
}

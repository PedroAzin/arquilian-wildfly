package org.conference.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import org.conference.model.Speaker;

public class SpeakerDao {

	@PersistenceContext(unitName = "conference-persistence-unit")
	private EntityManager em;
	
	

	public Speaker create(Speaker entity) {
		em.persist(entity);
		return entity;
	}

	public void deleteById(Long id) {
		Speaker entity = em.find(Speaker.class, id);
		em.remove(entity);
	}

	public Speaker findById(Long id) {
		TypedQuery<Speaker> findByIdQuery = em
				.createQuery("SELECT DISTINCT s FROM Speaker s WHERE s.id = :entityId ORDER BY s.id", Speaker.class);
		findByIdQuery.setParameter("entityId", id);
		Speaker entity;
		try {
			entity = findByIdQuery.getSingleResult();
		} catch (NoResultException nre) {
			entity = null;
		}
		return entity;
	}

	public List<Speaker> listAll(Integer startPosition, Integer maxResult) {
		TypedQuery<Speaker> findAllQuery = em.createQuery("SELECT DISTINCT s FROM Speaker s ORDER BY s.id",
				Speaker.class);
		if (startPosition != null) {
			findAllQuery.setFirstResult(startPosition);
		}
		if (maxResult != null) {
			findAllQuery.setMaxResults(maxResult);
		}
		final List<Speaker> results = findAllQuery.getResultList();
		return results;
	}

	public Speaker update(Long id, Speaker entity) {
		return em.merge(entity);
	}
}

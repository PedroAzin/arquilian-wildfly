package org.conference.rest;

import java.util.List;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.OptimisticLockException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriBuilder;

import org.conference.dao.SpeakerDao;
import org.conference.model.Speaker;

/**
 * 
 */
@Path("/speakers")
public class SpeakerEndpoint {

	@Inject
	SpeakerDao dao;

	@POST
	@Consumes("application/json")
	@Transactional
	public Response create(Speaker entity) {
		dao.create(entity);
		return Response
				.created(UriBuilder.fromResource(SpeakerEndpoint.class).path(String.valueOf(entity.getId())).build())
				.build();
	}

	@DELETE
	@Path("/{id:[0-9][0-9]*}")
	public Response deleteById(@PathParam("id") Long id) {
		Speaker entity = dao.findById(id);
		if (entity == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		dao.deleteById(id);
		return Response.noContent().build();
	}

	@GET
	@Path("/{id:[0-9][0-9]*}")
	@Produces("application/json")
	public Response findById(@PathParam("id") Long id) {
		return Response.ok(dao.findById(id)).build();
	}

	@GET
	@Produces("application/json")
	public List<Speaker> listAll(@QueryParam("start") Integer startPosition, @QueryParam("max") Integer maxResult) {
		return dao.listAll(startPosition, maxResult);
	}

	@PUT
	@Path("/{id:[0-9][0-9]*}")
	@Consumes("application/json")
	public Response update(@PathParam("id") Long id, Speaker entity) {
		if (entity == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (id == null) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		if (!id.equals(entity.getId())) {
			return Response.status(Status.CONFLICT).entity(entity).build();
		}
		if (dao.findById(id) == null) {
			return Response.status(Status.NOT_FOUND).build();
		}
		dao.update(id, entity);
		return Response.noContent().build();
	}
}

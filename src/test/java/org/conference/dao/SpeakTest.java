package org.conference.dao;

import javax.inject.Inject;
import javax.transaction.Transactional;

import org.conference.model.Speaker;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

@RunWith(Arquillian.class)
public class SpeakTest {

	@Deployment
	public static JavaArchive createDeployment() {
		return ShrinkWrap.create(JavaArchive.class).addAsResource("META-INF/persistence.xml").addClass(SpeakerDao.class)
				.addClass(Speaker.class)
				.addAsManifestResource(EmptyAsset.INSTANCE, "beans.xml");
	}

	@Inject
	SpeakerDao greeter;

	@Test
	@Transactional
	public void should_create_greeting() {
		
		Speaker speaker = new Speaker();
		Speaker create = greeter.create(speaker);
		
		Assert.assertNotNull(create.getId());
	}
}

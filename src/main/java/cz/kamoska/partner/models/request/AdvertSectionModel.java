package cz.kamoska.partner.models.request;

import cz.kamoska.partner.dao.interfaces.SectionDaoInterface;
import cz.kamoska.partner.entities.SectionEntity;

import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 18.10.12
 * Time: 20:46
 * To change this template use File | Settings | File Templates.
 */
@Model
public class AdvertSectionModel {

	@EJB
	private SectionDaoInterface sectionDaoInterface;


	public List<SectionEntity> getAll() {
		return sectionDaoInterface.findAll();
	}

}

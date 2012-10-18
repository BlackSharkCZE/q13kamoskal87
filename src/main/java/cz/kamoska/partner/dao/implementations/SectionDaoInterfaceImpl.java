package cz.kamoska.partner.dao.implementations;

import cz.kamoska.partner.dao.interfaces.SectionDaoInterface;
import cz.kamoska.partner.dao.template.DaoTemplate;
import cz.kamoska.partner.entities.SectionEntity;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 18.10.12
 * Time: 20:33
 * To change this template use File | Settings | File Templates.
 */
@Stateless
@EJB(name = "java:app/sectionDaoInterface", beanInterface = SectionDaoInterface.class)
public class SectionDaoInterfaceImpl extends DaoTemplate<SectionEntity> implements SectionDaoInterface {

	@Override
	public List<SectionEntity> findAll() {
		return findListByNamedQuery("SectionEntity.findAll", 0, -1, null);
	}

	@Override
	public List<SectionEntity> findAllAlwaysSelected() {
		return findListByNamedQuery("SectionEntity.findAllAlwaysSelected", 0, -1, null);

	}
}

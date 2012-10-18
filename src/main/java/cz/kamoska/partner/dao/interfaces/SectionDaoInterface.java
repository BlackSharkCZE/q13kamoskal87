package cz.kamoska.partner.dao.interfaces;

import cz.kamoska.partner.dao.template.DaoInterface;
import cz.kamoska.partner.entities.SectionEntity;

import javax.ejb.Local;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 4.10.12
 * Time: 21:25
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface SectionDaoInterface extends DaoInterface<SectionEntity> {

	List<SectionEntity> findAll();

	List<SectionEntity> findAllAlwaysSelected();

}

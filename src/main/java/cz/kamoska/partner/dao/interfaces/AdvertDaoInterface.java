package cz.kamoska.partner.dao.interfaces;

import cz.kamoska.partner.dao.template.DaoInterface;
import cz.kamoska.partner.entities.AdvertEntity;
import cz.kamoska.partner.enums.AdvertState;

import javax.ejb.Local;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 4.10.12
 * Time: 21:23
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface AdvertDaoInterface extends DaoInterface<AdvertEntity> {

	List<AdvertEntity> findAllWaitingToAck();

	Long getAdvertCountInState(AdvertState state);

	List<AdvertEntity> findLessUsedBySection(String section, int count, List<Integer> excludeIDList);


}

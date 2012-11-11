package cz.kamoska.partner.dao.interfaces;

import cz.kamoska.partner.dao.template.DaoInterface;
import cz.kamoska.partner.entities.AdvertAccessListDailyEntity;
import cz.kamoska.partner.entities.AdvertAccessListEntity;

import javax.ejb.Local;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 11.11.12
 * Time: 16:01
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface AdvertAccessListActualInterface extends DaoInterface<AdvertAccessListEntity> {
}

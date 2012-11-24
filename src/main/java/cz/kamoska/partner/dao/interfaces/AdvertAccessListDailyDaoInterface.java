package cz.kamoska.partner.dao.interfaces;

import cz.kamoska.partner.dao.template.DaoInterface;
import cz.kamoska.partner.entities.AdvertAccessListDailyEntity;

import javax.ejb.Local;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 24.11.12
 * Time: 20:45
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface AdvertAccessListDailyDaoInterface extends DaoInterface<AdvertAccessListDailyEntity> {

	Long getDisplayCountByFromDateAndToDate(Date fromDate, Date toDate);

}

package cz.kamoska.partner.dao.implementations;

import cz.kamoska.partner.dao.interfaces.AdvertDaoInterface;
import cz.kamoska.partner.dao.template.DaoTemplate;
import cz.kamoska.partner.entities.AdvertEntity;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 20.10.12
 * Time: 20:57
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class AdvertDaoInterfaceImpl extends DaoTemplate<AdvertEntity> implements AdvertDaoInterface {
}

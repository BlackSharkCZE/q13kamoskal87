package cz.kamoska.partner.dao.interfaces;

import cz.kamoska.partner.dao.template.DaoInterface;
import cz.kamoska.partner.entities.MessageEntity;

import javax.ejb.Local;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 4.10.12
 * Time: 21:24
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface MessageDaoInterface extends DaoInterface<MessageEntity> {
}

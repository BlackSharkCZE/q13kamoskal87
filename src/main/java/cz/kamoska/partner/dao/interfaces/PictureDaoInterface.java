package cz.kamoska.partner.dao.interfaces;

import cz.kamoska.partner.dao.template.DaoInterface;
import cz.kamoska.partner.entities.PictureEntity;

import javax.ejb.Local;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 17.10.12
 * Time: 21:29
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface PictureDaoInterface extends DaoInterface<PictureEntity> {
}

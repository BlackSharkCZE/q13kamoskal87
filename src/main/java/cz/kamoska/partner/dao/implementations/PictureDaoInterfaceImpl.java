package cz.kamoska.partner.dao.implementations;

import cz.kamoska.partner.dao.interfaces.PictureDaoInterface;
import cz.kamoska.partner.dao.template.DaoTemplate;
import cz.kamoska.partner.entities.PictureEntity;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 17.10.12
 * Time: 21:29
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class PictureDaoInterfaceImpl extends DaoTemplate<PictureEntity> implements PictureDaoInterface {
}

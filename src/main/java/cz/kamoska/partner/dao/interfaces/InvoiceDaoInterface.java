package cz.kamoska.partner.dao.interfaces;

import cz.kamoska.partner.dao.template.DaoInterface;
import cz.kamoska.partner.entities.InvoiceEntity;

import javax.ejb.Local;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 1.11.12
 * Time: 21:44
 * To change this template use File | Settings | File Templates.
 */
@Local
public interface InvoiceDaoInterface extends DaoInterface<InvoiceEntity> {
}

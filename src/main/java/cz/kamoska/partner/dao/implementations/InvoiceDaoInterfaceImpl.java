package cz.kamoska.partner.dao.implementations;

import cz.kamoska.partner.dao.interfaces.InvoiceDaoInterface;
import cz.kamoska.partner.dao.template.DaoTemplate;
import cz.kamoska.partner.entities.InvoiceEntity;

import javax.ejb.Stateless;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 1.11.12
 * Time: 21:44
 * To change this template use File | Settings | File Templates.
 */
@Stateless
public class InvoiceDaoInterfaceImpl extends DaoTemplate<InvoiceEntity> implements InvoiceDaoInterface {
}

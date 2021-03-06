package cz.kamoska.partner.controllers;

import cz.kamoska.partner.beans.singletons.AdvertCacheNG;
import cz.kamoska.partner.beans.singletons.InvoiceChecker;

import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.enterprise.inject.Model;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.Date;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 5.2.13
 * Time: 17:23
 * To change this template use File | Settings | File Templates.
 */
@Model
@Named
public class AdminController implements Serializable {

	@EJB
	private InvoiceChecker invoiceChecker;

	@Inject
	private AdvertCacheNG advertCacheNG;

	@RolesAllowed("admin")
	public String checkPaidInvoices() {
		invoiceChecker.checkPaidInvoices();
		return null;
	}

	@RolesAllowed("admin")
	public String createProforma() {
		invoiceChecker.createNewProforma();
		return null;
	}

	@RolesAllowed("admin")
	public void reloadCache() {
		advertCacheNG.reload();
	}


	public Date getLastCacheReload() {
		return advertCacheNG.getLastRealodCache();
	}


	public Date getLastCreateProforma() {
		return invoiceChecker.getLastCreateNewProforma();
	}

	public Date getLastCheckPaid() {
		return invoiceChecker.getLastCheckPaidInvoices();
	}



}

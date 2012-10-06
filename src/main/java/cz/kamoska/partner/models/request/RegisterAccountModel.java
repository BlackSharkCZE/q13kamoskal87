package cz.kamoska.partner.models.request;

import cz.kamoska.partner.entities.PartnerEntity;

import javax.enterprise.inject.Model;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 6.10.12
 * Time: 22:17
 * Nese data zadana do formulare pri registraci noveho uctu
 */
@Model
public class RegisterAccountModel {

	private PartnerEntity partnerEntity;

	public RegisterAccountModel() {
		partnerEntity = new PartnerEntity();
	}

	public PartnerEntity getPartnerEntity() {
		return partnerEntity;
	}

	public void setPartnerEntity(PartnerEntity partnerEntity) {
		this.partnerEntity = partnerEntity;
	}
}

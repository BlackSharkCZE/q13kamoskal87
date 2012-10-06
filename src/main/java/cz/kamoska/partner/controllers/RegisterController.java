package cz.kamoska.partner.controllers;

import cz.kamoska.partner.models.request.RegisterAccountModel;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.inject.Named;

/**
 * Created with IntelliJ IDEA.
 * User: blackshark
 * Date: 6.10.12
 * Time: 22:15
 * Controller, ktery zajistuje registraci noveho partnera
 */
@Named
@RequestScoped
public class RegisterController {

	private final String REGISTER_SUCCESSFUL_OUTCOME = "register_successful";

	@Inject
	private RegisterAccountModel registerAccountModel;


	public String registerNewAccount() {
		//todo implement body of register account method
		return null;
	}

}

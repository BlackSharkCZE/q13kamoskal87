/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.airtoy.ares;

/**
 *
 * @author blackshark
 */
public enum OrganizationType {

	P("Pravnicka osoba"),
	Z("Fyzická osoba zahraniční"),
	F("Fyzicka osoba");

	private String value;

	private OrganizationType(String value) {
		this.value = value;
	}

	public String getValue() {
		return value;
	}

}

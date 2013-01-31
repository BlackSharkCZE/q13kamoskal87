/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package net.airtoy.ares;

/**
 *
 * @author blackshark
 */
public class Address {

	private String city;
	private String street;
	private String cd;
	private String co;
	private String psc;

	@Override
	public String toString() {
		return street +" " + getCd() + "/" + getCo() + " "+ city + " "+psc;
	}

	public String getJSON() {
		return this.toString();
	}

	//<editor-fold defaultstate="collapsed" desc="Getter and Setter">
	public String getCd() {
		return cd == null ? "" : cd;
	}

	public void setCd(String cd) {
		this.cd = cd;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getCo() {
		return co == null ? "" : co;
	}

	public void setCo(String co) {
		this.co = co;
	}

	public String getPsc() {
		return psc;
	}

	public void setPsc(String psc) {
		this.psc = psc;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}
	//</editor-fold>

}

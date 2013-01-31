//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.11.04 at 01:30:36 odp. CET 
//


package net.airtoy.ares.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * Základní identifikační a alokační údaje ekonomického subjektu v Seznamu devizových míst a licencí
 * 
 * <p>Java class for zakladni_udaje_DML complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="zakladni_udaje_DML">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ICO" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/uvis_datatypes/v_1.0.3}ico" minOccurs="0"/>
 *         &lt;element name="Obchodni_jmeno" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/uvis_datatypes/v_1.0.3}obchodni_firma" minOccurs="0"/>
 *         &lt;element name="A" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}adresa_ARES" minOccurs="0"/>
 *         &lt;element name="Zdroj_cz" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Zdroj_en" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Typ" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}typ_licence" minOccurs="0"/>
 *         &lt;element name="S" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}stav_licence" minOccurs="0"/>
 *         &lt;element name="Koncese" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Datum_udeleni" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/uvis_datatypes/v_1.0.3}popis" minOccurs="0"/>
 *         &lt;element name="Evid_cislo" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/uvis_datatypes/v_1.0.3}popis" minOccurs="0"/>
 *         &lt;element name="Ucinnost" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *         &lt;element name="Rozsah" type="{http://www.w3.org/2001/XMLSchema}string" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "zakladni_udaje_DML", propOrder = {
    "ico",
    "obchodniJmeno",
    "a",
    "zdrojCz",
    "zdrojEn",
    "typ",
    "s",
    "koncese",
    "datumUdeleni",
    "evidCislo",
    "ucinnost",
    "rozsah"
})
public class ZakladniUdajeDML {

    @XmlElement(name = "ICO")
    protected String ico;
    @XmlElement(name = "Obchodni_jmeno")
    protected String obchodniJmeno;
    @XmlElement(name = "A")
    protected AdresaARES a;
    @XmlElement(name = "Zdroj_cz")
    protected String zdrojCz;
    @XmlElement(name = "Zdroj_en")
    protected String zdrojEn;
    @XmlElement(name = "Typ")
    protected TypLicence typ;
    @XmlElement(name = "S")
    protected StavLicence s;
    @XmlElement(name = "Koncese")
    protected String koncese;
    @XmlElement(name = "Datum_udeleni")
    protected String datumUdeleni;
    @XmlElement(name = "Evid_cislo")
    protected String evidCislo;
    @XmlElement(name = "Ucinnost")
    protected String ucinnost;
    @XmlElement(name = "Rozsah")
    protected String rozsah;

    /**
     * Gets the value of the ico property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getICO() {
        return ico;
    }

    /**
     * Sets the value of the ico property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setICO(String value) {
        this.ico = value;
    }

    /**
     * Gets the value of the obchodniJmeno property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObchodniJmeno() {
        return obchodniJmeno;
    }

    /**
     * Sets the value of the obchodniJmeno property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObchodniJmeno(String value) {
        this.obchodniJmeno = value;
    }

    /**
     * Gets the value of the a property.
     * 
     * @return
     *     possible object is
     *     {@link AdresaARES }
     *     
     */
    public AdresaARES getA() {
        return a;
    }

    /**
     * Sets the value of the a property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdresaARES }
     *     
     */
    public void setA(AdresaARES value) {
        this.a = value;
    }

    /**
     * Gets the value of the zdrojCz property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZdrojCz() {
        return zdrojCz;
    }

    /**
     * Sets the value of the zdrojCz property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZdrojCz(String value) {
        this.zdrojCz = value;
    }

    /**
     * Gets the value of the zdrojEn property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getZdrojEn() {
        return zdrojEn;
    }

    /**
     * Sets the value of the zdrojEn property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setZdrojEn(String value) {
        this.zdrojEn = value;
    }

    /**
     * Gets the value of the typ property.
     * 
     * @return
     *     possible object is
     *     {@link TypLicence }
     *     
     */
    public TypLicence getTyp() {
        return typ;
    }

    /**
     * Sets the value of the typ property.
     * 
     * @param value
     *     allowed object is
     *     {@link TypLicence }
     *     
     */
    public void setTyp(TypLicence value) {
        this.typ = value;
    }

    /**
     * Gets the value of the s property.
     * 
     * @return
     *     possible object is
     *     {@link StavLicence }
     *     
     */
    public StavLicence getS() {
        return s;
    }

    /**
     * Sets the value of the s property.
     * 
     * @param value
     *     allowed object is
     *     {@link StavLicence }
     *     
     */
    public void setS(StavLicence value) {
        this.s = value;
    }

    /**
     * Gets the value of the koncese property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getKoncese() {
        return koncese;
    }

    /**
     * Sets the value of the koncese property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setKoncese(String value) {
        this.koncese = value;
    }

    /**
     * Gets the value of the datumUdeleni property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDatumUdeleni() {
        return datumUdeleni;
    }

    /**
     * Sets the value of the datumUdeleni property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDatumUdeleni(String value) {
        this.datumUdeleni = value;
    }

    /**
     * Gets the value of the evidCislo property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEvidCislo() {
        return evidCislo;
    }

    /**
     * Sets the value of the evidCislo property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEvidCislo(String value) {
        this.evidCislo = value;
    }

    /**
     * Gets the value of the ucinnost property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUcinnost() {
        return ucinnost;
    }

    /**
     * Sets the value of the ucinnost property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUcinnost(String value) {
        this.ucinnost = value;
    }

    /**
     * Gets the value of the rozsah property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRozsah() {
        return rozsah;
    }

    /**
     * Sets the value of the rozsah property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRozsah(String value) {
        this.rozsah = value;
    }

}

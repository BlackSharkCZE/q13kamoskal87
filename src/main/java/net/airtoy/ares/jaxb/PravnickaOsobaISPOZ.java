//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.11.04 at 01:30:36 odp. CET 
//


package net.airtoy.ares.jaxb;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * právnická osoba v registru ISPOZ
 * 
 * <p>Java class for pravnickaOsobaISPOZ complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pravnickaOsobaISPOZ">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ISPOZregistrace" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}registraceISPOZ" minOccurs="0"/>
 *         &lt;element name="ICO" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/uvis_datatypes/v_1.0.3}ico" minOccurs="0"/>
 *         &lt;element name="OF" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/uvis_datatypes/v_1.0.3}obchodni_firma" minOccurs="0"/>
 *         &lt;element name="ObchodniNazev" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/uvis_datatypes/v_1.0.3}obchodni_firma" minOccurs="0"/>
 *         &lt;element name="PF" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}pravni_forma" minOccurs="0"/>
 *         &lt;element name="SI" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}adresa_ARES" minOccurs="0"/>
 *         &lt;element name="Zastupce" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}osoba_s_adresou" minOccurs="0"/>
 *         &lt;element name="AOS" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}adresa_ARES" minOccurs="0"/>
 *         &lt;element name="StatutarniOrgany" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}statutarniOrgany" minOccurs="0"/>
 *         &lt;element name="RozsahDleOdvetviNazvy" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}rozsahDleOdvetviNazvy" minOccurs="0"/>
 *         &lt;element name="Zahajenicinnosti" type="{http://www.w3.org/2001/XMLSchema}date" minOccurs="0"/>
 *         &lt;element name="OpravneniPrijimatPojistne" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RozsahDleUzemiStaty" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}rozsahDleUzemiStaty" minOccurs="0"/>
 *         &lt;element name="PojistovnyReg" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}registracePojistovny" minOccurs="0"/>
 *         &lt;element name="Pokuty" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}pokuty" minOccurs="0"/>
 *         &lt;element name="Zprostredkovatele" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}zprostredkovatele" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="Id" type="{http://www.w3.org/2001/XMLSchema}int" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pravnickaOsobaISPOZ", propOrder = {
    "ispoZregistrace",
    "ico",
    "of",
    "obchodniNazev",
    "pf",
    "si",
    "zastupce",
    "aos",
    "statutarniOrgany",
    "rozsahDleOdvetviNazvy",
    "zahajenicinnosti",
    "opravneniPrijimatPojistne",
    "rozsahDleUzemiStaty",
    "pojistovnyReg",
    "pokuty",
    "zprostredkovatele"
})
public class PravnickaOsobaISPOZ {

    @XmlElement(name = "ISPOZregistrace")
    protected RegistraceISPOZ ispoZregistrace;
    @XmlElement(name = "ICO")
    protected String ico;
    @XmlElement(name = "OF")
    protected String of;
    @XmlElement(name = "ObchodniNazev")
    protected String obchodniNazev;
    @XmlElement(name = "PF")
    protected PravniForma pf;
    @XmlElement(name = "SI")
    protected AdresaARES si;
    @XmlElement(name = "Zastupce")
    protected OsobaSAdresou zastupce;
    @XmlElement(name = "AOS")
    protected AdresaARES aos;
    @XmlElement(name = "StatutarniOrgany")
    protected StatutarniOrgany statutarniOrgany;
    @XmlElement(name = "RozsahDleOdvetviNazvy")
    protected RozsahDleOdvetviNazvy rozsahDleOdvetviNazvy;
    @XmlElement(name = "Zahajenicinnosti")
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar zahajenicinnosti;
    @XmlElement(name = "OpravneniPrijimatPojistne", required = true)
    protected String opravneniPrijimatPojistne;
    @XmlElement(name = "RozsahDleUzemiStaty")
    protected RozsahDleUzemiStaty rozsahDleUzemiStaty;
    @XmlElement(name = "PojistovnyReg")
    protected RegistracePojistovny pojistovnyReg;
    @XmlElement(name = "Pokuty")
    protected Pokuty pokuty;
    @XmlElement(name = "Zprostredkovatele")
    protected Zprostredkovatele zprostredkovatele;
    @XmlAttribute(name = "Id")
    protected Integer id;

    /**
     * Gets the value of the ispoZregistrace property.
     * 
     * @return
     *     possible object is
     *     {@link RegistraceISPOZ }
     *     
     */
    public RegistraceISPOZ getISPOZregistrace() {
        return ispoZregistrace;
    }

    /**
     * Sets the value of the ispoZregistrace property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegistraceISPOZ }
     *     
     */
    public void setISPOZregistrace(RegistraceISPOZ value) {
        this.ispoZregistrace = value;
    }

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
     * Gets the value of the of property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOF() {
        return of;
    }

    /**
     * Sets the value of the of property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOF(String value) {
        this.of = value;
    }

    /**
     * Gets the value of the obchodniNazev property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getObchodniNazev() {
        return obchodniNazev;
    }

    /**
     * Sets the value of the obchodniNazev property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setObchodniNazev(String value) {
        this.obchodniNazev = value;
    }

    /**
     * Gets the value of the pf property.
     * 
     * @return
     *     possible object is
     *     {@link PravniForma }
     *     
     */
    public PravniForma getPF() {
        return pf;
    }

    /**
     * Sets the value of the pf property.
     * 
     * @param value
     *     allowed object is
     *     {@link PravniForma }
     *     
     */
    public void setPF(PravniForma value) {
        this.pf = value;
    }

    /**
     * Gets the value of the si property.
     * 
     * @return
     *     possible object is
     *     {@link AdresaARES }
     *     
     */
    public AdresaARES getSI() {
        return si;
    }

    /**
     * Sets the value of the si property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdresaARES }
     *     
     */
    public void setSI(AdresaARES value) {
        this.si = value;
    }

    /**
     * Gets the value of the zastupce property.
     * 
     * @return
     *     possible object is
     *     {@link OsobaSAdresou }
     *     
     */
    public OsobaSAdresou getZastupce() {
        return zastupce;
    }

    /**
     * Sets the value of the zastupce property.
     * 
     * @param value
     *     allowed object is
     *     {@link OsobaSAdresou }
     *     
     */
    public void setZastupce(OsobaSAdresou value) {
        this.zastupce = value;
    }

    /**
     * Gets the value of the aos property.
     * 
     * @return
     *     possible object is
     *     {@link AdresaARES }
     *     
     */
    public AdresaARES getAOS() {
        return aos;
    }

    /**
     * Sets the value of the aos property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdresaARES }
     *     
     */
    public void setAOS(AdresaARES value) {
        this.aos = value;
    }

    /**
     * Gets the value of the statutarniOrgany property.
     * 
     * @return
     *     possible object is
     *     {@link StatutarniOrgany }
     *     
     */
    public StatutarniOrgany getStatutarniOrgany() {
        return statutarniOrgany;
    }

    /**
     * Sets the value of the statutarniOrgany property.
     * 
     * @param value
     *     allowed object is
     *     {@link StatutarniOrgany }
     *     
     */
    public void setStatutarniOrgany(StatutarniOrgany value) {
        this.statutarniOrgany = value;
    }

    /**
     * Gets the value of the rozsahDleOdvetviNazvy property.
     * 
     * @return
     *     possible object is
     *     {@link RozsahDleOdvetviNazvy }
     *     
     */
    public RozsahDleOdvetviNazvy getRozsahDleOdvetviNazvy() {
        return rozsahDleOdvetviNazvy;
    }

    /**
     * Sets the value of the rozsahDleOdvetviNazvy property.
     * 
     * @param value
     *     allowed object is
     *     {@link RozsahDleOdvetviNazvy }
     *     
     */
    public void setRozsahDleOdvetviNazvy(RozsahDleOdvetviNazvy value) {
        this.rozsahDleOdvetviNazvy = value;
    }

    /**
     * Gets the value of the zahajenicinnosti property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getZahajenicinnosti() {
        return zahajenicinnosti;
    }

    /**
     * Sets the value of the zahajenicinnosti property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setZahajenicinnosti(XMLGregorianCalendar value) {
        this.zahajenicinnosti = value;
    }

    /**
     * Gets the value of the opravneniPrijimatPojistne property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getOpravneniPrijimatPojistne() {
        return opravneniPrijimatPojistne;
    }

    /**
     * Sets the value of the opravneniPrijimatPojistne property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setOpravneniPrijimatPojistne(String value) {
        this.opravneniPrijimatPojistne = value;
    }

    /**
     * Gets the value of the rozsahDleUzemiStaty property.
     * 
     * @return
     *     possible object is
     *     {@link RozsahDleUzemiStaty }
     *     
     */
    public RozsahDleUzemiStaty getRozsahDleUzemiStaty() {
        return rozsahDleUzemiStaty;
    }

    /**
     * Sets the value of the rozsahDleUzemiStaty property.
     * 
     * @param value
     *     allowed object is
     *     {@link RozsahDleUzemiStaty }
     *     
     */
    public void setRozsahDleUzemiStaty(RozsahDleUzemiStaty value) {
        this.rozsahDleUzemiStaty = value;
    }

    /**
     * Gets the value of the pojistovnyReg property.
     * 
     * @return
     *     possible object is
     *     {@link RegistracePojistovny }
     *     
     */
    public RegistracePojistovny getPojistovnyReg() {
        return pojistovnyReg;
    }

    /**
     * Sets the value of the pojistovnyReg property.
     * 
     * @param value
     *     allowed object is
     *     {@link RegistracePojistovny }
     *     
     */
    public void setPojistovnyReg(RegistracePojistovny value) {
        this.pojistovnyReg = value;
    }

    /**
     * Gets the value of the pokuty property.
     * 
     * @return
     *     possible object is
     *     {@link Pokuty }
     *     
     */
    public Pokuty getPokuty() {
        return pokuty;
    }

    /**
     * Sets the value of the pokuty property.
     * 
     * @param value
     *     allowed object is
     *     {@link Pokuty }
     *     
     */
    public void setPokuty(Pokuty value) {
        this.pokuty = value;
    }

    /**
     * Gets the value of the zprostredkovatele property.
     * 
     * @return
     *     possible object is
     *     {@link Zprostredkovatele }
     *     
     */
    public Zprostredkovatele getZprostredkovatele() {
        return zprostredkovatele;
    }

    /**
     * Sets the value of the zprostredkovatele property.
     * 
     * @param value
     *     allowed object is
     *     {@link Zprostredkovatele }
     *     
     */
    public void setZprostredkovatele(Zprostredkovatele value) {
        this.zprostredkovatele = value;
    }

    /**
     * Gets the value of the id property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the value of the id property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setId(Integer value) {
        this.id = value;
    }

}

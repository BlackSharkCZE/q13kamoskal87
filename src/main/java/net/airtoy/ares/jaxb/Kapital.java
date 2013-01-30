//
// This file was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.3 in JDK 1.6 
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this file will be lost upon recompilation of the source schema. 
// Generated on: 2009.11.04 at 01:30:36 odp. CET 
//


package net.airtoy.ares.jaxb;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * informace o základním jmění subjektu
 * 
 * <p>Java class for kapital complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="kapital">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="ZA" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}vklad_spolecnika" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="UK" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}vklad_spolecnika" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="KJ" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}jmeni" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="NJ" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}jmeni" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ZAP" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}vklad_spolecnika" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="MKJ" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}jmeni" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="MV" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}jmeni" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ZAV" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}jmeni" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Csky_vklad" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}jmeni" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SP" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}jmeni" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="UOP" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}jmeni" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="Akcie" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}akcie" minOccurs="0"/>
 *         &lt;element name="UM" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}texty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="NM" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}texty" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "kapital", propOrder = {
    "za",
    "uk",
    "kj",
    "nj",
    "zap",
    "mkj",
    "mv",
    "zav",
    "cskyVklad",
    "sp",
    "uop",
    "akcie",
    "um",
    "nm"
})
public class Kapital {

    @XmlElement(name = "ZA")
    protected List<VkladSpolecnika> za;
    @XmlElement(name = "UK")
    protected List<VkladSpolecnika> uk;
    @XmlElement(name = "KJ")
    protected List<Jmeni> kj;
    @XmlElement(name = "NJ")
    protected List<Jmeni> nj;
    @XmlElement(name = "ZAP")
    protected List<VkladSpolecnika> zap;
    @XmlElement(name = "MKJ")
    protected List<Jmeni> mkj;
    @XmlElement(name = "MV")
    protected List<Jmeni> mv;
    @XmlElement(name = "ZAV")
    protected List<Jmeni> zav;
    @XmlElement(name = "Csky_vklad")
    protected List<Jmeni> cskyVklad;
    @XmlElement(name = "SP")
    protected List<Jmeni> sp;
    @XmlElement(name = "UOP")
    protected List<Jmeni> uop;
    @XmlElement(name = "Akcie")
    protected Akcie akcie;
    @XmlElement(name = "UM")
    protected List<Texty> um;
    @XmlElement(name = "NM")
    protected List<Texty> nm;

    /**
     * Gets the value of the za property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the za property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZA().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VkladSpolecnika }
     * 
     * 
     */
    public List<VkladSpolecnika> getZA() {
        if (za == null) {
            za = new ArrayList<VkladSpolecnika>();
        }
        return this.za;
    }

    /**
     * Gets the value of the uk property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the uk property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUK().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VkladSpolecnika }
     * 
     * 
     */
    public List<VkladSpolecnika> getUK() {
        if (uk == null) {
            uk = new ArrayList<VkladSpolecnika>();
        }
        return this.uk;
    }

    /**
     * Gets the value of the kj property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kj property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKJ().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Jmeni }
     * 
     * 
     */
    public List<Jmeni> getKJ() {
        if (kj == null) {
            kj = new ArrayList<Jmeni>();
        }
        return this.kj;
    }

    /**
     * Gets the value of the nj property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nj property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNJ().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Jmeni }
     * 
     * 
     */
    public List<Jmeni> getNJ() {
        if (nj == null) {
            nj = new ArrayList<Jmeni>();
        }
        return this.nj;
    }

    /**
     * Gets the value of the zap property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the zap property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZAP().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link VkladSpolecnika }
     * 
     * 
     */
    public List<VkladSpolecnika> getZAP() {
        if (zap == null) {
            zap = new ArrayList<VkladSpolecnika>();
        }
        return this.zap;
    }

    /**
     * Gets the value of the mkj property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mkj property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMKJ().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Jmeni }
     * 
     * 
     */
    public List<Jmeni> getMKJ() {
        if (mkj == null) {
            mkj = new ArrayList<Jmeni>();
        }
        return this.mkj;
    }

    /**
     * Gets the value of the mv property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the mv property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getMV().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Jmeni }
     * 
     * 
     */
    public List<Jmeni> getMV() {
        if (mv == null) {
            mv = new ArrayList<Jmeni>();
        }
        return this.mv;
    }

    /**
     * Gets the value of the zav property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the zav property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZAV().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Jmeni }
     * 
     * 
     */
    public List<Jmeni> getZAV() {
        if (zav == null) {
            zav = new ArrayList<Jmeni>();
        }
        return this.zav;
    }

    /**
     * Gets the value of the cskyVklad property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the cskyVklad property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getCskyVklad().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Jmeni }
     * 
     * 
     */
    public List<Jmeni> getCskyVklad() {
        if (cskyVklad == null) {
            cskyVklad = new ArrayList<Jmeni>();
        }
        return this.cskyVklad;
    }

    /**
     * Gets the value of the sp property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the sp property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSP().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Jmeni }
     * 
     * 
     */
    public List<Jmeni> getSP() {
        if (sp == null) {
            sp = new ArrayList<Jmeni>();
        }
        return this.sp;
    }

    /**
     * Gets the value of the uop property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the uop property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUOP().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Jmeni }
     * 
     * 
     */
    public List<Jmeni> getUOP() {
        if (uop == null) {
            uop = new ArrayList<Jmeni>();
        }
        return this.uop;
    }

    /**
     * Gets the value of the akcie property.
     * 
     * @return
     *     possible object is
     *     {@link Akcie }
     *     
     */
    public Akcie getAkcie() {
        return akcie;
    }

    /**
     * Sets the value of the akcie property.
     * 
     * @param value
     *     allowed object is
     *     {@link Akcie }
     *     
     */
    public void setAkcie(Akcie value) {
        this.akcie = value;
    }

    /**
     * Gets the value of the um property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the um property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getUM().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Texty }
     * 
     * 
     */
    public List<Texty> getUM() {
        if (um == null) {
            um = new ArrayList<Texty>();
        }
        return this.um;
    }

    /**
     * Gets the value of the nm property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the nm property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getNM().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Texty }
     * 
     * 
     */
    public List<Texty> getNM() {
        if (nm == null) {
            nm = new ArrayList<Texty>();
        }
        return this.nm;
    }

}

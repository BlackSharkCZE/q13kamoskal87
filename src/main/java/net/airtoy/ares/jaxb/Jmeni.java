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
import javax.xml.bind.annotation.XmlAttribute;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * Jmeni z Kapitálu, nové v 1.0.2
 * 
 * <p>Java class for jmeni complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="jmeni">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PIK" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}angazma" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="VK" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}vklad" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="SPL" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}splaceno" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="TSP" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}texty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="ZP" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}texty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="OP" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}texty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="KOM" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}texty" maxOccurs="unbounded" minOccurs="0"/>
 *         &lt;element name="TUP" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}texty" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="dod" type="{http://www.w3.org/2001/XMLSchema}date" />
 *       &lt;attribute name="ddo" type="{http://www.w3.org/2001/XMLSchema}date" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "jmeni", propOrder = {
    "pik",
    "vk",
    "spl",
    "tsp",
    "zp",
    "op",
    "kom",
    "tup"
})
public class Jmeni {

    @XmlElement(name = "PIK")
    protected List<Angazma> pik;
    @XmlElement(name = "VK")
    protected List<Vklad> vk;
    @XmlElement(name = "SPL")
    protected List<Splaceno> spl;
    @XmlElement(name = "TSP")
    protected List<Texty> tsp;
    @XmlElement(name = "ZP")
    protected List<Texty> zp;
    @XmlElement(name = "OP")
    protected List<Texty> op;
    @XmlElement(name = "KOM")
    protected List<Texty> kom;
    @XmlElement(name = "TUP")
    protected List<Texty> tup;
    @XmlAttribute
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dod;
    @XmlAttribute
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar ddo;

    /**
     * Gets the value of the pik property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the pik property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getPIK().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Angazma }
     * 
     * 
     */
    public List<Angazma> getPIK() {
        if (pik == null) {
            pik = new ArrayList<Angazma>();
        }
        return this.pik;
    }

    /**
     * Gets the value of the vk property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the vk property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getVK().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Vklad }
     * 
     * 
     */
    public List<Vklad> getVK() {
        if (vk == null) {
            vk = new ArrayList<Vklad>();
        }
        return this.vk;
    }

    /**
     * Gets the value of the spl property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the spl property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getSPL().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Splaceno }
     * 
     * 
     */
    public List<Splaceno> getSPL() {
        if (spl == null) {
            spl = new ArrayList<Splaceno>();
        }
        return this.spl;
    }

    /**
     * Gets the value of the tsp property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tsp property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTSP().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Texty }
     * 
     * 
     */
    public List<Texty> getTSP() {
        if (tsp == null) {
            tsp = new ArrayList<Texty>();
        }
        return this.tsp;
    }

    /**
     * Gets the value of the zp property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the zp property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getZP().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Texty }
     * 
     * 
     */
    public List<Texty> getZP() {
        if (zp == null) {
            zp = new ArrayList<Texty>();
        }
        return this.zp;
    }

    /**
     * Gets the value of the op property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the op property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getOP().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Texty }
     * 
     * 
     */
    public List<Texty> getOP() {
        if (op == null) {
            op = new ArrayList<Texty>();
        }
        return this.op;
    }

    /**
     * Gets the value of the kom property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the kom property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getKOM().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Texty }
     * 
     * 
     */
    public List<Texty> getKOM() {
        if (kom == null) {
            kom = new ArrayList<Texty>();
        }
        return this.kom;
    }

    /**
     * Gets the value of the tup property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the tup property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTUP().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link Texty }
     * 
     * 
     */
    public List<Texty> getTUP() {
        if (tup == null) {
            tup = new ArrayList<Texty>();
        }
        return this.tup;
    }

    /**
     * Gets the value of the dod property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDod() {
        return dod;
    }

    /**
     * Sets the value of the dod property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDod(XMLGregorianCalendar value) {
        this.dod = value;
    }

    /**
     * Gets the value of the ddo property.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getDdo() {
        return ddo;
    }

    /**
     * Sets the value of the ddo property.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setDdo(XMLGregorianCalendar value) {
        this.ddo = value;
    }

}
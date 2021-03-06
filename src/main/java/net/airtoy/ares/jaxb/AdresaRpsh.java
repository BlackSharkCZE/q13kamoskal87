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
 * Údaje o předávané adrese subjektu nebo bydliště fyzické osoby řídícího orgánu v RPSH
 * 
 * <p>Java class for adresa_rpsh complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="adresa_rpsh">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="IDA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="AT" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}adresa_textem" minOccurs="0"/>
 *       &lt;/sequence>
 *       &lt;attribute name="dod" type="{http://www.w3.org/2001/XMLSchema}date" />
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "adresa_rpsh", propOrder = {
    "ida",
    "at"
})
public class AdresaRpsh {

    @XmlElement(name = "IDA", required = true)
    protected String ida;
    @XmlElement(name = "AT")
    protected String at;
    @XmlAttribute
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar dod;

    /**
     * Gets the value of the ida property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDA() {
        return ida;
    }

    /**
     * Sets the value of the ida property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDA(String value) {
        this.ida = value;
    }

    /**
     * Gets the value of the at property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getAT() {
        return at;
    }

    /**
     * Sets the value of the at property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setAT(String value) {
        this.at = value;
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

}

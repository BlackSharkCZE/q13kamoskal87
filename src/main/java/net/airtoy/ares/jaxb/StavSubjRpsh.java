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
 * Informace o stavu, ve kterém se subjekt nachází
 * 
 * <p>Java class for stav_subj_rpsh complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="stav_subj_rpsh">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="T_stavu" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/uvis_datatypes/v_1.0.3}popis" minOccurs="0"/>
 *         &lt;element name="Popis" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}texty" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "stav_subj_rpsh", propOrder = {
    "tStavu",
    "popis"
})
public class StavSubjRpsh {

    @XmlElement(name = "T_stavu")
    protected String tStavu;
    @XmlElement(name = "Popis")
    protected Texty popis;

    /**
     * Gets the value of the tStavu property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTStavu() {
        return tStavu;
    }

    /**
     * Sets the value of the tStavu property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTStavu(String value) {
        this.tStavu = value;
    }

    /**
     * Gets the value of the popis property.
     * 
     * @return
     *     possible object is
     *     {@link Texty }
     *     
     */
    public Texty getPopis() {
        return popis;
    }

    /**
     * Sets the value of the popis property.
     * 
     * @param value
     *     allowed object is
     *     {@link Texty }
     *     
     */
    public void setPopis(Texty value) {
        this.popis = value;
    }

}

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
 * <p>Java class for pobocka complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="pobocka">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="UzemiStat" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="APobocky" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}adresa_ARES" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "pobocka", propOrder = {
    "uzemiStat",
    "aPobocky"
})
public class Pobocka {

    @XmlElement(name = "UzemiStat", required = true)
    protected String uzemiStat;
    @XmlElement(name = "APobocky")
    protected AdresaARES aPobocky;

    /**
     * Gets the value of the uzemiStat property.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUzemiStat() {
        return uzemiStat;
    }

    /**
     * Sets the value of the uzemiStat property.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUzemiStat(String value) {
        this.uzemiStat = value;
    }

    /**
     * Gets the value of the aPobocky property.
     * 
     * @return
     *     possible object is
     *     {@link AdresaARES }
     *     
     */
    public AdresaARES getAPobocky() {
        return aPobocky;
    }

    /**
     * Sets the value of the aPobocky property.
     * 
     * @param value
     *     allowed object is
     *     {@link AdresaARES }
     *     
     */
    public void setAPobocky(AdresaARES value) {
        this.aPobocky = value;
    }

}

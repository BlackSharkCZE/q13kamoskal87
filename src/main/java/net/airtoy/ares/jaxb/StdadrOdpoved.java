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
 * <p>Java class for stdadr_odpoved complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="stdadr_odpoved">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="Vsechna_slova" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}vysledek_dotazu"/>
 *         &lt;element name="Po_redukci_slov" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}vysledek_dotazu" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "stdadr_odpoved", propOrder = {
    "vsechnaSlova",
    "poRedukciSlov"
})
public class StdadrOdpoved {

    @XmlElement(name = "Vsechna_slova", required = true)
    protected VysledekDotazu vsechnaSlova;
    @XmlElement(name = "Po_redukci_slov")
    protected VysledekDotazu poRedukciSlov;

    /**
     * Gets the value of the vsechnaSlova property.
     * 
     * @return
     *     possible object is
     *     {@link VysledekDotazu }
     *     
     */
    public VysledekDotazu getVsechnaSlova() {
        return vsechnaSlova;
    }

    /**
     * Sets the value of the vsechnaSlova property.
     * 
     * @param value
     *     allowed object is
     *     {@link VysledekDotazu }
     *     
     */
    public void setVsechnaSlova(VysledekDotazu value) {
        this.vsechnaSlova = value;
    }

    /**
     * Gets the value of the poRedukciSlov property.
     * 
     * @return
     *     possible object is
     *     {@link VysledekDotazu }
     *     
     */
    public VysledekDotazu getPoRedukciSlov() {
        return poRedukciSlov;
    }

    /**
     * Sets the value of the poRedukciSlov property.
     * 
     * @param value
     *     allowed object is
     *     {@link VysledekDotazu }
     *     
     */
    public void setPoRedukciSlov(VysledekDotazu value) {
        this.poRedukciSlov = value;
    }

}
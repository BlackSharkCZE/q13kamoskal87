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
 * <p>Java class for odpoved_rpsh complex type.
 * 
 * <p>The following schema fragment specifies the expected content contained within this class.
 * 
 * <pre>
 * &lt;complexType name="odpoved_rpsh">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="PID" type="{http://www.w3.org/2001/XMLSchema}int" minOccurs="0"/>
 *         &lt;element name="VH" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}vysledek_hledani" minOccurs="0"/>
 *         &lt;element name="E" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}error_ARES" minOccurs="0"/>
 *         &lt;element name="UVOD" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}uvod" minOccurs="0"/>
 *         &lt;element name="Vypis_RPSH" type="{http://wwwinfo.mfcr.cz/ares/xml_doc/schemas/ares/ares_datatypes/v_1.0.5}subjekt_rpsh" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "odpoved_rpsh", propOrder = {
    "pid",
    "vh",
    "e",
    "uvod",
    "vypisRPSH"
})
public class OdpovedRpsh {

    @XmlElement(name = "PID")
    protected Integer pid;
    @XmlElement(name = "VH")
    protected VysledekHledani vh;
    @XmlElement(name = "E")
    protected ErrorARES e;
    @XmlElement(name = "UVOD")
    protected Uvod uvod;
    @XmlElement(name = "Vypis_RPSH")
    protected SubjektRpsh vypisRPSH;

    /**
     * Gets the value of the pid property.
     * 
     * @return
     *     possible object is
     *     {@link Integer }
     *     
     */
    public Integer getPID() {
        return pid;
    }

    /**
     * Sets the value of the pid property.
     * 
     * @param value
     *     allowed object is
     *     {@link Integer }
     *     
     */
    public void setPID(Integer value) {
        this.pid = value;
    }

    /**
     * Gets the value of the vh property.
     * 
     * @return
     *     possible object is
     *     {@link VysledekHledani }
     *     
     */
    public VysledekHledani getVH() {
        return vh;
    }

    /**
     * Sets the value of the vh property.
     * 
     * @param value
     *     allowed object is
     *     {@link VysledekHledani }
     *     
     */
    public void setVH(VysledekHledani value) {
        this.vh = value;
    }

    /**
     * Gets the value of the e property.
     * 
     * @return
     *     possible object is
     *     {@link ErrorARES }
     *     
     */
    public ErrorARES getE() {
        return e;
    }

    /**
     * Sets the value of the e property.
     * 
     * @param value
     *     allowed object is
     *     {@link ErrorARES }
     *     
     */
    public void setE(ErrorARES value) {
        this.e = value;
    }

    /**
     * Gets the value of the uvod property.
     * 
     * @return
     *     possible object is
     *     {@link Uvod }
     *     
     */
    public Uvod getUVOD() {
        return uvod;
    }

    /**
     * Sets the value of the uvod property.
     * 
     * @param value
     *     allowed object is
     *     {@link Uvod }
     *     
     */
    public void setUVOD(Uvod value) {
        this.uvod = value;
    }

    /**
     * Gets the value of the vypisRPSH property.
     * 
     * @return
     *     possible object is
     *     {@link SubjektRpsh }
     *     
     */
    public SubjektRpsh getVypisRPSH() {
        return vypisRPSH;
    }

    /**
     * Sets the value of the vypisRPSH property.
     * 
     * @param value
     *     allowed object is
     *     {@link SubjektRpsh }
     *     
     */
    public void setVypisRPSH(SubjektRpsh value) {
        this.vypisRPSH = value;
    }

}

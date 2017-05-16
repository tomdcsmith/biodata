//
// This path was generated by the JavaTM Architecture for XML Binding(JAXB) Reference Implementation, vJAXB 2.1.10 in JDK 6
// See <a href="http://java.sun.com/xml/jaxb">http://java.sun.com/xml/jaxb</a> 
// Any modifications to this path will be lost upon recompilation of the source schema.
// Generated on: 2010.06.14 at 12:38:27 PM CEST 
//


package org.opencb.biodata.formats.protein.uniprot.v135jaxb;

import javax.xml.bind.annotation.*;
import javax.xml.datatype.XMLGregorianCalendar;
import java.math.BigInteger;


/**
 * <p>Java class for sequenceType complex type.
 * <p/>
 * <p>The following schema fragment specifies the expected content contained within this class.
 * <p/>
 * <pre>
 * &lt;complexType name="sequenceType">
 *   &lt;simpleContent>
 *     &lt;extension base="&lt;http://www.w3.org/2001/XMLSchema>string">
 *       &lt;attribute name="length" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="mass" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="checksum" use="required" type="{http://www.w3.org/2001/XMLSchema}string" />
 *       &lt;attribute name="modified" use="required" type="{http://www.w3.org/2001/XMLSchema}date" />
 *       &lt;attribute name="version" use="required" type="{http://www.w3.org/2001/XMLSchema}integer" />
 *       &lt;attribute name="precursor" type="{http://www.w3.org/2001/XMLSchema}boolean" />
 *       &lt;attribute name="fragment">
 *         &lt;simpleType>
 *           &lt;restriction base="{http://www.w3.org/2001/XMLSchema}string">
 *             &lt;enumeration value="single"/>
 *             &lt;enumeration value="multiple"/>
 *           &lt;/restriction>
 *         &lt;/simpleType>
 *       &lt;/attribute>
 *     &lt;/extension>
 *   &lt;/simpleContent>
 * &lt;/complexType>
 * </pre>
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "sequenceType", propOrder = {
        "value"
})
public class SequenceType {

    @XmlValue
    protected String value;
    @XmlAttribute(required = true)
    protected BigInteger length;
    @XmlAttribute(required = true)
    protected BigInteger mass;
    @XmlAttribute(required = true)
    protected String checksum;
    @XmlAttribute(required = true)
    @XmlSchemaType(name = "date")
    protected XMLGregorianCalendar modified;
    @XmlAttribute(required = true)
    protected BigInteger version;
    @XmlAttribute
    protected Boolean precursor;
    @XmlAttribute
    protected String fragment;

    /**
     * Gets the value of the value property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getValue() {
        return value;
    }

    /**
     * Sets the value of the value property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setValue(String value) {
        this.value = value;
    }

    /**
     * Gets the value of the length property.
     *
     * @return possible object is
     *         {@link java.math.BigInteger }
     */
    public BigInteger getLength() {
        return length;
    }

    /**
     * Sets the value of the length property.
     *
     * @param value allowed object is
     *              {@link java.math.BigInteger }
     */
    public void setLength(BigInteger value) {
        this.length = value;
    }

    /**
     * Gets the value of the mass property.
     *
     * @return possible object is
     *         {@link java.math.BigInteger }
     */
    public BigInteger getMass() {
        return mass;
    }

    /**
     * Sets the value of the mass property.
     *
     * @param value allowed object is
     *              {@link java.math.BigInteger }
     */
    public void setMass(BigInteger value) {
        this.mass = value;
    }

    /**
     * Gets the value of the checksum property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getChecksum() {
        return checksum;
    }

    /**
     * Sets the value of the checksum property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setChecksum(String value) {
        this.checksum = value;
    }

    /**
     * Gets the value of the modified property.
     *
     * @return possible object is
     *         {@link javax.xml.datatype.XMLGregorianCalendar }
     */
    public XMLGregorianCalendar getModified() {
        return modified;
    }

    /**
     * Sets the value of the modified property.
     *
     * @param value allowed object is
     *              {@link javax.xml.datatype.XMLGregorianCalendar }
     */
    public void setModified(XMLGregorianCalendar value) {
        this.modified = value;
    }

    /**
     * Gets the value of the version property.
     *
     * @return possible object is
     *         {@link java.math.BigInteger }
     */
    public BigInteger getVersion() {
        return version;
    }

    /**
     * Sets the value of the version property.
     *
     * @param value allowed object is
     *              {@link java.math.BigInteger }
     */
    public void setVersion(BigInteger value) {
        this.version = value;
    }

    /**
     * Gets the value of the precursor property.
     *
     * @return possible object is
     *         {@link Boolean }
     */
    public Boolean isPrecursor() {
        return precursor;
    }

    /**
     * Sets the value of the precursor property.
     *
     * @param value allowed object is
     *              {@link Boolean }
     */
    public void setPrecursor(Boolean value) {
        this.precursor = value;
    }

    /**
     * Gets the value of the fragment property.
     *
     * @return possible object is
     *         {@link String }
     */
    public String getFragment() {
        return fragment;
    }

    /**
     * Sets the value of the fragment property.
     *
     * @param value allowed object is
     *              {@link String }
     */
    public void setFragment(String value) {
        this.fragment = value;
    }

}
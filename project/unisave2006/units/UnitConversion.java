/*
 * Created on Jul 12, 2006
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 */
package unisave2006.units;

/**
 * @author David Ježek
 *
 * Copyright (C) 2006
 * David Ježek 
 * david.jezek@vsb.cz.
 * All rights reserved.
 * This class was created as part of project UniSave2006Implementation.
 * @uml.annotations
 * derived_abstraction="platform:/resource/UniSave2006%20Design/Main.emx#_yJ2PoBDREdu2xrm-LBKU-Q"
 * @generated "UML to Java (com.ibm.xtools.transform.uml2.java.internal.UML2JavaTransform)"
 */
public class UnitConversion {
	
	private Long id;
    /**
     * Comment for <code>from</code>
     */
    private UnitDescription from;
    /**
     * Comment for <code>to</code>
     */
    private UnitDescription to;
    /**
     * Comment for <code>multiplier</code>
     */
    private double multiplier;
    /**
     * Comment for <code>divider</code>
     */
    private double divider;
    /**
     * Comment for <code>offset2</code>
     */
    private double offset2;
    /**
     * Comment for <code>offset</code>
     */
    private double offset;

    public Long getId() {
    	return id;
    }
    
    public void setId(Long id) {
    	this.id = id;
    }
    /**
     * @return Returns the to.
     */
    public UnitDescription getTo() {
        return to;
    }

    /**
     * @param theTo The to to set.
     */
    private void setTo(UnitDescription theTo) {
        to = theTo;
    }

    public UnitConversion() {
    	
    }

    public UnitConversion(UnitDescription from, UnitDescription to,
            double multiplier, double divider, double offset, double offset2){
        setFrom(from);
        setTo(to);
        setMultiplier(multiplier);
        setDivider(divider);
        setOffset(offset);
        setOffset2(offset2);
        from.addConversion(this);
    }
    /**
     * @return Returns the from.
     */
    public UnitDescription getFrom() {
        return from;
    }

    /**
     * @param theFrom The from to set.
     */
    private void setFrom(UnitDescription theFrom) {
        from = theFrom;
    }


    /**
     * @return Returns the multiplier.
     */
    public double getMultiplier() {
        return multiplier;
    }

    /**
     * @param theMultiplier The multiplier to set.
     */
    private void setMultiplier(double theMultiplier) {
        multiplier = theMultiplier;
    }

    /**
     * @return Returns the multiplier.
     */
    public double getDivider() {
        return divider;
    }

    /**
     * @param theMultiplier The multiplier to set.
     */
    private void setDivider(double theDivider) {
        divider = theDivider;
    }


    /**
     * @return Returns the offset.
     */
    public double getOffset() {
        return offset;
    }

    /**
     * @param theOffset The offset to set.
     */
    private void setOffset(double theOffset) {
        offset = theOffset;
    }


    /**
     * @return Returns the offset2.
     */
    public double getOffset2() {
        return offset2;
    }

    /**
     * @param theOffset2 The offset2 to set.
     */
    private void setOffset2(double theOffset2) {
        offset2 = theOffset2;
    }
    
/*    *//** vytvori kopii hodnoty a prevede ji na dane jednotky
     * 
     * @param value
     * @param targetPrefix
     * @return
     *//*
    public Value convert(Value value, UnitPrefix targetPrefix){
        if(value.getUnit().getDescription().equals(from))
                return value;
            double newVal;
            //newVal = value;
            newVal = (value.getTrueValue() + offset)*multiplier/divider + offset2;
            value.setValue(newVal /targetPrefix.getMultiplier());

            //newVal.normalValue = (value.getTrueNormalValue() + offset)*multiplier/divider + offset2;
            //newVal.normalValue /= targetPrefix->getMultiplier();

            //newVal.tolerance = (value.getTrueTolerance() + offset)*multiplier/divider + offset2;
            //newVal.tolerance /= targetPrefix->getMultiplier();

            value.getUnit().setPrefix(targetPrefix);
            value.getUnit().setDescription(to);
            return value;
    }*/
    public double convert(double trueValue){
        return (trueValue + offset)*multiplier/divider + offset2;
    }
}

package com.difotofoto.snmperfect.snmp.mib;

import org.snmp4j.smi.AbstractVariable;

import java.io.IOException;
import java.io.PrintWriter;

public class ObjectType<V extends AbstractVariable> extends MibEntry {


    private static final long serialVersionUID = -406666126335240300L;

    private SyntaxEnum syntax;
    private MaxAccessEnum maxAccess;
    private String status = "current";
    private String description;
    private ValueGetter<V> valueGetter;
    private ValueSetter<V> valueSetter;
    private ValueChecker<V> valueChecker;

    public ObjectType(String name, int oidElement, SyntaxEnum syntax,
                      MaxAccessEnum maxAccess, String description) {
        super(name, oidElement);
        this.syntax = syntax;
        this.maxAccess = maxAccess;
        this.description = description;
    }


    /**
     * @return Value checker
     */
    public ValueChecker<V> getValueChecker() {
        return valueChecker;
    }

    /**
     * @param valueChecker to set
     */
    public void setValueChecker(ValueChecker<V> valueChecker) {
        this.valueChecker = valueChecker;
    }

    /**
     * @return the valueGetter
     */
    public ValueGetter<V> getValueGetter() {
        return valueGetter;
    }

    /**
     * @param valueGetter the valueGetter to set
     */
    public void setValueGetter(ValueGetter<V> valueGetter) {
        this.valueGetter = valueGetter;
    }

    /**
     * @return the valueSetter
     */
    public ValueSetter<V> getValueSetter() {
        return valueSetter;
    }

    /**
     * @param valueSetter the valueSetter to set
     */
    public void setValueSetter(ValueSetter<V> valueSetter) {
        this.valueSetter = valueSetter;
    }

    /**
     * @return the syntax
     */
    public SyntaxEnum getSyntax() {
        return syntax;
    }

    /**
     * @param syntax the syntax to set
     */
    public void setSyntax(SyntaxEnum syntax) {
        this.syntax = syntax;
    }

    /**
     * @return the maxAccess
     */
    public MaxAccessEnum getMaxAccess() {
        return maxAccess;
    }

    /**
     * @param maxAccess the maxAccess to set
     */
    public void setMaxAccess(MaxAccessEnum maxAccess) {
        this.maxAccess = maxAccess;
    }

    /**
     * @return the status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status the status to set
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return the description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description the description to set
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /* (non-Javadoc)
     * @see com.sicpa.markii.monitoring.snmp.mib.MibObject#printMib(java.io.PrintWriter)
     */
    @Override
    public void printMib(PrintWriter pw) throws IOException {
        pw.println("");
        pw.println("-- " + this.getOIDString());
        pw.println(this.getName() + " OBJECT-TYPE");
        pw.println("\tSYNTAX " + syntax.getText());
        pw.println("\tMAX-ACCESS " + maxAccess.getText());
        pw.println("\tSTATUS " + status);
        pw.println("\tDESCRIPTION \"" + description + "\"");
        pw.println("\t::= { " + this.getParent().getName() + " " + this.getOidElement() + " }");
    }

	/*smdlInkLevel OBJECT-TYPE
    SYNTAX      Integer32
    MAX-ACCESS  read-only
    STATUS      current
    DESCRIPTION "Get the current ink level as detected by SML"
    ::= { smdlRead 1 }*/


}

package com.difotofoto.snmperfect.snmp.mib;

import org.snmp4j.agent.mo.MOAccessImpl;

/**
 * This enum specifies all possible access type to a specific OID.
 * Currently only READ_ONLY access were developed.
 *
 * @author Fneman
 */
public enum MaxAccessEnum {

    NOT_ACCESSIBLE("not-accessible", MOAccessImpl.ACCESSIBLE_FOR_WRITE),
    NOT_ACCESSIBLE_FOR_NOTIFY("accessible-for-notify", MOAccessImpl.ACCESSIBLE_FOR_NOTIFY),
    READ_ONLY("read-only", MOAccessImpl.ACCESSIBLE_FOR_READ_ONLY),
    READ_WRITE("read-write", MOAccessImpl.ACCESSIBLE_FOR_READ_WRITE),
    READ_CREATE("read-create", MOAccessImpl.ACCESSIBLE_FOR_READ_CREATE);

    private String text;
    private short moType;

    MaxAccessEnum(String text, short moType) {
        this.text = text;
        this.moType = moType;
    }

    /**
     * @return the text
     */
    public String getText() {
        return text;
    }

    /**
     * Get mapping from this enum to their MO Access type.
     *
     * @return
     */
    public short getAccessType() {
        return moType;
    }
}

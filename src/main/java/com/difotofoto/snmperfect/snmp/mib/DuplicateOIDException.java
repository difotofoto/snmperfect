package com.difotofoto.snmperfect.snmp.mib;

/**
 * Exeption thrown if there are duplicate OID detected when registered into the engine.
 */
public class DuplicateOIDException extends Exception {

    private static final long serialVersionUID = 8887282687403580534L;

    /**
     * Constructor without parameter.
     */
    public DuplicateOIDException() {
        super();
    }

    /**
     * Constructor
     *
     * @param arg0 The Exception message
     * @param arg1 The nested exception
     */
    public DuplicateOIDException(String arg0, Throwable arg1) {
        super(arg0, arg1);
    }

    /**
     * Constructor
     *
     * @param arg0 The Exception message
     */
    public DuplicateOIDException(String arg0) {
        super(arg0);
    }

    /**
     * Constructor
     *
     * @param arg0 The nested exception
     */
    public DuplicateOIDException(Throwable arg0) {
        super(arg0);
    }

}

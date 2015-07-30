package com.difotofoto.snmperfect.snmp.mib;

import java.io.IOException;
import java.io.PrintWriter;

/**
 * An enterprise OID node. With default OID of 1.3.6.1.4.1
 *
 * @author Fneman
 */
public class Enterprise extends MibEntry {

    private static final long serialVersionUID = 2018447430535924228L;

    /**
     * The constructor
     */
    public Enterprise() {
        super("enterprises", 0);
    }


    /**
     * @param pw PrintWriter object use to print the entry line.
     * @throws IOException
     */
    @Override
    public void printMib(PrintWriter pw) throws IOException {
        // do nothing.

    }

    /**
     * Get the OID in string of this MIB Entry
     *
     * @return The OID in string
     */
    @Override
    public String getOIDString() {
        return "1.3.6.1.4.1";
    }

}

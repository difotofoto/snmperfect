package com.difotofoto.snmperfect.snmp.mib;

import java.io.IOException;
import java.io.PrintWriter;

public class ObjectIdentifier extends MibEntry {

    // sicpa OBJECT IDENTIFIER ::= { enterprises 50000 }

    private static final long serialVersionUID = 2691321275068874378L;

    public ObjectIdentifier(String name, int oidElement) {
        super(name, oidElement);
    }

    /* (non-Javadoc)
     * @see com.sicpa.markii.monitoring.snmp.mib.MibObject#printMib(java.io.PrintWriter)
     */
    @Override
    public void printMib(PrintWriter pw) throws IOException {
        pw.println("");
        pw.println("-- " + this.getOIDString());
        pw.println(this.getName() + " OBJECT IDENTIFIER ::= { " + this.getParent().getName() + " " + this.getOidElement() + " }");
    }

}

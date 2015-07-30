package com.difotofoto.snmperfect.snmp.mib;

import java.io.IOException;
import java.io.PrintWriter;

public class NotificationType extends MibEntry {

    private static final long serialVersionUID = 3272967626076081562L;

    private String status = "current";
    private String description;

    public NotificationType(String name, int oidElement,
                            String description) {
        super(name, oidElement);
        this.description = description;
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
        pw.println(this.getName() + " NOTIFICATION-TYPE");
        pw.println("\tSTATUS " + status);
        pw.println("\tDESCRIPTION \"" + description + "\"");
        pw.println("\t::= { " + this.getParent().getName() + " " + this.getOidElement() + " }");
    }

	/*smdlLineStart NOTIFICATION-TYPE
    STATUS  current
    DESCRIPTION "Notification: LINE is started"
    ::= { smdlLineNotification 1 }*/

}

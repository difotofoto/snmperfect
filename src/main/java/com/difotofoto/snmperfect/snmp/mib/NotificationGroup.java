package com.difotofoto.snmperfect.snmp.mib;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class NotificationGroup extends MibEntry {

    private static final long serialVersionUID = 5712694951849198053L;
    private List<NotificationType> notifications = new ArrayList<NotificationType>();
    private NotificationScanProcess scanner;
    private String status = "current";
    private String description;

    public NotificationGroup(String name, int oidElement,
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

    /**
     * @return the scanner
     */
    public NotificationScanProcess getScanner() {
        return scanner;
    }

    /**
     * @param scanner the scanner to set
     */
    public void setScanner(NotificationScanProcess scanner) {
        this.scanner = scanner;
    }

    public int size() {
        return notifications.size();
    }

    public NotificationType getNotificationTypeAt(int idx) {
        return notifications.get(idx);
    }

    public void addNotificationType(NotificationType notificationType) {
        this.notifications.add(notificationType);
    }

    public void removeNotificationTypeAt(int idx) {
        notifications.remove(idx);
    }

    public void removeNotificationType(NotificationType type) {
        notifications.remove(type);
    }

    /* (non-Javadoc)
     * @see com.sicpa.markii.monitoring.snmp.mib.MibObject#printMib(java.io.PrintWriter)
     */
    @Override
    public void printMib(PrintWriter pw) throws IOException {
        pw.println("");
        pw.println("-- " + this.getOIDString());
        pw.println(this.getName() + " NOTIFICATION-GROUP");
        pw.println("\tNOTIFICATIONS {");
        for (int i = 0; i < size(); i++) {
            if (i < size() - 1) {
                pw.println("\t\t" + getNotificationTypeAt(i).getName() + ",");
            } else {
                pw.println("\t\t" + getNotificationTypeAt(i).getName());
            }
        }
        pw.println("\t}");
        pw.println("\tSTATUS " + status);
        pw.println("\tDESCRIPTION \"" + description + "\"");
        pw.println("\t::= { " + this.getParent().getName() + " " + this.getOidElement() + " }");
    }

	
/*smdlLineNotificationGroup NOTIFICATION-GROUP
    NOTIFICATIONS {
        smdlLineStart,
		smdlLineStop
    }
    STATUS current
    DESCRIPTION "A collection of notification nodes in this MIB"
    ::= { smdlMIBConformance 2 }*/
}

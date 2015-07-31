package com.difotofoto.snmperfect.snmp.mib;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ModuleCompliance extends MibEntry {
    private static final long serialVersionUID = -2863735568359280922L;

    private String status = "current";
    private String description;
    private List<NotificationGroup> notificationGroups = new ArrayList<NotificationGroup>();
    private List<ObjectGroup> objectGroups = new ArrayList<ObjectGroup>();

    public ModuleCompliance(String name, int oidElement, String description) {
        super(name, oidElement);
        this.description = description;
    }

    public int getNotificationGroupSize() {
        return notificationGroups.size();
    }

    public void addNotificationGroup(NotificationGroup notificationGroup) {
        notificationGroups.add(notificationGroup);
    }

    public void removeNotificationGroupAt(int idx) {
        notificationGroups.remove(idx);
    }

    public void removeNotificationGroup(NotificationGroup notificationGroup) {
        notificationGroups.remove(notificationGroup);
    }

    public int getObjectGroupSize() {
        return objectGroups.size();
    }

    public void addObjectGroup(ObjectGroup objectGroup) {
        objectGroups.add(objectGroup);
    }

    public void removeObjectGroupAt(int idx) {
        objectGroups.remove(idx);
    }

    public void removeObjectGroup(ObjectGroup objectGroup) {
        objectGroups.remove(objectGroup);
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
     * @see MibEntry#printMib(java.io.PrintWriter)
     */
    @Override
    public void printMib(PrintWriter pw) throws IOException {
        pw.println("");
        pw.println("-- " + this.getOIDString());
        pw.println(this.getName() + " MODULE-COMPLIANCE");
        pw.println("\tSTATUS " + status);
        pw.println("\tDESCRIPTION \"" + description + "\"");
        pw.println("\tMODULE  -- this module");
        pw.println("\t\tMANDATORY-GROUPS {");
        List<String> strings = new ArrayList<String>();
        for (NotificationGroup ng : notificationGroups) {
            strings.add(ng.getName());
        }
        for (ObjectGroup og : objectGroups) {
            strings.add(og.getName());
        }
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        for (String s : strings) {
            if (!first) {
                sb.append(", ");
            }
            sb.append(s);
            first = false;
        }
        pw.println("\t\t\t" + sb.toString());
        pw.println("\t\t}");
        pw.println("\t::= { " + this.getParent().getName() + " " + this.getOidElement() + " }");
    }
    /*smlCompliance MODULE-COMPLIANCE
    STATUS  current
    DESCRIPTION
        "The compliance statement for SNMP entities which
        implement this MIB."
    MODULE  -- this module
    MANDATORY-GROUPS { 
        smdlLineNotificationGroup, smdlPrinterNotificationGroup,smdlInkNotificationGroup,smdlReadGroup
    }
    ::= { smdlMIBConformance 1 }
    */

}

package com.difotofoto.snmperfect.snmp.mib;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class ObjectGroup extends MibEntry {

    private static final long serialVersionUID = 5057387561581211275L;

    private List<ObjectType> objectTypes = new ArrayList<ObjectType>();
    private String status = "current";
    private String description;

    public ObjectGroup(String name, int oidElement,
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

    public int size() {
        return objectTypes.size();
    }

    public ObjectType getObjectTypeAt(int idx) {
        return objectTypes.get(idx);
    }

    public void addObjectType(ObjectType notificationType) {
        this.objectTypes.add(notificationType);
    }

    public void removeObjectTypeAt(int idx) {
        objectTypes.remove(idx);
    }

    public void removeObjectType(ObjectType type) {
        objectTypes.remove(type);
    }

    /* (non-Javadoc)
     * @see com.sicpa.markii.monitoring.snmp.mib.MibObject#printMib(java.io.PrintWriter)
     */
    @Override
    public void printMib(PrintWriter pw) throws IOException {
        pw.println("");
        pw.println("-- " + this.getOIDString());
        pw.println(this.getName() + " OBJECT-GROUP");
        pw.println("\tOBJECTS {");
        for (int i = 0; i < size(); i++) {
            if (i < size() - 1) {
                pw.println("\t\t" + getObjectTypeAt(i).getName() + ",");
            } else {
                pw.println("\t\t" + getObjectTypeAt(i).getName());
            }
        }
        pw.println("\t}");
        pw.println("\tSTATUS " + status);
        pw.println("\tDESCRIPTION \"" + description + "\"");
        pw.println("\t::= { " + this.getParent().getName() + " " + this.getOidElement() + " }");
    }

	/*smdlReadGroup OBJECT-GROUP
    OBJECTS {
        smdlInkLevel,
        smdlSuccessCount,
        smdlFailCount,
        smdlSkuName
    }
    STATUS current
    DESCRIPTION "A collection of object nodes in this MIB"
    ::= { smdlMIBConformance 5 }*/

}

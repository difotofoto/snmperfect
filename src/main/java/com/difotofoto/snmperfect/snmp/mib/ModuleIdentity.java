package com.difotofoto.snmperfect.snmp.mib;

import java.io.IOException;
import java.io.PrintWriter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ModuleIdentity extends MibEntry {

    private static final long serialVersionUID = -3714177225187498942L;

    private static SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMdd");
    private static SimpleDateFormat sdfProp = new SimpleDateFormat(
            "yyyyMMddHHmm");
    public List<Revision> revisions = new ArrayList<ModuleIdentity.Revision>();
    private Date lastUpdate;
    private String organization;
    private String contactInfo;
    private String description;

    public ModuleIdentity(String name, int oidElement, Date lastUpdate,
                          String organization, String contactInfo, String description) {
        super(name, oidElement);
        this.lastUpdate = lastUpdate;
        this.organization = organization;
        this.contactInfo = contactInfo;
        this.description = description;
        addRevision(sdf2.format(new Date()) + "0000Z", "Generation Of this MIB");
    }

    public void addRevision(int year, int month, int day, int hour, int minute,
                            String description) {
        Calendar cal = Calendar.getInstance();
        cal.set(year, month, day, hour, minute);
        addRevision(cal.getTime(), description);
    }

    public void addRevision(Date revision, String description) {
        addRevision(sdfProp.format(revision) + "Z", description);
    }

    public void addRevision(String revision, String description) {
        revisions.add(new Revision(revision, description));
    }

    /**
     * @return the lastUpdate
     */
    public Date getLastUpdate() {
        return lastUpdate;
    }

    /**
     * @param lastUpdate the lastUpdate to set
     */
    public void setLastUpdate(Date lastUpdate) {
        this.lastUpdate = lastUpdate;
    }

    /**
     * @return the organization
     */
    public String getOrganization() {
        return organization;
    }

    /**
     * @param organization the organization to set
     */
    public void setOrganization(String organization) {
        this.organization = organization;
    }

    /**
     * @return the contactInfo
     */
    public String getContactInfo() {
        return contactInfo;
    }

    /**
     * @param contactInfo the contactInfo to set
     */
    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
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
     * @return the revisions
     */
    public List<Revision> getRevisions() {
        return revisions;
    }

    /**
     * @param revisions the revisions to set
     */
    public void setRevisions(List<Revision> revisions) {
        this.revisions = revisions;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * com.sicpa.markii.monitoring.snmp.mib.MibObject#printMib(java.io.PrintWriter
     * )
     */
    @Override
    public void printMib(PrintWriter pw) throws IOException {
        pw.println("");
        pw.println("-- " + this.getOIDString());
        pw.println(this.getName() + " MODULE-IDENTITY");
        pw.println("\t-- Date Format is \"YYYYMMDDhhmmZ\"");
        pw.println("\tLAST-UPDATED \"" + sdf2.format(lastUpdate) + "0000Z\"");
        pw.println("\tORGANIZATION \"" + organization + "\"");
        pw.println("\tCONTACT-INFO \"" + contactInfo + "\"");
        pw.println("\tDESCRIPTION \"" + description + "\"");
        pw.println("\t-- Multiple pairs revision/description are allowed");
        for (Revision rev : revisions) {
            pw.println("\tREVISION \"" + rev.getRevision() + "\"");
            pw.println("\tDESCRIPTION \"" + rev.getDescription() + "\"");
        }
        pw.println("\t::= { " + this.getParent().getName() + " "
                + this.getOidElement() + " }");
    }

    /*
     * tobacoRenewal MODULE-IDENTITY -- Date Format is "YYYYMMDDhhmmZ"
     * LAST-UPDATED "201506170000Z" ORGANIZATION "SICPA" CONTACT-INFO
     * "ferdinand.neman@sicpa.com" DESCRIPTION
     * "This MIB used for managing TOBACO Renewal in SICPA MALAYSIA" -- Multiple
     * pairs revision/description are allowed REVISION "201506170000Z"
     * DESCRIPTION "Tobaco Renewal MIB" -- 22521.10.1 ::= { sicpaMalaysia 1 }
     */

    public static class Revision {
        private String revision;
        private String description;

        public Revision(String revision, String description) {
            super();
            this.revision = revision;
            this.description = description;
        }

        /**
         * @return the revision
         */
        public String getRevision() {
            return revision;
        }

        /**
         * @param revision the revision to set
         */
        public void setRevision(String revision) {
            this.revision = revision;
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
    }

}

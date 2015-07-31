package com.difotofoto.snmperfect.snmp.mib;

import com.difotofoto.snmperfect.snmp.NotificationMibFriendly;
import org.friendlysnmp.FException;
import org.friendlysnmp.FriendlyAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This class represent the structure of MIB file.
 * IT also become the center point of MIB agent on the library.
 * Here you can attach logic of Read Only OID and also scanner for notification.
 * <p/>
 * Developer must extends this class to develop their own MibDocument for their need.
 *
 * @author Fneman
 */
public abstract class MibDocument {

    private static Logger log = LoggerFactory.getLogger(MibDocument.class);

    protected Enterprise enterprise = new Enterprise();
    private String name;
    private List<Import> imports = new ArrayList<Import>();
    private Map<String, MibEntry> entries = new HashMap<String, MibEntry>();
    private ModuleIdentity moduleIdentity;
    private ModuleCompliance moduleCompliance;

    private List<ObjectIdentifier> objectIdentifiers = new ArrayList<ObjectIdentifier>();
    private List<ObjectType> objectTypes = new ArrayList<ObjectType>();
    private List<NotificationType> notificationTypes = new ArrayList<NotificationType>();
    private List<ObjectGroup> objectGroups = new ArrayList<ObjectGroup>();
    private List<NotificationGroup> notificationGroups = new ArrayList<NotificationGroup>();

    private Map<NotificationScanProcess, String> scanners = new HashMap<NotificationScanProcess, String>();
    private boolean scannersSTarted = false;


    /**
     * Set the name of this MIB document.
     *
     * @param name Name of the MIB document
     */
    public MibDocument(String name) {
        this.name = name;
    }

    /**
     * Add import entry into this document.
     *
     * @param imp Import entry to add.
     */
    public void addImport(Import imp) {
        imports.add(imp);
    }

    /**
     * Clear all import entry
     */
    public void clearImport() {
        imports.clear();
    }

    /**
     * Initialize this MibDocument with the Agent.
     *
     * @param agent Agent to be initialized with this document.
     */
    public void initializeAgent(FriendlyAgent agent) {

    }

    /**
     * Find an MIB entry with the specified entry name
     *
     * @param name Name to look at.
     * @return MibEntry object with specified name. or <code>null</code> if the MibEntry not found.
     */
    public MibEntry getEntryByName(String name) {
        return findEntry(enterprise, name);
    }

    /**
     * Find an MIB entry with the specified entry's OID string
     *
     * @param oid The OID we are looking for.
     * @return MibEntry object with specified name. or <code>null</code> if the MibEntry not found
     */
    public MibEntry getEntryByOidString(String oid) {
        return findEntryByOidString(enterprise, oid);
    }

    private MibEntry findEntry(MibEntry entry, String name) {
        if (entry.getName().equals(name)) {
            return entry;
        } else if (entry.getChildCount() > 0) {
            for (int i = 0; i < entry.getChildCount(); i++) {
                MibEntry e = findEntry(entry.getChildAt(i), name);
                if (e != null) return e;
            }
            return null;
        } else return null;
    }

    private MibEntry findEntryByOidString(MibEntry entry, String oid) {
        if (entry.getOIDString().equals(oid)) {
            return entry;
        } else if (entry.getChildCount() > 0) {
            for (int i = 0; i < entry.getChildCount(); i++) {
                MibEntry e = findEntryByOidString(entry.getChildAt(i), oid);
                if (e != null) return e;
            }
            return null;
        } else return null;
    }

    /**
     * Initialize all notifications in this MIB with the proper MIB.
     * Notification are threads that must trigger speciffic notification in the MIB.
     *
     * @param mib The Mib context.
     * @throws FException Thrown if there are MIB registration problem.
     */
    public void initializeNotification(NotificationMibFriendly mib) throws FException {
        for (NotificationGroup ng : notificationGroups) {
            ng.getScanner().setNotificationMibFriendly(mib);
        }
    }

    /**
     * Start all NotificationScanProcess-es bound to any notification group in this document.
     */
    public void startNotificationScanners() {
        if (scannersSTarted == true) return;
        scanners.clear();
        for (NotificationGroup ng : notificationGroups) {
            NotificationScanProcess scanner = ng.getScanner();
            if (scanner != null) {
                scanners.put(scanner, ng.getName());
            }
        }
        for (NotificationScanProcess scanner : scanners.keySet()) {
            if (scanner.isAlive() == false) {
                scanner.initialize();
                log.warn("Starting scanner " + scanners.get(scanner));
                scanner.start();
            }
        }
        scannersSTarted = true;
    }

    /**
     * Stop all notification scanner from running.
     */
    public void stopNotificationScanners() {
        if (scannersSTarted == false) return;
        for (NotificationScanProcess scanner : scanners.keySet()) {
            if (scanner.isAlive() == true) {
                log.warn("Stopping scanner " + scanners.get(scanner));
                scanner.stop();
                scanner.shutdown();
            }
        }
        scannersSTarted = false;
    }

    /**
     * Add MIB Entry into this MibDocument. This method will automatically,
     * try to identify the type of MIB file and set. Once identify, each of respective
     * entry will be grouped together.
     *
     * @param entry Entry to add.
     */
    private void addEntry(MibEntry entry) {
        entries.put(entry.getName(), entry);
        if (entry instanceof ModuleIdentity) {
            moduleIdentity = (ModuleIdentity) entry;
        } else if (entry instanceof ModuleCompliance) {
            moduleCompliance = (ModuleCompliance) entry;
        } else if (entry instanceof ObjectIdentifier) {
            objectIdentifiers.add((ObjectIdentifier) entry);
        } else if (entry instanceof ObjectType) {
            objectTypes.add((ObjectType) entry);
        } else if (entry instanceof NotificationType) {
            notificationTypes.add((NotificationType) entry);
        } else if (entry instanceof ObjectGroup) {
            objectGroups.add((ObjectGroup) entry);
        } else if (entry instanceof NotificationGroup) {
            notificationGroups.add((NotificationGroup) entry);
        }
        for (int i = 0; i < entry.getChildCount(); i++) {
            MibEntry child = entry.getChildAt(i);
            addEntry(child);
        }
    }


    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the imports
     */
    public List<Import> getImports() {
        return imports;
    }

    /**
     * @param imports the imports to set
     */
    public void setImports(List<Import> imports) {
        this.imports = imports;
    }

    /**
     * @return the entries
     */
    public Map<String, MibEntry> getEntries() {
        return entries;
    }

    /**
     * @param entries the entries to set
     */
    public void setEntries(Map<String, MibEntry> entries) {
        this.entries = entries;
    }

    /**
     * @return the moduleIdentity
     */
    public ModuleIdentity getModuleIdentity() {
        return moduleIdentity;
    }

    /**
     * @param moduleIdentity the moduleIdentity to set
     */
    public void setModuleIdentity(ModuleIdentity moduleIdentity) {
        this.moduleIdentity = moduleIdentity;
    }

    /**
     * @return the moduleCompliance
     */
    public ModuleCompliance getModuleCompliance() {
        return moduleCompliance;
    }

    /**
     * @param moduleCompliance the moduleCompliance to set
     */
    public void setModuleCompliance(ModuleCompliance moduleCompliance) {
        this.moduleCompliance = moduleCompliance;
    }

    /**
     * @return the objectIdentifiers
     */
    public List<ObjectIdentifier> getObjectIdentifiers() {
        return objectIdentifiers;
    }

    /**
     * @param objectIdentifiers the objectIdentifiers to set
     */
    public void setObjectIdentifiers(List<ObjectIdentifier> objectIdentifiers) {
        this.objectIdentifiers = objectIdentifiers;
    }

    /**
     * @return the objectTypes
     */
    public List<ObjectType> getObjectTypes() {
        return objectTypes;
    }

    /**
     * @param objectTypes the objectTypes to set
     */
    public void setObjectTypes(List<ObjectType> objectTypes) {
        this.objectTypes = objectTypes;
    }

    /**
     * @return the notificationTypes
     */
    public List<NotificationType> getNotificationTypes() {
        return notificationTypes;
    }

    /**
     * @param notificationTypes the notificationTypes to set
     */
    public void setNotificationTypes(List<NotificationType> notificationTypes) {
        this.notificationTypes = notificationTypes;
    }

    /**
     * @return the objectGroups
     */
    public List<ObjectGroup> getObjectGroups() {
        return objectGroups;
    }

    /**
     * @param objectGroups the objectGroups to set
     */
    public void setObjectGroups(List<ObjectGroup> objectGroups) {
        this.objectGroups = objectGroups;
    }

    /**
     * @return the notificationGroups
     */
    public List<NotificationGroup> getNotificationGroups() {
        return notificationGroups;
    }

    /**
     * @param notificationGroups the notificationGroups to set
     */
    public void setNotificationGroups(List<NotificationGroup> notificationGroups) {
        this.notificationGroups = notificationGroups;
    }

    /**
     * Indicate that the tree construction is done.
     * This method MUST be called once you have done constructing the MIB tree.
     * Calling this method second time will reconstruct the the tree.
     */
    public void endOfDocument() {
        entries.clear();
        addEntry(enterprise);
    }


    /*--
-- This is FriendlySNMP SICPA-MY-MIB
--

SICPA-SNMP-MIB DEFINITIONS ::= BEGIN*/
    public void printMib(PrintWriter pw) throws IOException {
        pw.println("--");
        pw.println("-- This is FriendlySNMP " + name);
        pw.println("--");
        pw.println("");
        pw.println(name + " DEFINITIONS ::= BEGIN");
        pw.println("");
        printImports(pw);
        pw.println("");
        pw.println("--");
        pw.println("-- MODULE IDENTITY");
        pw.println("--");
        pw.println("");
        moduleIdentity.printMib(pw);
        pw.println("");
        pw.println("--");
        pw.println("-- OBJECT IDENTIFIERS");
        pw.println("--");
        pw.println("");
        for (ObjectIdentifier oi : objectIdentifiers) {
            oi.printMib(pw);
        }
        pw.println("");
        pw.println("--");
        pw.println("-- SCALAR GET VALUES");
        pw.println("--");
        pw.println("");
        for (ObjectType o : objectTypes) {
            o.printMib(pw);
        }
        pw.println("");
        pw.println("--");
        pw.println("-- NOTIFICATION TYPES");
        pw.println("--");
        pw.println("");
        for (NotificationType o : notificationTypes) {
            o.printMib(pw);
        }
        pw.println("");
        pw.println("--");
        pw.println("-- MODULE COMPLIANCE");
        pw.println("--");
        pw.println("");
        moduleCompliance.printMib(pw);
        pw.println("");
        pw.println("--");
        pw.println("-- NOTIFICATION GROUPS");
        pw.println("--");
        pw.println("");
        for (NotificationGroup o : notificationGroups) {
            o.printMib(pw);
        }
        pw.println("");
        pw.println("--");
        pw.println("-- OBJECT GROUPS");
        pw.println("--");
        pw.println("");
        for (ObjectGroup o : objectGroups) {
            o.printMib(pw);
        }
        pw.println("END");
    }

    /*
IMPORTS
    enterprises,
    MODULE-IDENTITY,
    OBJECT-TYPE,
    Integer32,
    NOTIFICATION-TYPE
        FROM SNMPv2-SMI
    MODULE-COMPLIANCE, 
    OBJECT-GROUP,
    NOTIFICATION-GROUP
        FROM SNMPv2-CONF
    ;*/
    private void printImports(PrintWriter pw) throws IOException {
        pw.println("IMPORTS");
        for (Import imp : imports) {
            imp.printMib(pw);
        }
        pw.println("\t;");
    }

    public void saveToFile(String filePath) throws IOException {
        saveTo(new File(filePath));
    }

    public void saveTo(File file) throws IOException {
        if (file.exists()) {
            if (!file.isFile())
                throw new IOException(file.getAbsolutePath() + " is not not file. Cant delete directory.");
            file.delete();
        }
        file.createNewFile();
        FileOutputStream fos = new FileOutputStream(file);
        PrintWriter pw = new PrintWriter(fos);
        printImports(pw);
        pw.flush();
        pw.close();
    }

}

package com.difotofoto.snmperfect.snmp.simulation;

import com.difotofoto.snmperfect.snmp.mib.*;
import org.apache.log4j.Logger;
import org.snmp4j.smi.Integer32;
import org.snmp4j.smi.OctetString;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;

public class SicpaMibDocument extends MibDocument {

    private final Logger log = Logger.getLogger(SicpaMibDocument.class);
    public PrinterStatusEnum printerStatus = PrinterStatusEnum.CONNECTED;

    public LineStatusEnum lineStatus = LineStatusEnum.ONLINE;

    public int inkLevel = 100;

    public int successCount = 0;
    public int faileCount = 0;
    public String skuName = "SOMETHING";

    public SicpaMibDocument() throws DuplicateOIDException {
        super("SICPA-MIB");
        init();
    }

    public static void main(String[] args) {
        try {
            PrintWriter writer = new PrintWriter(System.out);
            SicpaMibDocument doc = new SicpaMibDocument();
            doc.printMib(writer);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (DuplicateOIDException e) {
            e.printStackTrace();
        }
    }

    private void init() throws DuplicateOIDException {
        addImport(new Import(new String[]{"enterprises", "MODULE-IDENTITY",
                "OBJECT-TYPE", "Integer32", "NOTIFICATION-TYPE"}, "SNMPv2-SMI"));
        addImport(new Import(new String[]{"MODULE-COMPLIANCE",
                "OBJECT-GROUP", "NOTIFICATION-GROUP"}, "SNMPv2-CONF"));

        // construct the company
        final MibEntry sicpa = new ObjectIdentifier("sicpa", 50000);
        final MibEntry sicpaMalaysia = new ObjectIdentifier("sicpaMY", 1);
        enterprise.addChildren(sicpa);
        sicpa.addChildren(sicpaMalaysia);

        // make the module
        final ModuleIdentity tobaccoRenewal = new ModuleIdentity(
                "tobaccoRenewal", 1, new Date(), "SICPA",
                "ferdinand.neman@sicpa.com",
                "SNMP Module to monitor elements in TobaccoRenewal Product.");
        sicpaMalaysia.addChildren(tobaccoRenewal);
        tobaccoRenewal.addRevision("201506180000Z", "The first test Revision");

        // setup the tree. The trunk here
        final ObjectIdentifier smdl = new ObjectIdentifier("smdl", 1);
        tobaccoRenewal.addChildren(smdl);

        // notification tree here.
        ObjectIdentifier smdlNotification = new ObjectIdentifier(
                "smdlNotification", 1);

        // Printer Notification
        final ObjectIdentifier smdlPrinterNotification = new ObjectIdentifier(
                "smdlPrinterNotification", 1);
        final ObjectIdentifier smdlPrinterNotificationPrefix = new ObjectIdentifier(
                "smdlPrinterNotificationPrefix", 0);
        smdlPrinterNotification.addChildren(smdlPrinterNotificationPrefix);

        final NotificationType smdlPrinterConnected = new NotificationType(
                "smdlPrinterConnected", 1, "PRINTER - CONNECTED");
        final NotificationType smdlPrinterDisconnected = new NotificationType(
                "smdlPrinterDisconnected", 2, "PRINTER - DISCONNECTED");
        final NotificationType smdlPrinterError = new NotificationType(
                "smdlPrinterError", 3, "PRINTER - ERROR");
        smdlPrinterNotificationPrefix.addChildren(smdlPrinterConnected);
        smdlPrinterNotificationPrefix.addChildren(smdlPrinterDisconnected);
        smdlPrinterNotificationPrefix.addChildren(smdlPrinterError);

        // Line Notification
        final ObjectIdentifier smdlLineNotification = new ObjectIdentifier(
                "smdlLineNotification", 2);
        final ObjectIdentifier smdlLineNotificationPrefix = new ObjectIdentifier(
                "smdlLineNotificationPrefix", 0);
        smdlLineNotification.addChildren(smdlLineNotificationPrefix);
        final NotificationType smdlLineOffLine = new NotificationType(
                "smdlLineOffLine", 1, "LINE - OFF");
        final NotificationType smdlLineOnLine = new NotificationType(
                "smdlLineOnLine", 2, "LINE - ON");
        smdlLineNotificationPrefix.addChildren(smdlLineOffLine);
        smdlLineNotificationPrefix.addChildren(smdlLineOnLine);

        // Ink Notification
        final ObjectIdentifier smdlInkNotification = new ObjectIdentifier(
                "smdlInkNotification", 3);
        final ObjectIdentifier smdlInkNotificationPrefix = new ObjectIdentifier(
                "smdlInkNotificationPrefix", 0);
        smdlInkNotification.addChildren(smdlInkNotificationPrefix);
        final NotificationType smdlInkEmpty = new NotificationType(
                "smdlInkEmpty", 1, "INK - EMPTY");
        final NotificationType smdlInkLow = new NotificationType("smdlInkLow",
                2, "INK - LOW");
        final NotificationType smdlInkMedium = new NotificationType(
                "smdlInkMedium", 3, "INK - MEDIUM");
        final NotificationType smdlInkFull = new NotificationType(
                "smdlInkFull", 4, "INK - FULL");
        smdlInkNotificationPrefix.addChildren(smdlInkEmpty);
        smdlInkNotificationPrefix.addChildren(smdlInkLow);
        smdlInkNotificationPrefix.addChildren(smdlInkMedium);
        smdlInkNotificationPrefix.addChildren(smdlInkFull);

        // smdlInkNotification.addChildren(smdlInkFull);

        smdlNotification.addChildren(smdlPrinterNotification);
        smdlNotification.addChildren(smdlLineNotification);
        smdlNotification.addChildren(smdlInkNotification);

        // Read only here
        final ObjectIdentifier smdlRead = new ObjectIdentifier("smdlRead", 2);

        final ObjectIdentifier smdlInkLevel = new ObjectIdentifier(
                "smdlInkLevel", 1);
        final ObjectIdentifier smdlSuccessCount = new ObjectIdentifier(
                "smdlSuccessCount", 2);
        final ObjectIdentifier smdlFailCountCount = new ObjectIdentifier(
                "smdlFailCountCount", 3);
        final ObjectIdentifier smdlSkuName = new ObjectIdentifier(
                "smdlSkuName", 4);
        smdlRead.addChildren(smdlInkLevel);
        smdlRead.addChildren(smdlSuccessCount);
        smdlRead.addChildren(smdlFailCountCount);
        smdlRead.addChildren(smdlSkuName);

        final ObjectIdentifier smdlInkLevelPrefix = new ObjectIdentifier(
                "smdlInkLevelPrefix", 0);
        final ObjectType smdlInkLevelValue = new ObjectType<Integer32>(
                "smdlInkLevelValue", 1, SyntaxEnum.Integer32,
                MaxAccessEnum.READ_ONLY,
                "To get information about the current ink level");
        smdlInkLevelValue.setValueGetter(new ValueGetter<Integer32>() {
            @Override
            public Integer32 getValue() {
                log.debug("Getter called smdlInkLevelValue");
                return new Integer32(inkLevel);
            }
        });
        smdlInkLevelPrefix.addChildren(smdlInkLevelValue);
        smdlInkLevel.addChildren(smdlInkLevelPrefix);

        final ObjectIdentifier smdlSuccessCountPrefix = new ObjectIdentifier(
                "smdlSuccessCountPrefix", 0);
        final ObjectType smdlSuccessCountValue = new ObjectType<Integer32>(
                "smdlSuccessCountValue",
                1,
                SyntaxEnum.Integer32,
                MaxAccessEnum.READ_ONLY,
                "To get information about the current success print count within the current job");
        smdlSuccessCountValue.setValueGetter(new ValueGetter<Integer32>() {
            @Override
            public Integer32 getValue() {
                log.debug("Getter called smdlSuccessCountValue");
                return new Integer32(successCount);
            }
        });
        smdlSuccessCountPrefix.addChildren(smdlSuccessCountValue);
        smdlSuccessCount.addChildren(smdlSuccessCountPrefix);

        final ObjectIdentifier smdlFailCountCountPrefix = new ObjectIdentifier(
                "smdlFailCountCountPrefix", 0);
        final ObjectType smdlFailCountCountValue = new ObjectType<Integer32>(
                "smdlFailCountCountValue", 1, SyntaxEnum.Integer32,
                MaxAccessEnum.READ_ONLY,
                "To get information about the current fail print count within the current job");
        smdlFailCountCountValue.setValueGetter(new ValueGetter<Integer32>() {
            @Override
            public Integer32 getValue() {
                log.debug("Getter called smdlFailCountCountValue");
                return new Integer32(faileCount);
            }
        });
        smdlFailCountCountPrefix.addChildren(smdlFailCountCountValue);
        smdlFailCountCount.addChildren(smdlFailCountCountPrefix);

        final ObjectIdentifier smdlSkuNamePrefix = new ObjectIdentifier(
                "smdlSkuNamePrefix", 0);
        final ObjectType smdlSkuNameValue = new ObjectType<OctetString>("smdlSkuNameValue",
                1, SyntaxEnum.OctetString, MaxAccessEnum.READ_ONLY,
                "To get information about the current SKU being printed");
        smdlSkuNameValue.setValueGetter(new ValueGetter<OctetString>() {
            @Override
            public OctetString getValue() {
                log.debug("Getter called smdlSkuNameValue");
                return new OctetString(skuName);
            }
        });
        smdlSkuNamePrefix.addChildren(smdlSkuNameValue);
        smdlSkuName.addChildren(smdlSkuNamePrefix);

        // Conformance Here
        final ObjectIdentifier smdlMibConformance = new ObjectIdentifier(
                "smdlMibConformance", 3);

        smdl.addChildren(smdlNotification);
        smdl.addChildren(smdlRead);
        smdl.addChildren(smdlMibConformance);

        final ModuleCompliance smdlCompliance = new ModuleCompliance(
                "smdlCompliance", 1,
                "The compliance statement for SNMP entities which implement this MIB");

        final NotificationGroup smdlPrinterNotificationGroup = new NotificationGroup(
                "smdlPrinterNotificationGroup", 2,
                "A collection of printer notification nodes in this MIB");
        smdlPrinterNotificationGroup.addNotificationType(smdlPrinterConnected);
        smdlPrinterNotificationGroup
                .addNotificationType(smdlPrinterDisconnected);
        smdlPrinterNotificationGroup.addNotificationType(smdlPrinterError);
        smdlPrinterNotificationGroup.setScanner(new NotificationScanProcess() {
            PrinterStatusEnum current = null;

            @Override
            public int sleepTime() {
                return 500;
            }

            @Override
            public void scan() {
                if (!printerStatus.equals(current)) {
                    current = printerStatus;
                    if (current.equals(PrinterStatusEnum.CONNECTED)) {
                        this.sendNotification(smdlPrinterConnected);
                    } else if (current.equals(PrinterStatusEnum.DISCONNECTED)) {
                        this.sendNotification(smdlPrinterDisconnected);
                    } else {
                        this.sendNotification(smdlPrinterError);
                    }
                }
            }

            @Override
            public void onStop() {
                // do nothing
            }

            @Override
            public void onStart() {
                // do nothing
            }

            @Override
            public void onInitialize() {
                // do nothing
            }

            @Override
            public void onShutDown() {
                // do nothing
            }
        });

        final NotificationGroup smdlLineNotificationGroup = new NotificationGroup(
                "smdlLineNotificationGroup", 3,
                "A collection of line notification nodes in this MIB");
        smdlLineNotificationGroup.addNotificationType(smdlLineOffLine);
        smdlLineNotificationGroup.addNotificationType(smdlLineOnLine);
        smdlLineNotificationGroup.setScanner(new NotificationScanProcess() {
            LineStatusEnum current = null;

            @Override
            public int sleepTime() {
                return 4000;
            }

            @Override
            public void scan() {
                if (!lineStatus.equals(current)) {
                    current = lineStatus;
                    if (current.equals(LineStatusEnum.OFFLINE)) {
                        this.sendNotification(smdlLineOffLine);
                    } else {
                        this.sendNotification(smdlLineOnLine);
                    }
                }
            }

            @Override
            public void onStop() {
                // do nothing
            }

            @Override
            public void onStart() {
                // do nothing
            }

            @Override
            public void onInitialize() {
                // do nothing
            }

            @Override
            public void onShutDown() {
                // do nothing
            }
        });

        final NotificationGroup smdlInkNotificationGroup = new NotificationGroup(
                "smdlInkNotificationGroup", 4,
                "A collection of ink notification nodes in this MIB");
        smdlInkNotificationGroup.addNotificationType(smdlInkEmpty);
        smdlInkNotificationGroup.addNotificationType(smdlInkLow);
        smdlInkNotificationGroup.addNotificationType(smdlInkMedium);
        smdlInkNotificationGroup.addNotificationType(smdlInkFull);
        smdlInkNotificationGroup.setScanner(new NotificationScanProcess() {

            InkStatusEnum currentInk = null;

            @Override
            public int sleepTime() {
                return 500;
            }

            @Override
            public void scan() {
                InkStatusEnum newInk = null;
                if (inkLevel < 5) {
                    newInk = InkStatusEnum.EMPTY;
                } else if (inkLevel >= 5 && inkLevel < 20) {
                    newInk = InkStatusEnum.LOW;
                } else if (inkLevel >= 20 && inkLevel < 80) {
                    newInk = InkStatusEnum.MEDIUM;
                } else {
                    newInk = InkStatusEnum.FULL;
                }
                if (!newInk.equals(currentInk)) {
                    currentInk = newInk;
                    if (currentInk.equals(InkStatusEnum.EMPTY)) {
                        this.sendNotification(smdlInkEmpty);
                    } else if (currentInk.equals(InkStatusEnum.LOW)) {
                        this.sendNotification(smdlInkLow);
                    } else if (currentInk.equals(InkStatusEnum.MEDIUM)) {
                        this.sendNotification(smdlInkMedium);
                    } else if (currentInk.equals(InkStatusEnum.FULL)) {
                        this.sendNotification(smdlInkFull);
                    }
                }
            }

            @Override
            public void onStop() {
                // do nothing
            }

            @Override
            public void onStart() {
                // do nothing
            }

            @Override
            public void onInitialize() {
                // do nothing
            }

            @Override
            public void onShutDown() {
                // do nothing
            }
        });

        final ObjectGroup smdlReadGroup = new ObjectGroup("smdlReadGroup", 5,
                "A collection of read only parameters in this MIB");
        smdlReadGroup.addObjectType(smdlInkLevelValue);
        smdlReadGroup.addObjectType(smdlSuccessCountValue);
        smdlReadGroup.addObjectType(smdlFailCountCountValue);
        smdlReadGroup.addObjectType(smdlSkuNameValue);

        smdlCompliance.addNotificationGroup(smdlPrinterNotificationGroup);
        smdlCompliance.addNotificationGroup(smdlLineNotificationGroup);
        smdlCompliance.addNotificationGroup(smdlInkNotificationGroup);
        smdlCompliance.addObjectGroup(smdlReadGroup);

        smdlMibConformance.addChildren(smdlCompliance);
        smdlMibConformance.addChildren(smdlPrinterNotificationGroup);
        smdlMibConformance.addChildren(smdlLineNotificationGroup);
        smdlMibConformance.addChildren(smdlInkNotificationGroup);
        smdlMibConformance.addChildren(smdlReadGroup);

        endOfDocument();
    }

    public PrinterStatusEnum getPrinterStatus() {
        return printerStatus;
    }

    public void setPrinterStatus(PrinterStatusEnum printerStatus) {
        this.printerStatus = printerStatus;
    }

    public LineStatusEnum getLineStatus() {
        return lineStatus;
    }

    public void setLineStatus(LineStatusEnum lineStatus) {
        this.lineStatus = lineStatus;
    }

    public int getInkLevel() {
        return inkLevel;
    }

    public void setInkLevel(int inkLevel) {
        if (inkLevel > 100) {
            this.inkLevel = 100;
        } else if (inkLevel < 0) {
            this.inkLevel = 0;
        } else {
            this.inkLevel = inkLevel;
        }
    }

    public int getPrintSuccess() {
        return successCount;
    }

    public void setPrintSuccess(int success) {
        successCount = success;
    }

    public int getPrintFail() {
        return faileCount;
    }

    public void setPrintFail(int fail) {
        faileCount = fail;
    }

    public String getSkuName() {
        return skuName;
    }

    public void setSkuName(String name) {
        skuName = name;
    }

    public enum PrinterStatusEnum {
        CONNECTED, DISCONNECTED, ERROR
    }

    public enum LineStatusEnum {
        OFFLINE, ONLINE
    }

    public enum InkStatusEnum {
        FULL, MEDIUM, LOW, EMPTY
    }

}

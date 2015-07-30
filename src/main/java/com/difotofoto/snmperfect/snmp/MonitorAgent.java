package com.difotofoto.snmperfect.snmp;

import com.difotofoto.snmperfect.snmp.mib.MibDocument;
import org.friendlysnmp.FConstant;
import org.friendlysnmp.FException;
import org.friendlysnmp.FriendlyAgent;

import java.io.IOException;
import java.util.Properties;

/**
 * This is the entry point class where the SNMP agent get instantiated an then run.
 * Simply create a new instance of this class and then it will run.
 * After instantiation, the control flow of the application will keep continued until the application is terminated.
 *
 * @author Fneman
 */
public class MonitorAgent {

    private MibDocument mibDocument;
    private NotificationMibFriendly notificationMibFriendly;
    private ReadOnlyScalarMibFriendly readOnlyScalarMibFriendly;
    private FriendlyAgent agent;
    private Properties propApp = new Properties();
    private MonitorConfiguration config;


    /**
     * The class constructor.
     *
     * @param mibDocument MibDocument that will supply the agent MIB structure and applicaton.
     * @param config      Configuration class that will configure the IP and port of this agent and machine that it will send notification to.
     */
    public MonitorAgent(MibDocument mibDocument, MonitorConfiguration config) {
        this.mibDocument = mibDocument;
        this.config = config;
        init();
    }

    private void init() {
        notificationMibFriendly = new NotificationMibFriendly(mibDocument);
        readOnlyScalarMibFriendly = new ReadOnlyScalarMibFriendly(mibDocument);

        try {
            propApp.load(MonitorAgent.class.getResourceAsStream("/snmp.properties"));
            propApp.put(FConstant.PREFIX_APP + "customer", "FriendlySNMP community");
            propApp.put(FConstant.PREFIX_DEPENDENCY + "log4j", "v1.2.14");
            propApp.put(FConstant.PREFIX_DEPENDENCY + "slf4j", "v1.6.1");

            if (config != null) {
                propApp.put("snmp.address.get-set", config.getQueryIP() + "/" + config.getQueryPort());
                propApp.put("snmp.address.send-notification", config.getNotifyIP() + "/" + config.getNotifyPort());
            }

            // propApp.put("snmp.plugin.jvm", "org.friendlysnmp.plugin.jvm.PluginJVM");

            String version = mibDocument.getModuleIdentity().getRevisions().get(0).getRevision();

            agent = new FriendlyAgent(config.getTitle(), version, propApp);

            mibDocument.initializeAgent(agent);
            mibDocument.initializeNotification(notificationMibFriendly);

            agent.addMIB(notificationMibFriendly);
            agent.addMIB(readOnlyScalarMibFriendly);

            agent.init();
            agent.start();

            mibDocument.startNotificationScanners();
        } catch (FException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}

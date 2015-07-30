package com.difotofoto.snmperfect.snmp;

/**
 * This class specify the IP configuration
 * of the SNMP agent.
 *
 * @author Fneman
 */
public class MonitorConfiguration {
    private String queryIP = null;
    private int queryPort = 0;
    private String notifyIP = null;
    private int notifyPort = 0;

    private String title = "Selfcare SNMP Monitor";

    /**
     * Get the IP where the agent will bind it self on. It should be a valid IP of the machine where this agent is running.
     *
     * @return the queryIP
     */
    public String getQueryIP() {
        return queryIP;
    }

    /**
     * Set the IP where the agent will bind it self on. It should be a valid IP of the machine where this agent is running.
     *
     * @param queryIP the queryIP to set
     */
    public void setQueryIP(String queryIP) {
        this.queryIP = queryIP;
    }

    /**
     * Get the UDP port number where the agent will listen.
     *
     * @return the queryPort
     */
    public int getQueryPort() {
        return queryPort;
    }

    /**
     * Set the UDP port number where the agent will listen
     *
     * @param queryPort the queryPort to set
     */
    public void setQueryPort(int queryPort) {
        this.queryPort = queryPort;
    }

    /**
     * Get the IP Address of the machine who will receive notification when this agent is sending them.
     *
     * @return the notifyIP The IP address of machine that will receive notification.
     */
    public String getNotifyIP() {
        return notifyIP;
    }

    /**
     * Set the IP address of machine who will receive notification when this agent is sending them.
     *
     * @param notifyIP the notifyIP to set
     */
    public void setNotifyIP(String notifyIP) {
        this.notifyIP = notifyIP;
    }

    /**
     * Get the port number on the machine that will receive notification.
     *
     * @return the notifyPort
     */
    public int getNotifyPort() {
        return notifyPort;
    }

    /**
     * Set the port number on the machine that will receive notification.
     *
     * @param notifyPort the notifyPort to set
     */
    public void setNotifyPort(int notifyPort) {
        this.notifyPort = notifyPort;
    }

    /**
     * Get the agent Title.
     *
     * @return the title
     */
    public String getTitle() {
        return title;
    }

    /**
     * Set the agent title.
     *
     * @param title the title to set
     */
    public void setTitle(String title) {
        this.title = title;
    }


}

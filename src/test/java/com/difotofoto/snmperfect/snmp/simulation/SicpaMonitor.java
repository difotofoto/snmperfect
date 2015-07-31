package com.difotofoto.snmperfect.snmp.simulation;

import com.difotofoto.snmperfect.snmp.MonitorAgent;
import com.difotofoto.snmperfect.snmp.MonitorConfiguration;
import com.difotofoto.snmperfect.snmp.mib.DuplicateOIDException;
import com.difotofoto.snmperfect.snmp.simulation.SicpaMibDocument.LineStatusEnum;
import com.difotofoto.snmperfect.snmp.simulation.SicpaMibDocument.PrinterStatusEnum;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class SicpaMonitor {

    static SicpaMibDocument scipaMib;

    @SuppressWarnings("unused")
    public static void main(String[] args) {
        if (args.length != 4) {
            System.out.println("USAGE : java -jar abc.jar <notifyip> <notifyport> <queryip> <queryport>");
            System.exit(-1);
        }

        String notifyIp = args[0];
        int notifyPort = Integer.parseInt(args[1]);
        String queryIp = args[2];
        int queryPort = Integer.parseInt(args[3]);


        try {
            scipaMib = new SicpaMibDocument();
            MonitorConfiguration config = new MonitorConfiguration();
            config.setNotifyIP(notifyIp);
            config.setNotifyPort(notifyPort);
            config.setQueryIP(queryIp);
            config.setQueryPort(queryPort);
            config.setTitle("SICPA SNMP MONITOR");

            ValueSetter vset = new ValueSetter();
            Thread t = new Thread(vset);
            t.start();

            MonitorAgent agent = new MonitorAgent(scipaMib, config);

            while (true) {
                try {
                    Thread.sleep(3000);
                } catch (InterruptedException e) {
                    // TODO Auto-generated catch block
                    e.printStackTrace();
                }
            }
        } catch (DuplicateOIDException e) {
            e.printStackTrace();
        }
    }

    public static class ValueSetter implements Runnable {
        public void run() {
            System.out.println("Simulation ON");
            try {
                while (true) {
                    System.out.println("tick ...");
                    File file = new File("simulation.properties");
                    if (file.exists()) {
                        try {
                            loadProp(file);
                        } catch (IOException e) {
                            try {
                                createNewProp(file);
                            } catch (IOException e1) {
                                System.out.println("Properties exist but there was a problem to read, and can not recreate one.");
                                break;
                            }
                        }
                    } else {
                        try {
                            createNewProp(file);
                        } catch (IOException e) {
                            System.out.println("Properties NOT exist and can not recreate one.");
                            break;
                        }
                    }

                    Thread.sleep(1000);

                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            System.out.println("Simulation OFF");
        }

        private void loadProp(File file) throws IOException {
            Properties prop = new Properties();
            prop.load(new FileInputStream(file));

            try {
                int inkLevel = Integer.parseInt(prop.getProperty("simulation.inklevel"));
                scipaMib.setInkLevel(inkLevel);
            } catch (NumberFormatException nfe) {
                System.out.println("simulation.inklevel  number format exception");
            }

            try {
                int success = Integer.parseInt(prop.getProperty("simulation.print.success"));
                scipaMib.setPrintSuccess(success);
            } catch (NumberFormatException nfe) {
                System.out.println("simulation.print.success  number format exception");
            }

            try {
                int fail = Integer.parseInt(prop.getProperty("simulation.print.fail"));
                scipaMib.setPrintSuccess(fail);
            } catch (NumberFormatException nfe) {
                System.out.println("simulation.print.fail  number format exception");
            }

            String sku = prop.getProperty("simulation.sku");
            if (sku == null) {
                System.out.println("simulation.sku  not set");
            } else {
                scipaMib.setSkuName(sku);
            }

            boolean lineonline = Boolean.parseBoolean(prop.getProperty("simulation.linestatus.online"));
            boolean printerconnect = Boolean.parseBoolean(prop.getProperty("simulation.printer.connected"));
            boolean printererror = Boolean.parseBoolean(prop.getProperty("simulation.printer.error"));

            if (lineonline) {
                scipaMib.setLineStatus(LineStatusEnum.ONLINE);
            } else {
                scipaMib.setLineStatus(LineStatusEnum.OFFLINE);
            }

            if (printererror) {
                scipaMib.setPrinterStatus(PrinterStatusEnum.ERROR);
            } else if (printerconnect) {
                scipaMib.setPrinterStatus(PrinterStatusEnum.CONNECTED);
            } else {
                scipaMib.setPrinterStatus(PrinterStatusEnum.DISCONNECTED);
            }

        }

        private Properties createNewProp(File file) throws IOException {
            Properties prop = new Properties();
            prop.put("simulation.inklevel", "100");
            prop.put("simulation.print.success", "10");
            prop.put("simulation.print.fail", "10");
            prop.put("simulation.sku", "SKUABC");
            prop.put("simulation.linestatus.online", "true");
            prop.put("simulation.printer.connected", "true");
            prop.put("simulation.printer.error", "false");
            if (file.exists() && file.delete()) {
                if (file.createNewFile()) {
                    prop.store(new FileOutputStream(file), "GENERATED PROPS");
                }
            }
            return prop;
        }
    }

}

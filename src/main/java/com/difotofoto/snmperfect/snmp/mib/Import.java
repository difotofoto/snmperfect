package com.difotofoto.snmperfect.snmp.mib;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Import section of the MIB file.
 *
 * @author Fneman
 */
public class Import {
    private List<String> imports = new ArrayList<String>();
    private String from;

    /**
     * Construct the import with list of imports and its source MIB.
     *
     * @param imports The list of imports will be used in this MIB
     * @param from    The source MIB where those import were defined.
     */
    public Import(String[] imports, String from) {
        super();
        Collections.addAll(this.imports, imports);
        this.from = from;
    }

    /**
     * Construct the import with list of imports and its source MIB.
     *
     * @param imports The list of imports will be used in this MIB
     * @param from    The source MIB where those import were defined.
     */
    public Import(List<String> imports, String from) {
        super();
        this.imports = imports;
        this.from = from;
    }

    /**
     * Get list of imports within this import statement.
     *
     * @return the imports List of imports
     */
    public List<String> getImports() {
        return imports;
    }

    /**
     * Set list of imports within this import statement
     *
     * @param imports the imports to set
     */
    public void setImports(List<String> imports) {
        this.imports = imports;
    }

    /**
     * Get the source MIB where all the import were defined.
     *
     * @return the from
     */
    public String getFrom() {
        return from;
    }

    /**
     * Set the source MIB where all the import were defined.
     *
     * @param from the from to set
     */
    public void setFrom(String from) {
        this.from = from;
    }

    /**
     * Print the import statement into the specified PrintWriter.
     *
     * @param pw
     * @throws IOException
     */
    public void printMib(PrintWriter pw) throws IOException {
        boolean first = true;
        StringBuilder sb = new StringBuilder();
        for (String s : imports) {
            if (!first) sb.append(", ");
            sb.append(s);
            first = false;
        }
        pw.println("\t" + sb.toString());
        pw.println("\t\tFROM " + from);
    }
}

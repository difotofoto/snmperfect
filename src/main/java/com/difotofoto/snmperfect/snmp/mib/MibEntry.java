package com.difotofoto.snmperfect.snmp.mib;

import org.snmp4j.smi.OID;

import javax.swing.tree.TreeNode;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;

/**
 * An entry inside MIB structure. ITs equals to a node of tree. Each MIB Entry may have multiple child entry and it can only have 1 parent.
 *
 * @author Fneman
 */
public abstract class MibEntry implements Comparable<MibEntry>, Serializable, TreeNode {

    private static final long serialVersionUID = -4606638522243534885L;

    private String name;
    private int oidElement;
    private MibEntry parent;
    private List<MibEntry> children = new ArrayList<MibEntry>();

    /**
     * Constructor of the MIB Entry
     *
     * @param name       Unique mib name. It supposed to be a unique name in the entire document.
     * @param oidElement The OID element number for this entry. It must be unique for all entries that have the same parent.
     */
    public MibEntry(String name, int oidElement) {
        super();
        setName(name);
        this.oidElement = oidElement;
    }

    /**
     * Get the OID string for this entry
     *
     * @return OID String for this entry.
     */
    public String getOIDString() {
        if (parent == null) return String.valueOf(oidElement);
        return parent.getOIDString() + "." + String.valueOf(oidElement);
    }

    /**
     * Get the OID object for this entry.
     *
     * @return OID object for this entry.
     */
    public OID getOID() {
        return new OID(getOIDString());
    }

    /**
     * @return the oidElement
     */
    public int getOidElement() {
        return oidElement;
    }

    /**
     * @param oidElement the oidElement to set
     */
    public void setOidElement(int oidElement) {
        this.oidElement = oidElement;
    }

    /**
     * @return the parent
     */
    public MibEntry getParent() {
        return parent;
    }

    /**
     * @param parent the parent to set
     */
    public void setParent(MibEntry parent) {
        this.parent = parent;
        this.parent.children.add(this);
    }

    /**
     * Add sub node after this OID entry
     *
     * @param child The node to add
     * @throws DuplicateOIDException Throws if the OID of the newly added child already duplicated to the other child.
     */
    public void addChildren(MibEntry child) throws DuplicateOIDException {
        for (MibEntry c : children) {
            if (c.getOidElement() == child.oidElement) {
                throw new DuplicateOIDException("Duplicated to " + c.getName());
            }
        }
        children.add(child);
        child.parent = this;
    }

    /**
     * Count the number of child OID under this entry.
     *
     * @return Number of child entries.
     */
    public int getChildCount() {
        return children.size();
    }

    /**
     * Get child entry on the specified index
     *
     * @param idx The index.
     * @return The MIB Entry.
     */
    public MibEntry getChildAt(int idx) {
        return children.get(idx);
    }

    /**
     * Remove the child entry on specified index
     *
     * @param idx The index.
     */
    public void removeChildAt(int idx) {
        children.remove(idx);
    }

    /**
     * Remove the child MIB entry
     *
     * @param obj The entry to remove.
     */
    public void removeChild(MibEntry obj) {
        children.remove(obj);
    }


    /**
     * Get the name of this mib entry
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Set the name of this mib entry
     *
     * @param name the name to set
     */
    public void setName(String name) {
        if(name.trim().length() > 32) throw new IllegalArgumentException("MIB Entry name '"+name+"' is too long. Maximum 32 characters.");
        this.name = name;
    }

    /**
     * Print this MIB entry as it should be printed on the MIB file.
     *
     * @param pw PrintWriter object use to print the entry line.
     * @throws IOException Throws if there is an IO problem during writing the entry.
     */
    public abstract void printMib(PrintWriter pw) throws IOException;

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    public boolean equals(Object o) {
        if (o instanceof MibEntry) {
            MibEntry that = (MibEntry) o;
            return this.getOIDString().equals(that.getOIDString());
        } else return false;
    }

    /* (non-Javadoc)
     * @see java.lang.Comparable#compareTo(java.lang.Object)
     */
    @Override
    public int compareTo(MibEntry that) {
        return this.getOIDString().compareTo(that.getOIDString());
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeNode#isLeaf()
     */
    @Override
    public boolean isLeaf() {
        return getChildCount() == 0;
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeNode#getAllowsChildren()
     */
    @Override
    public boolean getAllowsChildren() {
        return true;
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeNode#getIndex(javax.swing.tree.TreeNode)
     */
    @Override
    public int getIndex(TreeNode arg) {
        return children.indexOf(arg);
    }

    /* (non-Javadoc)
     * @see javax.swing.tree.TreeNode#children()
     */
    @SuppressWarnings("rawtypes")
    @Override
    public Enumeration children() {
        return new Enumeration<MibEntry>() {
            int index = -1;

            /* (non-Javadoc)
             * @see java.util.Enumeration#hasMoreElements()
             */
            @Override
            public boolean hasMoreElements() {
                return index < children.size() - 1;
            }

            /* (non-Javadoc)
             * @see java.util.Enumeration#nextElement()
             */
            @Override
            public MibEntry nextElement() {
                index++;
                return children.get(index);
            }

        };
    }

    /* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "MibEntry [name=" + name + " oid=" + this.getOIDString() + "]";
    }


}

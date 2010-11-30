/*
 * Portions of this file Copyright 1999-2005 University of Chicago
 * Portions of this file Copyright 1999-2005 The University of Southern California.
 *
 * This file or a portion of this file is licensed under the
 * terms of the Globus Toolkit Public License, found at
 * http://www.globus.org/toolkit/download/license.html.
 * If you redistribute this file, with or without
 * modifications, you must include this notice in the file.
 */
package org.globus.wsrf.container.usage;

import org.globus.usage.packets.CustomByteBuffer;

public class ContainerUsageBasePacketV2 extends ContainerUsageBasePacket {
    
    public static final short PACKET_VERSION = 2;
    
    protected String list;
    protected int optionalIntField = -1;
    
    public ContainerUsageBasePacketV2(short eventType) {
        super(eventType);
        setPacketVersion(PACKET_VERSION);
    }

    public void setOptionalIntField(int value) {
        this.optionalIntField = value;
    }

    public int getOptionalIntField() {
        return this.optionalIntField;
    }

    public void setServiceList(String list) {
        this.list = list;
    }
    
    public String getServiceList() {
        return this.list;
    }

    public void packCustomFields(CustomByteBuffer buf) {
        super.packCustomFields(buf);

        buf.putInt(this.optionalIntField);
     
        // write service list
        byte [] data = this.list.getBytes();
        buf.putShort((short)data.length);
        int maxLen = Math.min(data.length, buf.remaining());
        buf.put(data, 0, maxLen);
    }
    
    public void unpackCustomFields(CustomByteBuffer buf) {
        super.unpackCustomFields(buf);
        
        this.optionalIntField = buf.getInt();

        // read service list
        short len = buf.getShort();
        int maxLen = Math.min(len, buf.remaining());
        byte [] data = new byte[maxLen];
        buf.get(data);
        this.list = new String(data);
    }

    public String toString() {
        StringBuffer buf = new StringBuffer();
        buf.append(super.toString());
        buf.append(", optField: " + getOptionalIntField());
        buf.append(", services: " + getServiceList());
        return buf.toString();
    }

}

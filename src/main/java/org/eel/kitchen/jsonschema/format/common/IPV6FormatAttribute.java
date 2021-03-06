/*
 * Copyright (c) 2012, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the Lesser GNU General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * Lesser GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.eel.kitchen.jsonschema.format.common;

import com.fasterxml.jackson.databind.JsonNode;
import com.google.common.net.InetAddresses;
import org.eel.kitchen.jsonschema.format.FormatAttribute;
import org.eel.kitchen.jsonschema.report.Message;
import org.eel.kitchen.jsonschema.report.ValidationReport;
import org.eel.kitchen.jsonschema.util.NodeType;

/**
 * Validator for the {@code ipv6} format attribute.
 *
 * <p>This uses Guava's {@link InetAddresses} to do the job.</p>
 */
public final class IPV6FormatAttribute
    extends FormatAttribute
{
    private static final FormatAttribute instance = new IPV6FormatAttribute();

    private static final int IPV6_LENGTH = 16;

    private IPV6FormatAttribute()
    {
        super(NodeType.STRING);
    }

    public static FormatAttribute getInstance()
    {
        return instance;
    }

    @Override
    public void checkValue(final String fmt, final ValidationReport report,
        final JsonNode value)
    {
        final String ipaddr = value.textValue();

        if (InetAddresses.isInetAddress(ipaddr) && InetAddresses
            .forString(ipaddr).getAddress().length == IPV6_LENGTH)
            return;

        final Message.Builder msg = newMsg(fmt)
            .setMessage("string is not a valid IPv6 address")
            .addInfo("value", value);
        report.addMessage(msg.build());
    }
}

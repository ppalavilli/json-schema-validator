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

/**
 * Format attribute base classes
 *
 * <p>The {@code format} keyword is defined by section 5.23 of JSON Schema draft
 * v3, and (currently) section 7 of the next validation draft.</p>
 *
 * <p>Draft v4 defines a narrower subset than draft v3. The following format
 * attributes are common to both draft v3 and draft v4:</p>
 *
 * <ul>
 *     <li>{@code date-time};</li>
 *     <li>{@code email};</li>
 *     <li>{@code host-name};</li>
 *     <li>{@code ipv6};</li>
 *     <li>{@code regex};</li>
 *     <li>{@code uri}.</li>
 * </ul>
 *
 * <p>Draft v3 defines the following additional attributes:</p>
 *
 * <ul>
 *     <li>{@code date};</li>
 *     <li>{@code phone};</li>
 *     <li>{@code time};</li>
 *     <li>{@code utc-millisec};</li>
 *     <li>{@code color} (<b>unsupported</b>);</li>
 *     <li>{@code style} (<b>unsupported</b>).</li>
 * </ul>
 *
 * <p>Additionally, there is an attribute named {@code ip-address} in draft v3,
 * and {@code ipv4} in draft v4, which can validate IP addresses.</p>
 *
 * <p>Other format attributes not defined by any of these drafts can be found
 * in a sister project: <a
 * href="https://github.com/fge/json-schema-formats">json-schema-formats</a>.
 * </p>
 */

package org.eel.kitchen.jsonschema.format;
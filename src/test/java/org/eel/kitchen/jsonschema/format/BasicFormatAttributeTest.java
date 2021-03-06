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

package org.eel.kitchen.jsonschema.format;

import com.fasterxml.jackson.databind.JsonNode;
import org.eel.kitchen.jsonschema.SampleNodeProvider;
import org.eel.kitchen.jsonschema.report.ValidationReport;
import org.eel.kitchen.jsonschema.util.NodeType;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.util.Iterator;

import static org.mockito.Mockito.*;

public final class BasicFormatAttributeTest
{
    /*
     * Here we create a basic FormatAttribute covering a certain type set,
     * and check that the checkValue() method is only called for this type set.
     *
     * We pick a set of covered types in advance.
     */
    private static final String FMT = "fmt";

    private ValidationReport report;
    private FormatAttribute attribute;

    private static class CustomFormatAttribute
        extends FormatAttribute
    {

        private CustomFormatAttribute()
        {
            super(NodeType.INTEGER, NodeType.NUMBER, NodeType.STRING);
        }

        @Override
        public void checkValue(final String fmt, final ValidationReport report,
            final JsonNode value)
        {
        }
    }

    @BeforeMethod
    public void ctxInit()
    {
        report = new ValidationReport();
        attribute = spy(new CustomFormatAttribute());
    }

    @DataProvider
    public Iterator<Object[]> coveredInstances()
    {
        return SampleNodeProvider.getSamples(NodeType.INTEGER,
            NodeType.NUMBER, NodeType.STRING);
    }

    @Test(dataProvider = "coveredInstances")
    public void checkValueIsCalledOnCoveredInstances(final JsonNode instance)
    {
        attribute.validate(FMT, report, instance);
        verify(attribute, times(1)).checkValue(FMT, report, instance);
    }

    @DataProvider
    public Iterator<Object[]> ignoredInstances()
    {
        return SampleNodeProvider.getSamplesExcept(NodeType.INTEGER,
            NodeType.NUMBER, NodeType.STRING);
    }

    @Test(dataProvider = "ignoredInstances")
    public void checkValueIsNotCalledOnIgnoredInstances(final JsonNode instance)
    {
        attribute.validate(FMT, report, instance);
        verify(attribute, never()).checkValue(FMT, report, instance);
    }
}

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

package org.eel.kitchen.jsonschema.keyword;

import com.fasterxml.jackson.databind.JsonNode;
import org.eel.kitchen.jsonschema.SampleNodeProvider;
import org.eel.kitchen.jsonschema.metaschema.KeywordRegistries;
import org.eel.kitchen.jsonschema.metaschema.KeywordRegistry;
import org.eel.kitchen.jsonschema.report.ValidationReport;
import org.eel.kitchen.jsonschema.schema.AddressingMode;
import org.eel.kitchen.jsonschema.schema.SchemaRegistry;
import org.eel.kitchen.jsonschema.uri.URIManager;
import org.eel.kitchen.jsonschema.util.NodeType;
import org.eel.kitchen.jsonschema.validator.JsonValidatorCache;
import org.eel.kitchen.jsonschema.validator.ValidationContext;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import java.net.URI;
import java.util.Iterator;

import static org.mockito.Mockito.*;

public final class BasicKeywordValidatorTest
{
    /*
     * This test is to check that validateValue() is only called on the
     * declared types of the keyword validator.
     *
     * Note that we don't bother calling the full mechanism (ie,
     * build via reflection).
     */
    private ValidationContext context;
    private ValidationReport report;
    private KeywordValidator validator;

    @BeforeMethod
    public void initContext()
    {
        final KeywordRegistry keywordRegistry
            = KeywordRegistries.defaultRegistry();
        final URIManager manager = new URIManager();
        final SchemaRegistry registry = new SchemaRegistry(manager,
            URI.create(""), AddressingMode.CANONICAL);
        final JsonValidatorCache cache = new JsonValidatorCache(keywordRegistry,
            registry);

        context = new ValidationContext(cache);
        report = new ValidationReport();
        validator = spy(new BasicKeywordValidator());
    }

    private static class BasicKeywordValidator
        extends KeywordValidator
    {
        private BasicKeywordValidator()
        {
            super("foo", NodeType.INTEGER, NodeType.NUMBER, NodeType.STRING);
        }

        @Override
        protected void validate(final ValidationContext context,
            final ValidationReport report, final JsonNode instance)
        {
        }

        @Override
        public final String toString()
        {
            return "KeywordValidator mock";
        }
    }

    @DataProvider
    public Iterator<Object[]> coveredInstances()
    {
        return SampleNodeProvider.getSamples(NodeType.INTEGER,
            NodeType.NUMBER, NodeType.STRING);
    }

    @Test(dataProvider = "coveredInstances")
    public void validateIsCalledOnCoveredInstances(final JsonNode instance)
    {
        validator.validateInstance(context, report, instance);
        verify(validator, times(1)).validate(context, report, instance);
    }

    @DataProvider
    public Iterator<Object[]> ignoredInstances()
    {
        return SampleNodeProvider.getSamplesExcept(NodeType.INTEGER,
            NodeType.NUMBER, NodeType.STRING);
    }

    @Test(dataProvider = "ignoredInstances")
    public void validateIsNotCalledOnIgnoredInstances(final JsonNode instance)
    {
        validator.validateInstance(context, report, instance);
        verify(validator, never()).validate(context, report, instance);
    }
}

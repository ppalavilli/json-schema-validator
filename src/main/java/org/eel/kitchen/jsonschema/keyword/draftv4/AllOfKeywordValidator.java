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

package org.eel.kitchen.jsonschema.keyword.draftv4;

import com.fasterxml.jackson.databind.JsonNode;
import org.eel.kitchen.jsonschema.report.Message;
import org.eel.kitchen.jsonschema.report.ValidationReport;
import org.eel.kitchen.jsonschema.validator.JsonValidator;
import org.eel.kitchen.jsonschema.validator.ValidationContext;

/**
 * Keyword validator for the {@code allOf} keyword
 *
 * <p>An instance is valid against this keyword if and only if it is valid
 * against all schemas defined by its value.</p>
 */
public final class AllOfKeywordValidator
    extends SchemaArrayKeywordValidator
{
    /**
     * Constructor
     *
     */
    public AllOfKeywordValidator(final JsonNode schema)
    {
        super("allOf", schema);
    }
    @Override
    protected void validate(final ValidationContext context,
        final ValidationReport report, final JsonNode instance)
    {
        JsonValidator validator;

        final ValidationReport subReport = report.copy();

        for (final JsonNode subSchema: subSchemas) {
            validator = context.newValidator(subSchema);
            validator.validate(context, subReport, instance);
            if (subReport.isSuccess())
                continue;
            report.mergeWith(subReport);
            final Message.Builder msg = newMsg()
                .setMessage("instance does not validate against all schemas");
            report.addMessage(msg.build());
        }
    }

    @Override
    public String toString()
    {
        return "all of " + subSchemas.size() + " schema(s)";
    }
}

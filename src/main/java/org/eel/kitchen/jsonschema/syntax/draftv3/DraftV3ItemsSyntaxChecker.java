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

package org.eel.kitchen.jsonschema.syntax.draftv3;

import com.fasterxml.jackson.databind.JsonNode;
import org.eel.kitchen.jsonschema.report.Message;
import org.eel.kitchen.jsonschema.syntax.AbstractSyntaxChecker;
import org.eel.kitchen.jsonschema.syntax.SyntaxChecker;
import org.eel.kitchen.jsonschema.util.NodeType;

import java.util.List;

/**
 * Syntax validator for the {@code items} keyword as defined in draft v3
 *
 * <p>Unlike draft v4, in draft v3, it is not required that a schema array have
 * at least one element.</p>
 */
public final class DraftV3ItemsSyntaxChecker
    extends AbstractSyntaxChecker
{
    private static final SyntaxChecker instance
        = new DraftV3ItemsSyntaxChecker();

    public static SyntaxChecker getInstance()
    {
        return instance;
    }

    private DraftV3ItemsSyntaxChecker()
    {
        super("items", NodeType.ARRAY, NodeType.OBJECT);
    }

    @Override
    public void checkValue(final Message.Builder msg,
        final List<Message> messages, final JsonNode schema)
    {
        final JsonNode itemsNode = schema.get(keyword);

        if (!itemsNode.isArray())
            return;

        /*
         * If it is an array, check that its elements are all objects
         */

        final int size = itemsNode.size();
        NodeType type;

        for (int index = 0; index < size; index++) {
            type = NodeType.getNodeType(itemsNode.get(index));
            if (type == NodeType.OBJECT)
                continue;
            msg.setMessage("incorrect type for array element")
                .addInfo("index", index).addInfo("found", type)
                .addInfo("expected", NodeType.OBJECT);
            messages.add(msg.build());
        }
    }
}

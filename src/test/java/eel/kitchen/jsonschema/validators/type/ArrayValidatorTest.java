/*
 * Copyright (c) 2011, Francis Galiegue <fgaliegue@gmail.com>
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package eel.kitchen.jsonschema.validators.type;

import eel.kitchen.jsonschema.exception.MalformedJasonSchemaException;
import eel.kitchen.jsonschema.validators.Validator;
import eel.kitchen.util.JasonHelper;
import org.codehaus.jackson.JsonNode;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import java.io.IOException;
import java.util.List;

import static org.testng.Assert.assertEquals;
import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;

public class ArrayValidatorTest
{
    private JsonNode testNode, node;
    private Validator validator;
    private List<String> messages;
    private boolean ret;

    @BeforeClass
    public void setUp()
        throws IOException
    {
        testNode = JasonHelper.load("array.json");
    }

    @Test
    public void testMinItems()
        throws MalformedJasonSchemaException
    {
        node = testNode.get("minItems");

        validator = new ArrayValidator().setSchema(node.get("schema"));
        validator.setup();

        ret = validator.validate(node.get("bad"));
        messages = validator.getMessages();
        assertFalse(ret);
        assertEquals(messages.size(), 1);
        assertEquals(messages.get(0), "array has less than minItems elements");

        ret = validator.validate(node.get("good"));
        messages = validator.getMessages();
        assertTrue(ret);
        assertTrue(messages.isEmpty());
    }

    @Test
    public void testMaxItems()
        throws MalformedJasonSchemaException
    {
        node = testNode.get("maxItems");

        validator = new ArrayValidator().setSchema(node.get("schema"));
        validator.setup();

        ret = validator.validate(node.get("bad"));
        messages = validator.getMessages();
        assertFalse(ret);
        assertEquals(messages.size(), 1);
        assertEquals(messages.get(0), "array has more than maxItems elements");

        ret = validator.validate(node.get("good"));
        messages = validator.getMessages();
        assertTrue(ret);
        assertTrue(messages.isEmpty());
    }

    @Test
    public void testUniqueItems()
        throws MalformedJasonSchemaException
    {
        node = testNode.get("uniqueItems");

        validator = new ArrayValidator().setSchema(node.get("schema"));
        validator.setup();

        ret = validator.validate(node.get("bad"));
        messages = validator.getMessages();
        assertFalse(ret);
        assertEquals(messages.size(), 1);
        assertEquals(messages.get(0), "items in the array are not unique");

        ret = validator.validate(node.get("good"));
        messages = validator.getMessages();
        assertTrue(ret);
        assertTrue(messages.isEmpty());
    }

    @Test
    public void testItemsTuples()
        throws MalformedJasonSchemaException
    {
        node = testNode.get("itemsTuples");

        validator = new ArrayValidator().setSchema(node.get("schema"));
        validator.setup();

        ret = validator.validate(node.get("bad"));
        messages = validator.getMessages();
        assertFalse(ret);
        assertEquals(messages.size(), 1);
        assertEquals(messages.get(0), "array has extra elements, "
            + "which the schema disallows");

        ret = validator.validate(node.get("good"));
        messages = validator.getMessages();
        assertTrue(ret);
        assertTrue(messages.isEmpty());
    }
}
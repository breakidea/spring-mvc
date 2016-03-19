package org.ionnic.common.support.view.directive;

/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */

import java.io.IOException;
import java.io.Writer;

import org.apache.velocity.context.InternalContextAdapter;
import org.apache.velocity.exception.MethodInvocationException;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.runtime.directive.Directive;
import org.apache.velocity.runtime.parser.node.Node;

/**
 * Directive that puts an unrendered AST block in the context under the specified key, postponing rendering until the reference is used and rendered.
 *
 * @author Andrew Tetlaw
 * @author Nathan Bubna
 */
public class WidgetDirective extends Directive {

    @Override
    public String getName() {
        return "widget";
    }

    @Override
    public int getType() {
        return BLOCK;
    }

    @Override
    public boolean render( InternalContextAdapter context, Writer writer, Node node ) throws IOException, ResourceNotFoundException,
            ParseErrorException, MethodInvocationException {
        return true;
    }
}

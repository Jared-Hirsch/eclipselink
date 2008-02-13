/*******************************************************************************
 * Copyright (c) 1998, 2007 Oracle. All rights reserved.
 * This program and the accompanying materials are made available under the
 * terms of the Eclipse Public License v1.0, which accompanies this distribution
 * and is available at http://www.eclipse.org/legal/epl-v10.html.
 *
 * Contributors:
 *     Oracle - initial API and implementation from Oracle TopLink
 ******************************************************************************/  
package org.eclipse.persistence.internal.jpa.parsing;

/**
 * INTERNAL
 * <p><b>Purpose</b>: Superclass for Aggregate Nodes
 * <p><b>Responsibilities</b>:<ul>
 * <li> Apply itself to a query correctly
 * </ul>
 *    @author Jon Driscoll
 *    @since TopLink 5.0
 */

import org.eclipse.persistence.expressions.Expression;

public abstract class AggregateNode extends Node {

    private boolean distinct = false;

    /**
     * INTERNAL
     */
    public String resolveAttribute() {
        Node arg = getLeft();
        return arg.isDotNode() ? ((DotNode)arg).resolveAttribute() : null;
    }

    /**
     * resolveClass: Answer the class associated with my left node.
     */
    public Class resolveClass(GenerationContext context) {
        return getLeft().resolveClass(context);
    }

    /**
     * INTERNAL
     * Is this node an Aggregate node
     */
    public boolean isAggregateNode() {
        return true;
    }

    /** */
    public boolean usesDistinct() {
        return distinct;
    }

    /** */
    public void setDistinct(boolean distinct) {
        this.distinct = distinct;
    }

    /**
     * INTERNAL
     * Return a EclipseLink expression generated using the left node
     */
    public Expression generateExpression(GenerationContext context) {
        String name = getAsString();
        Expression aggregateExpr = context.expressionFor(name);
        if (aggregateExpr == null) {
            Expression arg = getLeft().generateExpression(context);
            if (usesDistinct()) {
                arg = arg.distinct();
            }
            aggregateExpr = addAggregateExression(arg);
            context.addExpression(aggregateExpr, name);
        }
        return aggregateExpr;
    }

    /** 
     * INTERNAL
     */
    protected abstract Expression addAggregateExression(Expression expr);

}

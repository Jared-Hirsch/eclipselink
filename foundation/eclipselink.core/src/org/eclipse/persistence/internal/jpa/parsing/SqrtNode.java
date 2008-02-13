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

import org.eclipse.persistence.expressions.*;

/**
 * INTERNAL
 * <p><b>Purpose</b>: Represent a SQRT
 * <p><b>Responsibilities</b>:<ul>
 * <li> Generate the correct expression for SQRT
 * </ul>
 *    @author Jon Driscoll and Joel Lucuik
 *    @since TopLink 4.0
 */
public class SqrtNode extends ArithmeticFunctionNode {

    /**
     * Return a new SqrtNode
     */
    public SqrtNode() {
        super();
    }

    /**
     * INTERNAL
     * Validate node and calculate its type.
     */
    public void validate(ParseTreeContext context) {
        TypeHelper typeHelper = context.getTypeHelper();
        if (left != null) {
            left.validate(context);
        }
        setType(typeHelper.getDoubleType());
    }

    /**
     * INTERNAL 
     */
    public void validateParameter(ParseTreeContext context, Object contextType) {
        // delegate to the argument node
        left.validateParameter(context, contextType);
    }

    /**
     * INTERNAL
     * Generate the EclipseLink expression for this node
     */
    public Expression generateExpression(GenerationContext context) {
        Expression whereClause = getLeft().generateExpression(context);
        whereClause = whereClause.getFunction(ExpressionOperator.Sqrt);
        return whereClause;
    }

}

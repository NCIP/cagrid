/*
 * Portions of this file Copyright 1999-2005 University of Chicago
 * Portions of this file Copyright 1999-2005 The University of Southern California.
 *
 * This file or a portion of this file is licensed under the
 * terms of the Globus Toolkit Public License, found at
 * http://www.globus.org/toolkit/download/license.html.
 * If you redistribute this file, with or without
 * modifications, you must include this notice in the file.
 */
package org.globus.wsrf.impl.properties;

import java.util.List;
import java.util.ArrayList;

import org.apache.axis.message.MessageElement;
import org.apache.axis.message.Text;

import org.globus.wsrf.query.ExpressionEvaluator;
import org.globus.wsrf.query.QueryException;
import org.globus.wsrf.query.UnsupportedQueryDialectException;
import org.globus.wsrf.query.QueryEvaluationException;
import org.globus.wsrf.query.InvalidQueryExpressionException;
import org.globus.wsrf.ResourcePropertySet;
import org.globus.wsrf.utils.Resources;
import org.globus.util.I18n;

import org.oasis.wsrf.properties.QueryExpressionType;

public class TestExpressionEvaluator implements ExpressionEvaluator {

    private static I18n i18n = I18n.getI18n(Resources.class.getName());

    public static final String [] DIALECTS =
        new String [] { "http://www.globus.org/foobar" };

    public static final String TEST_QUERY = "testExpression";
    public static final String TEST_QUERY_RESPONSE = "hello";

    public String[] getDialects() {
        return DIALECTS;
    }

    public Object evaluate(QueryExpressionType expression,
                           ResourcePropertySet resourcePropertySet)
        throws UnsupportedQueryDialectException,
               QueryEvaluationException,
               InvalidQueryExpressionException,
               QueryException {
        try {
            return evaluateQuery(expression, resourcePropertySet);
        } catch (QueryException e) {
            throw e;
        } catch (Exception e) {
            throw new QueryEvaluationException(e);
        }
    }

    private List evaluateQuery(QueryExpressionType expression,
                               ResourcePropertySet resourcePropertySet)
        throws Exception {
        if (expression == null) {
            throw new QueryException(i18n.getMessage("noQuery"));
        }
        if (expression.getDialect() == null) {
            throw new QueryException(
                i18n.getMessage("nullArgument", "expression.dialect")
            );
        }
        String dialect = expression.getDialect().toString();
        if (!(dialect.equals(DIALECTS[0]))) {
            throw new UnsupportedQueryDialectException(
                i18n.getMessage("invalidQueryExpressionDialect"));
        }

        if (expression.getValue() == null ||
            expression.getValue().toString().trim().length() == 0) {
            throw new InvalidQueryExpressionException(
                i18n.getMessage("noQueryString"));
        }

        // TODO: error checking?
        String query = expression.getValue().toString().trim();

        if (query.equals(TEST_QUERY)) {
            ArrayList resultList = new ArrayList();
            resultList.add(new MessageElement(new Text(TEST_QUERY_RESPONSE)));
            return resultList;
        } else {
            throw new QueryException("Invalid expression");
        }
    }

}


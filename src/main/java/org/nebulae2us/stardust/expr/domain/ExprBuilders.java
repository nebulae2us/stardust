
package org.nebulae2us.stardust.expr.domain;

import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.expr.domain.Expression;
import org.nebulae2us.stardust.expr.domain.ExpressionBuilder;
import org.nebulae2us.stardust.expr.domain.ComparisonExpression;
import org.nebulae2us.stardust.expr.domain.ComparisonExpressionBuilder;
import org.nebulae2us.stardust.expr.domain.InListExpression;
import org.nebulae2us.stardust.expr.domain.InListExpressionBuilder;
import org.nebulae2us.stardust.expr.domain.LogicalExpression;
import org.nebulae2us.stardust.expr.domain.LogicalExpressionBuilder;

public class ExprBuilders {

	public static final DestinationClassResolver DESTINATION_CLASS_RESOLVER = new DestinationClassResolverByMap(
			new MapBuilder<Class<?>, Class<?>> ()
				.put(Expression.class, ExpressionBuilder.class)
				.put(ComparisonExpression.class, ComparisonExpressionBuilder.class)
				.put(InListExpression.class, InListExpressionBuilder.class)
				.put(LogicalExpression.class, LogicalExpressionBuilder.class)
			.toMap()
			);

	public static Converter converter() {
		return new Converter(DESTINATION_CLASS_RESOLVER, true);
	}

    public static ExpressionBuilder<?> expression() {
        return new ExpressionBuilder<Object>();
    }

    public static ExpressionBuilder<?> expression$restoreFrom(BuilderRepository repo, int builderId) {
        return (ExpressionBuilder<?>)repo.get(builderId);
    }

    public static ExpressionBuilder<?> expression$copyFrom(Expression expression) {
    	ExpressionBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(expression).to(ExpressionBuilder.class);
    	return result;
    }
    
    public static ExpressionBuilder<?> wrap(Expression expression) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(expression).to(ExpressionBuilder.class);
    }

    public static ComparisonExpressionBuilder<?> comparisonExpression() {
        return new ComparisonExpressionBuilder<Object>();
    }

    public static ComparisonExpressionBuilder<?> comparisonExpression$restoreFrom(BuilderRepository repo, int builderId) {
        return (ComparisonExpressionBuilder<?>)repo.get(builderId);
    }

    public static ComparisonExpressionBuilder<?> comparisonExpression$copyFrom(ComparisonExpression comparisonExpression) {
    	ComparisonExpressionBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(comparisonExpression).to(ComparisonExpressionBuilder.class);
    	return result;
    }
    
    public static ComparisonExpressionBuilder<?> wrap(ComparisonExpression comparisonExpression) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(comparisonExpression).to(ComparisonExpressionBuilder.class);
    }

    public static InListExpressionBuilder<?> inListExpression() {
        return new InListExpressionBuilder<Object>();
    }

    public static InListExpressionBuilder<?> inListExpression$restoreFrom(BuilderRepository repo, int builderId) {
        return (InListExpressionBuilder<?>)repo.get(builderId);
    }

    public static InListExpressionBuilder<?> inListExpression$copyFrom(InListExpression inListExpression) {
    	InListExpressionBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(inListExpression).to(InListExpressionBuilder.class);
    	return result;
    }
    
    public static InListExpressionBuilder<?> wrap(InListExpression inListExpression) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(inListExpression).to(InListExpressionBuilder.class);
    }

    public static LogicalExpressionBuilder<?> logicalExpression() {
        return new LogicalExpressionBuilder<Object>();
    }

    public static LogicalExpressionBuilder<?> logicalExpression$restoreFrom(BuilderRepository repo, int builderId) {
        return (LogicalExpressionBuilder<?>)repo.get(builderId);
    }

    public static LogicalExpressionBuilder<?> logicalExpression$copyFrom(LogicalExpression logicalExpression) {
    	LogicalExpressionBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(logicalExpression).to(LogicalExpressionBuilder.class);
    	return result;
    }
    
    public static LogicalExpressionBuilder<?> wrap(LogicalExpression logicalExpression) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(logicalExpression).to(LogicalExpressionBuilder.class);
    }

    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

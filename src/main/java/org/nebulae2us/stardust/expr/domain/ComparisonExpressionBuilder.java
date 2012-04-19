package org.nebulae2us.stardust.expr.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;

@Builder(destination=ComparisonExpression.class)
public class ComparisonExpressionBuilder<P> extends ExpressionBuilder<P> {

	public ComparisonExpressionBuilder() {
		super();
	}
	
	public ComparisonExpressionBuilder(P parentBuilder) {
		super(parentBuilder);
	}

	protected ComparisonExpressionBuilder(ComparisonExpression<?> wrapped) {
		super(wrapped);
	}

	@Override
    public ComparisonExpressionBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	@Override
	public ComparisonExpression<?> getWrappedObject() {
		return (ComparisonExpression<?>)this.$$$wrapped;
	}

    public ComparisonExpression<?> toComparisonExpression() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(ComparisonExpression.class);
    }
    
    public <T> ComparisonExpression<T> toComparisonExpression(Class<T> T) {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(ComparisonExpression.class);
    }
	

	@Override
    public ComparisonExpression<?> toExpression() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(ComparisonExpression.class);
    }
    


	private Object value;
	
	public Object getValue() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.value, Object.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, ComparisonExpression.class, "value");
			this.value = new WrapConverter(ExprBuilders.DESTINATION_CLASS_RESOLVER).convert(o).to(Object.class);
		}

		return value;
	}

	public void setValue(Object value) {
		verifyMutable();
		this.value = value;
	}

	public ComparisonExpressionBuilder<P> value(Object value) {
		verifyMutable();
		this.value = value;
		return this;
	}

	private ComparisonOperator operator;
	
	public ComparisonOperator getOperator() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.operator, ComparisonOperator.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, ComparisonExpression.class, "operator");
			this.operator = new WrapConverter(ExprBuilders.DESTINATION_CLASS_RESOLVER).convert(o).to(ComparisonOperator.class);
		}

		return operator;
	}

	public void setOperator(ComparisonOperator operator) {
		verifyMutable();
		this.operator = operator;
	}

	public ComparisonExpressionBuilder<P> operator(ComparisonOperator operator) {
		verifyMutable();
		this.operator = operator;
		return this;
	}

	@Override
	public ComparisonExpressionBuilder<P> negated(boolean negated) {
		return (ComparisonExpressionBuilder<P>)super.negated(negated);
	}

    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

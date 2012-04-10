package org.nebulae2us.stardust.expr.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;

@Builder(destination=Expression.class)
public class ExpressionBuilder<P> implements Wrappable<Expression> {

	protected final Expression $$$wrapped;

	protected final P $$$parentBuilder;
	
	public ExpressionBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public ExpressionBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected ExpressionBuilder(Expression wrapped) {
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public ExpressionBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public Expression getWrappedObject() {
		return this.$$$wrapped;
	}

	protected void verifyMutable() {
		if (this.$$$wrapped != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
		}
	}

	public P end() {
		return this.$$$parentBuilder;
	}

    public Expression toExpression() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(Expression.class);
    }



	private boolean negated;
	
	public boolean getNegated() {
		return negated;
	}

	public void setNegated(boolean negated) {
		verifyMutable();
		this.negated = negated;
	}

	public ExpressionBuilder<P> negated(boolean negated) {
		verifyMutable();
		this.negated = negated;
		return this;
	}
}

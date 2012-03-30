package org.nebulae2us.stardust.expr.domain;

import org.nebulae2us.electron.*;


public class ExpressionBuilder<B> implements Convertable {

	private final Expression $$$savedTarget;

	private ConverterOption $$$option;

	private final B $$$parentBuilder;

	protected ExpressionBuilder(Expression expression) {
		if (expression == null) {
			throw new NullPointerException();
		}
	
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = expression;
	}

	public ExpressionBuilder(ConverterOption option, B parentBuilder) {
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = parentBuilder;
		this.$$$savedTarget = null;
	}

	public ExpressionBuilder() {
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public ExpressionBuilder(ConverterOption option) {
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public ConverterOption getConverterOption() {
		return this.$$$option;
	}
	
	public void setConverterOption(ConverterOption option) {
		this.$$$option = option;
	}
	
	public Expression getSavedTarget() {
		return this.$$$savedTarget;
	}

	public boolean convertableTo(Class<?> c) {
		return this.$$$savedTarget != null && c.isAssignableFrom(this.$$$savedTarget.getClass());
	}

	@SuppressWarnings("unchecked")
	public <T> T convertTo(Class<T> c) {
		if (!convertableTo(c)) {
			throw new IllegalArgumentException();
		}
		return (T)this.$$$savedTarget;
	}

    protected void copyAttributes(ExpressionBuilder<?> copy) {
    	this.negated = copy.negated;
    }

    public B end() {
        return this.$$$parentBuilder;
    }

    public ExpressionBuilder<B> storeTo(BuilderRepository repo, int builderId) {
    	repo.put(builderId, this);
    	return this;
    }

    public Expression toExpression() {
    	return new Converter(this.$$$option).convert(this).to(Expression.class);
    }

    private boolean negated;

    public boolean isNegated() {
        return this.negated;
    }

    public void setNegated(boolean negated) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.negated = negated;
    }

    public ExpressionBuilder<B> negated(boolean negated) {
        this.negated = negated;
        return this;
    }
}

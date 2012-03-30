package org.nebulae2us.stardust.expr.domain;

import java.util.*;
import org.nebulae2us.electron.*;


public class LogicalExpressionBuilder<B> extends ExpressionBuilder<B> implements Convertable {

	private final LogicalExpression $$$savedTarget;

	private ConverterOption $$$option;

	private final B $$$parentBuilder;

	protected LogicalExpressionBuilder(LogicalExpression logicalExpression) {
		super(logicalExpression);
		if (logicalExpression == null) {
			throw new NullPointerException();
		}
	
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = logicalExpression;
	}

	public LogicalExpressionBuilder(ConverterOption option, B parentBuilder) {
		super(option, parentBuilder);
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = parentBuilder;
		this.$$$savedTarget = null;
	}

	public LogicalExpressionBuilder() {
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public LogicalExpressionBuilder(ConverterOption option) {
		super(option);
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
	
	public LogicalExpression getSavedTarget() {
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

    protected void copyAttributes(LogicalExpressionBuilder<?> copy) {
		super.copyAttributes(copy);
    	this.expressions = copy.expressions;
		this.operator = copy.operator;
    }

    public B end() {
        return this.$$$parentBuilder;
    }

    public LogicalExpressionBuilder<B> storeTo(BuilderRepository repo, int builderId) {
    	repo.put(builderId, this);
    	return this;
    }

    public LogicalExpression toLogicalExpression() {
    	return new Converter(this.$$$option).convert(this).to(LogicalExpression.class);
    }

    @Override
    public LogicalExpression toExpression() {
    	return new Converter(this.$$$option).convert(this).to(LogicalExpression.class);
    }

    private List<ExpressionBuilder<?>> expressions;

    public List<ExpressionBuilder<?>> getExpressions() {
        return this.expressions;
    }

    public void setExpressions(List<ExpressionBuilder<?>> expressions) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.expressions = expressions;
    }

    public ExpressionBuilder<LogicalExpressionBuilder<B>> expression() {
        if (this.expressions == null) {
            this.expressions = new ArrayList<ExpressionBuilder<?>>();
        }

        ExpressionBuilder<LogicalExpressionBuilder<B>> expression = new ExpressionBuilder<LogicalExpressionBuilder<B>>(this.$$$option, this);
        
        this.expressions.add(expression);
        
        return expression;
    }

    public LogicalExpressionBuilder<B> expression(ExpressionBuilder<?> expression) {
        if (this.expressions == null) {
            this.expressions = new ArrayList<ExpressionBuilder<?>>();
        }
        this.expressions.add(expression);
        return this;
    }

    public LogicalExpressionBuilder<B> expression(Expression expression) {
        if (this.expressions == null) {
            this.expressions = new ArrayList<ExpressionBuilder<?>>();
        }
    	ExpressionBuilder<?> wrap = new WrapConverter(this.$$$option).convert(expression).to(ExpressionBuilder.class);
        this.expressions.add(wrap);
        return this;
    }

    public LogicalExpressionBuilder<B> expressions(ExpressionBuilder<?> ... expressions) {
        if (this.expressions == null) {
            this.expressions = new ArrayList<ExpressionBuilder<?>>();
        }
        for (ExpressionBuilder<?> o : expressions) {
            this.expressions.add(o);
        }
        return this;
    }

    public LogicalExpressionBuilder<B> expressions(Expression ... expressions) {
        if (this.expressions == null) {
            this.expressions = new ArrayList<ExpressionBuilder<?>>();
        }
        for (Expression o : expressions) {
	    	ExpressionBuilder<?> wrap = new WrapConverter(this.$$$option).convert(o).to(ExpressionBuilder.class);
            this.expressions.add(wrap);
        }
        return this;
    }

    public LogicalExpressionBuilder<B> expression$restoreFrom(BuilderRepository repo, int builderId) {
        Object expression = repo.get(builderId);
        if (this.expressions == null) {
            this.expressions = new ArrayList<ExpressionBuilder<?>>();
        }

        if (expression == null) {
        	if (repo.isSupportLazy()) {
                
        		final int size = this.expressions.size();
        		this.expressions.add(null);

        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						LogicalExpressionBuilder.this.expressions.set(size, (ExpressionBuilder<?>)arguments[0]);
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.expressions.add((ExpressionBuilder<?>)expression);
        }
    	
    	return this;
    }

    public LogicalExpressionBuilder<B> expressions$restoreFrom(BuilderRepository repo, int ... builderIds) {

    	for (int builderId : builderIds) {
    		expression$restoreFrom(repo, builderId);
    	}
    	
    	return this;
    }

    private LogicalOperator operator;

    public LogicalOperator getOperator() {
        return this.operator;
    }

    public void setOperator(LogicalOperator operator) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.operator = operator;
    }

    public LogicalExpressionBuilder<B> operator(LogicalOperator operator) {
        this.operator = operator;
        return this;
    }

	@Override
    public LogicalExpressionBuilder<B> negated(boolean negated) {
        return (LogicalExpressionBuilder<B>)super.negated(negated);
    }
}

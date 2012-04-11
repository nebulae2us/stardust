package org.nebulae2us.stardust.expr.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;

@Builder(destination=LogicalExpression.class)
public class LogicalExpressionBuilder<P> extends ExpressionBuilder<P> {

	public LogicalExpressionBuilder() {
		super();
	}
	
	public LogicalExpressionBuilder(P parentBuilder) {
		super(parentBuilder);
	}

	protected LogicalExpressionBuilder(LogicalExpression wrapped) {
		super(wrapped);
	}

	@Override
    public LogicalExpressionBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	@Override
	public LogicalExpression getWrappedObject() {
		return (LogicalExpression)this.$$$wrapped;
	}

    public LogicalExpression toLogicalExpression() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(LogicalExpression.class);
    }
    

	@Override
    public LogicalExpression toExpression() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(LogicalExpression.class);
    }
    


	private List<ExpressionBuilder<?>> expressions;
	
	public List<ExpressionBuilder<?>> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<ExpressionBuilder<?>> expressions) {
		verifyMutable();
		this.expressions = expressions;
	}

	public LogicalExpressionBuilder<P> expressions(ExpressionBuilder<?> ... expressions) {
		verifyMutable();
		return expressions(new ListBuilder<ExpressionBuilder<?>>().add(expressions).toList());
	}
	
	public LogicalExpressionBuilder<P> expressions(Collection<ExpressionBuilder<?>> expressions) {
		verifyMutable();
		if (this.expressions == null) {
			this.expressions = new ArrayList<ExpressionBuilder<?>>();
		}
		if (expressions != null) {
			for (ExpressionBuilder<?> e : expressions) {
				CollectionUtils.addItem(this.expressions, e);
			}
		}
		return this;
	}

	public ExpressionBuilder<? extends LogicalExpressionBuilder<P>> expressions$addExpression() {
		verifyMutable();
		if (this.expressions == null) {
			this.expressions = new ArrayList<ExpressionBuilder<?>>();
		}
		
		ExpressionBuilder<LogicalExpressionBuilder<P>> result =
				new ExpressionBuilder<LogicalExpressionBuilder<P>>(this);
		
		CollectionUtils.addItem(this.expressions, result);
		
		return result;
	}
	
	public LogicalExpressionBuilder<? extends LogicalExpressionBuilder<P>> expressions$addLogicalExpression() {
		verifyMutable();
		if (this.expressions == null) {
			this.expressions = new ArrayList<ExpressionBuilder<?>>();
		}
		
		LogicalExpressionBuilder<LogicalExpressionBuilder<P>> result =
				new LogicalExpressionBuilder<LogicalExpressionBuilder<P>>(this);
		
		CollectionUtils.addItem(this.expressions, result);
		
		return result;
	}
	

	public class Expressions$$$builder<P1 extends LogicalExpressionBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected Expressions$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public ExpressionBuilder<Expressions$$$builder<P1>> expression$begin() {
			ExpressionBuilder<Expressions$$$builder<P1>> result = new ExpressionBuilder<Expressions$$$builder<P1>>(this);
			CollectionUtils.addItem(LogicalExpressionBuilder.this.expressions, result);
			return result;
		}
		
		public LogicalExpressionBuilder<Expressions$$$builder<P1>> logicalExpression$begin() {
			LogicalExpressionBuilder<Expressions$$$builder<P1>> result = new LogicalExpressionBuilder<Expressions$$$builder<P1>>(this);
			CollectionUtils.addItem(LogicalExpressionBuilder.this.expressions, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public Expressions$$$builder<? extends LogicalExpressionBuilder<P>> expressions$list() {
		verifyMutable();
		if (this.expressions == null) {
			this.expressions = new ArrayList<ExpressionBuilder<?>>();
		}
		return new Expressions$$$builder<LogicalExpressionBuilder<P>>(this);
	}

    public LogicalExpressionBuilder<P> expressions$wrap(Expression ... expressions) {
    	return expressions$wrap(new ListBuilder<Expression>().add(expressions).toList());
    }

    public LogicalExpressionBuilder<P> expressions$wrap(Collection<? extends Expression> expressions) {
		verifyMutable();

		if (this.expressions == null) {
			this.expressions = new ArrayList<ExpressionBuilder<?>>();
		}
		if (expressions != null) {
			for (Expression e : expressions) {
				ExpressionBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(ExpressionBuilder.class);
				CollectionUtils.addItem(this.expressions, wrapped);
			}
		}
		return this;
    }
    
    public LogicalExpressionBuilder<P> expressions$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return expressions$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public LogicalExpressionBuilder<P> expressions$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		verifyMutable();

		if (this.expressions == null) {
			this.expressions = new ArrayList<ExpressionBuilder<?>>();
		}
		if (builderIds != null) {
	    	for (Object builderId : builderIds) {
	            Object restoredObject = repo.get(builderId);
	            if (restoredObject == null) {
	            	if (repo.isSupportLazy()) {
	            		repo.addObjectStoredListener(builderId, new Procedure() {
	    					public void execute(Object... arguments) {
	    						CollectionUtils.addItem(LogicalExpressionBuilder.this.expressions, arguments[0]);
	    					}
	    				});
	            	}
	            	else {
	                    throw new IllegalStateException("Object does not exist with id " + builderId);
	            	}
	            }
	            else if (!(restoredObject instanceof ExpressionBuilder)) {
	            	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + ExpressionBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
	            }
	            else {
	                CollectionUtils.addItem(this.expressions, restoredObject);
	            }
	    	}
		}
        return this;
    }


	private LogicalOperator operator;
	
	public LogicalOperator getOperator() {
		return operator;
	}

	public void setOperator(LogicalOperator operator) {
		verifyMutable();
		this.operator = operator;
	}

	public LogicalExpressionBuilder<P> operator(LogicalOperator operator) {
		verifyMutable();
		this.operator = operator;
		return this;
	}

	@Override
	public LogicalExpressionBuilder<P> negated(boolean negated) {
		return (LogicalExpressionBuilder<P>)super.negated(negated);
	}

    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

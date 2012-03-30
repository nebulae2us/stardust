package org.nebulae2us.stardust.sql.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.stardust.expr.domain.*;


public class SelectQueryBuilder<B> implements Convertable {

	private final SelectQuery $$$savedTarget;

	private ConverterOption $$$option;

	private final B $$$parentBuilder;

	protected SelectQueryBuilder(SelectQuery selectQuery) {
		if (selectQuery == null) {
			throw new NullPointerException();
		}
	
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = selectQuery;
	}

	public SelectQueryBuilder(ConverterOption option, B parentBuilder) {
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = parentBuilder;
		this.$$$savedTarget = null;
	}

	public SelectQueryBuilder() {
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public SelectQueryBuilder(ConverterOption option) {
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
	
	public SelectQuery getSavedTarget() {
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

    protected void copyAttributes(SelectQueryBuilder<?> copy) {
    	this.entityClass = copy.entityClass;
		this.initialAlias = copy.initialAlias;
		this.aliasJoins = copy.aliasJoins;
		this.expressions = copy.expressions;
    }

    public B end() {
        return this.$$$parentBuilder;
    }

    public SelectQueryBuilder<B> storeTo(BuilderRepository repo, int builderId) {
    	repo.put(builderId, this);
    	return this;
    }

    public SelectQuery toSelectQuery() {
    	return new Converter(this.$$$option).convert(this).to(SelectQuery.class);
    }

    private Class entityClass;

    public Class getEntityClass() {
        return this.entityClass;
    }

    public void setEntityClass(Class entityClass) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.entityClass = entityClass;
    }

    public SelectQueryBuilder<B> entityClass(Class entityClass) {
        this.entityClass = entityClass;
        return this;
    }

    private String initialAlias;

    public String getInitialAlias() {
        return this.initialAlias;
    }

    public void setInitialAlias(String initialAlias) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.initialAlias = initialAlias;
    }

    public SelectQueryBuilder<B> initialAlias(String initialAlias) {
        this.initialAlias = initialAlias;
        return this;
    }

    private List<AliasJoinBuilder<?>> aliasJoins;

    public List<AliasJoinBuilder<?>> getAliasJoins() {
        return this.aliasJoins;
    }

    public void setAliasJoins(List<AliasJoinBuilder<?>> aliasJoins) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.aliasJoins = aliasJoins;
    }

    public AliasJoinBuilder<SelectQueryBuilder<B>> aliasJoin() {
        if (this.aliasJoins == null) {
            this.aliasJoins = new ArrayList<AliasJoinBuilder<?>>();
        }

        AliasJoinBuilder<SelectQueryBuilder<B>> aliasJoin = new AliasJoinBuilder<SelectQueryBuilder<B>>(this.$$$option, this);
        
        this.aliasJoins.add(aliasJoin);
        
        return aliasJoin;
    }

    public SelectQueryBuilder<B> aliasJoin(AliasJoinBuilder<?> aliasJoin) {
        if (this.aliasJoins == null) {
            this.aliasJoins = new ArrayList<AliasJoinBuilder<?>>();
        }
        this.aliasJoins.add(aliasJoin);
        return this;
    }

    public SelectQueryBuilder<B> aliasJoin(AliasJoin aliasJoin) {
        if (this.aliasJoins == null) {
            this.aliasJoins = new ArrayList<AliasJoinBuilder<?>>();
        }
    	AliasJoinBuilder<?> wrap = new WrapConverter(this.$$$option).convert(aliasJoin).to(AliasJoinBuilder.class);
        this.aliasJoins.add(wrap);
        return this;
    }

    public SelectQueryBuilder<B> aliasJoins(AliasJoinBuilder<?> ... aliasJoins) {
        if (this.aliasJoins == null) {
            this.aliasJoins = new ArrayList<AliasJoinBuilder<?>>();
        }
        for (AliasJoinBuilder<?> o : aliasJoins) {
            this.aliasJoins.add(o);
        }
        return this;
    }

    public SelectQueryBuilder<B> aliasJoins(AliasJoin ... aliasJoins) {
        if (this.aliasJoins == null) {
            this.aliasJoins = new ArrayList<AliasJoinBuilder<?>>();
        }
        for (AliasJoin o : aliasJoins) {
	    	AliasJoinBuilder<?> wrap = new WrapConverter(this.$$$option).convert(o).to(AliasJoinBuilder.class);
            this.aliasJoins.add(wrap);
        }
        return this;
    }

    public SelectQueryBuilder<B> aliasJoin$restoreFrom(BuilderRepository repo, int builderId) {
        Object aliasJoin = repo.get(builderId);
        if (this.aliasJoins == null) {
            this.aliasJoins = new ArrayList<AliasJoinBuilder<?>>();
        }

        if (aliasJoin == null) {
        	if (repo.isSupportLazy()) {
                
        		final int size = this.aliasJoins.size();
        		this.aliasJoins.add(null);

        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						SelectQueryBuilder.this.aliasJoins.set(size, (AliasJoinBuilder<?>)arguments[0]);
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.aliasJoins.add((AliasJoinBuilder<?>)aliasJoin);
        }
    	
    	return this;
    }

    public SelectQueryBuilder<B> aliasJoins$restoreFrom(BuilderRepository repo, int ... builderIds) {

    	for (int builderId : builderIds) {
    		aliasJoin$restoreFrom(repo, builderId);
    	}
    	
    	return this;
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

    public ExpressionBuilder<SelectQueryBuilder<B>> expression() {
        if (this.expressions == null) {
            this.expressions = new ArrayList<ExpressionBuilder<?>>();
        }

        ExpressionBuilder<SelectQueryBuilder<B>> expression = new ExpressionBuilder<SelectQueryBuilder<B>>(this.$$$option, this);
        
        this.expressions.add(expression);
        
        return expression;
    }

    public SelectQueryBuilder<B> expression(ExpressionBuilder<?> expression) {
        if (this.expressions == null) {
            this.expressions = new ArrayList<ExpressionBuilder<?>>();
        }
        this.expressions.add(expression);
        return this;
    }

    public SelectQueryBuilder<B> expression(Expression expression) {
        if (this.expressions == null) {
            this.expressions = new ArrayList<ExpressionBuilder<?>>();
        }
    	ExpressionBuilder<?> wrap = new WrapConverter(this.$$$option).convert(expression).to(ExpressionBuilder.class);
        this.expressions.add(wrap);
        return this;
    }

    public SelectQueryBuilder<B> expressions(ExpressionBuilder<?> ... expressions) {
        if (this.expressions == null) {
            this.expressions = new ArrayList<ExpressionBuilder<?>>();
        }
        for (ExpressionBuilder<?> o : expressions) {
            this.expressions.add(o);
        }
        return this;
    }

    public SelectQueryBuilder<B> expressions(Expression ... expressions) {
        if (this.expressions == null) {
            this.expressions = new ArrayList<ExpressionBuilder<?>>();
        }
        for (Expression o : expressions) {
	    	ExpressionBuilder<?> wrap = new WrapConverter(this.$$$option).convert(o).to(ExpressionBuilder.class);
            this.expressions.add(wrap);
        }
        return this;
    }

    public SelectQueryBuilder<B> expression$restoreFrom(BuilderRepository repo, int builderId) {
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
						SelectQueryBuilder.this.expressions.set(size, (ExpressionBuilder<?>)arguments[0]);
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

    public SelectQueryBuilder<B> expressions$restoreFrom(BuilderRepository repo, int ... builderIds) {

    	for (int builderId : builderIds) {
    		expression$restoreFrom(repo, builderId);
    	}
    	
    	return this;
    }
}

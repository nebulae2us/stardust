package org.nebulae2us.stardust.sql.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;
import org.nebulae2us.stardust.expr.domain.*;

@Builder(destination=SelectQuery.class)
public class SelectQueryBuilder<P> implements Wrappable<SelectQuery> {

	protected final SelectQuery $$$wrapped;

	protected final P $$$parentBuilder;
	
	public SelectQueryBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public SelectQueryBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected SelectQueryBuilder(SelectQuery wrapped) {
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public SelectQueryBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public SelectQuery getWrappedObject() {
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

    public SelectQuery toSelectQuery() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(SelectQuery.class);
    }

	private Class<?> entityClass;
	
	public Class<?> getEntityClass() {
		return entityClass;
	}

	public void setEntityClass(Class<?> entityClass) {
		verifyMutable();
		this.entityClass = entityClass;
	}

	public SelectQueryBuilder<P> entityClass(Class<?> entityClass) {
		verifyMutable();
		this.entityClass = entityClass;
		return this;
	}

	private String initialAlias;
	
	public String getInitialAlias() {
		return initialAlias;
	}

	public void setInitialAlias(String initialAlias) {
		verifyMutable();
		this.initialAlias = initialAlias;
	}

	public SelectQueryBuilder<P> initialAlias(String initialAlias) {
		verifyMutable();
		this.initialAlias = initialAlias;
		return this;
	}

	private List<AliasJoinBuilder<?>> aliasJoins;
	
	public List<AliasJoinBuilder<?>> getAliasJoins() {
		return aliasJoins;
	}

	public void setAliasJoins(List<AliasJoinBuilder<?>> aliasJoins) {
		verifyMutable();
		this.aliasJoins = aliasJoins;
	}

	public SelectQueryBuilder<P> aliasJoins(AliasJoinBuilder<?> ... aliasJoins) {
		verifyMutable();
		return aliasJoins(new ListBuilder<AliasJoinBuilder<?>>().add(aliasJoins).toList());
	}
	
	public SelectQueryBuilder<P> aliasJoins(Collection<AliasJoinBuilder<?>> aliasJoins) {
		verifyMutable();
		if (this.aliasJoins == null) {
			this.aliasJoins = new ArrayList<AliasJoinBuilder<?>>();
		}
		if (aliasJoins != null) {
			for (AliasJoinBuilder<?> e : aliasJoins) {
				this.aliasJoins.add(e);
			}
		}
		return this;
	}

	public AliasJoinBuilder<SelectQueryBuilder<P>> aliasJoins$one() {
		verifyMutable();
		if (this.aliasJoins == null) {
			this.aliasJoins = new ArrayList<AliasJoinBuilder<?>>();
		}
		
		AliasJoinBuilder<SelectQueryBuilder<P>> result =
				new AliasJoinBuilder<SelectQueryBuilder<P>>(this);
		
		this.aliasJoins.add(result);
		
		return result;
	}

	public class AliasJoins$$$builder {
		
		public AliasJoinBuilder<AliasJoins$$$builder> blank$begin() {
			AliasJoinBuilder<AliasJoins$$$builder> result = new AliasJoinBuilder<AliasJoins$$$builder>(this);
			SelectQueryBuilder.this.aliasJoins.add(result);
			return result;
		}
		
		public SelectQueryBuilder<P> end() {
			return SelectQueryBuilder.this;
		}
	}
	
	public AliasJoins$$$builder aliasJoins$list() {
		verifyMutable();
		if (this.aliasJoins == null) {
			this.aliasJoins = new ArrayList<AliasJoinBuilder<?>>();
		}
		return new AliasJoins$$$builder();
	}

    public SelectQueryBuilder<P> aliasJoins$wrap(AliasJoin ... aliasJoins) {
    	return aliasJoins$wrap(new ListBuilder<AliasJoin>().add(aliasJoins).toList());
    }

    public SelectQueryBuilder<P> aliasJoins$wrap(Collection<AliasJoin> aliasJoins) {
		verifyMutable();

		if (this.aliasJoins == null) {
			this.aliasJoins = new ArrayList<AliasJoinBuilder<?>>();
		}
		if (aliasJoins != null) {
			for (AliasJoin e : aliasJoins) {
				AliasJoinBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(AliasJoinBuilder.class);
				this.aliasJoins.add(wrapped);
			}
		}
		return this;
    }
    
    public SelectQueryBuilder<P> aliasJoins$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return aliasJoins$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public SelectQueryBuilder<P> aliasJoins$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		verifyMutable();

		if (this.aliasJoins == null) {
			this.aliasJoins = new ArrayList<AliasJoinBuilder<?>>();
		}
		if (builderIds != null) {
	    	for (Object builderId : builderIds) {
	            Object restoredObject = repo.get(builderId);
	            if (restoredObject == null) {
	            	if (repo.isSupportLazy()) {
	            		repo.addObjectStoredListener(builderId, new Procedure() {
	    					public void execute(Object... arguments) {
	    						SelectQueryBuilder.this.aliasJoins.add((AliasJoinBuilder<?>)arguments[0]);
	    					}
	    				});
	            	}
	            	else {
	                    throw new IllegalStateException("Object does not exist with id " + builderId);
	            	}
	            }
	            else if (!(restoredObject instanceof AliasJoinBuilder)) {
	            	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + AliasJoinBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
	            }
	            else {
	                this.aliasJoins.add((AliasJoinBuilder<?>)restoredObject);
	            }
	    	}
		}
        return this;
    }

	private List<ExpressionBuilder<?>> expressions;
	
	public List<ExpressionBuilder<?>> getExpressions() {
		return expressions;
	}

	public void setExpressions(List<ExpressionBuilder<?>> expressions) {
		verifyMutable();
		this.expressions = expressions;
	}

	public SelectQueryBuilder<P> expressions(ExpressionBuilder<?> ... expressions) {
		verifyMutable();
		return expressions(new ListBuilder<ExpressionBuilder<?>>().add(expressions).toList());
	}
	
	public SelectQueryBuilder<P> expressions(Collection<ExpressionBuilder<?>> expressions) {
		verifyMutable();
		if (this.expressions == null) {
			this.expressions = new ArrayList<ExpressionBuilder<?>>();
		}
		if (expressions != null) {
			for (ExpressionBuilder<?> e : expressions) {
				this.expressions.add(e);
			}
		}
		return this;
	}

	public ExpressionBuilder<SelectQueryBuilder<P>> expressions$one() {
		verifyMutable();
		if (this.expressions == null) {
			this.expressions = new ArrayList<ExpressionBuilder<?>>();
		}
		
		ExpressionBuilder<SelectQueryBuilder<P>> result =
				new ExpressionBuilder<SelectQueryBuilder<P>>(this);
		
		this.expressions.add(result);
		
		return result;
	}

	public class Expressions$$$builder {
		
		public ExpressionBuilder<Expressions$$$builder> blank$begin() {
			ExpressionBuilder<Expressions$$$builder> result = new ExpressionBuilder<Expressions$$$builder>(this);
			SelectQueryBuilder.this.expressions.add(result);
			return result;
		}
		
		public SelectQueryBuilder<P> end() {
			return SelectQueryBuilder.this;
		}
	}
	
	public Expressions$$$builder expressions$list() {
		verifyMutable();
		if (this.expressions == null) {
			this.expressions = new ArrayList<ExpressionBuilder<?>>();
		}
		return new Expressions$$$builder();
	}

    public SelectQueryBuilder<P> expressions$wrap(Expression ... expressions) {
    	return expressions$wrap(new ListBuilder<Expression>().add(expressions).toList());
    }

    public SelectQueryBuilder<P> expressions$wrap(Collection<Expression> expressions) {
		verifyMutable();

		if (this.expressions == null) {
			this.expressions = new ArrayList<ExpressionBuilder<?>>();
		}
		if (expressions != null) {
			for (Expression e : expressions) {
				ExpressionBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(ExpressionBuilder.class);
				this.expressions.add(wrapped);
			}
		}
		return this;
    }
    
    public SelectQueryBuilder<P> expressions$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return expressions$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public SelectQueryBuilder<P> expressions$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
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
	    						SelectQueryBuilder.this.expressions.add((ExpressionBuilder<?>)arguments[0]);
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
	                this.expressions.add((ExpressionBuilder<?>)restoredObject);
	            }
	    	}
		}
        return this;
    }
}

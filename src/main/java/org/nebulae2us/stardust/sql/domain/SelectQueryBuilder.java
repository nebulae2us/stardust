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
		if (wrapped == null) {
			throw new NullPointerException();
		}
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
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(SelectQuery.class);
    }



	private Class<?> entityClass;
	
	public Class<?> getEntityClass() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.entityClass, Class.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, SelectQuery.class, "entityClass");
			this.entityClass = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(Class.class);
		}

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
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.initialAlias, String.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, SelectQuery.class, "initialAlias");
			this.initialAlias = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(String.class);
		}

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
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.aliasJoins, List.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, SelectQuery.class, "aliasJoins");
			this.aliasJoins = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(List.class);
		}

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
				CollectionUtils.addItem(this.aliasJoins, e);
			}
		}
		return this;
	}

	public AliasJoinBuilder<? extends SelectQueryBuilder<P>> aliasJoins$addAliasJoin() {
		verifyMutable();
		if (this.aliasJoins == null) {
			this.aliasJoins = new ArrayList<AliasJoinBuilder<?>>();
		}
		
		AliasJoinBuilder<SelectQueryBuilder<P>> result =
				new AliasJoinBuilder<SelectQueryBuilder<P>>(this);
		
		CollectionUtils.addItem(this.aliasJoins, result);
		
		return result;
	}
	

	public class AliasJoins$$$builder<P1 extends SelectQueryBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected AliasJoins$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public AliasJoinBuilder<AliasJoins$$$builder<P1>> aliasJoin$begin() {
			AliasJoinBuilder<AliasJoins$$$builder<P1>> result = new AliasJoinBuilder<AliasJoins$$$builder<P1>>(this);
			CollectionUtils.addItem(SelectQueryBuilder.this.aliasJoins, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public AliasJoins$$$builder<? extends SelectQueryBuilder<P>> aliasJoins$list() {
		verifyMutable();
		if (this.aliasJoins == null) {
			this.aliasJoins = new ArrayList<AliasJoinBuilder<?>>();
		}
		return new AliasJoins$$$builder<SelectQueryBuilder<P>>(this);
	}

    public SelectQueryBuilder<P> aliasJoins$wrap(AliasJoin ... aliasJoins) {
    	return aliasJoins$wrap(new ListBuilder<AliasJoin>().add(aliasJoins).toList());
    }

    public SelectQueryBuilder<P> aliasJoins$wrap(Collection<? extends AliasJoin> aliasJoins) {
		verifyMutable();

		if (this.aliasJoins == null) {
			this.aliasJoins = new ArrayList<AliasJoinBuilder<?>>();
		}
		if (aliasJoins != null) {
			for (AliasJoin e : aliasJoins) {
				AliasJoinBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(AliasJoinBuilder.class);
				CollectionUtils.addItem(this.aliasJoins, wrapped);
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
	    						CollectionUtils.addItem(SelectQueryBuilder.this.aliasJoins, arguments[0]);
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
	                CollectionUtils.addItem(this.aliasJoins, restoredObject);
	            }
	    	}
		}
        return this;
    }


	private List<Expression> expressions;
	
	public List<Expression> getExpressions() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.expressions, List.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, SelectQuery.class, "expressions");
			this.expressions = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(List.class);
		}

		return expressions;
	}

	public void setExpressions(List<Expression> expressions) {
		verifyMutable();
		this.expressions = expressions;
	}

	public SelectQueryBuilder<P> expressions(Expression ... expressions) {
		verifyMutable();
		return expressions(new ListBuilder<Expression>().add(expressions).toList());
	}
	
	public SelectQueryBuilder<P> expressions(Collection<Expression> expressions) {
		verifyMutable();
		if (this.expressions == null) {
			this.expressions = new ArrayList<Expression>();
		}
		if (expressions != null) {
			for (Expression e : expressions) {
				CollectionUtils.addItem(this.expressions, e);
			}
		}
		return this;
	}



    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

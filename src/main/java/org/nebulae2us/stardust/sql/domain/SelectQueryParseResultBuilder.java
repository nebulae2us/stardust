package org.nebulae2us.stardust.sql.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;
import org.nebulae2us.stardust.db.domain.*;

@Builder(destination=SelectQueryParseResult.class)
public class SelectQueryParseResultBuilder<P> implements Wrappable<SelectQueryParseResult> {

	protected final SelectQueryParseResult $$$wrapped;

	protected final P $$$parentBuilder;
	
	public SelectQueryParseResultBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public SelectQueryParseResultBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected SelectQueryParseResultBuilder(SelectQueryParseResult wrapped) {
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public SelectQueryParseResultBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public SelectQueryParseResult getWrappedObject() {
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

    public SelectQueryParseResult toSelectQueryParseResult() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(SelectQueryParseResult.class);
    }

	private RelationalEntitiesBuilder<?> relationalEntities;
	
	public RelationalEntitiesBuilder<?> getRelationalEntities() {
		return relationalEntities;
	}

	public void setRelationalEntities(RelationalEntitiesBuilder<?> relationalEntities) {
		verifyMutable();
		this.relationalEntities = relationalEntities;
	}

	public SelectQueryParseResultBuilder<P> relationalEntities(RelationalEntitiesBuilder<?> relationalEntities) {
		verifyMutable();
		this.relationalEntities = relationalEntities;
		return this;
	}

	public RelationalEntitiesBuilder<? extends SelectQueryParseResultBuilder<P>> relationalEntities$begin() {
		RelationalEntitiesBuilder<SelectQueryParseResultBuilder<P>> result = new RelationalEntitiesBuilder<SelectQueryParseResultBuilder<P>>(this);
		this.relationalEntities = result;
		return result;
	}

    public SelectQueryParseResultBuilder<P> relationalEntities$wrap(RelationalEntities relationalEntities) {
    	verifyMutable();
    	this.relationalEntities = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(relationalEntities).to(RelationalEntitiesBuilder.class);
        return this;
    }
    
    public SelectQueryParseResultBuilder<P> relationalEntities$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						SelectQueryParseResultBuilder.this.relationalEntities = (RelationalEntitiesBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else if (!(restoredObject instanceof RelationalEntitiesBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + RelationalEntitiesBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.relationalEntities = (RelationalEntitiesBuilder<?>)restoredObject;
        }
        return this;
    }

	private JoinedTablesBuilder<?> joinedTables;
	
	public JoinedTablesBuilder<?> getJoinedTables() {
		return joinedTables;
	}

	public void setJoinedTables(JoinedTablesBuilder<?> joinedTables) {
		verifyMutable();
		this.joinedTables = joinedTables;
	}

	public SelectQueryParseResultBuilder<P> joinedTables(JoinedTablesBuilder<?> joinedTables) {
		verifyMutable();
		this.joinedTables = joinedTables;
		return this;
	}

	public JoinedTablesBuilder<? extends SelectQueryParseResultBuilder<P>> joinedTables$begin() {
		JoinedTablesBuilder<SelectQueryParseResultBuilder<P>> result = new JoinedTablesBuilder<SelectQueryParseResultBuilder<P>>(this);
		this.joinedTables = result;
		return result;
	}

    public SelectQueryParseResultBuilder<P> joinedTables$wrap(JoinedTables joinedTables) {
    	verifyMutable();
    	this.joinedTables = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(joinedTables).to(JoinedTablesBuilder.class);
        return this;
    }
    
    public SelectQueryParseResultBuilder<P> joinedTables$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						SelectQueryParseResultBuilder.this.joinedTables = (JoinedTablesBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else if (!(restoredObject instanceof JoinedTablesBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + JoinedTablesBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.joinedTables = (JoinedTablesBuilder<?>)restoredObject;
        }
        return this;
    }

	private List<ColumnBuilder<?>> columns;
	
	public List<ColumnBuilder<?>> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnBuilder<?>> columns) {
		verifyMutable();
		this.columns = columns;
	}

	public SelectQueryParseResultBuilder<P> columns(ColumnBuilder<?> ... columns) {
		verifyMutable();
		return columns(new ListBuilder<ColumnBuilder<?>>().add(columns).toList());
	}
	
	public SelectQueryParseResultBuilder<P> columns(Collection<ColumnBuilder<?>> columns) {
		verifyMutable();
		if (this.columns == null) {
			this.columns = new ArrayList<ColumnBuilder<?>>();
		}
		if (columns != null) {
			for (ColumnBuilder<?> e : columns) {
				this.columns.add(e);
			}
		}
		return this;
	}

	public ColumnBuilder<SelectQueryParseResultBuilder<P>> columns$one() {
		verifyMutable();
		if (this.columns == null) {
			this.columns = new ArrayList<ColumnBuilder<?>>();
		}
		
		ColumnBuilder<SelectQueryParseResultBuilder<P>> result =
				new ColumnBuilder<SelectQueryParseResultBuilder<P>>(this);
		
		this.columns.add(result);
		
		return result;
	}

	public class Columns$$$builder {
		
		public ColumnBuilder<Columns$$$builder> blank$begin() {
			ColumnBuilder<Columns$$$builder> result = new ColumnBuilder<Columns$$$builder>(this);
			SelectQueryParseResultBuilder.this.columns.add(result);
			return result;
		}
		
		public SelectQueryParseResultBuilder<P> end() {
			return SelectQueryParseResultBuilder.this;
		}
	}
	
	public Columns$$$builder columns$list() {
		verifyMutable();
		if (this.columns == null) {
			this.columns = new ArrayList<ColumnBuilder<?>>();
		}
		return new Columns$$$builder();
	}

    public SelectQueryParseResultBuilder<P> columns$wrap(Column ... columns) {
    	return columns$wrap(new ListBuilder<Column>().add(columns).toList());
    }

    public SelectQueryParseResultBuilder<P> columns$wrap(Collection<Column> columns) {
		verifyMutable();

		if (this.columns == null) {
			this.columns = new ArrayList<ColumnBuilder<?>>();
		}
		if (columns != null) {
			for (Column e : columns) {
				ColumnBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(ColumnBuilder.class);
				this.columns.add(wrapped);
			}
		}
		return this;
    }
    
    public SelectQueryParseResultBuilder<P> columns$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return columns$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public SelectQueryParseResultBuilder<P> columns$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		verifyMutable();

		if (this.columns == null) {
			this.columns = new ArrayList<ColumnBuilder<?>>();
		}
		if (builderIds != null) {
	    	for (Object builderId : builderIds) {
	            Object restoredObject = repo.get(builderId);
	            if (restoredObject == null) {
	            	if (repo.isSupportLazy()) {
	            		repo.addObjectStoredListener(builderId, new Procedure() {
	    					public void execute(Object... arguments) {
	    						SelectQueryParseResultBuilder.this.columns.add((ColumnBuilder<?>)arguments[0]);
	    					}
	    				});
	            	}
	            	else {
	                    throw new IllegalStateException("Object does not exist with id " + builderId);
	            	}
	            }
	            else if (!(restoredObject instanceof ColumnBuilder)) {
	            	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + ColumnBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
	            }
	            else {
	                this.columns.add((ColumnBuilder<?>)restoredObject);
	            }
	    	}
		}
        return this;
    }
}

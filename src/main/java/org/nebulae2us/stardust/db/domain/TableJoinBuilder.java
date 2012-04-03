package org.nebulae2us.stardust.db.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;

@Builder(destination=TableJoin.class)
public class TableJoinBuilder<P> implements Wrappable<TableJoin> {

	protected final TableJoin $$$wrapped;

	protected final P $$$parentBuilder;
	
	public TableJoinBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public TableJoinBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected TableJoinBuilder(TableJoin wrapped) {
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public TableJoinBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public TableJoin getWrappedObject() {
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

    public TableJoin toTableJoin() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(TableJoin.class);
    }

	private TableBuilder<?> leftTable;
	
	public TableBuilder<?> getLeftTable() {
		return leftTable;
	}

	public void setLeftTable(TableBuilder<?> leftTable) {
		verifyMutable();
		this.leftTable = leftTable;
	}

	public TableJoinBuilder<P> leftTable(TableBuilder<?> leftTable) {
		verifyMutable();
		this.leftTable = leftTable;
		return this;
	}

	public TableBuilder<? extends TableJoinBuilder<P>> leftTable$begin() {
		TableBuilder<TableJoinBuilder<P>> result = new TableBuilder<TableJoinBuilder<P>>(this);
		this.leftTable = result;
		return result;
	}

    public TableJoinBuilder<P> leftTable$wrap(Table leftTable) {
    	verifyMutable();
    	this.leftTable = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(leftTable).to(TableBuilder.class);
        return this;
    }
    
    public TableJoinBuilder<P> leftTable$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						TableJoinBuilder.this.leftTable = (TableBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else if (!(restoredObject instanceof TableBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + TableBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.leftTable = (TableBuilder<?>)restoredObject;
        }
        return this;
    }

	private List<ColumnBuilder<?>> leftColumns;
	
	public List<ColumnBuilder<?>> getLeftColumns() {
		return leftColumns;
	}

	public void setLeftColumns(List<ColumnBuilder<?>> leftColumns) {
		verifyMutable();
		this.leftColumns = leftColumns;
	}

	public TableJoinBuilder<P> leftColumns(ColumnBuilder<?> ... leftColumns) {
		verifyMutable();
		return leftColumns(new ListBuilder<ColumnBuilder<?>>().add(leftColumns).toList());
	}
	
	public TableJoinBuilder<P> leftColumns(Collection<ColumnBuilder<?>> leftColumns) {
		verifyMutable();
		if (this.leftColumns == null) {
			this.leftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		if (leftColumns != null) {
			for (ColumnBuilder<?> e : leftColumns) {
				this.leftColumns.add(e);
			}
		}
		return this;
	}

	public ColumnBuilder<TableJoinBuilder<P>> leftColumns$one() {
		verifyMutable();
		if (this.leftColumns == null) {
			this.leftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		
		ColumnBuilder<TableJoinBuilder<P>> result =
				new ColumnBuilder<TableJoinBuilder<P>>(this);
		
		this.leftColumns.add(result);
		
		return result;
	}

	public class LeftColumns$$$builder {
		
		public ColumnBuilder<LeftColumns$$$builder> blank$begin() {
			ColumnBuilder<LeftColumns$$$builder> result = new ColumnBuilder<LeftColumns$$$builder>(this);
			TableJoinBuilder.this.leftColumns.add(result);
			return result;
		}
		
		public TableJoinBuilder<P> end() {
			return TableJoinBuilder.this;
		}
	}
	
	public LeftColumns$$$builder leftColumns$list() {
		verifyMutable();
		if (this.leftColumns == null) {
			this.leftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		return new LeftColumns$$$builder();
	}

    public TableJoinBuilder<P> leftColumns$wrap(Column ... leftColumns) {
    	return leftColumns$wrap(new ListBuilder<Column>().add(leftColumns).toList());
    }

    public TableJoinBuilder<P> leftColumns$wrap(Collection<Column> leftColumns) {
		verifyMutable();

		if (this.leftColumns == null) {
			this.leftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		if (leftColumns != null) {
			for (Column e : leftColumns) {
				ColumnBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(ColumnBuilder.class);
				this.leftColumns.add(wrapped);
			}
		}
		return this;
    }
    
    public TableJoinBuilder<P> leftColumns$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return leftColumns$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public TableJoinBuilder<P> leftColumns$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		verifyMutable();

		if (this.leftColumns == null) {
			this.leftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		if (builderIds != null) {
	    	for (Object builderId : builderIds) {
	            Object restoredObject = repo.get(builderId);
	            if (restoredObject == null) {
	            	if (repo.isSupportLazy()) {
	            		repo.addObjectStoredListener(builderId, new Procedure() {
	    					public void execute(Object... arguments) {
	    						TableJoinBuilder.this.leftColumns.add((ColumnBuilder<?>)arguments[0]);
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
	                this.leftColumns.add((ColumnBuilder<?>)restoredObject);
	            }
	    	}
		}
        return this;
    }

	private TableBuilder<?> rightTable;
	
	public TableBuilder<?> getRightTable() {
		return rightTable;
	}

	public void setRightTable(TableBuilder<?> rightTable) {
		verifyMutable();
		this.rightTable = rightTable;
	}

	public TableJoinBuilder<P> rightTable(TableBuilder<?> rightTable) {
		verifyMutable();
		this.rightTable = rightTable;
		return this;
	}

	public TableBuilder<? extends TableJoinBuilder<P>> rightTable$begin() {
		TableBuilder<TableJoinBuilder<P>> result = new TableBuilder<TableJoinBuilder<P>>(this);
		this.rightTable = result;
		return result;
	}

    public TableJoinBuilder<P> rightTable$wrap(Table rightTable) {
    	verifyMutable();
    	this.rightTable = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(rightTable).to(TableBuilder.class);
        return this;
    }
    
    public TableJoinBuilder<P> rightTable$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						TableJoinBuilder.this.rightTable = (TableBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else if (!(restoredObject instanceof TableBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + TableBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.rightTable = (TableBuilder<?>)restoredObject;
        }
        return this;
    }

	private List<ColumnBuilder<?>> rightColumns;
	
	public List<ColumnBuilder<?>> getRightColumns() {
		return rightColumns;
	}

	public void setRightColumns(List<ColumnBuilder<?>> rightColumns) {
		verifyMutable();
		this.rightColumns = rightColumns;
	}

	public TableJoinBuilder<P> rightColumns(ColumnBuilder<?> ... rightColumns) {
		verifyMutable();
		return rightColumns(new ListBuilder<ColumnBuilder<?>>().add(rightColumns).toList());
	}
	
	public TableJoinBuilder<P> rightColumns(Collection<ColumnBuilder<?>> rightColumns) {
		verifyMutable();
		if (this.rightColumns == null) {
			this.rightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		if (rightColumns != null) {
			for (ColumnBuilder<?> e : rightColumns) {
				this.rightColumns.add(e);
			}
		}
		return this;
	}

	public ColumnBuilder<TableJoinBuilder<P>> rightColumns$one() {
		verifyMutable();
		if (this.rightColumns == null) {
			this.rightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		
		ColumnBuilder<TableJoinBuilder<P>> result =
				new ColumnBuilder<TableJoinBuilder<P>>(this);
		
		this.rightColumns.add(result);
		
		return result;
	}

	public class RightColumns$$$builder {
		
		public ColumnBuilder<RightColumns$$$builder> blank$begin() {
			ColumnBuilder<RightColumns$$$builder> result = new ColumnBuilder<RightColumns$$$builder>(this);
			TableJoinBuilder.this.rightColumns.add(result);
			return result;
		}
		
		public TableJoinBuilder<P> end() {
			return TableJoinBuilder.this;
		}
	}
	
	public RightColumns$$$builder rightColumns$list() {
		verifyMutable();
		if (this.rightColumns == null) {
			this.rightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		return new RightColumns$$$builder();
	}

    public TableJoinBuilder<P> rightColumns$wrap(Column ... rightColumns) {
    	return rightColumns$wrap(new ListBuilder<Column>().add(rightColumns).toList());
    }

    public TableJoinBuilder<P> rightColumns$wrap(Collection<Column> rightColumns) {
		verifyMutable();

		if (this.rightColumns == null) {
			this.rightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		if (rightColumns != null) {
			for (Column e : rightColumns) {
				ColumnBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(ColumnBuilder.class);
				this.rightColumns.add(wrapped);
			}
		}
		return this;
    }
    
    public TableJoinBuilder<P> rightColumns$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return rightColumns$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public TableJoinBuilder<P> rightColumns$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		verifyMutable();

		if (this.rightColumns == null) {
			this.rightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		if (builderIds != null) {
	    	for (Object builderId : builderIds) {
	            Object restoredObject = repo.get(builderId);
	            if (restoredObject == null) {
	            	if (repo.isSupportLazy()) {
	            		repo.addObjectStoredListener(builderId, new Procedure() {
	    					public void execute(Object... arguments) {
	    						TableJoinBuilder.this.rightColumns.add((ColumnBuilder<?>)arguments[0]);
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
	                this.rightColumns.add((ColumnBuilder<?>)restoredObject);
	            }
	    	}
		}
        return this;
    }

	private JoinType joinType;
	
	public JoinType getJoinType() {
		return joinType;
	}

	public void setJoinType(JoinType joinType) {
		verifyMutable();
		this.joinType = joinType;
	}

	public TableJoinBuilder<P> joinType(JoinType joinType) {
		verifyMutable();
		this.joinType = joinType;
		return this;
	}
}

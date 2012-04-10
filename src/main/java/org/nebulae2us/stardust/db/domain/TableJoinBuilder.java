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

	public TableBuilder<? extends TableJoinBuilder<P>> leftTable$begin() {
		verifyMutable();
		TableBuilder<TableJoinBuilder<P>> result = new TableBuilder<TableJoinBuilder<P>>(this);
		this.leftTable = result;
		return result;
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
				CollectionUtils.addItem(this.leftColumns, e);
			}
		}
		return this;
	}

	public ColumnBuilder<? extends TableJoinBuilder<P>> leftColumns$addColumn() {
		verifyMutable();
		if (this.leftColumns == null) {
			this.leftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		
		ColumnBuilder<TableJoinBuilder<P>> result =
				new ColumnBuilder<TableJoinBuilder<P>>(this);
		
		CollectionUtils.addItem(this.leftColumns, result);
		
		return result;
	}
	

	public class LeftColumns$$$builder<P1 extends TableJoinBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected LeftColumns$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public ColumnBuilder<LeftColumns$$$builder<P1>> column$begin() {
			ColumnBuilder<LeftColumns$$$builder<P1>> result = new ColumnBuilder<LeftColumns$$$builder<P1>>(this);
			CollectionUtils.addItem(TableJoinBuilder.this.leftColumns, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public LeftColumns$$$builder<? extends TableJoinBuilder<P>> leftColumns$list() {
		verifyMutable();
		if (this.leftColumns == null) {
			this.leftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		return new LeftColumns$$$builder<TableJoinBuilder<P>>(this);
	}

    public TableJoinBuilder<P> leftColumns$wrap(Column ... leftColumns) {
    	return leftColumns$wrap(new ListBuilder<Column>().add(leftColumns).toList());
    }

    public TableJoinBuilder<P> leftColumns$wrap(Collection<? extends Column> leftColumns) {
		verifyMutable();

		if (this.leftColumns == null) {
			this.leftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		if (leftColumns != null) {
			for (Column e : leftColumns) {
				ColumnBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(ColumnBuilder.class);
				CollectionUtils.addItem(this.leftColumns, wrapped);
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
	    						CollectionUtils.addItem(TableJoinBuilder.this.leftColumns, arguments[0]);
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
	                CollectionUtils.addItem(this.leftColumns, restoredObject);
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

	public TableBuilder<? extends TableJoinBuilder<P>> rightTable$begin() {
		verifyMutable();
		TableBuilder<TableJoinBuilder<P>> result = new TableBuilder<TableJoinBuilder<P>>(this);
		this.rightTable = result;
		return result;
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
				CollectionUtils.addItem(this.rightColumns, e);
			}
		}
		return this;
	}

	public ColumnBuilder<? extends TableJoinBuilder<P>> rightColumns$addColumn() {
		verifyMutable();
		if (this.rightColumns == null) {
			this.rightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		
		ColumnBuilder<TableJoinBuilder<P>> result =
				new ColumnBuilder<TableJoinBuilder<P>>(this);
		
		CollectionUtils.addItem(this.rightColumns, result);
		
		return result;
	}
	

	public class RightColumns$$$builder<P1 extends TableJoinBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected RightColumns$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public ColumnBuilder<RightColumns$$$builder<P1>> column$begin() {
			ColumnBuilder<RightColumns$$$builder<P1>> result = new ColumnBuilder<RightColumns$$$builder<P1>>(this);
			CollectionUtils.addItem(TableJoinBuilder.this.rightColumns, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public RightColumns$$$builder<? extends TableJoinBuilder<P>> rightColumns$list() {
		verifyMutable();
		if (this.rightColumns == null) {
			this.rightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		return new RightColumns$$$builder<TableJoinBuilder<P>>(this);
	}

    public TableJoinBuilder<P> rightColumns$wrap(Column ... rightColumns) {
    	return rightColumns$wrap(new ListBuilder<Column>().add(rightColumns).toList());
    }

    public TableJoinBuilder<P> rightColumns$wrap(Collection<? extends Column> rightColumns) {
		verifyMutable();

		if (this.rightColumns == null) {
			this.rightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		if (rightColumns != null) {
			for (Column e : rightColumns) {
				ColumnBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(ColumnBuilder.class);
				CollectionUtils.addItem(this.rightColumns, wrapped);
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
	    						CollectionUtils.addItem(TableJoinBuilder.this.rightColumns, arguments[0]);
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
	                CollectionUtils.addItem(this.rightColumns, restoredObject);
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

package org.nebulae2us.stardust.db.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;
import org.nebulae2us.stardust.internal.util.Builders;

@Builder(destination=LinkedTable.class)
public class LinkedTableBuilder<P> implements Wrappable<LinkedTable> {

	protected final LinkedTable $$$wrapped;

	protected final P $$$parentBuilder;
	
	public LinkedTableBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public LinkedTableBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected LinkedTableBuilder(LinkedTable wrapped) {
		if (wrapped == null) {
			throw new NullPointerException();
		}
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public LinkedTableBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public LinkedTable getWrappedObject() {
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

    public LinkedTable toLinkedTable() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(LinkedTable.class);
    }



	private LinkedTableBuilder<?> parent;
	
	public LinkedTableBuilder<?> getParent() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.parent, LinkedTableBuilder.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, LinkedTable.class, "parent");
			this.parent = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(LinkedTableBuilder.class);
		}

		return parent;
	}

	public void setParent(LinkedTableBuilder<?> parent) {
		verifyMutable();
		this.parent = parent;
	}

	public LinkedTableBuilder<P> parent(LinkedTableBuilder<?> parent) {
		verifyMutable();
		this.parent = parent;
		return this;
	}

    public LinkedTableBuilder<P> parent$wrap(LinkedTable parent) {
    	verifyMutable();
    	this.parent = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(parent).to(LinkedTableBuilder.class);
        return this;
    }
    
    public LinkedTableBuilder<P> parent$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						LinkedTableBuilder.this.parent = (LinkedTableBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else if (!(restoredObject instanceof LinkedTableBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + LinkedTableBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.parent = (LinkedTableBuilder<?>)restoredObject;
        }
        return this;
    }

	public LinkedTableBuilder<? extends LinkedTableBuilder<P>> parent$begin() {
		verifyMutable();
		LinkedTableBuilder<LinkedTableBuilder<P>> result = new LinkedTableBuilder<LinkedTableBuilder<P>>(this);
		this.parent = result;
		return result;
	}

	private List<ColumnBuilder<?>> parentColumns;
	
	public List<ColumnBuilder<?>> getParentColumns() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.parentColumns, List.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, LinkedTable.class, "parentColumns");
			this.parentColumns = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(List.class);
		}

		return parentColumns;
	}

	public void setParentColumns(List<ColumnBuilder<?>> parentColumns) {
		verifyMutable();
		this.parentColumns = parentColumns;
	}

	public LinkedTableBuilder<P> parentColumns(ColumnBuilder<?> ... parentColumns) {
		verifyMutable();
		return parentColumns(new ListBuilder<ColumnBuilder<?>>().add(parentColumns).toList());
	}
	
	public LinkedTableBuilder<P> parentColumns(Collection<ColumnBuilder<?>> parentColumns) {
		verifyMutable();
		if (this.parentColumns == null) {
			this.parentColumns = new ArrayList<ColumnBuilder<?>>();
		}
		if (parentColumns != null) {
			for (ColumnBuilder<?> e : parentColumns) {
				CollectionUtils.addItem(this.parentColumns, e);
			}
		}
		return this;
	}

	public ColumnBuilder<? extends LinkedTableBuilder<P>> parentColumns$addColumn() {
		verifyMutable();
		if (this.parentColumns == null) {
			this.parentColumns = new ArrayList<ColumnBuilder<?>>();
		}
		
		ColumnBuilder<LinkedTableBuilder<P>> result =
				new ColumnBuilder<LinkedTableBuilder<P>>(this);
		
		CollectionUtils.addItem(this.parentColumns, result);
		
		return result;
	}
	

	public class ParentColumns$$$builder<P1 extends LinkedTableBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected ParentColumns$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public ColumnBuilder<ParentColumns$$$builder<P1>> column$begin() {
			ColumnBuilder<ParentColumns$$$builder<P1>> result = new ColumnBuilder<ParentColumns$$$builder<P1>>(this);
			CollectionUtils.addItem(LinkedTableBuilder.this.parentColumns, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public ParentColumns$$$builder<? extends LinkedTableBuilder<P>> parentColumns$list() {
		verifyMutable();
		if (this.parentColumns == null) {
			this.parentColumns = new ArrayList<ColumnBuilder<?>>();
		}
		return new ParentColumns$$$builder<LinkedTableBuilder<P>>(this);
	}

    public LinkedTableBuilder<P> parentColumns$wrap(Column ... parentColumns) {
    	return parentColumns$wrap(new ListBuilder<Column>().add(parentColumns).toList());
    }

    public LinkedTableBuilder<P> parentColumns$wrap(Collection<? extends Column> parentColumns) {
		verifyMutable();

		if (this.parentColumns == null) {
			this.parentColumns = new ArrayList<ColumnBuilder<?>>();
		}
		if (parentColumns != null) {
			for (Column e : parentColumns) {
				ColumnBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(ColumnBuilder.class);
				CollectionUtils.addItem(this.parentColumns, wrapped);
			}
		}
		return this;
    }
    
    public LinkedTableBuilder<P> parentColumns$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return parentColumns$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public LinkedTableBuilder<P> parentColumns$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		verifyMutable();

		if (this.parentColumns == null) {
			this.parentColumns = new ArrayList<ColumnBuilder<?>>();
		}
		if (builderIds != null) {
	    	for (Object builderId : builderIds) {
	            Object restoredObject = repo.get(builderId);
	            if (restoredObject == null) {
	            	if (repo.isSupportLazy()) {
	            		repo.addObjectStoredListener(builderId, new Procedure() {
	    					public void execute(Object... arguments) {
	    						CollectionUtils.addItem(LinkedTableBuilder.this.parentColumns, arguments[0]);
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
	                CollectionUtils.addItem(this.parentColumns, restoredObject);
	            }
	    	}
		}
        return this;
    }


	private TableBuilder<?> table;
	
	public TableBuilder<?> getTable() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.table, TableBuilder.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, LinkedTable.class, "table");
			this.table = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(TableBuilder.class);
		}

		return table;
	}

	public void setTable(TableBuilder<?> table) {
		verifyMutable();
		this.table = table;
	}

	public LinkedTableBuilder<P> table(TableBuilder<?> table) {
		verifyMutable();
		this.table = table;
		return this;
	}

    public LinkedTableBuilder<P> table$wrap(Table table) {
    	verifyMutable();
    	this.table = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(table).to(TableBuilder.class);
        return this;
    }
    
    public LinkedTableBuilder<P> table$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						LinkedTableBuilder.this.table = (TableBuilder<?>)arguments[0];
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
            this.table = (TableBuilder<?>)restoredObject;
        }
        return this;
    }

	public TableBuilder<? extends LinkedTableBuilder<P>> table$begin() {
		verifyMutable();
		TableBuilder<LinkedTableBuilder<P>> result = new TableBuilder<LinkedTableBuilder<P>>(this);
		this.table = result;
		return result;
	}

	private List<ColumnBuilder<?>> columns;
	
	public List<ColumnBuilder<?>> getColumns() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.columns, List.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, LinkedTable.class, "columns");
			this.columns = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(List.class);
		}

		return columns;
	}

	public void setColumns(List<ColumnBuilder<?>> columns) {
		verifyMutable();
		this.columns = columns;
	}

	public LinkedTableBuilder<P> columns(ColumnBuilder<?> ... columns) {
		verifyMutable();
		return columns(new ListBuilder<ColumnBuilder<?>>().add(columns).toList());
	}
	
	public LinkedTableBuilder<P> columns(Collection<ColumnBuilder<?>> columns) {
		verifyMutable();
		if (this.columns == null) {
			this.columns = new ArrayList<ColumnBuilder<?>>();
		}
		if (columns != null) {
			for (ColumnBuilder<?> e : columns) {
				CollectionUtils.addItem(this.columns, e);
			}
		}
		return this;
	}

	public ColumnBuilder<? extends LinkedTableBuilder<P>> columns$addColumn() {
		verifyMutable();
		if (this.columns == null) {
			this.columns = new ArrayList<ColumnBuilder<?>>();
		}
		
		ColumnBuilder<LinkedTableBuilder<P>> result =
				new ColumnBuilder<LinkedTableBuilder<P>>(this);
		
		CollectionUtils.addItem(this.columns, result);
		
		return result;
	}
	

	public class Columns$$$builder<P1 extends LinkedTableBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected Columns$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public ColumnBuilder<Columns$$$builder<P1>> column$begin() {
			ColumnBuilder<Columns$$$builder<P1>> result = new ColumnBuilder<Columns$$$builder<P1>>(this);
			CollectionUtils.addItem(LinkedTableBuilder.this.columns, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public Columns$$$builder<? extends LinkedTableBuilder<P>> columns$list() {
		verifyMutable();
		if (this.columns == null) {
			this.columns = new ArrayList<ColumnBuilder<?>>();
		}
		return new Columns$$$builder<LinkedTableBuilder<P>>(this);
	}

    public LinkedTableBuilder<P> columns$wrap(Column ... columns) {
    	return columns$wrap(new ListBuilder<Column>().add(columns).toList());
    }

    public LinkedTableBuilder<P> columns$wrap(Collection<? extends Column> columns) {
		verifyMutable();

		if (this.columns == null) {
			this.columns = new ArrayList<ColumnBuilder<?>>();
		}
		if (columns != null) {
			for (Column e : columns) {
				ColumnBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(ColumnBuilder.class);
				CollectionUtils.addItem(this.columns, wrapped);
			}
		}
		return this;
    }
    
    public LinkedTableBuilder<P> columns$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return columns$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public LinkedTableBuilder<P> columns$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
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
	    						CollectionUtils.addItem(LinkedTableBuilder.this.columns, arguments[0]);
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
	                CollectionUtils.addItem(this.columns, restoredObject);
	            }
	    	}
		}
        return this;
    }


	private JoinType joinType;
	
	public JoinType getJoinType() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.joinType, JoinType.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, LinkedTable.class, "joinType");
			this.joinType = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(JoinType.class);
		}

		return joinType;
	}

	public void setJoinType(JoinType joinType) {
		verifyMutable();
		this.joinType = joinType;
	}

	public LinkedTableBuilder<P> joinType(JoinType joinType) {
		verifyMutable();
		this.joinType = joinType;
		return this;
	}

    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

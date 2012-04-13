package org.nebulae2us.stardust.sql.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;
import org.nebulae2us.stardust.db.domain.*;
import org.nebulae2us.stardust.my.domain.*;

@Builder(destination=LinkedTableEntity.class)
public class LinkedTableEntityBuilder<P> implements Wrappable<LinkedTableEntity> {

	protected final LinkedTableEntity $$$wrapped;

	protected final P $$$parentBuilder;
	
	public LinkedTableEntityBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public LinkedTableEntityBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected LinkedTableEntityBuilder(LinkedTableEntity wrapped) {
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public LinkedTableEntityBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public LinkedTableEntity getWrappedObject() {
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

    public LinkedTableEntity toLinkedTableEntity() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(LinkedTableEntity.class);
    }



	private LinkedTableEntityBuilder<?> parent;
	
	public LinkedTableEntityBuilder<?> getParent() {
		return parent;
	}

	public void setParent(LinkedTableEntityBuilder<?> parent) {
		verifyMutable();
		this.parent = parent;
	}

	public LinkedTableEntityBuilder<P> parent(LinkedTableEntityBuilder<?> parent) {
		verifyMutable();
		this.parent = parent;
		return this;
	}

    public LinkedTableEntityBuilder<P> parent$wrap(LinkedTableEntity parent) {
    	verifyMutable();
    	this.parent = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(parent).to(LinkedTableEntityBuilder.class);
        return this;
    }
    
    public LinkedTableEntityBuilder<P> parent$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						LinkedTableEntityBuilder.this.parent = (LinkedTableEntityBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else if (!(restoredObject instanceof LinkedTableEntityBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + LinkedTableEntityBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.parent = (LinkedTableEntityBuilder<?>)restoredObject;
        }
        return this;
    }

	public LinkedTableEntityBuilder<? extends LinkedTableEntityBuilder<P>> parent$begin() {
		verifyMutable();
		LinkedTableEntityBuilder<LinkedTableEntityBuilder<P>> result = new LinkedTableEntityBuilder<LinkedTableEntityBuilder<P>>(this);
		this.parent = result;
		return result;
	}

	private TableBuilder<?> table;
	
	public TableBuilder<?> getTable() {
		return table;
	}

	public void setTable(TableBuilder<?> table) {
		verifyMutable();
		this.table = table;
	}

	public LinkedTableEntityBuilder<P> table(TableBuilder<?> table) {
		verifyMutable();
		this.table = table;
		return this;
	}

    public LinkedTableEntityBuilder<P> table$wrap(Table table) {
    	verifyMutable();
    	this.table = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(table).to(TableBuilder.class);
        return this;
    }
    
    public LinkedTableEntityBuilder<P> table$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						LinkedTableEntityBuilder.this.table = (TableBuilder<?>)arguments[0];
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

	public TableBuilder<? extends LinkedTableEntityBuilder<P>> table$begin() {
		verifyMutable();
		TableBuilder<LinkedTableEntityBuilder<P>> result = new TableBuilder<LinkedTableEntityBuilder<P>>(this);
		this.table = result;
		return result;
	}

	private EntityBuilder<?> entity;
	
	public EntityBuilder<?> getEntity() {
		return entity;
	}

	public void setEntity(EntityBuilder<?> entity) {
		verifyMutable();
		this.entity = entity;
	}

	public LinkedTableEntityBuilder<P> entity(EntityBuilder<?> entity) {
		verifyMutable();
		this.entity = entity;
		return this;
	}

    public LinkedTableEntityBuilder<P> entity$wrap(Entity entity) {
    	verifyMutable();
    	this.entity = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(entity).to(EntityBuilder.class);
        return this;
    }
    
    public LinkedTableEntityBuilder<P> entity$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						LinkedTableEntityBuilder.this.entity = (EntityBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else if (!(restoredObject instanceof EntityBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + EntityBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.entity = (EntityBuilder<?>)restoredObject;
        }
        return this;
    }

	public EntityBuilder<? extends LinkedTableEntityBuilder<P>> entity$begin() {
		verifyMutable();
		EntityBuilder<LinkedTableEntityBuilder<P>> result = new EntityBuilder<LinkedTableEntityBuilder<P>>(this);
		this.entity = result;
		return result;
	}

	private String alias;
	
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		verifyMutable();
		this.alias = alias;
	}

	public LinkedTableEntityBuilder<P> alias(String alias) {
		verifyMutable();
		this.alias = alias;
		return this;
	}

	private String tableAlias;
	
	public String getTableAlias() {
		return tableAlias;
	}

	public void setTableAlias(String tableAlias) {
		verifyMutable();
		this.tableAlias = tableAlias;
	}

	public LinkedTableEntityBuilder<P> tableAlias(String tableAlias) {
		verifyMutable();
		this.tableAlias = tableAlias;
		return this;
	}

	private List<ScalarAttributeBuilder<?>> scalarAttributes;
	
	public List<ScalarAttributeBuilder<?>> getScalarAttributes() {
		return scalarAttributes;
	}

	public void setScalarAttributes(List<ScalarAttributeBuilder<?>> scalarAttributes) {
		verifyMutable();
		this.scalarAttributes = scalarAttributes;
	}

	public LinkedTableEntityBuilder<P> scalarAttributes(ScalarAttributeBuilder<?> ... scalarAttributes) {
		verifyMutable();
		return scalarAttributes(new ListBuilder<ScalarAttributeBuilder<?>>().add(scalarAttributes).toList());
	}
	
	public LinkedTableEntityBuilder<P> scalarAttributes(Collection<ScalarAttributeBuilder<?>> scalarAttributes) {
		verifyMutable();
		if (this.scalarAttributes == null) {
			this.scalarAttributes = new ArrayList<ScalarAttributeBuilder<?>>();
		}
		if (scalarAttributes != null) {
			for (ScalarAttributeBuilder<?> e : scalarAttributes) {
				CollectionUtils.addItem(this.scalarAttributes, e);
			}
		}
		return this;
	}

	public ScalarAttributeBuilder<? extends LinkedTableEntityBuilder<P>> scalarAttributes$addScalarAttribute() {
		verifyMutable();
		if (this.scalarAttributes == null) {
			this.scalarAttributes = new ArrayList<ScalarAttributeBuilder<?>>();
		}
		
		ScalarAttributeBuilder<LinkedTableEntityBuilder<P>> result =
				new ScalarAttributeBuilder<LinkedTableEntityBuilder<P>>(this);
		
		CollectionUtils.addItem(this.scalarAttributes, result);
		
		return result;
	}
	

	public class ScalarAttributes$$$builder<P1 extends LinkedTableEntityBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected ScalarAttributes$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public ScalarAttributeBuilder<ScalarAttributes$$$builder<P1>> scalarAttribute$begin() {
			ScalarAttributeBuilder<ScalarAttributes$$$builder<P1>> result = new ScalarAttributeBuilder<ScalarAttributes$$$builder<P1>>(this);
			CollectionUtils.addItem(LinkedTableEntityBuilder.this.scalarAttributes, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public ScalarAttributes$$$builder<? extends LinkedTableEntityBuilder<P>> scalarAttributes$list() {
		verifyMutable();
		if (this.scalarAttributes == null) {
			this.scalarAttributes = new ArrayList<ScalarAttributeBuilder<?>>();
		}
		return new ScalarAttributes$$$builder<LinkedTableEntityBuilder<P>>(this);
	}

    public LinkedTableEntityBuilder<P> scalarAttributes$wrap(ScalarAttribute ... scalarAttributes) {
    	return scalarAttributes$wrap(new ListBuilder<ScalarAttribute>().add(scalarAttributes).toList());
    }

    public LinkedTableEntityBuilder<P> scalarAttributes$wrap(Collection<? extends ScalarAttribute> scalarAttributes) {
		verifyMutable();

		if (this.scalarAttributes == null) {
			this.scalarAttributes = new ArrayList<ScalarAttributeBuilder<?>>();
		}
		if (scalarAttributes != null) {
			for (ScalarAttribute e : scalarAttributes) {
				ScalarAttributeBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(ScalarAttributeBuilder.class);
				CollectionUtils.addItem(this.scalarAttributes, wrapped);
			}
		}
		return this;
    }
    
    public LinkedTableEntityBuilder<P> scalarAttributes$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return scalarAttributes$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public LinkedTableEntityBuilder<P> scalarAttributes$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		verifyMutable();

		if (this.scalarAttributes == null) {
			this.scalarAttributes = new ArrayList<ScalarAttributeBuilder<?>>();
		}
		if (builderIds != null) {
	    	for (Object builderId : builderIds) {
	            Object restoredObject = repo.get(builderId);
	            if (restoredObject == null) {
	            	if (repo.isSupportLazy()) {
	            		repo.addObjectStoredListener(builderId, new Procedure() {
	    					public void execute(Object... arguments) {
	    						CollectionUtils.addItem(LinkedTableEntityBuilder.this.scalarAttributes, arguments[0]);
	    					}
	    				});
	            	}
	            	else {
	                    throw new IllegalStateException("Object does not exist with id " + builderId);
	            	}
	            }
	            else if (!(restoredObject instanceof ScalarAttributeBuilder)) {
	            	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + ScalarAttributeBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
	            }
	            else {
	                CollectionUtils.addItem(this.scalarAttributes, restoredObject);
	            }
	    	}
		}
        return this;
    }


	private List<ColumnBuilder<?>> parentColumns;
	
	public List<ColumnBuilder<?>> getParentColumns() {
		return parentColumns;
	}

	public void setParentColumns(List<ColumnBuilder<?>> parentColumns) {
		verifyMutable();
		this.parentColumns = parentColumns;
	}

	public LinkedTableEntityBuilder<P> parentColumns(ColumnBuilder<?> ... parentColumns) {
		verifyMutable();
		return parentColumns(new ListBuilder<ColumnBuilder<?>>().add(parentColumns).toList());
	}
	
	public LinkedTableEntityBuilder<P> parentColumns(Collection<ColumnBuilder<?>> parentColumns) {
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

	public ColumnBuilder<? extends LinkedTableEntityBuilder<P>> parentColumns$addColumn() {
		verifyMutable();
		if (this.parentColumns == null) {
			this.parentColumns = new ArrayList<ColumnBuilder<?>>();
		}
		
		ColumnBuilder<LinkedTableEntityBuilder<P>> result =
				new ColumnBuilder<LinkedTableEntityBuilder<P>>(this);
		
		CollectionUtils.addItem(this.parentColumns, result);
		
		return result;
	}
	

	public class ParentColumns$$$builder<P1 extends LinkedTableEntityBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected ParentColumns$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public ColumnBuilder<ParentColumns$$$builder<P1>> column$begin() {
			ColumnBuilder<ParentColumns$$$builder<P1>> result = new ColumnBuilder<ParentColumns$$$builder<P1>>(this);
			CollectionUtils.addItem(LinkedTableEntityBuilder.this.parentColumns, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public ParentColumns$$$builder<? extends LinkedTableEntityBuilder<P>> parentColumns$list() {
		verifyMutable();
		if (this.parentColumns == null) {
			this.parentColumns = new ArrayList<ColumnBuilder<?>>();
		}
		return new ParentColumns$$$builder<LinkedTableEntityBuilder<P>>(this);
	}

    public LinkedTableEntityBuilder<P> parentColumns$wrap(Column ... parentColumns) {
    	return parentColumns$wrap(new ListBuilder<Column>().add(parentColumns).toList());
    }

    public LinkedTableEntityBuilder<P> parentColumns$wrap(Collection<? extends Column> parentColumns) {
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
    
    public LinkedTableEntityBuilder<P> parentColumns$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return parentColumns$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public LinkedTableEntityBuilder<P> parentColumns$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
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
	    						CollectionUtils.addItem(LinkedTableEntityBuilder.this.parentColumns, arguments[0]);
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


	private List<ColumnBuilder<?>> columns;
	
	public List<ColumnBuilder<?>> getColumns() {
		return columns;
	}

	public void setColumns(List<ColumnBuilder<?>> columns) {
		verifyMutable();
		this.columns = columns;
	}

	public LinkedTableEntityBuilder<P> columns(ColumnBuilder<?> ... columns) {
		verifyMutable();
		return columns(new ListBuilder<ColumnBuilder<?>>().add(columns).toList());
	}
	
	public LinkedTableEntityBuilder<P> columns(Collection<ColumnBuilder<?>> columns) {
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

	public ColumnBuilder<? extends LinkedTableEntityBuilder<P>> columns$addColumn() {
		verifyMutable();
		if (this.columns == null) {
			this.columns = new ArrayList<ColumnBuilder<?>>();
		}
		
		ColumnBuilder<LinkedTableEntityBuilder<P>> result =
				new ColumnBuilder<LinkedTableEntityBuilder<P>>(this);
		
		CollectionUtils.addItem(this.columns, result);
		
		return result;
	}
	

	public class Columns$$$builder<P1 extends LinkedTableEntityBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected Columns$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public ColumnBuilder<Columns$$$builder<P1>> column$begin() {
			ColumnBuilder<Columns$$$builder<P1>> result = new ColumnBuilder<Columns$$$builder<P1>>(this);
			CollectionUtils.addItem(LinkedTableEntityBuilder.this.columns, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public Columns$$$builder<? extends LinkedTableEntityBuilder<P>> columns$list() {
		verifyMutable();
		if (this.columns == null) {
			this.columns = new ArrayList<ColumnBuilder<?>>();
		}
		return new Columns$$$builder<LinkedTableEntityBuilder<P>>(this);
	}

    public LinkedTableEntityBuilder<P> columns$wrap(Column ... columns) {
    	return columns$wrap(new ListBuilder<Column>().add(columns).toList());
    }

    public LinkedTableEntityBuilder<P> columns$wrap(Collection<? extends Column> columns) {
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
    
    public LinkedTableEntityBuilder<P> columns$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return columns$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public LinkedTableEntityBuilder<P> columns$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
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
	    						CollectionUtils.addItem(LinkedTableEntityBuilder.this.columns, arguments[0]);
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
		return joinType;
	}

	public void setJoinType(JoinType joinType) {
		verifyMutable();
		this.joinType = joinType;
	}

	public LinkedTableEntityBuilder<P> joinType(JoinType joinType) {
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

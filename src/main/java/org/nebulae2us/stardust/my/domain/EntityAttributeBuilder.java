package org.nebulae2us.stardust.my.domain;

import java.lang.reflect.*;
import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;
import org.nebulae2us.stardust.db.domain.*;
import org.nebulae2us.stardust.internal.util.Builders;

@Builder(destination=EntityAttribute.class)
public class EntityAttributeBuilder<P> extends AttributeBuilder<P> {

	public EntityAttributeBuilder() {
		super();
	}
	
	public EntityAttributeBuilder(P parentBuilder) {
		super(parentBuilder);
	}

	protected EntityAttributeBuilder(EntityAttribute wrapped) {
		super(wrapped);
	}

	@Override
    public EntityAttributeBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	@Override
	public EntityAttribute getWrappedObject() {
		return (EntityAttribute)this.$$$wrapped;
	}

    public EntityAttribute toEntityAttribute() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(EntityAttribute.class);
    }
    

	@Override
    public EntityAttribute toAttribute() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(EntityAttribute.class);
    }
    


	private EntityBuilder<?> entity;
	
	public EntityBuilder<?> getEntity() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.entity, EntityBuilder.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, EntityAttribute.class, "entity");
			this.entity = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(EntityBuilder.class);
		}

		return entity;
	}

	public void setEntity(EntityBuilder<?> entity) {
		verifyMutable();
		this.entity = entity;
	}

	public EntityAttributeBuilder<P> entity(EntityBuilder<?> entity) {
		verifyMutable();
		this.entity = entity;
		return this;
	}

    public EntityAttributeBuilder<P> entity$wrap(Entity entity) {
    	verifyMutable();
    	this.entity = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(entity).to(EntityBuilder.class);
        return this;
    }
    
    public EntityAttributeBuilder<P> entity$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						EntityAttributeBuilder.this.entity = (EntityBuilder<?>)arguments[0];
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

	public EntityBuilder<? extends EntityAttributeBuilder<P>> entity$begin() {
		verifyMutable();
		EntityBuilder<EntityAttributeBuilder<P>> result = new EntityBuilder<EntityAttributeBuilder<P>>(this);
		this.entity = result;
		return result;
	}

	private RelationalType relationalType;
	
	public RelationalType getRelationalType() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.relationalType, RelationalType.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, EntityAttribute.class, "relationalType");
			this.relationalType = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(RelationalType.class);
		}

		return relationalType;
	}

	public void setRelationalType(RelationalType relationalType) {
		verifyMutable();
		this.relationalType = relationalType;
	}

	public EntityAttributeBuilder<P> relationalType(RelationalType relationalType) {
		verifyMutable();
		this.relationalType = relationalType;
		return this;
	}

	private JoinType joinType;
	
	public JoinType getJoinType() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.joinType, JoinType.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, EntityAttribute.class, "joinType");
			this.joinType = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(JoinType.class);
		}

		return joinType;
	}

	public void setJoinType(JoinType joinType) {
		verifyMutable();
		this.joinType = joinType;
	}

	public EntityAttributeBuilder<P> joinType(JoinType joinType) {
		verifyMutable();
		this.joinType = joinType;
		return this;
	}

	private boolean owningSide;
	
	public boolean getOwningSide() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.owningSide, boolean.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, EntityAttribute.class, "owningSide");
			this.owningSide = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(boolean.class);
		}

		return owningSide;
	}

	public void setOwningSide(boolean owningSide) {
		verifyMutable();
		this.owningSide = owningSide;
	}

	public EntityAttributeBuilder<P> owningSide(boolean owningSide) {
		verifyMutable();
		this.owningSide = owningSide;
		return this;
	}

	private List<ColumnBuilder<?>> leftColumns;
	
	public List<ColumnBuilder<?>> getLeftColumns() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.leftColumns, List.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, EntityAttribute.class, "leftColumns");
			this.leftColumns = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(List.class);
		}

		return leftColumns;
	}

	public void setLeftColumns(List<ColumnBuilder<?>> leftColumns) {
		verifyMutable();
		this.leftColumns = leftColumns;
	}

	public EntityAttributeBuilder<P> leftColumns(ColumnBuilder<?> ... leftColumns) {
		verifyMutable();
		return leftColumns(new ListBuilder<ColumnBuilder<?>>().add(leftColumns).toList());
	}
	
	public EntityAttributeBuilder<P> leftColumns(Collection<ColumnBuilder<?>> leftColumns) {
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

	public ColumnBuilder<? extends EntityAttributeBuilder<P>> leftColumns$addColumn() {
		verifyMutable();
		if (this.leftColumns == null) {
			this.leftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		
		ColumnBuilder<EntityAttributeBuilder<P>> result =
				new ColumnBuilder<EntityAttributeBuilder<P>>(this);
		
		CollectionUtils.addItem(this.leftColumns, result);
		
		return result;
	}
	

	public class LeftColumns$$$builder<P1 extends EntityAttributeBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected LeftColumns$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public ColumnBuilder<LeftColumns$$$builder<P1>> column$begin() {
			ColumnBuilder<LeftColumns$$$builder<P1>> result = new ColumnBuilder<LeftColumns$$$builder<P1>>(this);
			CollectionUtils.addItem(EntityAttributeBuilder.this.leftColumns, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public LeftColumns$$$builder<? extends EntityAttributeBuilder<P>> leftColumns$list() {
		verifyMutable();
		if (this.leftColumns == null) {
			this.leftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		return new LeftColumns$$$builder<EntityAttributeBuilder<P>>(this);
	}

    public EntityAttributeBuilder<P> leftColumns$wrap(Column ... leftColumns) {
    	return leftColumns$wrap(new ListBuilder<Column>().add(leftColumns).toList());
    }

    public EntityAttributeBuilder<P> leftColumns$wrap(Collection<? extends Column> leftColumns) {
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
    
    public EntityAttributeBuilder<P> leftColumns$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return leftColumns$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public EntityAttributeBuilder<P> leftColumns$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
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
	    						CollectionUtils.addItem(EntityAttributeBuilder.this.leftColumns, arguments[0]);
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


	private List<ColumnBuilder<?>> rightColumns;
	
	public List<ColumnBuilder<?>> getRightColumns() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.rightColumns, List.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, EntityAttribute.class, "rightColumns");
			this.rightColumns = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(List.class);
		}

		return rightColumns;
	}

	public void setRightColumns(List<ColumnBuilder<?>> rightColumns) {
		verifyMutable();
		this.rightColumns = rightColumns;
	}

	public EntityAttributeBuilder<P> rightColumns(ColumnBuilder<?> ... rightColumns) {
		verifyMutable();
		return rightColumns(new ListBuilder<ColumnBuilder<?>>().add(rightColumns).toList());
	}
	
	public EntityAttributeBuilder<P> rightColumns(Collection<ColumnBuilder<?>> rightColumns) {
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

	public ColumnBuilder<? extends EntityAttributeBuilder<P>> rightColumns$addColumn() {
		verifyMutable();
		if (this.rightColumns == null) {
			this.rightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		
		ColumnBuilder<EntityAttributeBuilder<P>> result =
				new ColumnBuilder<EntityAttributeBuilder<P>>(this);
		
		CollectionUtils.addItem(this.rightColumns, result);
		
		return result;
	}
	

	public class RightColumns$$$builder<P1 extends EntityAttributeBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected RightColumns$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public ColumnBuilder<RightColumns$$$builder<P1>> column$begin() {
			ColumnBuilder<RightColumns$$$builder<P1>> result = new ColumnBuilder<RightColumns$$$builder<P1>>(this);
			CollectionUtils.addItem(EntityAttributeBuilder.this.rightColumns, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public RightColumns$$$builder<? extends EntityAttributeBuilder<P>> rightColumns$list() {
		verifyMutable();
		if (this.rightColumns == null) {
			this.rightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		return new RightColumns$$$builder<EntityAttributeBuilder<P>>(this);
	}

    public EntityAttributeBuilder<P> rightColumns$wrap(Column ... rightColumns) {
    	return rightColumns$wrap(new ListBuilder<Column>().add(rightColumns).toList());
    }

    public EntityAttributeBuilder<P> rightColumns$wrap(Collection<? extends Column> rightColumns) {
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
    
    public EntityAttributeBuilder<P> rightColumns$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return rightColumns$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public EntityAttributeBuilder<P> rightColumns$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
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
	    						CollectionUtils.addItem(EntityAttributeBuilder.this.rightColumns, arguments[0]);
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


	private TableBuilder<?> junctionTable;
	
	public TableBuilder<?> getJunctionTable() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.junctionTable, TableBuilder.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, EntityAttribute.class, "junctionTable");
			this.junctionTable = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(TableBuilder.class);
		}

		return junctionTable;
	}

	public void setJunctionTable(TableBuilder<?> junctionTable) {
		verifyMutable();
		this.junctionTable = junctionTable;
	}

	public EntityAttributeBuilder<P> junctionTable(TableBuilder<?> junctionTable) {
		verifyMutable();
		this.junctionTable = junctionTable;
		return this;
	}

    public EntityAttributeBuilder<P> junctionTable$wrap(Table junctionTable) {
    	verifyMutable();
    	this.junctionTable = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(junctionTable).to(TableBuilder.class);
        return this;
    }
    
    public EntityAttributeBuilder<P> junctionTable$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						EntityAttributeBuilder.this.junctionTable = (TableBuilder<?>)arguments[0];
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
            this.junctionTable = (TableBuilder<?>)restoredObject;
        }
        return this;
    }

	public TableBuilder<? extends EntityAttributeBuilder<P>> junctionTable$begin() {
		verifyMutable();
		TableBuilder<EntityAttributeBuilder<P>> result = new TableBuilder<EntityAttributeBuilder<P>>(this);
		this.junctionTable = result;
		return result;
	}

	private List<ColumnBuilder<?>> junctionLeftColumns;
	
	public List<ColumnBuilder<?>> getJunctionLeftColumns() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.junctionLeftColumns, List.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, EntityAttribute.class, "junctionLeftColumns");
			this.junctionLeftColumns = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(List.class);
		}

		return junctionLeftColumns;
	}

	public void setJunctionLeftColumns(List<ColumnBuilder<?>> junctionLeftColumns) {
		verifyMutable();
		this.junctionLeftColumns = junctionLeftColumns;
	}

	public EntityAttributeBuilder<P> junctionLeftColumns(ColumnBuilder<?> ... junctionLeftColumns) {
		verifyMutable();
		return junctionLeftColumns(new ListBuilder<ColumnBuilder<?>>().add(junctionLeftColumns).toList());
	}
	
	public EntityAttributeBuilder<P> junctionLeftColumns(Collection<ColumnBuilder<?>> junctionLeftColumns) {
		verifyMutable();
		if (this.junctionLeftColumns == null) {
			this.junctionLeftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		if (junctionLeftColumns != null) {
			for (ColumnBuilder<?> e : junctionLeftColumns) {
				CollectionUtils.addItem(this.junctionLeftColumns, e);
			}
		}
		return this;
	}

	public ColumnBuilder<? extends EntityAttributeBuilder<P>> junctionLeftColumns$addColumn() {
		verifyMutable();
		if (this.junctionLeftColumns == null) {
			this.junctionLeftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		
		ColumnBuilder<EntityAttributeBuilder<P>> result =
				new ColumnBuilder<EntityAttributeBuilder<P>>(this);
		
		CollectionUtils.addItem(this.junctionLeftColumns, result);
		
		return result;
	}
	

	public class JunctionLeftColumns$$$builder<P1 extends EntityAttributeBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected JunctionLeftColumns$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public ColumnBuilder<JunctionLeftColumns$$$builder<P1>> column$begin() {
			ColumnBuilder<JunctionLeftColumns$$$builder<P1>> result = new ColumnBuilder<JunctionLeftColumns$$$builder<P1>>(this);
			CollectionUtils.addItem(EntityAttributeBuilder.this.junctionLeftColumns, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public JunctionLeftColumns$$$builder<? extends EntityAttributeBuilder<P>> junctionLeftColumns$list() {
		verifyMutable();
		if (this.junctionLeftColumns == null) {
			this.junctionLeftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		return new JunctionLeftColumns$$$builder<EntityAttributeBuilder<P>>(this);
	}

    public EntityAttributeBuilder<P> junctionLeftColumns$wrap(Column ... junctionLeftColumns) {
    	return junctionLeftColumns$wrap(new ListBuilder<Column>().add(junctionLeftColumns).toList());
    }

    public EntityAttributeBuilder<P> junctionLeftColumns$wrap(Collection<? extends Column> junctionLeftColumns) {
		verifyMutable();

		if (this.junctionLeftColumns == null) {
			this.junctionLeftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		if (junctionLeftColumns != null) {
			for (Column e : junctionLeftColumns) {
				ColumnBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(ColumnBuilder.class);
				CollectionUtils.addItem(this.junctionLeftColumns, wrapped);
			}
		}
		return this;
    }
    
    public EntityAttributeBuilder<P> junctionLeftColumns$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return junctionLeftColumns$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public EntityAttributeBuilder<P> junctionLeftColumns$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		verifyMutable();

		if (this.junctionLeftColumns == null) {
			this.junctionLeftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		if (builderIds != null) {
	    	for (Object builderId : builderIds) {
	            Object restoredObject = repo.get(builderId);
	            if (restoredObject == null) {
	            	if (repo.isSupportLazy()) {
	            		repo.addObjectStoredListener(builderId, new Procedure() {
	    					public void execute(Object... arguments) {
	    						CollectionUtils.addItem(EntityAttributeBuilder.this.junctionLeftColumns, arguments[0]);
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
	                CollectionUtils.addItem(this.junctionLeftColumns, restoredObject);
	            }
	    	}
		}
        return this;
    }


	private List<ColumnBuilder<?>> junctionRightColumns;
	
	public List<ColumnBuilder<?>> getJunctionRightColumns() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.junctionRightColumns, List.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, EntityAttribute.class, "junctionRightColumns");
			this.junctionRightColumns = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(List.class);
		}

		return junctionRightColumns;
	}

	public void setJunctionRightColumns(List<ColumnBuilder<?>> junctionRightColumns) {
		verifyMutable();
		this.junctionRightColumns = junctionRightColumns;
	}

	public EntityAttributeBuilder<P> junctionRightColumns(ColumnBuilder<?> ... junctionRightColumns) {
		verifyMutable();
		return junctionRightColumns(new ListBuilder<ColumnBuilder<?>>().add(junctionRightColumns).toList());
	}
	
	public EntityAttributeBuilder<P> junctionRightColumns(Collection<ColumnBuilder<?>> junctionRightColumns) {
		verifyMutable();
		if (this.junctionRightColumns == null) {
			this.junctionRightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		if (junctionRightColumns != null) {
			for (ColumnBuilder<?> e : junctionRightColumns) {
				CollectionUtils.addItem(this.junctionRightColumns, e);
			}
		}
		return this;
	}

	public ColumnBuilder<? extends EntityAttributeBuilder<P>> junctionRightColumns$addColumn() {
		verifyMutable();
		if (this.junctionRightColumns == null) {
			this.junctionRightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		
		ColumnBuilder<EntityAttributeBuilder<P>> result =
				new ColumnBuilder<EntityAttributeBuilder<P>>(this);
		
		CollectionUtils.addItem(this.junctionRightColumns, result);
		
		return result;
	}
	

	public class JunctionRightColumns$$$builder<P1 extends EntityAttributeBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected JunctionRightColumns$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public ColumnBuilder<JunctionRightColumns$$$builder<P1>> column$begin() {
			ColumnBuilder<JunctionRightColumns$$$builder<P1>> result = new ColumnBuilder<JunctionRightColumns$$$builder<P1>>(this);
			CollectionUtils.addItem(EntityAttributeBuilder.this.junctionRightColumns, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public JunctionRightColumns$$$builder<? extends EntityAttributeBuilder<P>> junctionRightColumns$list() {
		verifyMutable();
		if (this.junctionRightColumns == null) {
			this.junctionRightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		return new JunctionRightColumns$$$builder<EntityAttributeBuilder<P>>(this);
	}

    public EntityAttributeBuilder<P> junctionRightColumns$wrap(Column ... junctionRightColumns) {
    	return junctionRightColumns$wrap(new ListBuilder<Column>().add(junctionRightColumns).toList());
    }

    public EntityAttributeBuilder<P> junctionRightColumns$wrap(Collection<? extends Column> junctionRightColumns) {
		verifyMutable();

		if (this.junctionRightColumns == null) {
			this.junctionRightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		if (junctionRightColumns != null) {
			for (Column e : junctionRightColumns) {
				ColumnBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(ColumnBuilder.class);
				CollectionUtils.addItem(this.junctionRightColumns, wrapped);
			}
		}
		return this;
    }
    
    public EntityAttributeBuilder<P> junctionRightColumns$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return junctionRightColumns$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public EntityAttributeBuilder<P> junctionRightColumns$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		verifyMutable();

		if (this.junctionRightColumns == null) {
			this.junctionRightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		if (builderIds != null) {
	    	for (Object builderId : builderIds) {
	            Object restoredObject = repo.get(builderId);
	            if (restoredObject == null) {
	            	if (repo.isSupportLazy()) {
	            		repo.addObjectStoredListener(builderId, new Procedure() {
	    					public void execute(Object... arguments) {
	    						CollectionUtils.addItem(EntityAttributeBuilder.this.junctionRightColumns, arguments[0]);
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
	                CollectionUtils.addItem(this.junctionRightColumns, restoredObject);
	            }
	    	}
		}
        return this;
    }


	private String inverseAttributeName;
	
	public String getInverseAttributeName() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.inverseAttributeName, String.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, EntityAttribute.class, "inverseAttributeName");
			this.inverseAttributeName = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(String.class);
		}

		return inverseAttributeName;
	}

	public void setInverseAttributeName(String inverseAttributeName) {
		verifyMutable();
		this.inverseAttributeName = inverseAttributeName;
	}

	public EntityAttributeBuilder<P> inverseAttributeName(String inverseAttributeName) {
		verifyMutable();
		this.inverseAttributeName = inverseAttributeName;
		return this;
	}

	@Override
	public EntityAttributeBuilder<P> fullName(String fullName) {
		return (EntityAttributeBuilder<P>)super.fullName(fullName);
	}

	@Override
	public EntityAttributeBuilder<P> field(Field field) {
		return (EntityAttributeBuilder<P>)super.field(field);
	}

	@Override
	public EntityAttributeBuilder<P> owningEntity(EntityBuilder<?> owningEntity) {
		return (EntityAttributeBuilder<P>)super.owningEntity(owningEntity);
	}

	@Override
    public EntityAttributeBuilder<P> owningEntity$wrap(Entity owningEntity) {
		return (EntityAttributeBuilder<P>)super.owningEntity$wrap(owningEntity);
    }

	@Override
    public EntityAttributeBuilder<P> owningEntity$restoreFrom(BuilderRepository repo, Object builderId) {
		return (EntityAttributeBuilder<P>)super.owningEntity$restoreFrom(repo, builderId);
    }

	@SuppressWarnings("unchecked")
	@Override
	public EntityBuilder<? extends EntityAttributeBuilder<P>> owningEntity$begin() {
		return (EntityBuilder<? extends EntityAttributeBuilder<P>>)super.owningEntity$begin();
	}

	@Override
	public EntityAttributeBuilder<P> insertable(boolean insertable) {
		return (EntityAttributeBuilder<P>)super.insertable(insertable);
	}

	@Override
	public EntityAttributeBuilder<P> updatable(boolean updatable) {
		return (EntityAttributeBuilder<P>)super.updatable(updatable);
	}

	@Override
	public EntityAttributeBuilder<P> nullable(boolean nullable) {
		return (EntityAttributeBuilder<P>)super.nullable(nullable);
	}

	@Override
	public EntityAttributeBuilder<P> fetchType(FetchType fetchType) {
		return (EntityAttributeBuilder<P>)super.fetchType(fetchType);
	}

    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
}

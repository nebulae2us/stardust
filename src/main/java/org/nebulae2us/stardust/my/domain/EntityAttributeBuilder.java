package org.nebulae2us.stardust.my.domain;

import java.lang.reflect.*;
import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;
import org.nebulae2us.stardust.db.domain.*;

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
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(EntityAttribute.class);
    }
    

	@Override
    public EntityAttribute toAttribute() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(EntityAttribute.class);
    }
    


	private EntityBuilder<?> entity;
	
	public EntityBuilder<?> getEntity() {
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

	private List<ColumnBuilder<?>> leftColumns;
	
	public List<ColumnBuilder<?>> getLeftColumns() {
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


	private List<ColumnBuilder<?>> junctionLeftColumns;
	
	public List<ColumnBuilder<?>> getJunctionLeftColumns() {
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
}

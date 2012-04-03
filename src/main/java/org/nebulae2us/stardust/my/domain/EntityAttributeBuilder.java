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
	public EntityAttribute getWrappedObject() {
		return (EntityAttribute)this.$$$wrapped;
	}

	@Override
    public EntityAttributeBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
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

	public EntityBuilder<? extends EntityAttributeBuilder<P>> entity$begin() {
		EntityBuilder<EntityAttributeBuilder<P>> result = new EntityBuilder<EntityAttributeBuilder<P>>(this);
		this.entity = result;
		return result;
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
				this.leftColumns.add(e);
			}
		}
		return this;
	}

	public ColumnBuilder<EntityAttributeBuilder<P>> leftColumns$one() {
		verifyMutable();
		if (this.leftColumns == null) {
			this.leftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		
		ColumnBuilder<EntityAttributeBuilder<P>> result =
				new ColumnBuilder<EntityAttributeBuilder<P>>(this);
		
		this.leftColumns.add(result);
		
		return result;
	}

	public class LeftColumns$$$builder {
		
		public ColumnBuilder<LeftColumns$$$builder> blank$begin() {
			ColumnBuilder<LeftColumns$$$builder> result = new ColumnBuilder<LeftColumns$$$builder>(this);
			EntityAttributeBuilder.this.leftColumns.add(result);
			return result;
		}
		
		public EntityAttributeBuilder<P> end() {
			return EntityAttributeBuilder.this;
		}
	}
	
	public LeftColumns$$$builder leftColumns$list() {
		verifyMutable();
		if (this.leftColumns == null) {
			this.leftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		return new LeftColumns$$$builder();
	}

    public EntityAttributeBuilder<P> leftColumns$wrap(Column ... leftColumns) {
    	return leftColumns$wrap(new ListBuilder<Column>().add(leftColumns).toList());
    }

    public EntityAttributeBuilder<P> leftColumns$wrap(Collection<Column> leftColumns) {
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
	    						EntityAttributeBuilder.this.leftColumns.add((ColumnBuilder<?>)arguments[0]);
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
				this.rightColumns.add(e);
			}
		}
		return this;
	}

	public ColumnBuilder<EntityAttributeBuilder<P>> rightColumns$one() {
		verifyMutable();
		if (this.rightColumns == null) {
			this.rightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		
		ColumnBuilder<EntityAttributeBuilder<P>> result =
				new ColumnBuilder<EntityAttributeBuilder<P>>(this);
		
		this.rightColumns.add(result);
		
		return result;
	}

	public class RightColumns$$$builder {
		
		public ColumnBuilder<RightColumns$$$builder> blank$begin() {
			ColumnBuilder<RightColumns$$$builder> result = new ColumnBuilder<RightColumns$$$builder>(this);
			EntityAttributeBuilder.this.rightColumns.add(result);
			return result;
		}
		
		public EntityAttributeBuilder<P> end() {
			return EntityAttributeBuilder.this;
		}
	}
	
	public RightColumns$$$builder rightColumns$list() {
		verifyMutable();
		if (this.rightColumns == null) {
			this.rightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		return new RightColumns$$$builder();
	}

    public EntityAttributeBuilder<P> rightColumns$wrap(Column ... rightColumns) {
    	return rightColumns$wrap(new ListBuilder<Column>().add(rightColumns).toList());
    }

    public EntityAttributeBuilder<P> rightColumns$wrap(Collection<Column> rightColumns) {
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
	    						EntityAttributeBuilder.this.rightColumns.add((ColumnBuilder<?>)arguments[0]);
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
				this.junctionLeftColumns.add(e);
			}
		}
		return this;
	}

	public ColumnBuilder<EntityAttributeBuilder<P>> junctionLeftColumns$one() {
		verifyMutable();
		if (this.junctionLeftColumns == null) {
			this.junctionLeftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		
		ColumnBuilder<EntityAttributeBuilder<P>> result =
				new ColumnBuilder<EntityAttributeBuilder<P>>(this);
		
		this.junctionLeftColumns.add(result);
		
		return result;
	}

	public class JunctionLeftColumns$$$builder {
		
		public ColumnBuilder<JunctionLeftColumns$$$builder> blank$begin() {
			ColumnBuilder<JunctionLeftColumns$$$builder> result = new ColumnBuilder<JunctionLeftColumns$$$builder>(this);
			EntityAttributeBuilder.this.junctionLeftColumns.add(result);
			return result;
		}
		
		public EntityAttributeBuilder<P> end() {
			return EntityAttributeBuilder.this;
		}
	}
	
	public JunctionLeftColumns$$$builder junctionLeftColumns$list() {
		verifyMutable();
		if (this.junctionLeftColumns == null) {
			this.junctionLeftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		return new JunctionLeftColumns$$$builder();
	}

    public EntityAttributeBuilder<P> junctionLeftColumns$wrap(Column ... junctionLeftColumns) {
    	return junctionLeftColumns$wrap(new ListBuilder<Column>().add(junctionLeftColumns).toList());
    }

    public EntityAttributeBuilder<P> junctionLeftColumns$wrap(Collection<Column> junctionLeftColumns) {
		verifyMutable();

		if (this.junctionLeftColumns == null) {
			this.junctionLeftColumns = new ArrayList<ColumnBuilder<?>>();
		}
		if (junctionLeftColumns != null) {
			for (Column e : junctionLeftColumns) {
				ColumnBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(ColumnBuilder.class);
				this.junctionLeftColumns.add(wrapped);
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
	    						EntityAttributeBuilder.this.junctionLeftColumns.add((ColumnBuilder<?>)arguments[0]);
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
	                this.junctionLeftColumns.add((ColumnBuilder<?>)restoredObject);
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
				this.junctionRightColumns.add(e);
			}
		}
		return this;
	}

	public ColumnBuilder<EntityAttributeBuilder<P>> junctionRightColumns$one() {
		verifyMutable();
		if (this.junctionRightColumns == null) {
			this.junctionRightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		
		ColumnBuilder<EntityAttributeBuilder<P>> result =
				new ColumnBuilder<EntityAttributeBuilder<P>>(this);
		
		this.junctionRightColumns.add(result);
		
		return result;
	}

	public class JunctionRightColumns$$$builder {
		
		public ColumnBuilder<JunctionRightColumns$$$builder> blank$begin() {
			ColumnBuilder<JunctionRightColumns$$$builder> result = new ColumnBuilder<JunctionRightColumns$$$builder>(this);
			EntityAttributeBuilder.this.junctionRightColumns.add(result);
			return result;
		}
		
		public EntityAttributeBuilder<P> end() {
			return EntityAttributeBuilder.this;
		}
	}
	
	public JunctionRightColumns$$$builder junctionRightColumns$list() {
		verifyMutable();
		if (this.junctionRightColumns == null) {
			this.junctionRightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		return new JunctionRightColumns$$$builder();
	}

    public EntityAttributeBuilder<P> junctionRightColumns$wrap(Column ... junctionRightColumns) {
    	return junctionRightColumns$wrap(new ListBuilder<Column>().add(junctionRightColumns).toList());
    }

    public EntityAttributeBuilder<P> junctionRightColumns$wrap(Collection<Column> junctionRightColumns) {
		verifyMutable();

		if (this.junctionRightColumns == null) {
			this.junctionRightColumns = new ArrayList<ColumnBuilder<?>>();
		}
		if (junctionRightColumns != null) {
			for (Column e : junctionRightColumns) {
				ColumnBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(ColumnBuilder.class);
				this.junctionRightColumns.add(wrapped);
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
	    						EntityAttributeBuilder.this.junctionRightColumns.add((ColumnBuilder<?>)arguments[0]);
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
	                this.junctionRightColumns.add((ColumnBuilder<?>)restoredObject);
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

	@SuppressWarnings("unchecked")
	@Override
	public EntityBuilder<? extends EntityAttributeBuilder<P>> owningEntity$begin() {
		return (EntityBuilder<? extends EntityAttributeBuilder<P>>)super.owningEntity$begin();
	}

	@Override
    public EntityAttributeBuilder<P> owningEntity$wrap(Entity owningEntity) {
		return (EntityAttributeBuilder<P>)super.owningEntity$wrap(owningEntity);
    }

	@Override
    public EntityAttributeBuilder<P> owningEntity$restoreFrom(BuilderRepository repo, Object builderId) {
		return (EntityAttributeBuilder<P>)super.owningEntity$restoreFrom(repo, builderId);
    }
}

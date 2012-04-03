package org.nebulae2us.stardust.sql.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;
import org.nebulae2us.stardust.my.domain.*;

@Builder(destination=RelationalEntities.class)
public class RelationalEntitiesBuilder<P> implements Wrappable<RelationalEntities> {

	protected final RelationalEntities $$$wrapped;

	protected final P $$$parentBuilder;
	
	public RelationalEntitiesBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public RelationalEntitiesBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected RelationalEntitiesBuilder(RelationalEntities wrapped) {
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public RelationalEntitiesBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public RelationalEntities getWrappedObject() {
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

    public RelationalEntities toRelationalEntities() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(RelationalEntities.class);
    }

	private EntityBuilder<?> entity;
	
	public EntityBuilder<?> getEntity() {
		return entity;
	}

	public void setEntity(EntityBuilder<?> entity) {
		verifyMutable();
		this.entity = entity;
	}

	public RelationalEntitiesBuilder<P> entity(EntityBuilder<?> entity) {
		verifyMutable();
		this.entity = entity;
		return this;
	}

	public EntityBuilder<? extends RelationalEntitiesBuilder<P>> entity$begin() {
		EntityBuilder<RelationalEntitiesBuilder<P>> result = new EntityBuilder<RelationalEntitiesBuilder<P>>(this);
		this.entity = result;
		return result;
	}

    public RelationalEntitiesBuilder<P> entity$wrap(Entity entity) {
    	verifyMutable();
    	this.entity = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(entity).to(EntityBuilder.class);
        return this;
    }
    
    public RelationalEntitiesBuilder<P> entity$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						RelationalEntitiesBuilder.this.entity = (EntityBuilder<?>)arguments[0];
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

	private List<EntityJoinBuilder<?>> entityJoins;
	
	public List<EntityJoinBuilder<?>> getEntityJoins() {
		return entityJoins;
	}

	public void setEntityJoins(List<EntityJoinBuilder<?>> entityJoins) {
		verifyMutable();
		this.entityJoins = entityJoins;
	}

	public RelationalEntitiesBuilder<P> entityJoins(EntityJoinBuilder<?> ... entityJoins) {
		verifyMutable();
		return entityJoins(new ListBuilder<EntityJoinBuilder<?>>().add(entityJoins).toList());
	}
	
	public RelationalEntitiesBuilder<P> entityJoins(Collection<EntityJoinBuilder<?>> entityJoins) {
		verifyMutable();
		if (this.entityJoins == null) {
			this.entityJoins = new ArrayList<EntityJoinBuilder<?>>();
		}
		if (entityJoins != null) {
			for (EntityJoinBuilder<?> e : entityJoins) {
				this.entityJoins.add(e);
			}
		}
		return this;
	}

	public EntityJoinBuilder<RelationalEntitiesBuilder<P>> entityJoins$one() {
		verifyMutable();
		if (this.entityJoins == null) {
			this.entityJoins = new ArrayList<EntityJoinBuilder<?>>();
		}
		
		EntityJoinBuilder<RelationalEntitiesBuilder<P>> result =
				new EntityJoinBuilder<RelationalEntitiesBuilder<P>>(this);
		
		this.entityJoins.add(result);
		
		return result;
	}

	public class EntityJoins$$$builder {
		
		public EntityJoinBuilder<EntityJoins$$$builder> blank$begin() {
			EntityJoinBuilder<EntityJoins$$$builder> result = new EntityJoinBuilder<EntityJoins$$$builder>(this);
			RelationalEntitiesBuilder.this.entityJoins.add(result);
			return result;
		}
		
		public RelationalEntitiesBuilder<P> end() {
			return RelationalEntitiesBuilder.this;
		}
	}
	
	public EntityJoins$$$builder entityJoins$list() {
		verifyMutable();
		if (this.entityJoins == null) {
			this.entityJoins = new ArrayList<EntityJoinBuilder<?>>();
		}
		return new EntityJoins$$$builder();
	}

    public RelationalEntitiesBuilder<P> entityJoins$wrap(EntityJoin ... entityJoins) {
    	return entityJoins$wrap(new ListBuilder<EntityJoin>().add(entityJoins).toList());
    }

    public RelationalEntitiesBuilder<P> entityJoins$wrap(Collection<EntityJoin> entityJoins) {
		verifyMutable();

		if (this.entityJoins == null) {
			this.entityJoins = new ArrayList<EntityJoinBuilder<?>>();
		}
		if (entityJoins != null) {
			for (EntityJoin e : entityJoins) {
				EntityJoinBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(EntityJoinBuilder.class);
				this.entityJoins.add(wrapped);
			}
		}
		return this;
    }
    
    public RelationalEntitiesBuilder<P> entityJoins$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return entityJoins$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public RelationalEntitiesBuilder<P> entityJoins$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		verifyMutable();

		if (this.entityJoins == null) {
			this.entityJoins = new ArrayList<EntityJoinBuilder<?>>();
		}
		if (builderIds != null) {
	    	for (Object builderId : builderIds) {
	            Object restoredObject = repo.get(builderId);
	            if (restoredObject == null) {
	            	if (repo.isSupportLazy()) {
	            		repo.addObjectStoredListener(builderId, new Procedure() {
	    					public void execute(Object... arguments) {
	    						RelationalEntitiesBuilder.this.entityJoins.add((EntityJoinBuilder<?>)arguments[0]);
	    					}
	    				});
	            	}
	            	else {
	                    throw new IllegalStateException("Object does not exist with id " + builderId);
	            	}
	            }
	            else if (!(restoredObject instanceof EntityJoinBuilder)) {
	            	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + EntityJoinBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
	            }
	            else {
	                this.entityJoins.add((EntityJoinBuilder<?>)restoredObject);
	            }
	    	}
		}
        return this;
    }
}

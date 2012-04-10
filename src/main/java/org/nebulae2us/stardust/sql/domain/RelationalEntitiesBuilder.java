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

	public EntityBuilder<? extends RelationalEntitiesBuilder<P>> entity$begin() {
		verifyMutable();
		EntityBuilder<RelationalEntitiesBuilder<P>> result = new EntityBuilder<RelationalEntitiesBuilder<P>>(this);
		this.entity = result;
		return result;
	}

	private String initialAlias;
	
	public String getInitialAlias() {
		return initialAlias;
	}

	public void setInitialAlias(String initialAlias) {
		verifyMutable();
		this.initialAlias = initialAlias;
	}

	public RelationalEntitiesBuilder<P> initialAlias(String initialAlias) {
		verifyMutable();
		this.initialAlias = initialAlias;
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
				CollectionUtils.addItem(this.entityJoins, e);
			}
		}
		return this;
	}

	public EntityJoinBuilder<? extends RelationalEntitiesBuilder<P>> entityJoins$addEntityJoin() {
		verifyMutable();
		if (this.entityJoins == null) {
			this.entityJoins = new ArrayList<EntityJoinBuilder<?>>();
		}
		
		EntityJoinBuilder<RelationalEntitiesBuilder<P>> result =
				new EntityJoinBuilder<RelationalEntitiesBuilder<P>>(this);
		
		CollectionUtils.addItem(this.entityJoins, result);
		
		return result;
	}
	

	public class EntityJoins$$$builder<P1 extends RelationalEntitiesBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected EntityJoins$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public EntityJoinBuilder<EntityJoins$$$builder<P1>> entityJoin$begin() {
			EntityJoinBuilder<EntityJoins$$$builder<P1>> result = new EntityJoinBuilder<EntityJoins$$$builder<P1>>(this);
			CollectionUtils.addItem(RelationalEntitiesBuilder.this.entityJoins, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public EntityJoins$$$builder<? extends RelationalEntitiesBuilder<P>> entityJoins$list() {
		verifyMutable();
		if (this.entityJoins == null) {
			this.entityJoins = new ArrayList<EntityJoinBuilder<?>>();
		}
		return new EntityJoins$$$builder<RelationalEntitiesBuilder<P>>(this);
	}

    public RelationalEntitiesBuilder<P> entityJoins$wrap(EntityJoin ... entityJoins) {
    	return entityJoins$wrap(new ListBuilder<EntityJoin>().add(entityJoins).toList());
    }

    public RelationalEntitiesBuilder<P> entityJoins$wrap(Collection<? extends EntityJoin> entityJoins) {
		verifyMutable();

		if (this.entityJoins == null) {
			this.entityJoins = new ArrayList<EntityJoinBuilder<?>>();
		}
		if (entityJoins != null) {
			for (EntityJoin e : entityJoins) {
				EntityJoinBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(EntityJoinBuilder.class);
				CollectionUtils.addItem(this.entityJoins, wrapped);
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
	    						CollectionUtils.addItem(RelationalEntitiesBuilder.this.entityJoins, arguments[0]);
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
	                CollectionUtils.addItem(this.entityJoins, restoredObject);
	            }
	    	}
		}
        return this;
    }

}

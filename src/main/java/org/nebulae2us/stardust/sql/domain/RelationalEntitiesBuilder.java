package org.nebulae2us.stardust.sql.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.stardust.my.domain.*;


public class RelationalEntitiesBuilder<B> implements Convertable {

	private final RelationalEntities $$$savedTarget;

	private ConverterOption $$$option;

	private final B $$$parentBuilder;

	protected RelationalEntitiesBuilder(RelationalEntities relationalEntities) {
		if (relationalEntities == null) {
			throw new NullPointerException();
		}
	
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = relationalEntities;
	}

	public RelationalEntitiesBuilder(ConverterOption option, B parentBuilder) {
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = parentBuilder;
		this.$$$savedTarget = null;
	}

	public RelationalEntitiesBuilder() {
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public RelationalEntitiesBuilder(ConverterOption option) {
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public ConverterOption getConverterOption() {
		return this.$$$option;
	}
	
	public void setConverterOption(ConverterOption option) {
		this.$$$option = option;
	}
	
	public RelationalEntities getSavedTarget() {
		return this.$$$savedTarget;
	}

	public boolean convertableTo(Class<?> c) {
		return this.$$$savedTarget != null && c.isAssignableFrom(this.$$$savedTarget.getClass());
	}

	@SuppressWarnings("unchecked")
	public <T> T convertTo(Class<T> c) {
		if (!convertableTo(c)) {
			throw new IllegalArgumentException();
		}
		return (T)this.$$$savedTarget;
	}

    protected void copyAttributes(RelationalEntitiesBuilder<?> copy) {
    	this.entity = copy.entity;
		this.entityJoins = copy.entityJoins;
    }

    public B end() {
        return this.$$$parentBuilder;
    }

    public RelationalEntitiesBuilder<B> storeTo(BuilderRepository repo, int builderId) {
    	repo.put(builderId, this);
    	return this;
    }

    public RelationalEntities toRelationalEntities() {
    	return new Converter(this.$$$option).convert(this).to(RelationalEntities.class);
    }

    private EntityBuilder<?> entity;

    public EntityBuilder<?> getEntity() {
        return this.entity;
    }

    public void setEntity(EntityBuilder<?> entity) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.entity = entity;
    }

    public EntityBuilder<? extends RelationalEntitiesBuilder<B>> entity() {
        EntityBuilder<RelationalEntitiesBuilder<B>> entity = new EntityBuilder<RelationalEntitiesBuilder<B>>(this.$$$option, this);
        this.entity = entity;
        
        return entity;
    }

    public RelationalEntitiesBuilder<B> entity(EntityBuilder<?> entity) {
        this.entity = entity;
        return this;
    }

    public RelationalEntitiesBuilder<B> entity(Entity entity) {
    	this.entity = new WrapConverter(this.$$$option).convert(entity).to(EntityBuilder.class);
        return this;
    }

    public RelationalEntitiesBuilder<B> entity$restoreFrom(BuilderRepository repo, int builderId) {
        Object entity = repo.get(builderId);
        if (entity == null) {
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
        else {
            this.entity = (EntityBuilder<?>)entity;
        }
        return this;
    }

    private List<EntityJoinBuilder<?>> entityJoins;

    public List<EntityJoinBuilder<?>> getEntityJoins() {
        return this.entityJoins;
    }

    public void setEntityJoins(List<EntityJoinBuilder<?>> entityJoins) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.entityJoins = entityJoins;
    }

    public EntityJoinBuilder<RelationalEntitiesBuilder<B>> entityJoin() {
        if (this.entityJoins == null) {
            this.entityJoins = new ArrayList<EntityJoinBuilder<?>>();
        }

        EntityJoinBuilder<RelationalEntitiesBuilder<B>> entityJoin = new EntityJoinBuilder<RelationalEntitiesBuilder<B>>(this.$$$option, this);
        
        this.entityJoins.add(entityJoin);
        
        return entityJoin;
    }

    public RelationalEntitiesBuilder<B> entityJoin(EntityJoinBuilder<?> entityJoin) {
        if (this.entityJoins == null) {
            this.entityJoins = new ArrayList<EntityJoinBuilder<?>>();
        }
        this.entityJoins.add(entityJoin);
        return this;
    }

    public RelationalEntitiesBuilder<B> entityJoin(EntityJoin entityJoin) {
        if (this.entityJoins == null) {
            this.entityJoins = new ArrayList<EntityJoinBuilder<?>>();
        }
    	EntityJoinBuilder<?> wrap = new WrapConverter(this.$$$option).convert(entityJoin).to(EntityJoinBuilder.class);
        this.entityJoins.add(wrap);
        return this;
    }

    public RelationalEntitiesBuilder<B> entityJoins(EntityJoinBuilder<?> ... entityJoins) {
        if (this.entityJoins == null) {
            this.entityJoins = new ArrayList<EntityJoinBuilder<?>>();
        }
        for (EntityJoinBuilder<?> o : entityJoins) {
            this.entityJoins.add(o);
        }
        return this;
    }

    public RelationalEntitiesBuilder<B> entityJoins(EntityJoin ... entityJoins) {
        if (this.entityJoins == null) {
            this.entityJoins = new ArrayList<EntityJoinBuilder<?>>();
        }
        for (EntityJoin o : entityJoins) {
	    	EntityJoinBuilder<?> wrap = new WrapConverter(this.$$$option).convert(o).to(EntityJoinBuilder.class);
            this.entityJoins.add(wrap);
        }
        return this;
    }

    public RelationalEntitiesBuilder<B> entityJoin$restoreFrom(BuilderRepository repo, int builderId) {
        Object entityJoin = repo.get(builderId);
        if (this.entityJoins == null) {
            this.entityJoins = new ArrayList<EntityJoinBuilder<?>>();
        }

        if (entityJoin == null) {
        	if (repo.isSupportLazy()) {
                
        		final int size = this.entityJoins.size();
        		this.entityJoins.add(null);

        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						RelationalEntitiesBuilder.this.entityJoins.set(size, (EntityJoinBuilder<?>)arguments[0]);
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.entityJoins.add((EntityJoinBuilder<?>)entityJoin);
        }
    	
    	return this;
    }

    public RelationalEntitiesBuilder<B> entityJoins$restoreFrom(BuilderRepository repo, int ... builderIds) {

    	for (int builderId : builderIds) {
    		entityJoin$restoreFrom(repo, builderId);
    	}
    	
    	return this;
    }
}

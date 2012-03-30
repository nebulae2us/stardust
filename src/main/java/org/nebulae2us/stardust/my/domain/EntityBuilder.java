package org.nebulae2us.stardust.my.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.stardust.db.domain.*;


public class EntityBuilder<B> extends AttributeHolderBuilder<B> implements Convertable {

	private final Entity $$$savedTarget;

	private ConverterOption $$$option;

	private final B $$$parentBuilder;

	protected EntityBuilder(Entity entity) {
		super(entity);
		if (entity == null) {
			throw new NullPointerException();
		}
	
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = entity;
	}

	public EntityBuilder(ConverterOption option, B parentBuilder) {
		super(option, parentBuilder);
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = parentBuilder;
		this.$$$savedTarget = null;
	}

	public EntityBuilder() {
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public EntityBuilder(ConverterOption option) {
		super(option);
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
	
	public Entity getSavedTarget() {
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

    protected void copyAttributes(EntityBuilder<?> copy) {
		super.copyAttributes(copy);
    	this.joinedTables = copy.joinedTables;
		this.entityIdentifier = copy.entityIdentifier;
		this.rootEntity = copy.rootEntity;
		this.inheritanceType = copy.inheritanceType;
		this.discriminator = copy.discriminator;
		this.discriminatorValue = copy.discriminatorValue;
    }

    public B end() {
        return this.$$$parentBuilder;
    }

    public EntityBuilder<B> storeTo(BuilderRepository repo, int builderId) {
    	repo.put(builderId, this);
    	return this;
    }

    public Entity toEntity() {
    	return new Converter(this.$$$option).convert(this).to(Entity.class);
    }

    @Override
    public Entity toAttributeHolder() {
    	return new Converter(this.$$$option).convert(this).to(Entity.class);
    }

    private JoinedTablesBuilder<?> joinedTables;

    public JoinedTablesBuilder<?> getJoinedTables() {
        return this.joinedTables;
    }

    public void setJoinedTables(JoinedTablesBuilder<?> joinedTables) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.joinedTables = joinedTables;
    }

    public JoinedTablesBuilder<? extends EntityBuilder<B>> joinedTables() {
        JoinedTablesBuilder<EntityBuilder<B>> joinedTables = new JoinedTablesBuilder<EntityBuilder<B>>(this.$$$option, this);
        this.joinedTables = joinedTables;
        
        return joinedTables;
    }

    public EntityBuilder<B> joinedTables(JoinedTablesBuilder<?> joinedTables) {
        this.joinedTables = joinedTables;
        return this;
    }

    public EntityBuilder<B> joinedTables(JoinedTables joinedTables) {
    	this.joinedTables = new WrapConverter(this.$$$option).convert(joinedTables).to(JoinedTablesBuilder.class);
        return this;
    }

    public EntityBuilder<B> joinedTables$restoreFrom(BuilderRepository repo, int builderId) {
        Object joinedTables = repo.get(builderId);
        if (joinedTables == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						EntityBuilder.this.joinedTables = (JoinedTablesBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.joinedTables = (JoinedTablesBuilder<?>)joinedTables;
        }
        return this;
    }

    private EntityIdentifierBuilder<?> entityIdentifier;

    public EntityIdentifierBuilder<?> getEntityIdentifier() {
        return this.entityIdentifier;
    }

    public void setEntityIdentifier(EntityIdentifierBuilder<?> entityIdentifier) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.entityIdentifier = entityIdentifier;
    }

    public EntityIdentifierBuilder<? extends EntityBuilder<B>> entityIdentifier() {
        EntityIdentifierBuilder<EntityBuilder<B>> entityIdentifier = new EntityIdentifierBuilder<EntityBuilder<B>>(this.$$$option, this);
        this.entityIdentifier = entityIdentifier;
        
        return entityIdentifier;
    }

    public EntityBuilder<B> entityIdentifier(EntityIdentifierBuilder<?> entityIdentifier) {
        this.entityIdentifier = entityIdentifier;
        return this;
    }

    public EntityBuilder<B> entityIdentifier(EntityIdentifier entityIdentifier) {
    	this.entityIdentifier = new WrapConverter(this.$$$option).convert(entityIdentifier).to(EntityIdentifierBuilder.class);
        return this;
    }

    public EntityBuilder<B> entityIdentifier$restoreFrom(BuilderRepository repo, int builderId) {
        Object entityIdentifier = repo.get(builderId);
        if (entityIdentifier == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						EntityBuilder.this.entityIdentifier = (EntityIdentifierBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.entityIdentifier = (EntityIdentifierBuilder<?>)entityIdentifier;
        }
        return this;
    }

    private EntityBuilder<?> rootEntity;

    public EntityBuilder<?> getRootEntity() {
        return this.rootEntity;
    }

    public void setRootEntity(EntityBuilder<?> rootEntity) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.rootEntity = rootEntity;
    }

    public EntityBuilder<? extends EntityBuilder<B>> rootEntity() {
        EntityBuilder<EntityBuilder<B>> rootEntity = new EntityBuilder<EntityBuilder<B>>(this.$$$option, this);
        this.rootEntity = rootEntity;
        
        return rootEntity;
    }

    public EntityBuilder<B> rootEntity(EntityBuilder<?> rootEntity) {
        this.rootEntity = rootEntity;
        return this;
    }

    public EntityBuilder<B> rootEntity(Entity rootEntity) {
    	this.rootEntity = new WrapConverter(this.$$$option).convert(rootEntity).to(EntityBuilder.class);
        return this;
    }

    public EntityBuilder<B> rootEntity$restoreFrom(BuilderRepository repo, int builderId) {
        Object entity = repo.get(builderId);
        if (entity == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						EntityBuilder.this.rootEntity = (EntityBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.rootEntity = (EntityBuilder<?>)entity;
        }
        return this;
    }

    private InheritanceType inheritanceType;

    public InheritanceType getInheritanceType() {
        return this.inheritanceType;
    }

    public void setInheritanceType(InheritanceType inheritanceType) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.inheritanceType = inheritanceType;
    }

    public EntityBuilder<B> inheritanceType(InheritanceType inheritanceType) {
        this.inheritanceType = inheritanceType;
        return this;
    }

    private ScalarAttributeBuilder<?> discriminator;

    public ScalarAttributeBuilder<?> getDiscriminator() {
        return this.discriminator;
    }

    public void setDiscriminator(ScalarAttributeBuilder<?> discriminator) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.discriminator = discriminator;
    }

    public ScalarAttributeBuilder<? extends EntityBuilder<B>> discriminator() {
        ScalarAttributeBuilder<EntityBuilder<B>> discriminator = new ScalarAttributeBuilder<EntityBuilder<B>>(this.$$$option, this);
        this.discriminator = discriminator;
        
        return discriminator;
    }

    public EntityBuilder<B> discriminator(ScalarAttributeBuilder<?> discriminator) {
        this.discriminator = discriminator;
        return this;
    }

    public EntityBuilder<B> discriminator(ScalarAttribute discriminator) {
    	this.discriminator = new WrapConverter(this.$$$option).convert(discriminator).to(ScalarAttributeBuilder.class);
        return this;
    }

    public EntityBuilder<B> discriminator$restoreFrom(BuilderRepository repo, int builderId) {
        Object scalarAttribute = repo.get(builderId);
        if (scalarAttribute == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						EntityBuilder.this.discriminator = (ScalarAttributeBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.discriminator = (ScalarAttributeBuilder<?>)scalarAttribute;
        }
        return this;
    }

    private String discriminatorValue;

    public String getDiscriminatorValue() {
        return this.discriminatorValue;
    }

    public void setDiscriminatorValue(String discriminatorValue) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.discriminatorValue = discriminatorValue;
    }

    public EntityBuilder<B> discriminatorValue(String discriminatorValue) {
        this.discriminatorValue = discriminatorValue;
        return this;
    }

	@Override
    public EntityBuilder<B> declaringClass(Class declaringClass) {
        return (EntityBuilder<B>)super.declaringClass(declaringClass);
    }
}

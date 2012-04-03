package org.nebulae2us.stardust.my.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;
import org.nebulae2us.stardust.db.domain.*;

@Builder(destination=Entity.class)
public class EntityBuilder<P> extends AttributeHolderBuilder<P> {

	public EntityBuilder() {
		super();
	}
	
	public EntityBuilder(P parentBuilder) {
		super(parentBuilder);
	}

	protected EntityBuilder(Entity wrapped) {
		super(wrapped);
	}

	@Override
	public Entity getWrappedObject() {
		return (Entity)this.$$$wrapped;
	}

	@Override
    public EntityBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }
	
    public Entity toEntity() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(Entity.class);
    }

    @Override
    public Entity toAttributeHolder() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(Entity.class);
    }

	private JoinedTablesBuilder<?> joinedTables;
	
	public JoinedTablesBuilder<?> getJoinedTables() {
		return joinedTables;
	}

	public void setJoinedTables(JoinedTablesBuilder<?> joinedTables) {
		verifyMutable();
		this.joinedTables = joinedTables;
	}

	public EntityBuilder<P> joinedTables(JoinedTablesBuilder<?> joinedTables) {
		verifyMutable();
		this.joinedTables = joinedTables;
		return this;
	}

	public JoinedTablesBuilder<? extends EntityBuilder<P>> joinedTables$begin() {
		JoinedTablesBuilder<EntityBuilder<P>> result = new JoinedTablesBuilder<EntityBuilder<P>>(this);
		this.joinedTables = result;
		return result;
	}

    public EntityBuilder<P> joinedTables$wrap(JoinedTables joinedTables) {
    	verifyMutable();
    	this.joinedTables = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(joinedTables).to(JoinedTablesBuilder.class);
        return this;
    }
    
    public EntityBuilder<P> joinedTables$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
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
        else if (!(restoredObject instanceof JoinedTablesBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + JoinedTablesBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.joinedTables = (JoinedTablesBuilder<?>)restoredObject;
        }
        return this;
    }

	private EntityIdentifierBuilder<?> entityIdentifier;
	
	public EntityIdentifierBuilder<?> getEntityIdentifier() {
		return entityIdentifier;
	}

	public void setEntityIdentifier(EntityIdentifierBuilder<?> entityIdentifier) {
		verifyMutable();
		this.entityIdentifier = entityIdentifier;
	}

	public EntityBuilder<P> entityIdentifier(EntityIdentifierBuilder<?> entityIdentifier) {
		verifyMutable();
		this.entityIdentifier = entityIdentifier;
		return this;
	}

	public EntityIdentifierBuilder<? extends EntityBuilder<P>> entityIdentifier$begin() {
		EntityIdentifierBuilder<EntityBuilder<P>> result = new EntityIdentifierBuilder<EntityBuilder<P>>(this);
		this.entityIdentifier = result;
		return result;
	}

    public EntityBuilder<P> entityIdentifier$wrap(EntityIdentifier entityIdentifier) {
    	verifyMutable();
    	this.entityIdentifier = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(entityIdentifier).to(EntityIdentifierBuilder.class);
        return this;
    }
    
    public EntityBuilder<P> entityIdentifier$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
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
        else if (!(restoredObject instanceof EntityIdentifierBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + EntityIdentifierBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.entityIdentifier = (EntityIdentifierBuilder<?>)restoredObject;
        }
        return this;
    }

	private EntityBuilder<?> rootEntity;
	
	public EntityBuilder<?> getRootEntity() {
		return rootEntity;
	}

	public void setRootEntity(EntityBuilder<?> rootEntity) {
		verifyMutable();
		this.rootEntity = rootEntity;
	}

	public EntityBuilder<P> rootEntity(EntityBuilder<?> rootEntity) {
		verifyMutable();
		this.rootEntity = rootEntity;
		return this;
	}

	public EntityBuilder<? extends EntityBuilder<P>> rootEntity$begin() {
		EntityBuilder<EntityBuilder<P>> result = new EntityBuilder<EntityBuilder<P>>(this);
		this.rootEntity = result;
		return result;
	}

    public EntityBuilder<P> rootEntity$wrap(Entity rootEntity) {
    	verifyMutable();
    	this.rootEntity = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(rootEntity).to(EntityBuilder.class);
        return this;
    }
    
    public EntityBuilder<P> rootEntity$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
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
        else if (!(restoredObject instanceof EntityBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + EntityBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.rootEntity = (EntityBuilder<?>)restoredObject;
        }
        return this;
    }

	private InheritanceType inheritanceType;
	
	public InheritanceType getInheritanceType() {
		return inheritanceType;
	}

	public void setInheritanceType(InheritanceType inheritanceType) {
		verifyMutable();
		this.inheritanceType = inheritanceType;
	}

	public EntityBuilder<P> inheritanceType(InheritanceType inheritanceType) {
		verifyMutable();
		this.inheritanceType = inheritanceType;
		return this;
	}

	private ScalarAttributeBuilder<?> discriminator;
	
	public ScalarAttributeBuilder<?> getDiscriminator() {
		return discriminator;
	}

	public void setDiscriminator(ScalarAttributeBuilder<?> discriminator) {
		verifyMutable();
		this.discriminator = discriminator;
	}

	public EntityBuilder<P> discriminator(ScalarAttributeBuilder<?> discriminator) {
		verifyMutable();
		this.discriminator = discriminator;
		return this;
	}

	public ScalarAttributeBuilder<? extends EntityBuilder<P>> discriminator$begin() {
		ScalarAttributeBuilder<EntityBuilder<P>> result = new ScalarAttributeBuilder<EntityBuilder<P>>(this);
		this.discriminator = result;
		return result;
	}

    public EntityBuilder<P> discriminator$wrap(ScalarAttribute discriminator) {
    	verifyMutable();
    	this.discriminator = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(discriminator).to(ScalarAttributeBuilder.class);
        return this;
    }
    
    public EntityBuilder<P> discriminator$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
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
        else if (!(restoredObject instanceof ScalarAttributeBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + ScalarAttributeBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.discriminator = (ScalarAttributeBuilder<?>)restoredObject;
        }
        return this;
    }

	private String discriminatorValue;
	
	public String getDiscriminatorValue() {
		return discriminatorValue;
	}

	public void setDiscriminatorValue(String discriminatorValue) {
		verifyMutable();
		this.discriminatorValue = discriminatorValue;
	}

	public EntityBuilder<P> discriminatorValue(String discriminatorValue) {
		verifyMutable();
		this.discriminatorValue = discriminatorValue;
		return this;
	}

	@Override
	public EntityBuilder<P> declaringClass(Class<?> declaringClass) {
		return (EntityBuilder<P>)super.declaringClass(declaringClass);
	}

	@Override
	public EntityBuilder<P> attributes(AttributeBuilder<?> ... attributes) {
		return (EntityBuilder<P>)super.attributes(attributes);
	}

	@Override
	public EntityBuilder<P> attributes(Collection<AttributeBuilder<?>> attributes) {
		return (EntityBuilder<P>)super.attributes(attributes);
	}

	@Override
    public EntityBuilder<P> attributes$wrap(Attribute ... attributes) {
		return (EntityBuilder<P>)super.attributes$wrap(attributes);
    }

	@Override
    public EntityBuilder<P> attributes$wrap(Collection<Attribute> attributes) {
		return (EntityBuilder<P>)super.attributes$wrap(attributes);
    }

	@Override
    public EntityBuilder<P> attributes$restoreFrom(BuilderRepository repo, Object ... builderIds) {
		return (EntityBuilder<P>)super.attributes$restoreFrom(repo, builderIds);
    }

	@Override
    public EntityBuilder<P> attributes$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		return (EntityBuilder<P>)super.attributes$restoreFrom(repo, builderIds);
    }
}

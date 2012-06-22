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
    public EntityBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	@Override
	public Entity getWrappedObject() {
		return (Entity)this.$$$wrapped;
	}

    public Entity toEntity() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(Entity.class);
    }
    

	@Override
    public Entity toAttributeHolder() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(Entity.class);
    }
    


	private LinkedTableBundleBuilder<?> linkedTableBundle;
	
	public LinkedTableBundleBuilder<?> getLinkedTableBundle() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.linkedTableBundle, LinkedTableBundleBuilder.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, Entity.class, "linkedTableBundle");
			this.linkedTableBundle = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(LinkedTableBundleBuilder.class);
		}

		return linkedTableBundle;
	}

	public void setLinkedTableBundle(LinkedTableBundleBuilder<?> linkedTableBundle) {
		verifyMutable();
		this.linkedTableBundle = linkedTableBundle;
	}

	public EntityBuilder<P> linkedTableBundle(LinkedTableBundleBuilder<?> linkedTableBundle) {
		verifyMutable();
		this.linkedTableBundle = linkedTableBundle;
		return this;
	}

    public EntityBuilder<P> linkedTableBundle$wrap(LinkedTableBundle linkedTableBundle) {
    	verifyMutable();
    	this.linkedTableBundle = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(linkedTableBundle).to(LinkedTableBundleBuilder.class);
        return this;
    }
    
    public EntityBuilder<P> linkedTableBundle$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						EntityBuilder.this.linkedTableBundle = (LinkedTableBundleBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else if (!(restoredObject instanceof LinkedTableBundleBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + LinkedTableBundleBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.linkedTableBundle = (LinkedTableBundleBuilder<?>)restoredObject;
        }
        return this;
    }

	public LinkedTableBundleBuilder<? extends EntityBuilder<P>> linkedTableBundle$begin() {
		verifyMutable();
		LinkedTableBundleBuilder<EntityBuilder<P>> result = new LinkedTableBundleBuilder<EntityBuilder<P>>(this);
		this.linkedTableBundle = result;
		return result;
	}

	private EntityIdentifierBuilder<?> entityIdentifier;
	
	public EntityIdentifierBuilder<?> getEntityIdentifier() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.entityIdentifier, EntityIdentifierBuilder.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, Entity.class, "entityIdentifier");
			this.entityIdentifier = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(EntityIdentifierBuilder.class);
		}

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

	public EntityIdentifierBuilder<? extends EntityBuilder<P>> entityIdentifier$begin() {
		verifyMutable();
		EntityIdentifierBuilder<EntityBuilder<P>> result = new EntityIdentifierBuilder<EntityBuilder<P>>(this);
		this.entityIdentifier = result;
		return result;
	}

	private EntityBuilder<?> rootEntity;
	
	public EntityBuilder<?> getRootEntity() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.rootEntity, EntityBuilder.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, Entity.class, "rootEntity");
			this.rootEntity = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(EntityBuilder.class);
		}

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

	public EntityBuilder<? extends EntityBuilder<P>> rootEntity$begin() {
		verifyMutable();
		EntityBuilder<EntityBuilder<P>> result = new EntityBuilder<EntityBuilder<P>>(this);
		this.rootEntity = result;
		return result;
	}

	private InheritanceType inheritanceType;
	
	public InheritanceType getInheritanceType() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.inheritanceType, InheritanceType.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, Entity.class, "inheritanceType");
			this.inheritanceType = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(InheritanceType.class);
		}

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

	private EntityDiscriminatorBuilder<?> entityDiscriminator;
	
	public EntityDiscriminatorBuilder<?> getEntityDiscriminator() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.entityDiscriminator, EntityDiscriminatorBuilder.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, Entity.class, "entityDiscriminator");
			this.entityDiscriminator = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(EntityDiscriminatorBuilder.class);
		}

		return entityDiscriminator;
	}

	public void setEntityDiscriminator(EntityDiscriminatorBuilder<?> entityDiscriminator) {
		verifyMutable();
		this.entityDiscriminator = entityDiscriminator;
	}

	public EntityBuilder<P> entityDiscriminator(EntityDiscriminatorBuilder<?> entityDiscriminator) {
		verifyMutable();
		this.entityDiscriminator = entityDiscriminator;
		return this;
	}

    public EntityBuilder<P> entityDiscriminator$wrap(EntityDiscriminator entityDiscriminator) {
    	verifyMutable();
    	this.entityDiscriminator = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(entityDiscriminator).to(EntityDiscriminatorBuilder.class);
        return this;
    }
    
    public EntityBuilder<P> entityDiscriminator$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						EntityBuilder.this.entityDiscriminator = (EntityDiscriminatorBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else if (!(restoredObject instanceof EntityDiscriminatorBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + EntityDiscriminatorBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.entityDiscriminator = (EntityDiscriminatorBuilder<?>)restoredObject;
        }
        return this;
    }

	public EntityDiscriminatorBuilder<? extends EntityBuilder<P>> entityDiscriminator$begin() {
		verifyMutable();
		EntityDiscriminatorBuilder<EntityBuilder<P>> result = new EntityDiscriminatorBuilder<EntityBuilder<P>>(this);
		this.entityDiscriminator = result;
		return result;
	}

	private ScalarAttributeBuilder<?> version;
	
	public ScalarAttributeBuilder<?> getVersion() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.version, ScalarAttributeBuilder.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, Entity.class, "version");
			this.version = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(ScalarAttributeBuilder.class);
		}

		return version;
	}

	public void setVersion(ScalarAttributeBuilder<?> version) {
		verifyMutable();
		this.version = version;
	}

	public EntityBuilder<P> version(ScalarAttributeBuilder<?> version) {
		verifyMutable();
		this.version = version;
		return this;
	}

    public EntityBuilder<P> version$wrap(ScalarAttribute version) {
    	verifyMutable();
    	this.version = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(version).to(ScalarAttributeBuilder.class);
        return this;
    }
    
    public EntityBuilder<P> version$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						EntityBuilder.this.version = (ScalarAttributeBuilder<?>)arguments[0];
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
            this.version = (ScalarAttributeBuilder<?>)restoredObject;
        }
        return this;
    }

	public ScalarAttributeBuilder<? extends EntityBuilder<P>> version$begin() {
		verifyMutable();
		ScalarAttributeBuilder<EntityBuilder<P>> result = new ScalarAttributeBuilder<EntityBuilder<P>>(this);
		this.version = result;
		return result;
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
	public AttributeBuilder<? extends EntityBuilder<P>> attributes$addAttribute() {
		return (AttributeBuilder<? extends EntityBuilder<P>>)super.attributes$addAttribute();
	}
	
	@Override
	public EntityAttributeBuilder<? extends EntityBuilder<P>> attributes$addEntityAttribute() {
		return (EntityAttributeBuilder<? extends EntityBuilder<P>>)super.attributes$addEntityAttribute();
	}
	
	@Override
	public ScalarAttributeBuilder<? extends EntityBuilder<P>> attributes$addScalarAttribute() {
		return (ScalarAttributeBuilder<? extends EntityBuilder<P>>)super.attributes$addScalarAttribute();
	}
	
	@Override
	public ValueObjectAttributeBuilder<? extends EntityBuilder<P>> attributes$addValueObjectAttribute() {
		return (ValueObjectAttributeBuilder<? extends EntityBuilder<P>>)super.attributes$addValueObjectAttribute();
	}
	

	public Attributes$$$builder<? extends EntityBuilder<P>> attributes$list() {
		return (Attributes$$$builder<? extends EntityBuilder<P>>)super.attributes$list();
	}
	
	@Override
    public EntityBuilder<P> attributes$wrap(Attribute ... attributes) {
		return (EntityBuilder<P>)super.attributes$wrap(attributes);
    }

	@Override
    public EntityBuilder<P> attributes$wrap(Collection<? extends Attribute> attributes) {
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


    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
    public List<ScalarAttributeBuilder<?>> getScalarAttributesForIdentifier() {
		List<ScalarAttributeBuilder<?>> result = new ArrayList<ScalarAttributeBuilder<?>>();
		
		for (AttributeBuilder<?> attribute : this.entityIdentifier.getAttributes()) {
			if (attribute instanceof ScalarAttributeBuilder) {
				result.add((ScalarAttributeBuilder<?>)attribute);
			}
			else if (attribute instanceof ValueObjectAttributeBuilder) {
				ValueObjectAttributeBuilder<?> valueObjectAttribute = (ValueObjectAttributeBuilder<?>)attribute;
				ValueObjectBuilder<?> valueObject = valueObjectAttribute.getValueObject();
				result.addAll(valueObject.getScalarAttributes());
			}
		}
		
		return result;
   	 
    }
     
     
}

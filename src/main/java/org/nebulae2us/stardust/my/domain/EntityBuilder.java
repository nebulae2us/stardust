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
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(Entity.class);
    }
    

	@Override
    public Entity toAttributeHolder() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(Entity.class);
    }
    


	private LinkedTableBundleBuilder<?> linkedTableBundle;
	
	public LinkedTableBundleBuilder<?> getLinkedTableBundle() {
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

	public ScalarAttributeBuilder<? extends EntityBuilder<P>> discriminator$begin() {
		verifyMutable();
		ScalarAttributeBuilder<EntityBuilder<P>> result = new ScalarAttributeBuilder<EntityBuilder<P>>(this);
		this.discriminator = result;
		return result;
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

package org.nebulae2us.stardust.my.domain;

import java.lang.reflect.*;
import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;

@Builder(destination=Attribute.class)
public class AttributeBuilder<P> implements Wrappable<Attribute> {

	protected final Attribute $$$wrapped;

	protected final P $$$parentBuilder;
	
	public AttributeBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public AttributeBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected AttributeBuilder(Attribute wrapped) {
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public AttributeBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public Attribute getWrappedObject() {
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

    public Attribute toAttribute() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(Attribute.class);
    }



	private String fullName;
	
	public String getFullName() {
		return fullName;
	}

	public void setFullName(String fullName) {
		verifyMutable();
		this.fullName = fullName;
	}

	public AttributeBuilder<P> fullName(String fullName) {
		verifyMutable();
		this.fullName = fullName;
		return this;
	}

	private Field field;
	
	public Field getField() {
		return field;
	}

	public void setField(Field field) {
		verifyMutable();
		this.field = field;
	}

	public AttributeBuilder<P> field(Field field) {
		verifyMutable();
		this.field = field;
		return this;
	}

	private EntityBuilder<?> owningEntity;
	
	public EntityBuilder<?> getOwningEntity() {
		return owningEntity;
	}

	public void setOwningEntity(EntityBuilder<?> owningEntity) {
		verifyMutable();
		this.owningEntity = owningEntity;
	}

	public AttributeBuilder<P> owningEntity(EntityBuilder<?> owningEntity) {
		verifyMutable();
		this.owningEntity = owningEntity;
		return this;
	}

    public AttributeBuilder<P> owningEntity$wrap(Entity owningEntity) {
    	verifyMutable();
    	this.owningEntity = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(owningEntity).to(EntityBuilder.class);
        return this;
    }
    
    public AttributeBuilder<P> owningEntity$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						AttributeBuilder.this.owningEntity = (EntityBuilder<?>)arguments[0];
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
            this.owningEntity = (EntityBuilder<?>)restoredObject;
        }
        return this;
    }

	public EntityBuilder<? extends AttributeBuilder<P>> owningEntity$begin() {
		verifyMutable();
		EntityBuilder<AttributeBuilder<P>> result = new EntityBuilder<AttributeBuilder<P>>(this);
		this.owningEntity = result;
		return result;
	}

    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     public String getName() {
    	 return this.field.getName();
     }
     
}

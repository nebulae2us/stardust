package org.nebulae2us.stardust.my.domain;

import java.lang.reflect.*;
import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;

@Builder(destination=ValueObjectAttribute.class)
public class ValueObjectAttributeBuilder<P> extends AttributeBuilder<P> {

	public ValueObjectAttributeBuilder() {
		super();
	}
	
	public ValueObjectAttributeBuilder(P parentBuilder) {
		super(parentBuilder);
	}

	protected ValueObjectAttributeBuilder(ValueObjectAttribute wrapped) {
		super(wrapped);
	}

	@Override
    public ValueObjectAttributeBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	@Override
	public ValueObjectAttribute getWrappedObject() {
		return (ValueObjectAttribute)this.$$$wrapped;
	}

    public ValueObjectAttribute toValueObjectAttribute() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(ValueObjectAttribute.class);
    }
    

	@Override
    public ValueObjectAttribute toAttribute() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(ValueObjectAttribute.class);
    }
    


	private ValueObjectBuilder<?> valueObject;
	
	public ValueObjectBuilder<?> getValueObject() {
		return valueObject;
	}

	public void setValueObject(ValueObjectBuilder<?> valueObject) {
		verifyMutable();
		this.valueObject = valueObject;
	}

	public ValueObjectAttributeBuilder<P> valueObject(ValueObjectBuilder<?> valueObject) {
		verifyMutable();
		this.valueObject = valueObject;
		return this;
	}

    public ValueObjectAttributeBuilder<P> valueObject$wrap(ValueObject valueObject) {
    	verifyMutable();
    	this.valueObject = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(valueObject).to(ValueObjectBuilder.class);
        return this;
    }
    
    public ValueObjectAttributeBuilder<P> valueObject$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						ValueObjectAttributeBuilder.this.valueObject = (ValueObjectBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else if (!(restoredObject instanceof ValueObjectBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + ValueObjectBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.valueObject = (ValueObjectBuilder<?>)restoredObject;
        }
        return this;
    }

	public ValueObjectBuilder<? extends ValueObjectAttributeBuilder<P>> valueObject$begin() {
		verifyMutable();
		ValueObjectBuilder<ValueObjectAttributeBuilder<P>> result = new ValueObjectBuilder<ValueObjectAttributeBuilder<P>>(this);
		this.valueObject = result;
		return result;
	}

	@Override
	public ValueObjectAttributeBuilder<P> fullName(String fullName) {
		return (ValueObjectAttributeBuilder<P>)super.fullName(fullName);
	}

	@Override
	public ValueObjectAttributeBuilder<P> field(Field field) {
		return (ValueObjectAttributeBuilder<P>)super.field(field);
	}

	@Override
	public ValueObjectAttributeBuilder<P> owningEntity(EntityBuilder<?> owningEntity) {
		return (ValueObjectAttributeBuilder<P>)super.owningEntity(owningEntity);
	}

	@Override
    public ValueObjectAttributeBuilder<P> owningEntity$wrap(Entity owningEntity) {
		return (ValueObjectAttributeBuilder<P>)super.owningEntity$wrap(owningEntity);
    }

	@Override
    public ValueObjectAttributeBuilder<P> owningEntity$restoreFrom(BuilderRepository repo, Object builderId) {
		return (ValueObjectAttributeBuilder<P>)super.owningEntity$restoreFrom(repo, builderId);
    }

	@SuppressWarnings("unchecked")
	@Override
	public EntityBuilder<? extends ValueObjectAttributeBuilder<P>> owningEntity$begin() {
		return (EntityBuilder<? extends ValueObjectAttributeBuilder<P>>)super.owningEntity$begin();
	}

    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

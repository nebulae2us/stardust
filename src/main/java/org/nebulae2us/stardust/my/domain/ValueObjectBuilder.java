package org.nebulae2us.stardust.my.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;

@Builder(destination=ValueObject.class)
public class ValueObjectBuilder<P> extends AttributeHolderBuilder<P> {

	public ValueObjectBuilder() {
		super();
	}
	
	public ValueObjectBuilder(P parentBuilder) {
		super(parentBuilder);
	}

	protected ValueObjectBuilder(ValueObject wrapped) {
		super(wrapped);
	}

	@Override
    public ValueObjectBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	@Override
	public ValueObject getWrappedObject() {
		return (ValueObject)this.$$$wrapped;
	}

    public ValueObject toValueObject() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(ValueObject.class);
    }
    

	@Override
    public ValueObject toAttributeHolder() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(ValueObject.class);
    }
    


	private ValueObjectBuilder<?> superValueObject;
	
	public ValueObjectBuilder<?> getSuperValueObject() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.superValueObject, ValueObjectBuilder.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, ValueObject.class, "superValueObject");
			this.superValueObject = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(ValueObjectBuilder.class);
		}

		return superValueObject;
	}

	public void setSuperValueObject(ValueObjectBuilder<?> superValueObject) {
		verifyMutable();
		this.superValueObject = superValueObject;
	}

	public ValueObjectBuilder<P> superValueObject(ValueObjectBuilder<?> superValueObject) {
		verifyMutable();
		this.superValueObject = superValueObject;
		return this;
	}

    public ValueObjectBuilder<P> superValueObject$wrap(ValueObject superValueObject) {
    	verifyMutable();
    	this.superValueObject = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(superValueObject).to(ValueObjectBuilder.class);
        return this;
    }
    
    public ValueObjectBuilder<P> superValueObject$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						ValueObjectBuilder.this.superValueObject = (ValueObjectBuilder<?>)arguments[0];
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
            this.superValueObject = (ValueObjectBuilder<?>)restoredObject;
        }
        return this;
    }

	public ValueObjectBuilder<? extends ValueObjectBuilder<P>> superValueObject$begin() {
		verifyMutable();
		ValueObjectBuilder<ValueObjectBuilder<P>> result = new ValueObjectBuilder<ValueObjectBuilder<P>>(this);
		this.superValueObject = result;
		return result;
	}

	@Override
	public ValueObjectBuilder<P> declaringClass(Class<?> declaringClass) {
		return (ValueObjectBuilder<P>)super.declaringClass(declaringClass);
	}

	@Override
	public ValueObjectBuilder<P> attributes(AttributeBuilder<?> ... attributes) {
		return (ValueObjectBuilder<P>)super.attributes(attributes);
	}

	@Override
	public ValueObjectBuilder<P> attributes(Collection<AttributeBuilder<?>> attributes) {
		return (ValueObjectBuilder<P>)super.attributes(attributes);
	}

	@Override
	public AttributeBuilder<? extends ValueObjectBuilder<P>> attributes$addAttribute() {
		return (AttributeBuilder<? extends ValueObjectBuilder<P>>)super.attributes$addAttribute();
	}
	
	@Override
	public EntityAttributeBuilder<? extends ValueObjectBuilder<P>> attributes$addEntityAttribute() {
		return (EntityAttributeBuilder<? extends ValueObjectBuilder<P>>)super.attributes$addEntityAttribute();
	}
	
	@Override
	public ScalarAttributeBuilder<? extends ValueObjectBuilder<P>> attributes$addScalarAttribute() {
		return (ScalarAttributeBuilder<? extends ValueObjectBuilder<P>>)super.attributes$addScalarAttribute();
	}
	
	@Override
	public ValueObjectAttributeBuilder<? extends ValueObjectBuilder<P>> attributes$addValueObjectAttribute() {
		return (ValueObjectAttributeBuilder<? extends ValueObjectBuilder<P>>)super.attributes$addValueObjectAttribute();
	}
	

	public Attributes$$$builder<? extends ValueObjectBuilder<P>> attributes$list() {
		return (Attributes$$$builder<? extends ValueObjectBuilder<P>>)super.attributes$list();
	}
	
	@Override
    public ValueObjectBuilder<P> attributes$wrap(Attribute ... attributes) {
		return (ValueObjectBuilder<P>)super.attributes$wrap(attributes);
    }

	@Override
    public ValueObjectBuilder<P> attributes$wrap(Collection<? extends Attribute> attributes) {
		return (ValueObjectBuilder<P>)super.attributes$wrap(attributes);
    }

	@Override
    public ValueObjectBuilder<P> attributes$restoreFrom(BuilderRepository repo, Object ... builderIds) {
		return (ValueObjectBuilder<P>)super.attributes$restoreFrom(repo, builderIds);
    }

	@Override
    public ValueObjectBuilder<P> attributes$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		return (ValueObjectBuilder<P>)super.attributes$restoreFrom(repo, builderIds);
    }


    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

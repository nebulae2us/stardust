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
	public ValueObject getWrappedObject() {
		return (ValueObject)this.$$$wrapped;
	}

	@Override
    public ValueObjectBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }
	
    public ValueObject toValueObject() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(ValueObject.class);
    }

    @Override
    public ValueObject toAttributeHolder() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(ValueObject.class);
    }

	private ValueObjectBuilder<?> superValueObject;
	
	public ValueObjectBuilder<?> getSuperValueObject() {
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

	public ValueObjectBuilder<? extends ValueObjectBuilder<P>> superValueObject$begin() {
		ValueObjectBuilder<ValueObjectBuilder<P>> result = new ValueObjectBuilder<ValueObjectBuilder<P>>(this);
		this.superValueObject = result;
		return result;
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
    public ValueObjectBuilder<P> attributes$wrap(Attribute ... attributes) {
		return (ValueObjectBuilder<P>)super.attributes$wrap(attributes);
    }

	@Override
    public ValueObjectBuilder<P> attributes$wrap(Collection<Attribute> attributes) {
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
}

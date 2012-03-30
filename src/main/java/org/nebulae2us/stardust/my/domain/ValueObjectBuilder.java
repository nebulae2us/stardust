package org.nebulae2us.stardust.my.domain;

import java.util.*;
import org.nebulae2us.electron.*;


public class ValueObjectBuilder<B> extends AttributeHolderBuilder<B> implements Convertable {

	private final ValueObject $$$savedTarget;

	private ConverterOption $$$option;

	private final B $$$parentBuilder;

	protected ValueObjectBuilder(ValueObject valueObject) {
		super(valueObject);
		if (valueObject == null) {
			throw new NullPointerException();
		}
	
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = valueObject;
	}

	public ValueObjectBuilder(ConverterOption option, B parentBuilder) {
		super(option, parentBuilder);
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = parentBuilder;
		this.$$$savedTarget = null;
	}

	public ValueObjectBuilder() {
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public ValueObjectBuilder(ConverterOption option) {
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
	
	public ValueObject getSavedTarget() {
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

    protected void copyAttributes(ValueObjectBuilder<?> copy) {
		super.copyAttributes(copy);
    	this.superValueObject = copy.superValueObject;
    }

    public B end() {
        return this.$$$parentBuilder;
    }

    public ValueObjectBuilder<B> storeTo(BuilderRepository repo, int builderId) {
    	repo.put(builderId, this);
    	return this;
    }

    public ValueObject toValueObject() {
    	return new Converter(this.$$$option).convert(this).to(ValueObject.class);
    }

    @Override
    public ValueObject toAttributeHolder() {
    	return new Converter(this.$$$option).convert(this).to(ValueObject.class);
    }

    private ValueObjectBuilder<?> superValueObject;

    public ValueObjectBuilder<?> getSuperValueObject() {
        return this.superValueObject;
    }

    public void setSuperValueObject(ValueObjectBuilder<?> superValueObject) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.superValueObject = superValueObject;
    }

    public ValueObjectBuilder<? extends ValueObjectBuilder<B>> superValueObject() {
        ValueObjectBuilder<ValueObjectBuilder<B>> superValueObject = new ValueObjectBuilder<ValueObjectBuilder<B>>(this.$$$option, this);
        this.superValueObject = superValueObject;
        
        return superValueObject;
    }

    public ValueObjectBuilder<B> superValueObject(ValueObjectBuilder<?> superValueObject) {
        this.superValueObject = superValueObject;
        return this;
    }

    public ValueObjectBuilder<B> superValueObject(ValueObject superValueObject) {
    	this.superValueObject = new WrapConverter(this.$$$option).convert(superValueObject).to(ValueObjectBuilder.class);
        return this;
    }

    public ValueObjectBuilder<B> superValueObject$restoreFrom(BuilderRepository repo, int builderId) {
        Object valueObject = repo.get(builderId);
        if (valueObject == null) {
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
        else {
            this.superValueObject = (ValueObjectBuilder<?>)valueObject;
        }
        return this;
    }

	@Override
    public ValueObjectBuilder<B> declaringClass(Class declaringClass) {
        return (ValueObjectBuilder<B>)super.declaringClass(declaringClass);
    }
}

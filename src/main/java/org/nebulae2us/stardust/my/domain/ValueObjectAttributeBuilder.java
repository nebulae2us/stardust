package org.nebulae2us.stardust.my.domain;

import java.lang.reflect.*;
import org.nebulae2us.electron.*;


public class ValueObjectAttributeBuilder<B> extends AttributeBuilder<B> implements Convertable {

	private final ValueObjectAttribute $$$savedTarget;

	private ConverterOption $$$option;

	private final B $$$parentBuilder;

	protected ValueObjectAttributeBuilder(ValueObjectAttribute valueObjectAttribute) {
		super(valueObjectAttribute);
		if (valueObjectAttribute == null) {
			throw new NullPointerException();
		}
	
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = valueObjectAttribute;
	}

	public ValueObjectAttributeBuilder(ConverterOption option, B parentBuilder) {
		super(option, parentBuilder);
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = parentBuilder;
		this.$$$savedTarget = null;
	}

	public ValueObjectAttributeBuilder() {
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public ValueObjectAttributeBuilder(ConverterOption option) {
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
	
	public ValueObjectAttribute getSavedTarget() {
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

    protected void copyAttributes(ValueObjectAttributeBuilder<?> copy) {
		super.copyAttributes(copy);
    	this.valueObject = copy.valueObject;
    }

    public B end() {
        return this.$$$parentBuilder;
    }

    public ValueObjectAttributeBuilder<B> storeTo(BuilderRepository repo, int builderId) {
    	repo.put(builderId, this);
    	return this;
    }

    public ValueObjectAttribute toValueObjectAttribute() {
    	return new Converter(this.$$$option).convert(this).to(ValueObjectAttribute.class);
    }

    @Override
    public ValueObjectAttribute toAttribute() {
    	return new Converter(this.$$$option).convert(this).to(ValueObjectAttribute.class);
    }

    private ValueObjectBuilder<?> valueObject;

    public ValueObjectBuilder<?> getValueObject() {
        return this.valueObject;
    }

    public void setValueObject(ValueObjectBuilder<?> valueObject) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.valueObject = valueObject;
    }

    public ValueObjectBuilder<? extends ValueObjectAttributeBuilder<B>> valueObject() {
        ValueObjectBuilder<ValueObjectAttributeBuilder<B>> valueObject = new ValueObjectBuilder<ValueObjectAttributeBuilder<B>>(this.$$$option, this);
        this.valueObject = valueObject;
        
        return valueObject;
    }

    public ValueObjectAttributeBuilder<B> valueObject(ValueObjectBuilder<?> valueObject) {
        this.valueObject = valueObject;
        return this;
    }

    public ValueObjectAttributeBuilder<B> valueObject(ValueObject valueObject) {
    	this.valueObject = new WrapConverter(this.$$$option).convert(valueObject).to(ValueObjectBuilder.class);
        return this;
    }

    public ValueObjectAttributeBuilder<B> valueObject$restoreFrom(BuilderRepository repo, int builderId) {
        Object valueObject = repo.get(builderId);
        if (valueObject == null) {
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
        else {
            this.valueObject = (ValueObjectBuilder<?>)valueObject;
        }
        return this;
    }

	@Override
    public ValueObjectAttributeBuilder<B> field(Field field) {
        return (ValueObjectAttributeBuilder<B>)super.field(field);
    }

    @SuppressWarnings("unchecked")
    @Override
    public EntityBuilder<? extends ValueObjectAttributeBuilder<B>> owningEntity() {
        return (EntityBuilder<ValueObjectAttributeBuilder<B>>)super.owningEntity();
    }
    
    public ValueObjectAttributeBuilder<B> owningEntity(EntityBuilder<?> owningEntity) {
		return (ValueObjectAttributeBuilder<B>)super.owningEntity(owningEntity);
    }

    public ValueObjectAttributeBuilder<B> owningEntity(Entity owningEntity) {
		return (ValueObjectAttributeBuilder<B>)super.owningEntity(owningEntity);
    }

    public ValueObjectAttributeBuilder<B> owningEntity$restoreFrom(BuilderRepository repo, int builderId) {
		return (ValueObjectAttributeBuilder<B>)super.owningEntity$restoreFrom(repo, builderId);
    }    
}

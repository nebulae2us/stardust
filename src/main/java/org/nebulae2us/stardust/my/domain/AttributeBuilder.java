package org.nebulae2us.stardust.my.domain;

import java.lang.reflect.*;
import org.nebulae2us.electron.*;


public class AttributeBuilder<B> implements Convertable {

	private final Attribute $$$savedTarget;

	private ConverterOption $$$option;

	private final B $$$parentBuilder;

	protected AttributeBuilder(Attribute attribute) {
		if (attribute == null) {
			throw new NullPointerException();
		}
	
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = attribute;
	}

	public AttributeBuilder(ConverterOption option, B parentBuilder) {
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = parentBuilder;
		this.$$$savedTarget = null;
	}

	public AttributeBuilder() {
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public AttributeBuilder(ConverterOption option) {
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
	
	public Attribute getSavedTarget() {
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

    protected void copyAttributes(AttributeBuilder<?> copy) {
    	this.field = copy.field;
		this.owningEntity = copy.owningEntity;
    }

    public B end() {
        return this.$$$parentBuilder;
    }

    public AttributeBuilder<B> storeTo(BuilderRepository repo, int builderId) {
    	repo.put(builderId, this);
    	return this;
    }

    public Attribute toAttribute() {
    	return new Converter(this.$$$option).convert(this).to(Attribute.class);
    }

    private Field field;

    public Field getField() {
        return this.field;
    }

    public void setField(Field field) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.field = field;
    }

    public AttributeBuilder<B> field(Field field) {
        this.field = field;
        return this;
    }

    private EntityBuilder<?> owningEntity;

    public EntityBuilder<?> getOwningEntity() {
        return this.owningEntity;
    }

    public void setOwningEntity(EntityBuilder<?> owningEntity) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.owningEntity = owningEntity;
    }

    public EntityBuilder<? extends AttributeBuilder<B>> owningEntity() {
        EntityBuilder<AttributeBuilder<B>> owningEntity = new EntityBuilder<AttributeBuilder<B>>(this.$$$option, this);
        this.owningEntity = owningEntity;
        
        return owningEntity;
    }

    public AttributeBuilder<B> owningEntity(EntityBuilder<?> owningEntity) {
        this.owningEntity = owningEntity;
        return this;
    }

    public AttributeBuilder<B> owningEntity(Entity owningEntity) {
    	this.owningEntity = new WrapConverter(this.$$$option).convert(owningEntity).to(EntityBuilder.class);
        return this;
    }

    public AttributeBuilder<B> owningEntity$restoreFrom(BuilderRepository repo, int builderId) {
        Object entity = repo.get(builderId);
        if (entity == null) {
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
        else {
            this.owningEntity = (EntityBuilder<?>)entity;
        }
        return this;
    }
}

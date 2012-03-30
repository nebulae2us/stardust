package org.nebulae2us.stardust.my.domain;

import java.util.*;
import org.nebulae2us.electron.*;


public class AttributeHolderBuilder<B> implements Convertable {

	private final AttributeHolder $$$savedTarget;

	private ConverterOption $$$option;

	private final B $$$parentBuilder;

	protected AttributeHolderBuilder(AttributeHolder attributeHolder) {
		if (attributeHolder == null) {
			throw new NullPointerException();
		}
	
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = attributeHolder;
	}

	public AttributeHolderBuilder(ConverterOption option, B parentBuilder) {
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = parentBuilder;
		this.$$$savedTarget = null;
	}

	public AttributeHolderBuilder() {
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public AttributeHolderBuilder(ConverterOption option) {
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
	
	public AttributeHolder getSavedTarget() {
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

    protected void copyAttributes(AttributeHolderBuilder<?> copy) {
    	this.declaringClass = copy.declaringClass;
		this.attributes = copy.attributes;
    }

    public B end() {
        return this.$$$parentBuilder;
    }

    public AttributeHolderBuilder<B> storeTo(BuilderRepository repo, int builderId) {
    	repo.put(builderId, this);
    	return this;
    }

    public AttributeHolder toAttributeHolder() {
    	return new Converter(this.$$$option).convert(this).to(AttributeHolder.class);
    }

    private Class<?> declaringClass;

    public Class<?> getDeclaringClass() {
        return this.declaringClass;
    }

    public void setDeclaringClass(Class<?> declaringClass) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.declaringClass = declaringClass;
    }

    public AttributeHolderBuilder<B> declaringClass(Class<?> declaringClass) {
        this.declaringClass = declaringClass;
        return this;
    }

    private List<AttributeBuilder<?>> attributes;

    public List<AttributeBuilder<?>> getAttributes() {
        return this.attributes;
    }

    public void setAttributes(List<AttributeBuilder<?>> attributes) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.attributes = attributes;
    }

    public AttributeBuilder<AttributeHolderBuilder<B>> attribute() {
        if (this.attributes == null) {
            this.attributes = new ArrayList<AttributeBuilder<?>>();
        }

        AttributeBuilder<AttributeHolderBuilder<B>> attribute = new AttributeBuilder<AttributeHolderBuilder<B>>(this.$$$option, this);
        
        this.attributes.add(attribute);
        
        return attribute;
    }

    public AttributeHolderBuilder<B> attribute(AttributeBuilder<?> attribute) {
        if (this.attributes == null) {
            this.attributes = new ArrayList<AttributeBuilder<?>>();
        }
        this.attributes.add(attribute);
        return this;
    }

    public AttributeHolderBuilder<B> attribute(Attribute attribute) {
        if (this.attributes == null) {
            this.attributes = new ArrayList<AttributeBuilder<?>>();
        }
    	AttributeBuilder<?> wrap = new WrapConverter(this.$$$option).convert(attribute).to(AttributeBuilder.class);
        this.attributes.add(wrap);
        return this;
    }

    public AttributeHolderBuilder<B> attributes(AttributeBuilder<?> ... attributes) {
        if (this.attributes == null) {
            this.attributes = new ArrayList<AttributeBuilder<?>>();
        }
        for (AttributeBuilder<?> o : attributes) {
            this.attributes.add(o);
        }
        return this;
    }

    public AttributeHolderBuilder<B> attributes(Attribute ... attributes) {
        if (this.attributes == null) {
            this.attributes = new ArrayList<AttributeBuilder<?>>();
        }
        for (Attribute o : attributes) {
	    	AttributeBuilder<?> wrap = new WrapConverter(this.$$$option).convert(o).to(AttributeBuilder.class);
            this.attributes.add(wrap);
        }
        return this;
    }

    public AttributeHolderBuilder<B> attribute$restoreFrom(BuilderRepository repo, int builderId) {
        Object attribute = repo.get(builderId);
        if (this.attributes == null) {
            this.attributes = new ArrayList<AttributeBuilder<?>>();
        }

        if (attribute == null) {
        	if (repo.isSupportLazy()) {
                
        		final int size = this.attributes.size();
        		this.attributes.add(null);

        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						AttributeHolderBuilder.this.attributes.set(size, (AttributeBuilder<?>)arguments[0]);
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.attributes.add((AttributeBuilder<?>)attribute);
        }
    	
    	return this;
    }

    public AttributeHolderBuilder<B> attributes$restoreFrom(BuilderRepository repo, int ... builderIds) {

    	for (int builderId : builderIds) {
    		attribute$restoreFrom(repo, builderId);
    	}
    	
    	return this;
    }
}

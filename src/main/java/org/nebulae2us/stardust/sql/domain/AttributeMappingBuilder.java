package org.nebulae2us.stardust.sql.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;
import org.nebulae2us.stardust.internal.util.Builders;
import org.nebulae2us.stardust.my.domain.*;

@Builder(destination=AttributeMapping.class)
public class AttributeMappingBuilder<P> implements Wrappable<AttributeMapping> {

	protected final AttributeMapping $$$wrapped;

	protected final P $$$parentBuilder;
	
	public AttributeMappingBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public AttributeMappingBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected AttributeMappingBuilder(AttributeMapping wrapped) {
		if (wrapped == null) {
			throw new NullPointerException();
		}
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public AttributeMappingBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public AttributeMapping getWrappedObject() {
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

    public AttributeMapping toAttributeMapping() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(AttributeMapping.class);
    }



	private AttributeBuilder<?> attribute;
	
	public AttributeBuilder<?> getAttribute() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.attribute, AttributeBuilder.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, AttributeMapping.class, "attribute");
			this.attribute = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(AttributeBuilder.class);
		}

		return attribute;
	}

	public void setAttribute(AttributeBuilder<?> attribute) {
		verifyMutable();
		this.attribute = attribute;
	}

	public AttributeMappingBuilder<P> attribute(AttributeBuilder<?> attribute) {
		verifyMutable();
		this.attribute = attribute;
		return this;
	}

    public AttributeMappingBuilder<P> attribute$wrap(Attribute attribute) {
    	verifyMutable();
    	this.attribute = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(attribute).to(AttributeBuilder.class);
        return this;
    }
    
    public AttributeMappingBuilder<P> attribute$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						AttributeMappingBuilder.this.attribute = (AttributeBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else if (!(restoredObject instanceof AttributeBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + AttributeBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.attribute = (AttributeBuilder<?>)restoredObject;
        }
        return this;
    }

	public AttributeBuilder<? extends AttributeMappingBuilder<P>> attribute$begin() {
		verifyMutable();
		AttributeBuilder<AttributeMappingBuilder<P>> result = new AttributeBuilder<AttributeMappingBuilder<P>>(this);
		this.attribute = result;
		return result;
	}

	public EntityAttributeBuilder<? extends AttributeMappingBuilder<P>> attribute$asEntityAttribute$begin() {
		verifyMutable();
		EntityAttributeBuilder<AttributeMappingBuilder<P>> result = new EntityAttributeBuilder<AttributeMappingBuilder<P>>(this);
		this.attribute = result;
		return result;
	}

	public ScalarAttributeBuilder<? extends AttributeMappingBuilder<P>> attribute$asScalarAttribute$begin() {
		verifyMutable();
		ScalarAttributeBuilder<AttributeMappingBuilder<P>> result = new ScalarAttributeBuilder<AttributeMappingBuilder<P>>(this);
		this.attribute = result;
		return result;
	}

	public ValueObjectAttributeBuilder<? extends AttributeMappingBuilder<P>> attribute$asValueObjectAttribute$begin() {
		verifyMutable();
		ValueObjectAttributeBuilder<AttributeMappingBuilder<P>> result = new ValueObjectAttributeBuilder<AttributeMappingBuilder<P>>(this);
		this.attribute = result;
		return result;
	}

    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

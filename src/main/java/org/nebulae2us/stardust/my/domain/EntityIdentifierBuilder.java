package org.nebulae2us.stardust.my.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;

@Builder(destination=EntityIdentifier.class)
public class EntityIdentifierBuilder<P> extends AttributeHolderBuilder<P> {

	public EntityIdentifierBuilder() {
		super();
	}
	
	public EntityIdentifierBuilder(P parentBuilder) {
		super(parentBuilder);
	}

	protected EntityIdentifierBuilder(EntityIdentifier wrapped) {
		super(wrapped);
	}

	@Override
    public EntityIdentifierBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	@Override
	public EntityIdentifier getWrappedObject() {
		return (EntityIdentifier)this.$$$wrapped;
	}

    public EntityIdentifier toEntityIdentifier() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(EntityIdentifier.class);
    }
    

	@Override
    public EntityIdentifier toAttributeHolder() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(EntityIdentifier.class);
    }
    


	@Override
	public EntityIdentifierBuilder<P> declaringClass(Class<?> declaringClass) {
		return (EntityIdentifierBuilder<P>)super.declaringClass(declaringClass);
	}

	@Override
	public EntityIdentifierBuilder<P> attributes(AttributeBuilder<?> ... attributes) {
		return (EntityIdentifierBuilder<P>)super.attributes(attributes);
	}

	@Override
	public EntityIdentifierBuilder<P> attributes(Collection<AttributeBuilder<?>> attributes) {
		return (EntityIdentifierBuilder<P>)super.attributes(attributes);
	}

	@Override
	public AttributeBuilder<? extends EntityIdentifierBuilder<P>> attributes$addAttribute() {
		return (AttributeBuilder<? extends EntityIdentifierBuilder<P>>)super.attributes$addAttribute();
	}
	
	@Override
	public EntityAttributeBuilder<? extends EntityIdentifierBuilder<P>> attributes$addEntityAttribute() {
		return (EntityAttributeBuilder<? extends EntityIdentifierBuilder<P>>)super.attributes$addEntityAttribute();
	}
	
	@Override
	public ScalarAttributeBuilder<? extends EntityIdentifierBuilder<P>> attributes$addScalarAttribute() {
		return (ScalarAttributeBuilder<? extends EntityIdentifierBuilder<P>>)super.attributes$addScalarAttribute();
	}
	
	@Override
	public ValueObjectAttributeBuilder<? extends EntityIdentifierBuilder<P>> attributes$addValueObjectAttribute() {
		return (ValueObjectAttributeBuilder<? extends EntityIdentifierBuilder<P>>)super.attributes$addValueObjectAttribute();
	}
	

	public Attributes$$$builder<? extends EntityIdentifierBuilder<P>> attributes$list() {
		return (Attributes$$$builder<? extends EntityIdentifierBuilder<P>>)super.attributes$list();
	}
	
	@Override
    public EntityIdentifierBuilder<P> attributes$wrap(Attribute ... attributes) {
		return (EntityIdentifierBuilder<P>)super.attributes$wrap(attributes);
    }

	@Override
    public EntityIdentifierBuilder<P> attributes$wrap(Collection<? extends Attribute> attributes) {
		return (EntityIdentifierBuilder<P>)super.attributes$wrap(attributes);
    }

	@Override
    public EntityIdentifierBuilder<P> attributes$restoreFrom(BuilderRepository repo, Object ... builderIds) {
		return (EntityIdentifierBuilder<P>)super.attributes$restoreFrom(repo, builderIds);
    }

	@Override
    public EntityIdentifierBuilder<P> attributes$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		return (EntityIdentifierBuilder<P>)super.attributes$restoreFrom(repo, builderIds);
    }


    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

package org.nebulae2us.stardust.my.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;

@Builder(destination=EntityIdentifier.class)
public class EntityIdentifierBuilder<P> implements Wrappable<EntityIdentifier> {

	protected final EntityIdentifier $$$wrapped;

	protected final P $$$parentBuilder;
	
	public EntityIdentifierBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public EntityIdentifierBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected EntityIdentifierBuilder(EntityIdentifier wrapped) {
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public EntityIdentifierBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public EntityIdentifier getWrappedObject() {
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

    public EntityIdentifier toEntityIdentifier() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(EntityIdentifier.class);
    }



	private List<AttributeBuilder<?>> attributes;
	
	public List<AttributeBuilder<?>> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AttributeBuilder<?>> attributes) {
		verifyMutable();
		this.attributes = attributes;
	}

	public EntityIdentifierBuilder<P> attributes(AttributeBuilder<?> ... attributes) {
		verifyMutable();
		return attributes(new ListBuilder<AttributeBuilder<?>>().add(attributes).toList());
	}
	
	public EntityIdentifierBuilder<P> attributes(Collection<AttributeBuilder<?>> attributes) {
		verifyMutable();
		if (this.attributes == null) {
			this.attributes = new ArrayList<AttributeBuilder<?>>();
		}
		if (attributes != null) {
			for (AttributeBuilder<?> e : attributes) {
				CollectionUtils.addItem(this.attributes, e);
			}
		}
		return this;
	}

	public AttributeBuilder<? extends EntityIdentifierBuilder<P>> attributes$addAttribute() {
		verifyMutable();
		if (this.attributes == null) {
			this.attributes = new ArrayList<AttributeBuilder<?>>();
		}
		
		AttributeBuilder<EntityIdentifierBuilder<P>> result =
				new AttributeBuilder<EntityIdentifierBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributes, result);
		
		return result;
	}
	
	public EntityAttributeBuilder<? extends EntityIdentifierBuilder<P>> attributes$addEntityAttribute() {
		verifyMutable();
		if (this.attributes == null) {
			this.attributes = new ArrayList<AttributeBuilder<?>>();
		}
		
		EntityAttributeBuilder<EntityIdentifierBuilder<P>> result =
				new EntityAttributeBuilder<EntityIdentifierBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributes, result);
		
		return result;
	}
	
	public ScalarAttributeBuilder<? extends EntityIdentifierBuilder<P>> attributes$addScalarAttribute() {
		verifyMutable();
		if (this.attributes == null) {
			this.attributes = new ArrayList<AttributeBuilder<?>>();
		}
		
		ScalarAttributeBuilder<EntityIdentifierBuilder<P>> result =
				new ScalarAttributeBuilder<EntityIdentifierBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributes, result);
		
		return result;
	}
	
	public ValueObjectAttributeBuilder<? extends EntityIdentifierBuilder<P>> attributes$addValueObjectAttribute() {
		verifyMutable();
		if (this.attributes == null) {
			this.attributes = new ArrayList<AttributeBuilder<?>>();
		}
		
		ValueObjectAttributeBuilder<EntityIdentifierBuilder<P>> result =
				new ValueObjectAttributeBuilder<EntityIdentifierBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributes, result);
		
		return result;
	}
	

	public class Attributes$$$builder<P1 extends EntityIdentifierBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected Attributes$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public AttributeBuilder<Attributes$$$builder<P1>> attribute$begin() {
			AttributeBuilder<Attributes$$$builder<P1>> result = new AttributeBuilder<Attributes$$$builder<P1>>(this);
			CollectionUtils.addItem(EntityIdentifierBuilder.this.attributes, result);
			return result;
		}
		
		public EntityAttributeBuilder<Attributes$$$builder<P1>> entityAttribute$begin() {
			EntityAttributeBuilder<Attributes$$$builder<P1>> result = new EntityAttributeBuilder<Attributes$$$builder<P1>>(this);
			CollectionUtils.addItem(EntityIdentifierBuilder.this.attributes, result);
			return result;
		}
		
		public ScalarAttributeBuilder<Attributes$$$builder<P1>> scalarAttribute$begin() {
			ScalarAttributeBuilder<Attributes$$$builder<P1>> result = new ScalarAttributeBuilder<Attributes$$$builder<P1>>(this);
			CollectionUtils.addItem(EntityIdentifierBuilder.this.attributes, result);
			return result;
		}
		
		public ValueObjectAttributeBuilder<Attributes$$$builder<P1>> valueObjectAttribute$begin() {
			ValueObjectAttributeBuilder<Attributes$$$builder<P1>> result = new ValueObjectAttributeBuilder<Attributes$$$builder<P1>>(this);
			CollectionUtils.addItem(EntityIdentifierBuilder.this.attributes, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public Attributes$$$builder<? extends EntityIdentifierBuilder<P>> attributes$list() {
		verifyMutable();
		if (this.attributes == null) {
			this.attributes = new ArrayList<AttributeBuilder<?>>();
		}
		return new Attributes$$$builder<EntityIdentifierBuilder<P>>(this);
	}

    public EntityIdentifierBuilder<P> attributes$wrap(Attribute ... attributes) {
    	return attributes$wrap(new ListBuilder<Attribute>().add(attributes).toList());
    }

    public EntityIdentifierBuilder<P> attributes$wrap(Collection<? extends Attribute> attributes) {
		verifyMutable();

		if (this.attributes == null) {
			this.attributes = new ArrayList<AttributeBuilder<?>>();
		}
		if (attributes != null) {
			for (Attribute e : attributes) {
				AttributeBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(AttributeBuilder.class);
				CollectionUtils.addItem(this.attributes, wrapped);
			}
		}
		return this;
    }
    
    public EntityIdentifierBuilder<P> attributes$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return attributes$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public EntityIdentifierBuilder<P> attributes$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		verifyMutable();

		if (this.attributes == null) {
			this.attributes = new ArrayList<AttributeBuilder<?>>();
		}
		if (builderIds != null) {
	    	for (Object builderId : builderIds) {
	            Object restoredObject = repo.get(builderId);
	            if (restoredObject == null) {
	            	if (repo.isSupportLazy()) {
	            		repo.addObjectStoredListener(builderId, new Procedure() {
	    					public void execute(Object... arguments) {
	    						CollectionUtils.addItem(EntityIdentifierBuilder.this.attributes, arguments[0]);
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
	                CollectionUtils.addItem(this.attributes, restoredObject);
	            }
	    	}
		}
        return this;
    }

}

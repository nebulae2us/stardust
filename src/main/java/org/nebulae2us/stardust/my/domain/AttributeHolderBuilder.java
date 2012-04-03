package org.nebulae2us.stardust.my.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;

@Builder(destination=AttributeHolder.class)
public class AttributeHolderBuilder<P> implements Wrappable<AttributeHolder> {

	protected final AttributeHolder $$$wrapped;

	protected final P $$$parentBuilder;
	
	public AttributeHolderBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public AttributeHolderBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected AttributeHolderBuilder(AttributeHolder wrapped) {
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public AttributeHolderBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public AttributeHolder getWrappedObject() {
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

    public AttributeHolder toAttributeHolder() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(AttributeHolder.class);
    }

	private Class<?> declaringClass;
	
	public Class<?> getDeclaringClass() {
		return declaringClass;
	}

	public void setDeclaringClass(Class<?> declaringClass) {
		verifyMutable();
		this.declaringClass = declaringClass;
	}

	public AttributeHolderBuilder<P> declaringClass(Class<?> declaringClass) {
		verifyMutable();
		this.declaringClass = declaringClass;
		return this;
	}

	private List<AttributeBuilder<?>> attributes;
	
	public List<AttributeBuilder<?>> getAttributes() {
		return attributes;
	}

	public void setAttributes(List<AttributeBuilder<?>> attributes) {
		verifyMutable();
		this.attributes = attributes;
	}

	public AttributeHolderBuilder<P> attributes(AttributeBuilder<?> ... attributes) {
		verifyMutable();
		return attributes(new ListBuilder<AttributeBuilder<?>>().add(attributes).toList());
	}
	
	public AttributeHolderBuilder<P> attributes(Collection<AttributeBuilder<?>> attributes) {
		verifyMutable();
		if (this.attributes == null) {
			this.attributes = new ArrayList<AttributeBuilder<?>>();
		}
		if (attributes != null) {
			for (AttributeBuilder<?> e : attributes) {
				this.attributes.add(e);
			}
		}
		return this;
	}

	public AttributeBuilder<AttributeHolderBuilder<P>> attributes$one() {
		verifyMutable();
		if (this.attributes == null) {
			this.attributes = new ArrayList<AttributeBuilder<?>>();
		}
		
		AttributeBuilder<AttributeHolderBuilder<P>> result =
				new AttributeBuilder<AttributeHolderBuilder<P>>(this);
		
		this.attributes.add(result);
		
		return result;
	}

	public class Attributes$$$builder {
		
		public AttributeBuilder<Attributes$$$builder> blank$begin() {
			AttributeBuilder<Attributes$$$builder> result = new AttributeBuilder<Attributes$$$builder>(this);
			AttributeHolderBuilder.this.attributes.add(result);
			return result;
		}
		
		public AttributeHolderBuilder<P> end() {
			return AttributeHolderBuilder.this;
		}
	}
	
	public Attributes$$$builder attributes$list() {
		verifyMutable();
		if (this.attributes == null) {
			this.attributes = new ArrayList<AttributeBuilder<?>>();
		}
		return new Attributes$$$builder();
	}

    public AttributeHolderBuilder<P> attributes$wrap(Attribute ... attributes) {
    	return attributes$wrap(new ListBuilder<Attribute>().add(attributes).toList());
    }

    public AttributeHolderBuilder<P> attributes$wrap(Collection<Attribute> attributes) {
		verifyMutable();

		if (this.attributes == null) {
			this.attributes = new ArrayList<AttributeBuilder<?>>();
		}
		if (attributes != null) {
			for (Attribute e : attributes) {
				AttributeBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(AttributeBuilder.class);
				this.attributes.add(wrapped);
			}
		}
		return this;
    }
    
    public AttributeHolderBuilder<P> attributes$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return attributes$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public AttributeHolderBuilder<P> attributes$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
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
	    						AttributeHolderBuilder.this.attributes.add((AttributeBuilder<?>)arguments[0]);
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
	                this.attributes.add((AttributeBuilder<?>)restoredObject);
	            }
	    	}
		}
        return this;
    }
}

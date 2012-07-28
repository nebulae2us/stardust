package org.nebulae2us.stardust.my.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.db.domain.ColumnBuilder;
import org.nebulae2us.stardust.internal.util.*;

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
		if (wrapped == null) {
			throw new NullPointerException();
		}
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
    	return new Converter(new DestinationClassResolverByAnnotation(), true, Builders.IGNORED_TYPES).convert(this).to(AttributeHolder.class);
    }



	private Class<?> declaringClass;
	
	public Class<?> getDeclaringClass() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.declaringClass, Class.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, AttributeHolder.class, "declaringClass");
			this.declaringClass = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(o).to(Class.class);
		}

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
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.attributes, List.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, AttributeHolder.class, "attributes");
			this.attributes = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(o).to(List.class);
		}

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
				CollectionUtils.addItem(this.attributes, e);
			}
		}
		return this;
	}

	public AttributeBuilder<? extends AttributeHolderBuilder<P>> attributes$addAttribute() {
		verifyMutable();
		if (this.attributes == null) {
			this.attributes = new ArrayList<AttributeBuilder<?>>();
		}
		
		AttributeBuilder<AttributeHolderBuilder<P>> result =
				new AttributeBuilder<AttributeHolderBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributes, result);
		
		return result;
	}
	
	public EntityAttributeBuilder<? extends AttributeHolderBuilder<P>> attributes$addEntityAttribute() {
		verifyMutable();
		if (this.attributes == null) {
			this.attributes = new ArrayList<AttributeBuilder<?>>();
		}
		
		EntityAttributeBuilder<AttributeHolderBuilder<P>> result =
				new EntityAttributeBuilder<AttributeHolderBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributes, result);
		
		return result;
	}
	
	public ScalarAttributeBuilder<? extends AttributeHolderBuilder<P>> attributes$addScalarAttribute() {
		verifyMutable();
		if (this.attributes == null) {
			this.attributes = new ArrayList<AttributeBuilder<?>>();
		}
		
		ScalarAttributeBuilder<AttributeHolderBuilder<P>> result =
				new ScalarAttributeBuilder<AttributeHolderBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributes, result);
		
		return result;
	}
	
	public ValueObjectAttributeBuilder<? extends AttributeHolderBuilder<P>> attributes$addValueObjectAttribute() {
		verifyMutable();
		if (this.attributes == null) {
			this.attributes = new ArrayList<AttributeBuilder<?>>();
		}
		
		ValueObjectAttributeBuilder<AttributeHolderBuilder<P>> result =
				new ValueObjectAttributeBuilder<AttributeHolderBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributes, result);
		
		return result;
	}
	

	public class Attributes$$$builder<P1 extends AttributeHolderBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected Attributes$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public AttributeBuilder<Attributes$$$builder<P1>> attribute$begin() {
			AttributeBuilder<Attributes$$$builder<P1>> result = new AttributeBuilder<Attributes$$$builder<P1>>(this);
			CollectionUtils.addItem(AttributeHolderBuilder.this.attributes, result);
			return result;
		}
		
		public EntityAttributeBuilder<Attributes$$$builder<P1>> entityAttribute$begin() {
			EntityAttributeBuilder<Attributes$$$builder<P1>> result = new EntityAttributeBuilder<Attributes$$$builder<P1>>(this);
			CollectionUtils.addItem(AttributeHolderBuilder.this.attributes, result);
			return result;
		}
		
		public ScalarAttributeBuilder<Attributes$$$builder<P1>> scalarAttribute$begin() {
			ScalarAttributeBuilder<Attributes$$$builder<P1>> result = new ScalarAttributeBuilder<Attributes$$$builder<P1>>(this);
			CollectionUtils.addItem(AttributeHolderBuilder.this.attributes, result);
			return result;
		}
		
		public ValueObjectAttributeBuilder<Attributes$$$builder<P1>> valueObjectAttribute$begin() {
			ValueObjectAttributeBuilder<Attributes$$$builder<P1>> result = new ValueObjectAttributeBuilder<Attributes$$$builder<P1>>(this);
			CollectionUtils.addItem(AttributeHolderBuilder.this.attributes, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public Attributes$$$builder<? extends AttributeHolderBuilder<P>> attributes$list() {
		verifyMutable();
		if (this.attributes == null) {
			this.attributes = new ArrayList<AttributeBuilder<?>>();
		}
		return new Attributes$$$builder<AttributeHolderBuilder<P>>(this);
	}

    public AttributeHolderBuilder<P> attributes$wrap(Attribute ... attributes) {
    	return attributes$wrap(new ListBuilder<Attribute>().add(attributes).toList());
    }

    public AttributeHolderBuilder<P> attributes$wrap(Collection<? extends Attribute> attributes) {
		verifyMutable();

		if (this.attributes == null) {
			this.attributes = new ArrayList<AttributeBuilder<?>>();
		}
		if (attributes != null) {
			for (Attribute e : attributes) {
				AttributeBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(e).to(AttributeBuilder.class);
				CollectionUtils.addItem(this.attributes, wrapped);
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
	    						CollectionUtils.addItem(AttributeHolderBuilder.this.attributes, arguments[0]);
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


    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
    public List<EntityAttributeBuilder<?>> getEntityAttributes() {
		List<EntityAttributeBuilder<?>> result = new ArrayList<EntityAttributeBuilder<?>>();
		
		if (this.getAttributes() != null) {
			for (AttributeBuilder<?> attribute : this.getAttributes()) {
				if (attribute instanceof EntityAttributeBuilder) {
					result.add((EntityAttributeBuilder<?>)attribute);
				}
			}
		}
		
		return result;
    }
    
    public List<ScalarAttributeBuilder<?>> getScalarAttributes() {
		List<ScalarAttributeBuilder<?>> result = new ArrayList<ScalarAttributeBuilder<?>>();

		if (this.getAttributes() != null) {
			for (AttributeBuilder<?> attribute : this.getAttributes()) {
				if (attribute instanceof ScalarAttributeBuilder) {
					result.add((ScalarAttributeBuilder<?>)attribute);
				}
				else if (attribute instanceof ValueObjectAttributeBuilder) {
					ValueObjectAttributeBuilder<?> valueObjectAttribute = (ValueObjectAttributeBuilder<?>)attribute;
					ValueObjectBuilder<?> valueObject = valueObjectAttribute.getValueObject();
					result.addAll(valueObject.getScalarAttributes());
				}
			}
		}
		
		return result;
    	
    }
    
    public List<ColumnBuilder<?>> getColumns() {
		List<ColumnBuilder<?>> result = new ArrayList<ColumnBuilder<?>>();

		if (this.getAttributes() != null) {
			for (AttributeBuilder<?> attribute : this.getAttributes()) {
				if (attribute instanceof ScalarAttributeBuilder) {
					result.add(((ScalarAttributeBuilder<?>)attribute).getColumn());
				}
				else if (attribute instanceof ValueObjectAttributeBuilder) {
					ValueObjectAttributeBuilder<?> valueObjectAttribute = (ValueObjectAttributeBuilder<?>)attribute;
					ValueObjectBuilder<?> valueObject = valueObjectAttribute.getValueObject();
					for (ScalarAttributeBuilder<?> scalarAttribute : valueObject.getScalarAttributes()) {
						result.add(scalarAttribute.getColumn());
						
					}
				}
			}
		}
		
		return result;
    	
    }
    
     
}

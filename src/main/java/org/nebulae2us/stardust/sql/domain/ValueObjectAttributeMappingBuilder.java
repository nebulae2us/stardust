package org.nebulae2us.stardust.sql.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.internal.util.*;
import org.nebulae2us.stardust.my.domain.*;

@Builder(destination=ValueObjectAttributeMapping.class)
public class ValueObjectAttributeMappingBuilder<P> extends AttributeMappingBuilder<P> {

	public ValueObjectAttributeMappingBuilder() {
		super();
	}
	
	public ValueObjectAttributeMappingBuilder(P parentBuilder) {
		super(parentBuilder);
	}

	protected ValueObjectAttributeMappingBuilder(ValueObjectAttributeMapping wrapped) {
		super(wrapped);
	}

	@Override
    public ValueObjectAttributeMappingBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	@Override
	public ValueObjectAttributeMapping getWrappedObject() {
		return (ValueObjectAttributeMapping)this.$$$wrapped;
	}

    public ValueObjectAttributeMapping toValueObjectAttributeMapping() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true, Builders.IGNORED_TYPES).convert(this).to(ValueObjectAttributeMapping.class);
    }
    

	@Override
    public ValueObjectAttributeMapping toAttributeMapping() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true, Builders.IGNORED_TYPES).convert(this).to(ValueObjectAttributeMapping.class);
    }
    


	private List<AttributeMappingBuilder<?>> attributeMappings;
	
	public List<AttributeMappingBuilder<?>> getAttributeMappings() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.attributeMappings, List.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, ValueObjectAttributeMapping.class, "attributeMappings");
			this.attributeMappings = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(o).to(List.class);
		}

		return attributeMappings;
	}

	public void setAttributeMappings(List<AttributeMappingBuilder<?>> attributeMappings) {
		verifyMutable();
		this.attributeMappings = attributeMappings;
	}

	public ValueObjectAttributeMappingBuilder<P> attributeMappings(AttributeMappingBuilder<?> ... attributeMappings) {
		verifyMutable();
		return attributeMappings(new ListBuilder<AttributeMappingBuilder<?>>().add(attributeMappings).toList());
	}
	
	public ValueObjectAttributeMappingBuilder<P> attributeMappings(Collection<AttributeMappingBuilder<?>> attributeMappings) {
		verifyMutable();
		if (this.attributeMappings == null) {
			this.attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		}
		if (attributeMappings != null) {
			for (AttributeMappingBuilder<?> e : attributeMappings) {
				CollectionUtils.addItem(this.attributeMappings, e);
			}
		}
		return this;
	}

	public AttributeMappingBuilder<? extends ValueObjectAttributeMappingBuilder<P>> attributeMappings$addAttributeMapping() {
		verifyMutable();
		if (this.attributeMappings == null) {
			this.attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		}
		
		AttributeMappingBuilder<ValueObjectAttributeMappingBuilder<P>> result =
				new AttributeMappingBuilder<ValueObjectAttributeMappingBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributeMappings, result);
		
		return result;
	}
	
	public EntityAttributeMappingBuilder<? extends ValueObjectAttributeMappingBuilder<P>> attributeMappings$addEntityAttributeMapping() {
		verifyMutable();
		if (this.attributeMappings == null) {
			this.attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		}
		
		EntityAttributeMappingBuilder<ValueObjectAttributeMappingBuilder<P>> result =
				new EntityAttributeMappingBuilder<ValueObjectAttributeMappingBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributeMappings, result);
		
		return result;
	}
	
	public ScalarAttributeMappingBuilder<? extends ValueObjectAttributeMappingBuilder<P>> attributeMappings$addScalarAttributeMapping() {
		verifyMutable();
		if (this.attributeMappings == null) {
			this.attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		}
		
		ScalarAttributeMappingBuilder<ValueObjectAttributeMappingBuilder<P>> result =
				new ScalarAttributeMappingBuilder<ValueObjectAttributeMappingBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributeMappings, result);
		
		return result;
	}
	
	public ValueObjectAttributeMappingBuilder<? extends ValueObjectAttributeMappingBuilder<P>> attributeMappings$addValueObjectAttributeMapping() {
		verifyMutable();
		if (this.attributeMappings == null) {
			this.attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		}
		
		ValueObjectAttributeMappingBuilder<ValueObjectAttributeMappingBuilder<P>> result =
				new ValueObjectAttributeMappingBuilder<ValueObjectAttributeMappingBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributeMappings, result);
		
		return result;
	}
	

	public class AttributeMappings$$$builder<P1 extends ValueObjectAttributeMappingBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected AttributeMappings$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public AttributeMappingBuilder<AttributeMappings$$$builder<P1>> attributeMapping$begin() {
			AttributeMappingBuilder<AttributeMappings$$$builder<P1>> result = new AttributeMappingBuilder<AttributeMappings$$$builder<P1>>(this);
			CollectionUtils.addItem(ValueObjectAttributeMappingBuilder.this.attributeMappings, result);
			return result;
		}
		
		public EntityAttributeMappingBuilder<AttributeMappings$$$builder<P1>> entityAttributeMapping$begin() {
			EntityAttributeMappingBuilder<AttributeMappings$$$builder<P1>> result = new EntityAttributeMappingBuilder<AttributeMappings$$$builder<P1>>(this);
			CollectionUtils.addItem(ValueObjectAttributeMappingBuilder.this.attributeMappings, result);
			return result;
		}
		
		public ScalarAttributeMappingBuilder<AttributeMappings$$$builder<P1>> scalarAttributeMapping$begin() {
			ScalarAttributeMappingBuilder<AttributeMappings$$$builder<P1>> result = new ScalarAttributeMappingBuilder<AttributeMappings$$$builder<P1>>(this);
			CollectionUtils.addItem(ValueObjectAttributeMappingBuilder.this.attributeMappings, result);
			return result;
		}
		
		public ValueObjectAttributeMappingBuilder<AttributeMappings$$$builder<P1>> valueObjectAttributeMapping$begin() {
			ValueObjectAttributeMappingBuilder<AttributeMappings$$$builder<P1>> result = new ValueObjectAttributeMappingBuilder<AttributeMappings$$$builder<P1>>(this);
			CollectionUtils.addItem(ValueObjectAttributeMappingBuilder.this.attributeMappings, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public AttributeMappings$$$builder<? extends ValueObjectAttributeMappingBuilder<P>> attributeMappings$list() {
		verifyMutable();
		if (this.attributeMappings == null) {
			this.attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		}
		return new AttributeMappings$$$builder<ValueObjectAttributeMappingBuilder<P>>(this);
	}

    public ValueObjectAttributeMappingBuilder<P> attributeMappings$wrap(AttributeMapping ... attributeMappings) {
    	return attributeMappings$wrap(new ListBuilder<AttributeMapping>().add(attributeMappings).toList());
    }

    public ValueObjectAttributeMappingBuilder<P> attributeMappings$wrap(Collection<? extends AttributeMapping> attributeMappings) {
		verifyMutable();

		if (this.attributeMappings == null) {
			this.attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		}
		if (attributeMappings != null) {
			for (AttributeMapping e : attributeMappings) {
				AttributeMappingBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(e).to(AttributeMappingBuilder.class);
				CollectionUtils.addItem(this.attributeMappings, wrapped);
			}
		}
		return this;
    }
    
    public ValueObjectAttributeMappingBuilder<P> attributeMappings$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return attributeMappings$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public ValueObjectAttributeMappingBuilder<P> attributeMappings$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		verifyMutable();

		if (this.attributeMappings == null) {
			this.attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		}
		if (builderIds != null) {
	    	for (Object builderId : builderIds) {
	            Object restoredObject = repo.get(builderId);
	            if (restoredObject == null) {
	            	if (repo.isSupportLazy()) {
	            		repo.addObjectStoredListener(builderId, new Procedure() {
	    					public void execute(Object... arguments) {
	    						CollectionUtils.addItem(ValueObjectAttributeMappingBuilder.this.attributeMappings, arguments[0]);
	    					}
	    				});
	            	}
	            	else {
	                    throw new IllegalStateException("Object does not exist with id " + builderId);
	            	}
	            }
	            else if (!(restoredObject instanceof AttributeMappingBuilder)) {
	            	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + AttributeMappingBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
	            }
	            else {
	                CollectionUtils.addItem(this.attributeMappings, restoredObject);
	            }
	    	}
		}
        return this;
    }


	@Override
	public ValueObjectAttributeMappingBuilder<P> attribute(AttributeBuilder<?> attribute) {
		return (ValueObjectAttributeMappingBuilder<P>)super.attribute(attribute);
	}

	@Override
    public ValueObjectAttributeMappingBuilder<P> attribute$wrap(Attribute attribute) {
		return (ValueObjectAttributeMappingBuilder<P>)super.attribute$wrap(attribute);
    }

	@Override
    public ValueObjectAttributeMappingBuilder<P> attribute$restoreFrom(BuilderRepository repo, Object builderId) {
		return (ValueObjectAttributeMappingBuilder<P>)super.attribute$restoreFrom(repo, builderId);
    }

	@SuppressWarnings("unchecked")
	@Override
	public AttributeBuilder<? extends ValueObjectAttributeMappingBuilder<P>> attribute$begin() {
		return (AttributeBuilder<? extends ValueObjectAttributeMappingBuilder<P>>)super.attribute$begin();
	}

	@Override
	public EntityAttributeBuilder<? extends ValueObjectAttributeMappingBuilder<P>> attribute$asEntityAttribute$begin() {
		return (EntityAttributeBuilder<? extends ValueObjectAttributeMappingBuilder<P>>)super.attribute$asEntityAttribute$begin();
	}

	@Override
	public ScalarAttributeBuilder<? extends ValueObjectAttributeMappingBuilder<P>> attribute$asScalarAttribute$begin() {
		return (ScalarAttributeBuilder<? extends ValueObjectAttributeMappingBuilder<P>>)super.attribute$asScalarAttribute$begin();
	}

	@Override
	public ValueObjectAttributeBuilder<? extends ValueObjectAttributeMappingBuilder<P>> attribute$asValueObjectAttribute$begin() {
		return (ValueObjectAttributeBuilder<? extends ValueObjectAttributeMappingBuilder<P>>)super.attribute$asValueObjectAttribute$begin();
	}

    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

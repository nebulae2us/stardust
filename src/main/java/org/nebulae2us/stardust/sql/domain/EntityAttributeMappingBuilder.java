package org.nebulae2us.stardust.sql.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;
import org.nebulae2us.stardust.my.domain.*;

@Builder(destination=EntityAttributeMapping.class)
public class EntityAttributeMappingBuilder<P> extends AttributeMappingBuilder<P> {

	public EntityAttributeMappingBuilder() {
		super();
	}
	
	public EntityAttributeMappingBuilder(P parentBuilder) {
		super(parentBuilder);
	}

	protected EntityAttributeMappingBuilder(EntityAttributeMapping wrapped) {
		super(wrapped);
	}

	@Override
    public EntityAttributeMappingBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	@Override
	public EntityAttributeMapping getWrappedObject() {
		return (EntityAttributeMapping)this.$$$wrapped;
	}

    public EntityAttributeMapping toEntityAttributeMapping() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(EntityAttributeMapping.class);
    }
    

	@Override
    public EntityAttributeMapping toAttributeMapping() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(EntityAttributeMapping.class);
    }
    


	private List<ScalarAttributeMappingBuilder<?>> identifierAttributeMappings;
	
	public List<ScalarAttributeMappingBuilder<?>> getIdentifierAttributeMappings() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.identifierAttributeMappings, List.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, EntityAttributeMapping.class, "identifierAttributeMappings");
			this.identifierAttributeMappings = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(List.class);
		}

		return identifierAttributeMappings;
	}

	public void setIdentifierAttributeMappings(List<ScalarAttributeMappingBuilder<?>> identifierAttributeMappings) {
		verifyMutable();
		this.identifierAttributeMappings = identifierAttributeMappings;
	}

	public EntityAttributeMappingBuilder<P> identifierAttributeMappings(ScalarAttributeMappingBuilder<?> ... identifierAttributeMappings) {
		verifyMutable();
		return identifierAttributeMappings(new ListBuilder<ScalarAttributeMappingBuilder<?>>().add(identifierAttributeMappings).toList());
	}
	
	public EntityAttributeMappingBuilder<P> identifierAttributeMappings(Collection<ScalarAttributeMappingBuilder<?>> identifierAttributeMappings) {
		verifyMutable();
		if (this.identifierAttributeMappings == null) {
			this.identifierAttributeMappings = new ArrayList<ScalarAttributeMappingBuilder<?>>();
		}
		if (identifierAttributeMappings != null) {
			for (ScalarAttributeMappingBuilder<?> e : identifierAttributeMappings) {
				CollectionUtils.addItem(this.identifierAttributeMappings, e);
			}
		}
		return this;
	}

	public ScalarAttributeMappingBuilder<? extends EntityAttributeMappingBuilder<P>> identifierAttributeMappings$addScalarAttributeMapping() {
		verifyMutable();
		if (this.identifierAttributeMappings == null) {
			this.identifierAttributeMappings = new ArrayList<ScalarAttributeMappingBuilder<?>>();
		}
		
		ScalarAttributeMappingBuilder<EntityAttributeMappingBuilder<P>> result =
				new ScalarAttributeMappingBuilder<EntityAttributeMappingBuilder<P>>(this);
		
		CollectionUtils.addItem(this.identifierAttributeMappings, result);
		
		return result;
	}
	

	public class IdentifierAttributeMappings$$$builder<P1 extends EntityAttributeMappingBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected IdentifierAttributeMappings$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public ScalarAttributeMappingBuilder<IdentifierAttributeMappings$$$builder<P1>> scalarAttributeMapping$begin() {
			ScalarAttributeMappingBuilder<IdentifierAttributeMappings$$$builder<P1>> result = new ScalarAttributeMappingBuilder<IdentifierAttributeMappings$$$builder<P1>>(this);
			CollectionUtils.addItem(EntityAttributeMappingBuilder.this.identifierAttributeMappings, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public IdentifierAttributeMappings$$$builder<? extends EntityAttributeMappingBuilder<P>> identifierAttributeMappings$list() {
		verifyMutable();
		if (this.identifierAttributeMappings == null) {
			this.identifierAttributeMappings = new ArrayList<ScalarAttributeMappingBuilder<?>>();
		}
		return new IdentifierAttributeMappings$$$builder<EntityAttributeMappingBuilder<P>>(this);
	}

    public EntityAttributeMappingBuilder<P> identifierAttributeMappings$wrap(ScalarAttributeMapping ... identifierAttributeMappings) {
    	return identifierAttributeMappings$wrap(new ListBuilder<ScalarAttributeMapping>().add(identifierAttributeMappings).toList());
    }

    public EntityAttributeMappingBuilder<P> identifierAttributeMappings$wrap(Collection<? extends ScalarAttributeMapping> identifierAttributeMappings) {
		verifyMutable();

		if (this.identifierAttributeMappings == null) {
			this.identifierAttributeMappings = new ArrayList<ScalarAttributeMappingBuilder<?>>();
		}
		if (identifierAttributeMappings != null) {
			for (ScalarAttributeMapping e : identifierAttributeMappings) {
				ScalarAttributeMappingBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(ScalarAttributeMappingBuilder.class);
				CollectionUtils.addItem(this.identifierAttributeMappings, wrapped);
			}
		}
		return this;
    }
    
    public EntityAttributeMappingBuilder<P> identifierAttributeMappings$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return identifierAttributeMappings$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public EntityAttributeMappingBuilder<P> identifierAttributeMappings$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		verifyMutable();

		if (this.identifierAttributeMappings == null) {
			this.identifierAttributeMappings = new ArrayList<ScalarAttributeMappingBuilder<?>>();
		}
		if (builderIds != null) {
	    	for (Object builderId : builderIds) {
	            Object restoredObject = repo.get(builderId);
	            if (restoredObject == null) {
	            	if (repo.isSupportLazy()) {
	            		repo.addObjectStoredListener(builderId, new Procedure() {
	    					public void execute(Object... arguments) {
	    						CollectionUtils.addItem(EntityAttributeMappingBuilder.this.identifierAttributeMappings, arguments[0]);
	    					}
	    				});
	            	}
	            	else {
	                    throw new IllegalStateException("Object does not exist with id " + builderId);
	            	}
	            }
	            else if (!(restoredObject instanceof ScalarAttributeMappingBuilder)) {
	            	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + ScalarAttributeMappingBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
	            }
	            else {
	                CollectionUtils.addItem(this.identifierAttributeMappings, restoredObject);
	            }
	    	}
		}
        return this;
    }


	private List<AttributeMappingBuilder<?>> attributeMappings;
	
	public List<AttributeMappingBuilder<?>> getAttributeMappings() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.attributeMappings, List.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, EntityAttributeMapping.class, "attributeMappings");
			this.attributeMappings = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(List.class);
		}

		return attributeMappings;
	}

	public void setAttributeMappings(List<AttributeMappingBuilder<?>> attributeMappings) {
		verifyMutable();
		this.attributeMappings = attributeMappings;
	}

	public EntityAttributeMappingBuilder<P> attributeMappings(AttributeMappingBuilder<?> ... attributeMappings) {
		verifyMutable();
		return attributeMappings(new ListBuilder<AttributeMappingBuilder<?>>().add(attributeMappings).toList());
	}
	
	public EntityAttributeMappingBuilder<P> attributeMappings(Collection<AttributeMappingBuilder<?>> attributeMappings) {
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

	public AttributeMappingBuilder<? extends EntityAttributeMappingBuilder<P>> attributeMappings$addAttributeMapping() {
		verifyMutable();
		if (this.attributeMappings == null) {
			this.attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		}
		
		AttributeMappingBuilder<EntityAttributeMappingBuilder<P>> result =
				new AttributeMappingBuilder<EntityAttributeMappingBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributeMappings, result);
		
		return result;
	}
	
	public EntityAttributeMappingBuilder<? extends EntityAttributeMappingBuilder<P>> attributeMappings$addEntityAttributeMapping() {
		verifyMutable();
		if (this.attributeMappings == null) {
			this.attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		}
		
		EntityAttributeMappingBuilder<EntityAttributeMappingBuilder<P>> result =
				new EntityAttributeMappingBuilder<EntityAttributeMappingBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributeMappings, result);
		
		return result;
	}
	
	public ScalarAttributeMappingBuilder<? extends EntityAttributeMappingBuilder<P>> attributeMappings$addScalarAttributeMapping() {
		verifyMutable();
		if (this.attributeMappings == null) {
			this.attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		}
		
		ScalarAttributeMappingBuilder<EntityAttributeMappingBuilder<P>> result =
				new ScalarAttributeMappingBuilder<EntityAttributeMappingBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributeMappings, result);
		
		return result;
	}
	
	public ValueObjectAttributeMappingBuilder<? extends EntityAttributeMappingBuilder<P>> attributeMappings$addValueObjectAttributeMapping() {
		verifyMutable();
		if (this.attributeMappings == null) {
			this.attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		}
		
		ValueObjectAttributeMappingBuilder<EntityAttributeMappingBuilder<P>> result =
				new ValueObjectAttributeMappingBuilder<EntityAttributeMappingBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributeMappings, result);
		
		return result;
	}
	

	public class AttributeMappings$$$builder<P1 extends EntityAttributeMappingBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected AttributeMappings$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public AttributeMappingBuilder<AttributeMappings$$$builder<P1>> attributeMapping$begin() {
			AttributeMappingBuilder<AttributeMappings$$$builder<P1>> result = new AttributeMappingBuilder<AttributeMappings$$$builder<P1>>(this);
			CollectionUtils.addItem(EntityAttributeMappingBuilder.this.attributeMappings, result);
			return result;
		}
		
		public EntityAttributeMappingBuilder<AttributeMappings$$$builder<P1>> entityAttributeMapping$begin() {
			EntityAttributeMappingBuilder<AttributeMappings$$$builder<P1>> result = new EntityAttributeMappingBuilder<AttributeMappings$$$builder<P1>>(this);
			CollectionUtils.addItem(EntityAttributeMappingBuilder.this.attributeMappings, result);
			return result;
		}
		
		public ScalarAttributeMappingBuilder<AttributeMappings$$$builder<P1>> scalarAttributeMapping$begin() {
			ScalarAttributeMappingBuilder<AttributeMappings$$$builder<P1>> result = new ScalarAttributeMappingBuilder<AttributeMappings$$$builder<P1>>(this);
			CollectionUtils.addItem(EntityAttributeMappingBuilder.this.attributeMappings, result);
			return result;
		}
		
		public ValueObjectAttributeMappingBuilder<AttributeMappings$$$builder<P1>> valueObjectAttributeMapping$begin() {
			ValueObjectAttributeMappingBuilder<AttributeMappings$$$builder<P1>> result = new ValueObjectAttributeMappingBuilder<AttributeMappings$$$builder<P1>>(this);
			CollectionUtils.addItem(EntityAttributeMappingBuilder.this.attributeMappings, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public AttributeMappings$$$builder<? extends EntityAttributeMappingBuilder<P>> attributeMappings$list() {
		verifyMutable();
		if (this.attributeMappings == null) {
			this.attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		}
		return new AttributeMappings$$$builder<EntityAttributeMappingBuilder<P>>(this);
	}

    public EntityAttributeMappingBuilder<P> attributeMappings$wrap(AttributeMapping ... attributeMappings) {
    	return attributeMappings$wrap(new ListBuilder<AttributeMapping>().add(attributeMappings).toList());
    }

    public EntityAttributeMappingBuilder<P> attributeMappings$wrap(Collection<? extends AttributeMapping> attributeMappings) {
		verifyMutable();

		if (this.attributeMappings == null) {
			this.attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		}
		if (attributeMappings != null) {
			for (AttributeMapping e : attributeMappings) {
				AttributeMappingBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(AttributeMappingBuilder.class);
				CollectionUtils.addItem(this.attributeMappings, wrapped);
			}
		}
		return this;
    }
    
    public EntityAttributeMappingBuilder<P> attributeMappings$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return attributeMappings$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public EntityAttributeMappingBuilder<P> attributeMappings$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
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
	    						CollectionUtils.addItem(EntityAttributeMappingBuilder.this.attributeMappings, arguments[0]);
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
	public EntityAttributeMappingBuilder<P> attribute(AttributeBuilder<?> attribute) {
		return (EntityAttributeMappingBuilder<P>)super.attribute(attribute);
	}

	@Override
    public EntityAttributeMappingBuilder<P> attribute$wrap(Attribute attribute) {
		return (EntityAttributeMappingBuilder<P>)super.attribute$wrap(attribute);
    }

	@Override
    public EntityAttributeMappingBuilder<P> attribute$restoreFrom(BuilderRepository repo, Object builderId) {
		return (EntityAttributeMappingBuilder<P>)super.attribute$restoreFrom(repo, builderId);
    }

	@SuppressWarnings("unchecked")
	@Override
	public AttributeBuilder<? extends EntityAttributeMappingBuilder<P>> attribute$begin() {
		return (AttributeBuilder<? extends EntityAttributeMappingBuilder<P>>)super.attribute$begin();
	}

	@Override
	public EntityAttributeBuilder<? extends EntityAttributeMappingBuilder<P>> attribute$asEntityAttribute$begin() {
		return (EntityAttributeBuilder<? extends EntityAttributeMappingBuilder<P>>)super.attribute$asEntityAttribute$begin();
	}

	@Override
	public ScalarAttributeBuilder<? extends EntityAttributeMappingBuilder<P>> attribute$asScalarAttribute$begin() {
		return (ScalarAttributeBuilder<? extends EntityAttributeMappingBuilder<P>>)super.attribute$asScalarAttribute$begin();
	}

	@Override
	public ValueObjectAttributeBuilder<? extends EntityAttributeMappingBuilder<P>> attribute$asValueObjectAttribute$begin() {
		return (ValueObjectAttributeBuilder<? extends EntityAttributeMappingBuilder<P>>)super.attribute$asValueObjectAttribute$begin();
	}

    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

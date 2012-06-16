package org.nebulae2us.stardust.sql.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;
import org.nebulae2us.stardust.internal.util.Builders;
import org.nebulae2us.stardust.my.domain.*;

@Builder(destination=EntityMapping.class)
public class EntityMappingBuilder<P> implements Wrappable<EntityMapping> {

	protected final EntityMapping $$$wrapped;

	protected final P $$$parentBuilder;
	
	public EntityMappingBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public EntityMappingBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected EntityMappingBuilder(EntityMapping wrapped) {
		if (wrapped == null) {
			throw new NullPointerException();
		}
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public EntityMappingBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public EntityMapping getWrappedObject() {
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

    public EntityMapping toEntityMapping() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(EntityMapping.class);
    }



	private EntityBuilder<?> entity;
	
	public EntityBuilder<?> getEntity() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.entity, EntityBuilder.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, EntityMapping.class, "entity");
			this.entity = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(EntityBuilder.class);
		}

		return entity;
	}

	public void setEntity(EntityBuilder<?> entity) {
		verifyMutable();
		this.entity = entity;
	}

	public EntityMappingBuilder<P> entity(EntityBuilder<?> entity) {
		verifyMutable();
		this.entity = entity;
		return this;
	}

    public EntityMappingBuilder<P> entity$wrap(Entity entity) {
    	verifyMutable();
    	this.entity = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(entity).to(EntityBuilder.class);
        return this;
    }
    
    public EntityMappingBuilder<P> entity$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						EntityMappingBuilder.this.entity = (EntityBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else if (!(restoredObject instanceof EntityBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + EntityBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.entity = (EntityBuilder<?>)restoredObject;
        }
        return this;
    }

	public EntityBuilder<? extends EntityMappingBuilder<P>> entity$begin() {
		verifyMutable();
		EntityBuilder<EntityMappingBuilder<P>> result = new EntityBuilder<EntityMappingBuilder<P>>(this);
		this.entity = result;
		return result;
	}

	private int discriminatorColumnIndex;
	
	public int getDiscriminatorColumnIndex() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.discriminatorColumnIndex, int.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, EntityMapping.class, "discriminatorColumnIndex");
			this.discriminatorColumnIndex = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(int.class);
		}

		return discriminatorColumnIndex;
	}

	public void setDiscriminatorColumnIndex(int discriminatorColumnIndex) {
		verifyMutable();
		this.discriminatorColumnIndex = discriminatorColumnIndex;
	}

	public EntityMappingBuilder<P> discriminatorColumnIndex(int discriminatorColumnIndex) {
		verifyMutable();
		this.discriminatorColumnIndex = discriminatorColumnIndex;
		return this;
	}

	private List<ScalarAttributeMappingBuilder<?>> identifierAttributeMappings;
	
	public List<ScalarAttributeMappingBuilder<?>> getIdentifierAttributeMappings() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.identifierAttributeMappings, List.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, EntityMapping.class, "identifierAttributeMappings");
			this.identifierAttributeMappings = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(List.class);
		}

		return identifierAttributeMappings;
	}

	public void setIdentifierAttributeMappings(List<ScalarAttributeMappingBuilder<?>> identifierAttributeMappings) {
		verifyMutable();
		this.identifierAttributeMappings = identifierAttributeMappings;
	}

	public EntityMappingBuilder<P> identifierAttributeMappings(ScalarAttributeMappingBuilder<?> ... identifierAttributeMappings) {
		verifyMutable();
		return identifierAttributeMappings(new ListBuilder<ScalarAttributeMappingBuilder<?>>().add(identifierAttributeMappings).toList());
	}
	
	public EntityMappingBuilder<P> identifierAttributeMappings(Collection<ScalarAttributeMappingBuilder<?>> identifierAttributeMappings) {
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

	public ScalarAttributeMappingBuilder<? extends EntityMappingBuilder<P>> identifierAttributeMappings$addScalarAttributeMapping() {
		verifyMutable();
		if (this.identifierAttributeMappings == null) {
			this.identifierAttributeMappings = new ArrayList<ScalarAttributeMappingBuilder<?>>();
		}
		
		ScalarAttributeMappingBuilder<EntityMappingBuilder<P>> result =
				new ScalarAttributeMappingBuilder<EntityMappingBuilder<P>>(this);
		
		CollectionUtils.addItem(this.identifierAttributeMappings, result);
		
		return result;
	}
	

	public class IdentifierAttributeMappings$$$builder<P1 extends EntityMappingBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected IdentifierAttributeMappings$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public ScalarAttributeMappingBuilder<IdentifierAttributeMappings$$$builder<P1>> scalarAttributeMapping$begin() {
			ScalarAttributeMappingBuilder<IdentifierAttributeMappings$$$builder<P1>> result = new ScalarAttributeMappingBuilder<IdentifierAttributeMappings$$$builder<P1>>(this);
			CollectionUtils.addItem(EntityMappingBuilder.this.identifierAttributeMappings, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public IdentifierAttributeMappings$$$builder<? extends EntityMappingBuilder<P>> identifierAttributeMappings$list() {
		verifyMutable();
		if (this.identifierAttributeMappings == null) {
			this.identifierAttributeMappings = new ArrayList<ScalarAttributeMappingBuilder<?>>();
		}
		return new IdentifierAttributeMappings$$$builder<EntityMappingBuilder<P>>(this);
	}

    public EntityMappingBuilder<P> identifierAttributeMappings$wrap(ScalarAttributeMapping ... identifierAttributeMappings) {
    	return identifierAttributeMappings$wrap(new ListBuilder<ScalarAttributeMapping>().add(identifierAttributeMappings).toList());
    }

    public EntityMappingBuilder<P> identifierAttributeMappings$wrap(Collection<? extends ScalarAttributeMapping> identifierAttributeMappings) {
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
    
    public EntityMappingBuilder<P> identifierAttributeMappings$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return identifierAttributeMappings$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public EntityMappingBuilder<P> identifierAttributeMappings$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
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
	    						CollectionUtils.addItem(EntityMappingBuilder.this.identifierAttributeMappings, arguments[0]);
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
			Object o = WrapHelper.getValue(this.$$$wrapped, EntityMapping.class, "attributeMappings");
			this.attributeMappings = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(List.class);
		}

		return attributeMappings;
	}

	public void setAttributeMappings(List<AttributeMappingBuilder<?>> attributeMappings) {
		verifyMutable();
		this.attributeMappings = attributeMappings;
	}

	public EntityMappingBuilder<P> attributeMappings(AttributeMappingBuilder<?> ... attributeMappings) {
		verifyMutable();
		return attributeMappings(new ListBuilder<AttributeMappingBuilder<?>>().add(attributeMappings).toList());
	}
	
	public EntityMappingBuilder<P> attributeMappings(Collection<AttributeMappingBuilder<?>> attributeMappings) {
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

	public AttributeMappingBuilder<? extends EntityMappingBuilder<P>> attributeMappings$addAttributeMapping() {
		verifyMutable();
		if (this.attributeMappings == null) {
			this.attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		}
		
		AttributeMappingBuilder<EntityMappingBuilder<P>> result =
				new AttributeMappingBuilder<EntityMappingBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributeMappings, result);
		
		return result;
	}
	
	public EntityAttributeMappingBuilder<? extends EntityMappingBuilder<P>> attributeMappings$addEntityAttributeMapping() {
		verifyMutable();
		if (this.attributeMappings == null) {
			this.attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		}
		
		EntityAttributeMappingBuilder<EntityMappingBuilder<P>> result =
				new EntityAttributeMappingBuilder<EntityMappingBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributeMappings, result);
		
		return result;
	}
	
	public ScalarAttributeMappingBuilder<? extends EntityMappingBuilder<P>> attributeMappings$addScalarAttributeMapping() {
		verifyMutable();
		if (this.attributeMappings == null) {
			this.attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		}
		
		ScalarAttributeMappingBuilder<EntityMappingBuilder<P>> result =
				new ScalarAttributeMappingBuilder<EntityMappingBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributeMappings, result);
		
		return result;
	}
	
	public ValueObjectAttributeMappingBuilder<? extends EntityMappingBuilder<P>> attributeMappings$addValueObjectAttributeMapping() {
		verifyMutable();
		if (this.attributeMappings == null) {
			this.attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		}
		
		ValueObjectAttributeMappingBuilder<EntityMappingBuilder<P>> result =
				new ValueObjectAttributeMappingBuilder<EntityMappingBuilder<P>>(this);
		
		CollectionUtils.addItem(this.attributeMappings, result);
		
		return result;
	}
	

	public class AttributeMappings$$$builder<P1 extends EntityMappingBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected AttributeMappings$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public AttributeMappingBuilder<AttributeMappings$$$builder<P1>> attributeMapping$begin() {
			AttributeMappingBuilder<AttributeMappings$$$builder<P1>> result = new AttributeMappingBuilder<AttributeMappings$$$builder<P1>>(this);
			CollectionUtils.addItem(EntityMappingBuilder.this.attributeMappings, result);
			return result;
		}
		
		public EntityAttributeMappingBuilder<AttributeMappings$$$builder<P1>> entityAttributeMapping$begin() {
			EntityAttributeMappingBuilder<AttributeMappings$$$builder<P1>> result = new EntityAttributeMappingBuilder<AttributeMappings$$$builder<P1>>(this);
			CollectionUtils.addItem(EntityMappingBuilder.this.attributeMappings, result);
			return result;
		}
		
		public ScalarAttributeMappingBuilder<AttributeMappings$$$builder<P1>> scalarAttributeMapping$begin() {
			ScalarAttributeMappingBuilder<AttributeMappings$$$builder<P1>> result = new ScalarAttributeMappingBuilder<AttributeMappings$$$builder<P1>>(this);
			CollectionUtils.addItem(EntityMappingBuilder.this.attributeMappings, result);
			return result;
		}
		
		public ValueObjectAttributeMappingBuilder<AttributeMappings$$$builder<P1>> valueObjectAttributeMapping$begin() {
			ValueObjectAttributeMappingBuilder<AttributeMappings$$$builder<P1>> result = new ValueObjectAttributeMappingBuilder<AttributeMappings$$$builder<P1>>(this);
			CollectionUtils.addItem(EntityMappingBuilder.this.attributeMappings, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public AttributeMappings$$$builder<? extends EntityMappingBuilder<P>> attributeMappings$list() {
		verifyMutable();
		if (this.attributeMappings == null) {
			this.attributeMappings = new ArrayList<AttributeMappingBuilder<?>>();
		}
		return new AttributeMappings$$$builder<EntityMappingBuilder<P>>(this);
	}

    public EntityMappingBuilder<P> attributeMappings$wrap(AttributeMapping ... attributeMappings) {
    	return attributeMappings$wrap(new ListBuilder<AttributeMapping>().add(attributeMappings).toList());
    }

    public EntityMappingBuilder<P> attributeMappings$wrap(Collection<? extends AttributeMapping> attributeMappings) {
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
    
    public EntityMappingBuilder<P> attributeMappings$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return attributeMappings$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public EntityMappingBuilder<P> attributeMappings$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
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
	    						CollectionUtils.addItem(EntityMappingBuilder.this.attributeMappings, arguments[0]);
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


    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

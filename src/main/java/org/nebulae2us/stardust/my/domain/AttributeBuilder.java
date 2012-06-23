package org.nebulae2us.stardust.my.domain;

import java.lang.reflect.*;
import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.internal.util.*;

@Builder(destination=Attribute.class)
public class AttributeBuilder<P> implements Wrappable<Attribute> {

	protected final Attribute $$$wrapped;

	protected final P $$$parentBuilder;
	
	public AttributeBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public AttributeBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected AttributeBuilder(Attribute wrapped) {
		if (wrapped == null) {
			throw new NullPointerException();
		}
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public AttributeBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public Attribute getWrappedObject() {
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

    public Attribute toAttribute() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true, Builders.IGNORED_TYPES).convert(this).to(Attribute.class);
    }



	private String fullName;
	
	public String getFullName() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.fullName, String.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, Attribute.class, "fullName");
			this.fullName = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(o).to(String.class);
		}

		return fullName;
	}

	public void setFullName(String fullName) {
		verifyMutable();
		this.fullName = fullName;
	}

	public AttributeBuilder<P> fullName(String fullName) {
		verifyMutable();
		this.fullName = fullName;
		return this;
	}

	private Field field;
	
	public Field getField() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.field, Field.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, Attribute.class, "field");
			this.field = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(o).to(Field.class);
		}

		return field;
	}

	public void setField(Field field) {
		verifyMutable();
		this.field = field;
	}

	public AttributeBuilder<P> field(Field field) {
		verifyMutable();
		this.field = field;
		return this;
	}

	private EntityBuilder<?> owningEntity;
	
	public EntityBuilder<?> getOwningEntity() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.owningEntity, EntityBuilder.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, Attribute.class, "owningEntity");
			this.owningEntity = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(o).to(EntityBuilder.class);
		}

		return owningEntity;
	}

	public void setOwningEntity(EntityBuilder<?> owningEntity) {
		verifyMutable();
		this.owningEntity = owningEntity;
	}

	public AttributeBuilder<P> owningEntity(EntityBuilder<?> owningEntity) {
		verifyMutable();
		this.owningEntity = owningEntity;
		return this;
	}

    public AttributeBuilder<P> owningEntity$wrap(Entity owningEntity) {
    	verifyMutable();
    	this.owningEntity = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(owningEntity).to(EntityBuilder.class);
        return this;
    }
    
    public AttributeBuilder<P> owningEntity$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
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
        else if (!(restoredObject instanceof EntityBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + EntityBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.owningEntity = (EntityBuilder<?>)restoredObject;
        }
        return this;
    }

	public EntityBuilder<? extends AttributeBuilder<P>> owningEntity$begin() {
		verifyMutable();
		EntityBuilder<AttributeBuilder<P>> result = new EntityBuilder<AttributeBuilder<P>>(this);
		this.owningEntity = result;
		return result;
	}

	private boolean insertable;
	
	public boolean getInsertable() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.insertable, boolean.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, Attribute.class, "insertable");
			this.insertable = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(o).to(boolean.class);
		}

		return insertable;
	}

	public void setInsertable(boolean insertable) {
		verifyMutable();
		this.insertable = insertable;
	}

	public AttributeBuilder<P> insertable(boolean insertable) {
		verifyMutable();
		this.insertable = insertable;
		return this;
	}

	private boolean updatable;
	
	public boolean getUpdatable() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.updatable, boolean.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, Attribute.class, "updatable");
			this.updatable = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(o).to(boolean.class);
		}

		return updatable;
	}

	public void setUpdatable(boolean updatable) {
		verifyMutable();
		this.updatable = updatable;
	}

	public AttributeBuilder<P> updatable(boolean updatable) {
		verifyMutable();
		this.updatable = updatable;
		return this;
	}

	private boolean nullable;
	
	public boolean getNullable() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.nullable, boolean.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, Attribute.class, "nullable");
			this.nullable = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(o).to(boolean.class);
		}

		return nullable;
	}

	public void setNullable(boolean nullable) {
		verifyMutable();
		this.nullable = nullable;
	}

	public AttributeBuilder<P> nullable(boolean nullable) {
		verifyMutable();
		this.nullable = nullable;
		return this;
	}

	private FetchType fetchType;
	
	public FetchType getFetchType() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.fetchType, FetchType.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, Attribute.class, "fetchType");
			this.fetchType = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(o).to(FetchType.class);
		}

		return fetchType;
	}

	public void setFetchType(FetchType fetchType) {
		verifyMutable();
		this.fetchType = fetchType;
	}

	public AttributeBuilder<P> fetchType(FetchType fetchType) {
		verifyMutable();
		this.fetchType = fetchType;
		return this;
	}

    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     public String getName() {
    	 return this.field.getName();
     }
     
}

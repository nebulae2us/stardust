package org.nebulae2us.stardust.sql.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.db.domain.*;
import org.nebulae2us.stardust.internal.util.*;
import org.nebulae2us.stardust.my.domain.*;

@Builder(destination=LinkedEntity.class)
public class LinkedEntityBuilder<P> implements Wrappable<LinkedEntity> {

	protected final LinkedEntity $$$wrapped;

	protected final P $$$parentBuilder;
	
	public LinkedEntityBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public LinkedEntityBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected LinkedEntityBuilder(LinkedEntity wrapped) {
		if (wrapped == null) {
			throw new NullPointerException();
		}
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public LinkedEntityBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public LinkedEntity getWrappedObject() {
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

    public LinkedEntity toLinkedEntity() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true, Builders.IGNORED_TYPES).convert(this).to(LinkedEntity.class);
    }



	private LinkedEntityBuilder<?> parent;
	
	public LinkedEntityBuilder<?> getParent() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.parent, LinkedEntityBuilder.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, LinkedEntity.class, "parent");
			this.parent = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(o).to(LinkedEntityBuilder.class);
		}

		return parent;
	}

	public void setParent(LinkedEntityBuilder<?> parent) {
		verifyMutable();
		this.parent = parent;
	}

	public LinkedEntityBuilder<P> parent(LinkedEntityBuilder<?> parent) {
		verifyMutable();
		this.parent = parent;
		return this;
	}

    public LinkedEntityBuilder<P> parent$wrap(LinkedEntity parent) {
    	verifyMutable();
    	this.parent = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(parent).to(LinkedEntityBuilder.class);
        return this;
    }
    
    public LinkedEntityBuilder<P> parent$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						LinkedEntityBuilder.this.parent = (LinkedEntityBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else if (!(restoredObject instanceof LinkedEntityBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + LinkedEntityBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.parent = (LinkedEntityBuilder<?>)restoredObject;
        }
        return this;
    }

	public LinkedEntityBuilder<? extends LinkedEntityBuilder<P>> parent$begin() {
		verifyMutable();
		LinkedEntityBuilder<LinkedEntityBuilder<P>> result = new LinkedEntityBuilder<LinkedEntityBuilder<P>>(this);
		this.parent = result;
		return result;
	}

	private EntityBuilder<?> entity;
	
	public EntityBuilder<?> getEntity() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.entity, EntityBuilder.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, LinkedEntity.class, "entity");
			this.entity = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(o).to(EntityBuilder.class);
		}

		return entity;
	}

	public void setEntity(EntityBuilder<?> entity) {
		verifyMutable();
		this.entity = entity;
	}

	public LinkedEntityBuilder<P> entity(EntityBuilder<?> entity) {
		verifyMutable();
		this.entity = entity;
		return this;
	}

    public LinkedEntityBuilder<P> entity$wrap(Entity entity) {
    	verifyMutable();
    	this.entity = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(entity).to(EntityBuilder.class);
        return this;
    }
    
    public LinkedEntityBuilder<P> entity$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						LinkedEntityBuilder.this.entity = (EntityBuilder<?>)arguments[0];
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

	public EntityBuilder<? extends LinkedEntityBuilder<P>> entity$begin() {
		verifyMutable();
		EntityBuilder<LinkedEntityBuilder<P>> result = new EntityBuilder<LinkedEntityBuilder<P>>(this);
		this.entity = result;
		return result;
	}

	private String alias;
	
	public String getAlias() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.alias, String.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, LinkedEntity.class, "alias");
			this.alias = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(o).to(String.class);
		}

		return alias;
	}

	public void setAlias(String alias) {
		verifyMutable();
		this.alias = alias;
	}

	public LinkedEntityBuilder<P> alias(String alias) {
		verifyMutable();
		this.alias = alias;
		return this;
	}

	private EntityAttributeBuilder<?> attribute;
	
	public EntityAttributeBuilder<?> getAttribute() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.attribute, EntityAttributeBuilder.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, LinkedEntity.class, "attribute");
			this.attribute = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(o).to(EntityAttributeBuilder.class);
		}

		return attribute;
	}

	public void setAttribute(EntityAttributeBuilder<?> attribute) {
		verifyMutable();
		this.attribute = attribute;
	}

	public LinkedEntityBuilder<P> attribute(EntityAttributeBuilder<?> attribute) {
		verifyMutable();
		this.attribute = attribute;
		return this;
	}

    public LinkedEntityBuilder<P> attribute$wrap(EntityAttribute attribute) {
    	verifyMutable();
    	this.attribute = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(attribute).to(EntityAttributeBuilder.class);
        return this;
    }
    
    public LinkedEntityBuilder<P> attribute$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						LinkedEntityBuilder.this.attribute = (EntityAttributeBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else if (!(restoredObject instanceof EntityAttributeBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + EntityAttributeBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.attribute = (EntityAttributeBuilder<?>)restoredObject;
        }
        return this;
    }

	public EntityAttributeBuilder<? extends LinkedEntityBuilder<P>> attribute$begin() {
		verifyMutable();
		EntityAttributeBuilder<LinkedEntityBuilder<P>> result = new EntityAttributeBuilder<LinkedEntityBuilder<P>>(this);
		this.attribute = result;
		return result;
	}

	private JoinType joinType;
	
	public JoinType getJoinType() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.joinType, JoinType.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, LinkedEntity.class, "joinType");
			this.joinType = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER, Builders.IGNORED_TYPES).convert(o).to(JoinType.class);
		}

		return joinType;
	}

	public void setJoinType(JoinType joinType) {
		verifyMutable();
		this.joinType = joinType;
	}

	public LinkedEntityBuilder<P> joinType(JoinType joinType) {
		verifyMutable();
		this.joinType = joinType;
		return this;
	}

    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

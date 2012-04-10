package org.nebulae2us.stardust.sql.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;
import org.nebulae2us.stardust.db.domain.*;
import org.nebulae2us.stardust.my.domain.*;

@Builder(destination=EntityJoin.class)
public class EntityJoinBuilder<P> implements Wrappable<EntityJoin> {

	protected final EntityJoin $$$wrapped;

	protected final P $$$parentBuilder;
	
	public EntityJoinBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public EntityJoinBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected EntityJoinBuilder(EntityJoin wrapped) {
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public EntityJoinBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public EntityJoin getWrappedObject() {
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

    public EntityJoin toEntityJoin() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(EntityJoin.class);
    }



	private EntityAttributeBuilder<?> attribute;
	
	public EntityAttributeBuilder<?> getAttribute() {
		return attribute;
	}

	public void setAttribute(EntityAttributeBuilder<?> attribute) {
		verifyMutable();
		this.attribute = attribute;
	}

	public EntityJoinBuilder<P> attribute(EntityAttributeBuilder<?> attribute) {
		verifyMutable();
		this.attribute = attribute;
		return this;
	}

    public EntityJoinBuilder<P> attribute$wrap(EntityAttribute attribute) {
    	verifyMutable();
    	this.attribute = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(attribute).to(EntityAttributeBuilder.class);
        return this;
    }
    
    public EntityJoinBuilder<P> attribute$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						EntityJoinBuilder.this.attribute = (EntityAttributeBuilder<?>)arguments[0];
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

	public EntityAttributeBuilder<? extends EntityJoinBuilder<P>> attribute$begin() {
		verifyMutable();
		EntityAttributeBuilder<EntityJoinBuilder<P>> result = new EntityAttributeBuilder<EntityJoinBuilder<P>>(this);
		this.attribute = result;
		return result;
	}

	private String alias;
	
	public String getAlias() {
		return alias;
	}

	public void setAlias(String alias) {
		verifyMutable();
		this.alias = alias;
	}

	public EntityJoinBuilder<P> alias(String alias) {
		verifyMutable();
		this.alias = alias;
		return this;
	}

	private JoinType joinType;
	
	public JoinType getJoinType() {
		return joinType;
	}

	public void setJoinType(JoinType joinType) {
		verifyMutable();
		this.joinType = joinType;
	}

	public EntityJoinBuilder<P> joinType(JoinType joinType) {
		verifyMutable();
		this.joinType = joinType;
		return this;
	}
}

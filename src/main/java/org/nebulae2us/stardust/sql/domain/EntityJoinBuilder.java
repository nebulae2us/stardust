package org.nebulae2us.stardust.sql.domain;

import org.nebulae2us.electron.*;
import org.nebulae2us.stardust.db.domain.*;
import org.nebulae2us.stardust.my.domain.*;


public class EntityJoinBuilder<B> implements Convertable {

	private final EntityJoin $$$savedTarget;

	private ConverterOption $$$option;

	private final B $$$parentBuilder;

	protected EntityJoinBuilder(EntityJoin entityJoin) {
		if (entityJoin == null) {
			throw new NullPointerException();
		}
	
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = entityJoin;
	}

	public EntityJoinBuilder(ConverterOption option, B parentBuilder) {
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = parentBuilder;
		this.$$$savedTarget = null;
	}

	public EntityJoinBuilder() {
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public EntityJoinBuilder(ConverterOption option) {
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public ConverterOption getConverterOption() {
		return this.$$$option;
	}
	
	public void setConverterOption(ConverterOption option) {
		this.$$$option = option;
	}
	
	public EntityJoin getSavedTarget() {
		return this.$$$savedTarget;
	}

	public boolean convertableTo(Class<?> c) {
		return this.$$$savedTarget != null && c.isAssignableFrom(this.$$$savedTarget.getClass());
	}

	@SuppressWarnings("unchecked")
	public <T> T convertTo(Class<T> c) {
		if (!convertableTo(c)) {
			throw new IllegalArgumentException();
		}
		return (T)this.$$$savedTarget;
	}

    protected void copyAttributes(EntityJoinBuilder<?> copy) {
    	this.attribute = copy.attribute;
		this.alias = copy.alias;
		this.joinType = copy.joinType;
    }

    public B end() {
        return this.$$$parentBuilder;
    }

    public EntityJoinBuilder<B> storeTo(BuilderRepository repo, int builderId) {
    	repo.put(builderId, this);
    	return this;
    }

    public EntityJoin toEntityJoin() {
    	return new Converter(this.$$$option).convert(this).to(EntityJoin.class);
    }

    private EntityAttributeBuilder<?> attribute;

    public EntityAttributeBuilder<?> getAttribute() {
        return this.attribute;
    }

    public void setAttribute(EntityAttributeBuilder<?> attribute) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.attribute = attribute;
    }

    public EntityAttributeBuilder<? extends EntityJoinBuilder<B>> attribute() {
        EntityAttributeBuilder<EntityJoinBuilder<B>> attribute = new EntityAttributeBuilder<EntityJoinBuilder<B>>(this.$$$option, this);
        this.attribute = attribute;
        
        return attribute;
    }

    public EntityJoinBuilder<B> attribute(EntityAttributeBuilder<?> attribute) {
        this.attribute = attribute;
        return this;
    }

    public EntityJoinBuilder<B> attribute(EntityAttribute attribute) {
    	this.attribute = new WrapConverter(this.$$$option).convert(attribute).to(EntityAttributeBuilder.class);
        return this;
    }

    public EntityJoinBuilder<B> attribute$restoreFrom(BuilderRepository repo, int builderId) {
        Object entityAttribute = repo.get(builderId);
        if (entityAttribute == null) {
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
        else {
            this.attribute = (EntityAttributeBuilder<?>)entityAttribute;
        }
        return this;
    }

    private String alias;

    public String getAlias() {
        return this.alias;
    }

    public void setAlias(String alias) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.alias = alias;
    }

    public EntityJoinBuilder<B> alias(String alias) {
        this.alias = alias;
        return this;
    }

    private JoinType joinType;

    public JoinType getJoinType() {
        return this.joinType;
    }

    public void setJoinType(JoinType joinType) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.joinType = joinType;
    }

    public EntityJoinBuilder<B> joinType(JoinType joinType) {
        this.joinType = joinType;
        return this;
    }
}

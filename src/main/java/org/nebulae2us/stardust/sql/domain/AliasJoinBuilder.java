package org.nebulae2us.stardust.sql.domain;

import org.nebulae2us.electron.*;
import org.nebulae2us.stardust.db.domain.*;


public class AliasJoinBuilder<B> implements Convertable {

	private final AliasJoin $$$savedTarget;

	private ConverterOption $$$option;

	private final B $$$parentBuilder;

	protected AliasJoinBuilder(AliasJoin aliasJoin) {
		if (aliasJoin == null) {
			throw new NullPointerException();
		}
	
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = aliasJoin;
	}

	public AliasJoinBuilder(ConverterOption option, B parentBuilder) {
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = parentBuilder;
		this.$$$savedTarget = null;
	}

	public AliasJoinBuilder() {
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public AliasJoinBuilder(ConverterOption option) {
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
	
	public AliasJoin getSavedTarget() {
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

    protected void copyAttributes(AliasJoinBuilder<?> copy) {
    	this.name = copy.name;
		this.alias = copy.alias;
		this.joinType = copy.joinType;
    }

    public B end() {
        return this.$$$parentBuilder;
    }

    public AliasJoinBuilder<B> storeTo(BuilderRepository repo, int builderId) {
    	repo.put(builderId, this);
    	return this;
    }

    public AliasJoin toAliasJoin() {
    	return new Converter(this.$$$option).convert(this).to(AliasJoin.class);
    }

    private String name;

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.name = name;
    }

    public AliasJoinBuilder<B> name(String name) {
        this.name = name;
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

    public AliasJoinBuilder<B> alias(String alias) {
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

    public AliasJoinBuilder<B> joinType(JoinType joinType) {
        this.joinType = joinType;
        return this;
    }
}

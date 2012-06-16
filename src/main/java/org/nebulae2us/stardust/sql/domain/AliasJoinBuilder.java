package org.nebulae2us.stardust.sql.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;
import org.nebulae2us.stardust.db.domain.*;
import org.nebulae2us.stardust.internal.util.Builders;

@Builder(destination=AliasJoin.class)
public class AliasJoinBuilder<P> implements Wrappable<AliasJoin> {

	protected final AliasJoin $$$wrapped;

	protected final P $$$parentBuilder;
	
	public AliasJoinBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public AliasJoinBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected AliasJoinBuilder(AliasJoin wrapped) {
		if (wrapped == null) {
			throw new NullPointerException();
		}
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public AliasJoinBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public AliasJoin getWrappedObject() {
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

    public AliasJoin toAliasJoin() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(AliasJoin.class);
    }



	private String name;
	
	public String getName() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.name, String.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, AliasJoin.class, "name");
			this.name = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(String.class);
		}

		return name;
	}

	public void setName(String name) {
		verifyMutable();
		this.name = name;
	}

	public AliasJoinBuilder<P> name(String name) {
		verifyMutable();
		this.name = name;
		return this;
	}

	private String alias;
	
	public String getAlias() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.alias, String.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, AliasJoin.class, "alias");
			this.alias = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(String.class);
		}

		return alias;
	}

	public void setAlias(String alias) {
		verifyMutable();
		this.alias = alias;
	}

	public AliasJoinBuilder<P> alias(String alias) {
		verifyMutable();
		this.alias = alias;
		return this;
	}

	private JoinType joinType;
	
	public JoinType getJoinType() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.joinType, JoinType.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, AliasJoin.class, "joinType");
			this.joinType = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(JoinType.class);
		}

		return joinType;
	}

	public void setJoinType(JoinType joinType) {
		verifyMutable();
		this.joinType = joinType;
	}

	public AliasJoinBuilder<P> joinType(JoinType joinType) {
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

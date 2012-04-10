package org.nebulae2us.stardust.sql.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;
import org.nebulae2us.stardust.db.domain.*;

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
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(AliasJoin.class);
    }



	private String name;
	
	public String getName() {
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
}

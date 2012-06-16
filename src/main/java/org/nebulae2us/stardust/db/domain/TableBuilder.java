package org.nebulae2us.stardust.db.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;
import org.nebulae2us.stardust.internal.util.Builders;

@Builder(destination=Table.class)
public class TableBuilder<P> implements Wrappable<Table> {

	protected final Table $$$wrapped;

	protected final P $$$parentBuilder;
	
	public TableBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public TableBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected TableBuilder(Table wrapped) {
		if (wrapped == null) {
			throw new NullPointerException();
		}
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public TableBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public Table getWrappedObject() {
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

    public Table toTable() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(Table.class);
    }



	private String name;
	
	public String getName() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.name, String.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, Table.class, "name");
			this.name = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(String.class);
		}

		return name;
	}

	public void setName(String name) {
		verifyMutable();
		this.name = name;
	}

	public TableBuilder<P> name(String name) {
		verifyMutable();
		this.name = name;
		return this;
	}

	private String schemaName;
	
	public String getSchemaName() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.schemaName, String.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, Table.class, "schemaName");
			this.schemaName = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(String.class);
		}

		return schemaName;
	}

	public void setSchemaName(String schemaName) {
		verifyMutable();
		this.schemaName = schemaName;
	}

	public TableBuilder<P> schemaName(String schemaName) {
		verifyMutable();
		this.schemaName = schemaName;
		return this;
	}

	private String catalogName;
	
	public String getCatalogName() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.catalogName, String.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, Table.class, "catalogName");
			this.catalogName = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(String.class);
		}

		return catalogName;
	}

	public void setCatalogName(String catalogName) {
		verifyMutable();
		this.catalogName = catalogName;
	}

	public TableBuilder<P> catalogName(String catalogName) {
		verifyMutable();
		this.catalogName = catalogName;
		return this;
	}

    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

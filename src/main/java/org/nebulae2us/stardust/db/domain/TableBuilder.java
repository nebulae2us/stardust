package org.nebulae2us.stardust.db.domain;

import org.nebulae2us.electron.*;


public class TableBuilder<B> implements Convertable {

	private final Table $$$savedTarget;

	private ConverterOption $$$option;

	private final B $$$parentBuilder;

	protected TableBuilder(Table table) {
		if (table == null) {
			throw new NullPointerException();
		}
	
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = table;
	}

	public TableBuilder(ConverterOption option, B parentBuilder) {
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = parentBuilder;
		this.$$$savedTarget = null;
	}

	public TableBuilder() {
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public TableBuilder(ConverterOption option) {
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
	
	public Table getSavedTarget() {
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

    protected void copyAttributes(TableBuilder<?> copy) {
    	this.name = copy.name;
		this.schemaName = copy.schemaName;
		this.catalogName = copy.catalogName;
    }

    public B end() {
        return this.$$$parentBuilder;
    }

    public TableBuilder<B> storeTo(BuilderRepository repo, int builderId) {
    	repo.put(builderId, this);
    	return this;
    }

    public Table toTable() {
    	return new Converter(this.$$$option).convert(this).to(Table.class);
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

    public TableBuilder<B> name(String name) {
        this.name = name;
        return this;
    }

    private String schemaName;

    public String getSchemaSchemaName() {
        return this.schemaName;
    }

    public void setSchemaName(String schemaName) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.schemaName = schemaName;
    }

    public TableBuilder<B> schemaName(String schemaName) {
        this.schemaName = schemaName;
        return this;
    }

    private String catalogName;

    public String getCatalogCatalogName() {
        return this.catalogName;
    }

    public void setCatalogName(String catalogName) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.catalogName = catalogName;
    }

    public TableBuilder<B> catalogName(String catalogName) {
        this.catalogName = catalogName;
        return this;
    }
}

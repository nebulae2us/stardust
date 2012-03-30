package org.nebulae2us.stardust.db.domain;

import org.nebulae2us.electron.*;


public class ColumnBuilder<B> implements Convertable {

	private final Column $$$savedTarget;

	private ConverterOption $$$option;

	private final B $$$parentBuilder;

	protected ColumnBuilder(Column column) {
		if (column == null) {
			throw new NullPointerException();
		}
	
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = column;
	}

	public ColumnBuilder(ConverterOption option, B parentBuilder) {
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = parentBuilder;
		this.$$$savedTarget = null;
	}

	public ColumnBuilder() {
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public ColumnBuilder(ConverterOption option) {
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
	
	public Column getSavedTarget() {
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

    protected void copyAttributes(ColumnBuilder<?> copy) {
    	this.name = copy.name;
		this.table = copy.table;
    }

    public B end() {
        return this.$$$parentBuilder;
    }

    public ColumnBuilder<B> storeTo(BuilderRepository repo, int builderId) {
    	repo.put(builderId, this);
    	return this;
    }

    public Column toColumn() {
    	return new Converter(this.$$$option).convert(this).to(Column.class);
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

    public ColumnBuilder<B> name(String name) {
        this.name = name;
        return this;
    }

    private TableBuilder<?> table;

    public TableBuilder<?> getTable() {
        return this.table;
    }

    public void setTable(TableBuilder<?> table) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.table = table;
    }

    public TableBuilder<? extends ColumnBuilder<B>> table() {
        TableBuilder<ColumnBuilder<B>> table = new TableBuilder<ColumnBuilder<B>>(this.$$$option, this);
        this.table = table;
        
        return table;
    }

    public ColumnBuilder<B> table(TableBuilder<?> table) {
        this.table = table;
        return this;
    }

    public ColumnBuilder<B> table(Table table) {
    	this.table = new WrapConverter(this.$$$option).convert(table).to(TableBuilder.class);
        return this;
    }

    public ColumnBuilder<B> table$restoreFrom(BuilderRepository repo, int builderId) {
        Object table = repo.get(builderId);
        if (table == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						ColumnBuilder.this.table = (TableBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.table = (TableBuilder<?>)table;
        }
        return this;
    }
}

package org.nebulae2us.stardust.db.domain;

import java.util.*;
import org.nebulae2us.electron.*;


public class JoinedTablesBuilder<B> implements Convertable {

	private final JoinedTables $$$savedTarget;

	private ConverterOption $$$option;

	private final B $$$parentBuilder;

	protected JoinedTablesBuilder(JoinedTables joinedTables) {
		if (joinedTables == null) {
			throw new NullPointerException();
		}
	
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = joinedTables;
	}

	public JoinedTablesBuilder(ConverterOption option, B parentBuilder) {
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = parentBuilder;
		this.$$$savedTarget = null;
	}

	public JoinedTablesBuilder() {
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public JoinedTablesBuilder(ConverterOption option) {
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
	
	public JoinedTables getSavedTarget() {
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

    protected void copyAttributes(JoinedTablesBuilder<?> copy) {
    	this.table = copy.table;
		this.tableJoins = copy.tableJoins;
    }

    public B end() {
        return this.$$$parentBuilder;
    }

    public JoinedTablesBuilder<B> storeTo(BuilderRepository repo, int builderId) {
    	repo.put(builderId, this);
    	return this;
    }

    public JoinedTables toJoinedTables() {
    	return new Converter(this.$$$option).convert(this).to(JoinedTables.class);
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

    public TableBuilder<? extends JoinedTablesBuilder<B>> table() {
        TableBuilder<JoinedTablesBuilder<B>> table = new TableBuilder<JoinedTablesBuilder<B>>(this.$$$option, this);
        this.table = table;
        
        return table;
    }

    public JoinedTablesBuilder<B> table(TableBuilder<?> table) {
        this.table = table;
        return this;
    }

    public JoinedTablesBuilder<B> table(Table table) {
    	this.table = new WrapConverter(this.$$$option).convert(table).to(TableBuilder.class);
        return this;
    }

    public JoinedTablesBuilder<B> table$restoreFrom(BuilderRepository repo, int builderId) {
        Object table = repo.get(builderId);
        if (table == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						JoinedTablesBuilder.this.table = (TableBuilder<?>)arguments[0];
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

    private List<TableJoinBuilder<?>> tableJoins;

    public List<TableJoinBuilder<?>> getTableJoins() {
        return this.tableJoins;
    }

    public void setTableJoins(List<TableJoinBuilder<?>> tableJoins) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.tableJoins = tableJoins;
    }

    public TableJoinBuilder<JoinedTablesBuilder<B>> tableJoin() {
        if (this.tableJoins == null) {
            this.tableJoins = new ArrayList<TableJoinBuilder<?>>();
        }

        TableJoinBuilder<JoinedTablesBuilder<B>> tableJoin = new TableJoinBuilder<JoinedTablesBuilder<B>>(this.$$$option, this);
        
        this.tableJoins.add(tableJoin);
        
        return tableJoin;
    }

    public JoinedTablesBuilder<B> tableJoin(TableJoinBuilder<?> tableJoin) {
        if (this.tableJoins == null) {
            this.tableJoins = new ArrayList<TableJoinBuilder<?>>();
        }
        this.tableJoins.add(tableJoin);
        return this;
    }

    public JoinedTablesBuilder<B> tableJoin(TableJoin tableJoin) {
        if (this.tableJoins == null) {
            this.tableJoins = new ArrayList<TableJoinBuilder<?>>();
        }
    	TableJoinBuilder<?> wrap = new WrapConverter(this.$$$option).convert(tableJoin).to(TableJoinBuilder.class);
        this.tableJoins.add(wrap);
        return this;
    }

    public JoinedTablesBuilder<B> tableJoins(TableJoinBuilder<?> ... tableJoins) {
        if (this.tableJoins == null) {
            this.tableJoins = new ArrayList<TableJoinBuilder<?>>();
        }
        for (TableJoinBuilder<?> o : tableJoins) {
            this.tableJoins.add(o);
        }
        return this;
    }

    public JoinedTablesBuilder<B> tableJoins(TableJoin ... tableJoins) {
        if (this.tableJoins == null) {
            this.tableJoins = new ArrayList<TableJoinBuilder<?>>();
        }
        for (TableJoin o : tableJoins) {
	    	TableJoinBuilder<?> wrap = new WrapConverter(this.$$$option).convert(o).to(TableJoinBuilder.class);
            this.tableJoins.add(wrap);
        }
        return this;
    }

    public JoinedTablesBuilder<B> tableJoin$restoreFrom(BuilderRepository repo, int builderId) {
        Object tableJoin = repo.get(builderId);
        if (this.tableJoins == null) {
            this.tableJoins = new ArrayList<TableJoinBuilder<?>>();
        }

        if (tableJoin == null) {
        	if (repo.isSupportLazy()) {
                
        		final int size = this.tableJoins.size();
        		this.tableJoins.add(null);

        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						JoinedTablesBuilder.this.tableJoins.set(size, (TableJoinBuilder<?>)arguments[0]);
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.tableJoins.add((TableJoinBuilder<?>)tableJoin);
        }
    	
    	return this;
    }

    public JoinedTablesBuilder<B> tableJoins$restoreFrom(BuilderRepository repo, int ... builderIds) {

    	for (int builderId : builderIds) {
    		tableJoin$restoreFrom(repo, builderId);
    	}
    	
    	return this;
    }
}

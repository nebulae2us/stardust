package org.nebulae2us.stardust.db.domain;

import java.util.*;
import org.nebulae2us.electron.*;


public class TableJoinBuilder<B> implements Convertable {

	private final TableJoin $$$savedTarget;

	private ConverterOption $$$option;

	private final B $$$parentBuilder;

	protected TableJoinBuilder(TableJoin tableJoin) {
		if (tableJoin == null) {
			throw new NullPointerException();
		}
	
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = tableJoin;
	}

	public TableJoinBuilder(ConverterOption option, B parentBuilder) {
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = parentBuilder;
		this.$$$savedTarget = null;
	}

	public TableJoinBuilder() {
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public TableJoinBuilder(ConverterOption option) {
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
	
	public TableJoin getSavedTarget() {
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

    protected void copyAttributes(TableJoinBuilder<?> copy) {
    	this.leftTable = copy.leftTable;
		this.leftColumns = copy.leftColumns;
		this.rightTable = copy.rightTable;
		this.rightColumns = copy.rightColumns;
		this.joinType = copy.joinType;
    }

    public B end() {
        return this.$$$parentBuilder;
    }

    public TableJoinBuilder<B> storeTo(BuilderRepository repo, int builderId) {
    	repo.put(builderId, this);
    	return this;
    }

    public TableJoin toTableJoin() {
    	return new Converter(this.$$$option).convert(this).to(TableJoin.class);
    }

    private TableBuilder<?> leftTable;

    public TableBuilder<?> getLeftTable() {
        return this.leftTable;
    }

    public void setLeftTable(TableBuilder<?> leftTable) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.leftTable = leftTable;
    }

    public TableBuilder<? extends TableJoinBuilder<B>> leftTable() {
        TableBuilder<TableJoinBuilder<B>> leftTable = new TableBuilder<TableJoinBuilder<B>>(this.$$$option, this);
        this.leftTable = leftTable;
        
        return leftTable;
    }

    public TableJoinBuilder<B> leftTable(TableBuilder<?> leftTable) {
        this.leftTable = leftTable;
        return this;
    }

    public TableJoinBuilder<B> leftTable(Table leftTable) {
    	this.leftTable = new WrapConverter(this.$$$option).convert(leftTable).to(TableBuilder.class);
        return this;
    }

    public TableJoinBuilder<B> leftTable$restoreFrom(BuilderRepository repo, int builderId) {
        Object table = repo.get(builderId);
        if (table == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						TableJoinBuilder.this.leftTable = (TableBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.leftTable = (TableBuilder<?>)table;
        }
        return this;
    }

    private List<ColumnBuilder<?>> leftColumns;

    public List<ColumnBuilder<?>> getLeftColumns() {
        return this.leftColumns;
    }

    public void setLeftColumns(List<ColumnBuilder<?>> leftColumns) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.leftColumns = leftColumns;
    }

    public ColumnBuilder<TableJoinBuilder<B>> leftColumn() {
        if (this.leftColumns == null) {
            this.leftColumns = new ArrayList<ColumnBuilder<?>>();
        }

        ColumnBuilder<TableJoinBuilder<B>> leftColumn = new ColumnBuilder<TableJoinBuilder<B>>(this.$$$option, this);
        
        this.leftColumns.add(leftColumn);
        
        return leftColumn;
    }

    public TableJoinBuilder<B> leftColumn(ColumnBuilder<?> leftColumn) {
        if (this.leftColumns == null) {
            this.leftColumns = new ArrayList<ColumnBuilder<?>>();
        }
        this.leftColumns.add(leftColumn);
        return this;
    }

    public TableJoinBuilder<B> leftColumn(Column leftColumn) {
        if (this.leftColumns == null) {
            this.leftColumns = new ArrayList<ColumnBuilder<?>>();
        }
    	ColumnBuilder<?> wrap = new WrapConverter(this.$$$option).convert(leftColumn).to(ColumnBuilder.class);
        this.leftColumns.add(wrap);
        return this;
    }

    public TableJoinBuilder<B> leftColumns(ColumnBuilder<?> ... leftColumns) {
        if (this.leftColumns == null) {
            this.leftColumns = new ArrayList<ColumnBuilder<?>>();
        }
        for (ColumnBuilder<?> o : leftColumns) {
            this.leftColumns.add(o);
        }
        return this;
    }

    public TableJoinBuilder<B> leftColumns(Column ... leftColumns) {
        if (this.leftColumns == null) {
            this.leftColumns = new ArrayList<ColumnBuilder<?>>();
        }
        for (Column o : leftColumns) {
	    	ColumnBuilder<?> wrap = new WrapConverter(this.$$$option).convert(o).to(ColumnBuilder.class);
            this.leftColumns.add(wrap);
        }
        return this;
    }

    public TableJoinBuilder<B> leftColumn$restoreFrom(BuilderRepository repo, int builderId) {
        Object leftColumn = repo.get(builderId);
        if (this.leftColumns == null) {
            this.leftColumns = new ArrayList<ColumnBuilder<?>>();
        }

        if (leftColumn == null) {
        	if (repo.isSupportLazy()) {
                
        		final int size = this.leftColumns.size();
        		this.leftColumns.add(null);

        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						TableJoinBuilder.this.leftColumns.set(size, (ColumnBuilder<?>)arguments[0]);
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.leftColumns.add((ColumnBuilder<?>)leftColumn);
        }
    	
    	return this;
    }

    public TableJoinBuilder<B> leftColumns$restoreFrom(BuilderRepository repo, int ... builderIds) {

    	for (int builderId : builderIds) {
    		leftColumn$restoreFrom(repo, builderId);
    	}
    	
    	return this;
    }

    private TableBuilder<?> rightTable;

    public TableBuilder<?> getRightTable() {
        return this.rightTable;
    }

    public void setRightTable(TableBuilder<?> rightTable) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.rightTable = rightTable;
    }

    public TableBuilder<? extends TableJoinBuilder<B>> rightTable() {
        TableBuilder<TableJoinBuilder<B>> rightTable = new TableBuilder<TableJoinBuilder<B>>(this.$$$option, this);
        this.rightTable = rightTable;
        
        return rightTable;
    }

    public TableJoinBuilder<B> rightTable(TableBuilder<?> rightTable) {
        this.rightTable = rightTable;
        return this;
    }

    public TableJoinBuilder<B> rightTable(Table rightTable) {
    	this.rightTable = new WrapConverter(this.$$$option).convert(rightTable).to(TableBuilder.class);
        return this;
    }

    public TableJoinBuilder<B> rightTable$restoreFrom(BuilderRepository repo, int builderId) {
        Object table = repo.get(builderId);
        if (table == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						TableJoinBuilder.this.rightTable = (TableBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.rightTable = (TableBuilder<?>)table;
        }
        return this;
    }

    private List<ColumnBuilder<?>> rightColumns;

    public List<ColumnBuilder<?>> getRightColumns() {
        return this.rightColumns;
    }

    public void setRightColumns(List<ColumnBuilder<?>> rightColumns) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.rightColumns = rightColumns;
    }

    public ColumnBuilder<TableJoinBuilder<B>> rightColumn() {
        if (this.rightColumns == null) {
            this.rightColumns = new ArrayList<ColumnBuilder<?>>();
        }

        ColumnBuilder<TableJoinBuilder<B>> rightColumn = new ColumnBuilder<TableJoinBuilder<B>>(this.$$$option, this);
        
        this.rightColumns.add(rightColumn);
        
        return rightColumn;
    }

    public TableJoinBuilder<B> rightColumn(ColumnBuilder<?> rightColumn) {
        if (this.rightColumns == null) {
            this.rightColumns = new ArrayList<ColumnBuilder<?>>();
        }
        this.rightColumns.add(rightColumn);
        return this;
    }

    public TableJoinBuilder<B> rightColumn(Column rightColumn) {
        if (this.rightColumns == null) {
            this.rightColumns = new ArrayList<ColumnBuilder<?>>();
        }
    	ColumnBuilder<?> wrap = new WrapConverter(this.$$$option).convert(rightColumn).to(ColumnBuilder.class);
        this.rightColumns.add(wrap);
        return this;
    }

    public TableJoinBuilder<B> rightColumns(ColumnBuilder<?> ... rightColumns) {
        if (this.rightColumns == null) {
            this.rightColumns = new ArrayList<ColumnBuilder<?>>();
        }
        for (ColumnBuilder<?> o : rightColumns) {
            this.rightColumns.add(o);
        }
        return this;
    }

    public TableJoinBuilder<B> rightColumns(Column ... rightColumns) {
        if (this.rightColumns == null) {
            this.rightColumns = new ArrayList<ColumnBuilder<?>>();
        }
        for (Column o : rightColumns) {
	    	ColumnBuilder<?> wrap = new WrapConverter(this.$$$option).convert(o).to(ColumnBuilder.class);
            this.rightColumns.add(wrap);
        }
        return this;
    }

    public TableJoinBuilder<B> rightColumn$restoreFrom(BuilderRepository repo, int builderId) {
        Object rightColumn = repo.get(builderId);
        if (this.rightColumns == null) {
            this.rightColumns = new ArrayList<ColumnBuilder<?>>();
        }

        if (rightColumn == null) {
        	if (repo.isSupportLazy()) {
                
        		final int size = this.rightColumns.size();
        		this.rightColumns.add(null);

        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						TableJoinBuilder.this.rightColumns.set(size, (ColumnBuilder<?>)arguments[0]);
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.rightColumns.add((ColumnBuilder<?>)rightColumn);
        }
    	
    	return this;
    }

    public TableJoinBuilder<B> rightColumns$restoreFrom(BuilderRepository repo, int ... builderIds) {

    	for (int builderId : builderIds) {
    		rightColumn$restoreFrom(repo, builderId);
    	}
    	
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

    public TableJoinBuilder<B> joinType(JoinType joinType) {
        this.joinType = joinType;
        return this;
    }
}

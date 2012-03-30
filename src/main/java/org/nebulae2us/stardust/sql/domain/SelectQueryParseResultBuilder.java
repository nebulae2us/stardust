package org.nebulae2us.stardust.sql.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.stardust.db.domain.*;


public class SelectQueryParseResultBuilder<B> implements Convertable {

	private final SelectQueryParseResult $$$savedTarget;

	private ConverterOption $$$option;

	private final B $$$parentBuilder;

	protected SelectQueryParseResultBuilder(SelectQueryParseResult selectQueryParseResult) {
		if (selectQueryParseResult == null) {
			throw new NullPointerException();
		}
	
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = selectQueryParseResult;
	}

	public SelectQueryParseResultBuilder(ConverterOption option, B parentBuilder) {
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = parentBuilder;
		this.$$$savedTarget = null;
	}

	public SelectQueryParseResultBuilder() {
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public SelectQueryParseResultBuilder(ConverterOption option) {
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
	
	public SelectQueryParseResult getSavedTarget() {
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

    protected void copyAttributes(SelectQueryParseResultBuilder<?> copy) {
    	this.relationalEntities = copy.relationalEntities;
		this.joinedTables = copy.joinedTables;
		this.columns = copy.columns;
    }

    public B end() {
        return this.$$$parentBuilder;
    }

    public SelectQueryParseResultBuilder<B> storeTo(BuilderRepository repo, int builderId) {
    	repo.put(builderId, this);
    	return this;
    }

    public SelectQueryParseResult toSelectQueryParseResult() {
    	return new Converter(this.$$$option).convert(this).to(SelectQueryParseResult.class);
    }

    private RelationalEntitiesBuilder<?> relationalEntities;

    public RelationalEntitiesBuilder<?> getRelationalEntities() {
        return this.relationalEntities;
    }

    public void setRelationalEntities(RelationalEntitiesBuilder<?> relationalEntities) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.relationalEntities = relationalEntities;
    }

    public RelationalEntitiesBuilder<? extends SelectQueryParseResultBuilder<B>> relationalEntities() {
        RelationalEntitiesBuilder<SelectQueryParseResultBuilder<B>> relationalEntities = new RelationalEntitiesBuilder<SelectQueryParseResultBuilder<B>>(this.$$$option, this);
        this.relationalEntities = relationalEntities;
        
        return relationalEntities;
    }

    public SelectQueryParseResultBuilder<B> relationalEntities(RelationalEntitiesBuilder<?> relationalEntities) {
        this.relationalEntities = relationalEntities;
        return this;
    }

    public SelectQueryParseResultBuilder<B> relationalEntities(RelationalEntities relationalEntities) {
    	this.relationalEntities = new WrapConverter(this.$$$option).convert(relationalEntities).to(RelationalEntitiesBuilder.class);
        return this;
    }

    public SelectQueryParseResultBuilder<B> relationalEntities$restoreFrom(BuilderRepository repo, int builderId) {
        Object relationalEntities = repo.get(builderId);
        if (relationalEntities == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						SelectQueryParseResultBuilder.this.relationalEntities = (RelationalEntitiesBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.relationalEntities = (RelationalEntitiesBuilder<?>)relationalEntities;
        }
        return this;
    }

    private JoinedTablesBuilder<?> joinedTables;

    public JoinedTablesBuilder<?> getJoinedTables() {
        return this.joinedTables;
    }

    public void setJoinedTables(JoinedTablesBuilder<?> joinedTables) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.joinedTables = joinedTables;
    }

    public JoinedTablesBuilder<? extends SelectQueryParseResultBuilder<B>> joinedTables() {
        JoinedTablesBuilder<SelectQueryParseResultBuilder<B>> joinedTables = new JoinedTablesBuilder<SelectQueryParseResultBuilder<B>>(this.$$$option, this);
        this.joinedTables = joinedTables;
        
        return joinedTables;
    }

    public SelectQueryParseResultBuilder<B> joinedTables(JoinedTablesBuilder<?> joinedTables) {
        this.joinedTables = joinedTables;
        return this;
    }

    public SelectQueryParseResultBuilder<B> joinedTables(JoinedTables joinedTables) {
    	this.joinedTables = new WrapConverter(this.$$$option).convert(joinedTables).to(JoinedTablesBuilder.class);
        return this;
    }

    public SelectQueryParseResultBuilder<B> joinedTables$restoreFrom(BuilderRepository repo, int builderId) {
        Object joinedTables = repo.get(builderId);
        if (joinedTables == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						SelectQueryParseResultBuilder.this.joinedTables = (JoinedTablesBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.joinedTables = (JoinedTablesBuilder<?>)joinedTables;
        }
        return this;
    }

    private List<ColumnBuilder<?>> columns;

    public List<ColumnBuilder<?>> getColumns() {
        return this.columns;
    }

    public void setColumns(List<ColumnBuilder<?>> columns) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.columns = columns;
    }

    public ColumnBuilder<SelectQueryParseResultBuilder<B>> column() {
        if (this.columns == null) {
            this.columns = new ArrayList<ColumnBuilder<?>>();
        }

        ColumnBuilder<SelectQueryParseResultBuilder<B>> column = new ColumnBuilder<SelectQueryParseResultBuilder<B>>(this.$$$option, this);
        
        this.columns.add(column);
        
        return column;
    }

    public SelectQueryParseResultBuilder<B> column(ColumnBuilder<?> column) {
        if (this.columns == null) {
            this.columns = new ArrayList<ColumnBuilder<?>>();
        }
        this.columns.add(column);
        return this;
    }

    public SelectQueryParseResultBuilder<B> column(Column column) {
        if (this.columns == null) {
            this.columns = new ArrayList<ColumnBuilder<?>>();
        }
    	ColumnBuilder<?> wrap = new WrapConverter(this.$$$option).convert(column).to(ColumnBuilder.class);
        this.columns.add(wrap);
        return this;
    }

    public SelectQueryParseResultBuilder<B> columns(ColumnBuilder<?> ... columns) {
        if (this.columns == null) {
            this.columns = new ArrayList<ColumnBuilder<?>>();
        }
        for (ColumnBuilder<?> o : columns) {
            this.columns.add(o);
        }
        return this;
    }

    public SelectQueryParseResultBuilder<B> columns(Column ... columns) {
        if (this.columns == null) {
            this.columns = new ArrayList<ColumnBuilder<?>>();
        }
        for (Column o : columns) {
	    	ColumnBuilder<?> wrap = new WrapConverter(this.$$$option).convert(o).to(ColumnBuilder.class);
            this.columns.add(wrap);
        }
        return this;
    }

    public SelectQueryParseResultBuilder<B> column$restoreFrom(BuilderRepository repo, int builderId) {
        Object column = repo.get(builderId);
        if (this.columns == null) {
            this.columns = new ArrayList<ColumnBuilder<?>>();
        }

        if (column == null) {
        	if (repo.isSupportLazy()) {
                
        		final int size = this.columns.size();
        		this.columns.add(null);

        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						SelectQueryParseResultBuilder.this.columns.set(size, (ColumnBuilder<?>)arguments[0]);
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.columns.add((ColumnBuilder<?>)column);
        }
    	
    	return this;
    }

    public SelectQueryParseResultBuilder<B> columns$restoreFrom(BuilderRepository repo, int ... builderIds) {

    	for (int builderId : builderIds) {
    		column$restoreFrom(repo, builderId);
    	}
    	
    	return this;
    }
}

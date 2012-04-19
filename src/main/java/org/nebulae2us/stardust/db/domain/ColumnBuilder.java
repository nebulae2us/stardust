package org.nebulae2us.stardust.db.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;

@Builder(destination=Column.class)
public class ColumnBuilder<P> implements Wrappable<Column> {

	protected final Column $$$wrapped;

	protected final P $$$parentBuilder;
	
	public ColumnBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public ColumnBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected ColumnBuilder(Column wrapped) {
		if (wrapped == null) {
			throw new NullPointerException();
		}
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public ColumnBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public Column getWrappedObject() {
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

    public Column toColumn() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(Column.class);
    }



	private String name;
	
	public String getName() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.name, String.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, Column.class, "name");
			this.name = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(String.class);
		}

		return name;
	}

	public void setName(String name) {
		verifyMutable();
		this.name = name;
	}

	public ColumnBuilder<P> name(String name) {
		verifyMutable();
		this.name = name;
		return this;
	}

	private TableBuilder<?> table;
	
	public TableBuilder<?> getTable() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.table, TableBuilder.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, Column.class, "table");
			this.table = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(TableBuilder.class);
		}

		return table;
	}

	public void setTable(TableBuilder<?> table) {
		verifyMutable();
		this.table = table;
	}

	public ColumnBuilder<P> table(TableBuilder<?> table) {
		verifyMutable();
		this.table = table;
		return this;
	}

    public ColumnBuilder<P> table$wrap(Table table) {
    	verifyMutable();
    	this.table = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(table).to(TableBuilder.class);
        return this;
    }
    
    public ColumnBuilder<P> table$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
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
        else if (!(restoredObject instanceof TableBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + TableBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.table = (TableBuilder<?>)restoredObject;
        }
        return this;
    }

	public TableBuilder<? extends ColumnBuilder<P>> table$begin() {
		verifyMutable();
		TableBuilder<ColumnBuilder<P>> result = new TableBuilder<ColumnBuilder<P>>(this);
		this.table = result;
		return result;
	}

    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

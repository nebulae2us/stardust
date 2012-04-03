package org.nebulae2us.stardust.db.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;

@Builder(destination=JoinedTables.class)
public class JoinedTablesBuilder<P> implements Wrappable<JoinedTables> {

	protected final JoinedTables $$$wrapped;

	protected final P $$$parentBuilder;
	
	public JoinedTablesBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public JoinedTablesBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected JoinedTablesBuilder(JoinedTables wrapped) {
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public JoinedTablesBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public JoinedTables getWrappedObject() {
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

    public JoinedTables toJoinedTables() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(JoinedTables.class);
    }

	private TableBuilder<?> table;
	
	public TableBuilder<?> getTable() {
		return table;
	}

	public void setTable(TableBuilder<?> table) {
		verifyMutable();
		this.table = table;
	}

	public JoinedTablesBuilder<P> table(TableBuilder<?> table) {
		verifyMutable();
		this.table = table;
		return this;
	}

	public TableBuilder<? extends JoinedTablesBuilder<P>> table$begin() {
		TableBuilder<JoinedTablesBuilder<P>> result = new TableBuilder<JoinedTablesBuilder<P>>(this);
		this.table = result;
		return result;
	}

    public JoinedTablesBuilder<P> table$wrap(Table table) {
    	verifyMutable();
    	this.table = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(table).to(TableBuilder.class);
        return this;
    }
    
    public JoinedTablesBuilder<P> table$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
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
        else if (!(restoredObject instanceof TableBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + TableBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.table = (TableBuilder<?>)restoredObject;
        }
        return this;
    }

	private List<TableJoinBuilder<?>> tableJoins;
	
	public List<TableJoinBuilder<?>> getTableJoins() {
		return tableJoins;
	}

	public void setTableJoins(List<TableJoinBuilder<?>> tableJoins) {
		verifyMutable();
		this.tableJoins = tableJoins;
	}

	public JoinedTablesBuilder<P> tableJoins(TableJoinBuilder<?> ... tableJoins) {
		verifyMutable();
		return tableJoins(new ListBuilder<TableJoinBuilder<?>>().add(tableJoins).toList());
	}
	
	public JoinedTablesBuilder<P> tableJoins(Collection<TableJoinBuilder<?>> tableJoins) {
		verifyMutable();
		if (this.tableJoins == null) {
			this.tableJoins = new ArrayList<TableJoinBuilder<?>>();
		}
		if (tableJoins != null) {
			for (TableJoinBuilder<?> e : tableJoins) {
				this.tableJoins.add(e);
			}
		}
		return this;
	}

	public TableJoinBuilder<JoinedTablesBuilder<P>> tableJoins$one() {
		verifyMutable();
		if (this.tableJoins == null) {
			this.tableJoins = new ArrayList<TableJoinBuilder<?>>();
		}
		
		TableJoinBuilder<JoinedTablesBuilder<P>> result =
				new TableJoinBuilder<JoinedTablesBuilder<P>>(this);
		
		this.tableJoins.add(result);
		
		return result;
	}

	public class TableJoins$$$builder {
		
		public TableJoinBuilder<TableJoins$$$builder> blank$begin() {
			TableJoinBuilder<TableJoins$$$builder> result = new TableJoinBuilder<TableJoins$$$builder>(this);
			JoinedTablesBuilder.this.tableJoins.add(result);
			return result;
		}
		
		public JoinedTablesBuilder<P> end() {
			return JoinedTablesBuilder.this;
		}
	}
	
	public TableJoins$$$builder tableJoins$list() {
		verifyMutable();
		if (this.tableJoins == null) {
			this.tableJoins = new ArrayList<TableJoinBuilder<?>>();
		}
		return new TableJoins$$$builder();
	}

    public JoinedTablesBuilder<P> tableJoins$wrap(TableJoin ... tableJoins) {
    	return tableJoins$wrap(new ListBuilder<TableJoin>().add(tableJoins).toList());
    }

    public JoinedTablesBuilder<P> tableJoins$wrap(Collection<TableJoin> tableJoins) {
		verifyMutable();

		if (this.tableJoins == null) {
			this.tableJoins = new ArrayList<TableJoinBuilder<?>>();
		}
		if (tableJoins != null) {
			for (TableJoin e : tableJoins) {
				TableJoinBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(TableJoinBuilder.class);
				this.tableJoins.add(wrapped);
			}
		}
		return this;
    }
    
    public JoinedTablesBuilder<P> tableJoins$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return tableJoins$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public JoinedTablesBuilder<P> tableJoins$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		verifyMutable();

		if (this.tableJoins == null) {
			this.tableJoins = new ArrayList<TableJoinBuilder<?>>();
		}
		if (builderIds != null) {
	    	for (Object builderId : builderIds) {
	            Object restoredObject = repo.get(builderId);
	            if (restoredObject == null) {
	            	if (repo.isSupportLazy()) {
	            		repo.addObjectStoredListener(builderId, new Procedure() {
	    					public void execute(Object... arguments) {
	    						JoinedTablesBuilder.this.tableJoins.add((TableJoinBuilder<?>)arguments[0]);
	    					}
	    				});
	            	}
	            	else {
	                    throw new IllegalStateException("Object does not exist with id " + builderId);
	            	}
	            }
	            else if (!(restoredObject instanceof TableJoinBuilder)) {
	            	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + TableJoinBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
	            }
	            else {
	                this.tableJoins.add((TableJoinBuilder<?>)restoredObject);
	            }
	    	}
		}
        return this;
    }
}

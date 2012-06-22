package org.nebulae2us.stardust.db.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;

@Builder(destination=LinkedTableBundle.class)
public class LinkedTableBundleBuilder<P> implements Wrappable<LinkedTableBundle> {

	protected final LinkedTableBundle $$$wrapped;

	protected final P $$$parentBuilder;
	
	public LinkedTableBundleBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public LinkedTableBundleBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected LinkedTableBundleBuilder(LinkedTableBundle wrapped) {
		if (wrapped == null) {
			throw new NullPointerException();
		}
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public LinkedTableBundleBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public LinkedTableBundle getWrappedObject() {
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

    public LinkedTableBundle toLinkedTableBundle() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(LinkedTableBundle.class);
    }



	private List<LinkedTableBuilder<?>> linkedTables;
	
	public List<LinkedTableBuilder<?>> getLinkedTables() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.linkedTables, List.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, LinkedTableBundle.class, "linkedTables");
			this.linkedTables = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(List.class);
		}

		return linkedTables;
	}

	public void setLinkedTables(List<LinkedTableBuilder<?>> linkedTables) {
		verifyMutable();
		this.linkedTables = linkedTables;
	}

	public LinkedTableBundleBuilder<P> linkedTables(LinkedTableBuilder<?> ... linkedTables) {
		verifyMutable();
		return linkedTables(new ListBuilder<LinkedTableBuilder<?>>().add(linkedTables).toList());
	}
	
	public LinkedTableBundleBuilder<P> linkedTables(Collection<LinkedTableBuilder<?>> linkedTables) {
		verifyMutable();
		if (this.linkedTables == null) {
			this.linkedTables = new ArrayList<LinkedTableBuilder<?>>();
		}
		if (linkedTables != null) {
			for (LinkedTableBuilder<?> e : linkedTables) {
				CollectionUtils.addItem(this.linkedTables, e);
			}
		}
		return this;
	}

	public LinkedTableBuilder<? extends LinkedTableBundleBuilder<P>> linkedTables$addLinkedTable() {
		verifyMutable();
		if (this.linkedTables == null) {
			this.linkedTables = new ArrayList<LinkedTableBuilder<?>>();
		}
		
		LinkedTableBuilder<LinkedTableBundleBuilder<P>> result =
				new LinkedTableBuilder<LinkedTableBundleBuilder<P>>(this);
		
		CollectionUtils.addItem(this.linkedTables, result);
		
		return result;
	}
	

	public class LinkedTables$$$builder<P1 extends LinkedTableBundleBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected LinkedTables$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public LinkedTableBuilder<LinkedTables$$$builder<P1>> linkedTable$begin() {
			LinkedTableBuilder<LinkedTables$$$builder<P1>> result = new LinkedTableBuilder<LinkedTables$$$builder<P1>>(this);
			CollectionUtils.addItem(LinkedTableBundleBuilder.this.linkedTables, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public LinkedTables$$$builder<? extends LinkedTableBundleBuilder<P>> linkedTables$list() {
		verifyMutable();
		if (this.linkedTables == null) {
			this.linkedTables = new ArrayList<LinkedTableBuilder<?>>();
		}
		return new LinkedTables$$$builder<LinkedTableBundleBuilder<P>>(this);
	}

    public LinkedTableBundleBuilder<P> linkedTables$wrap(LinkedTable ... linkedTables) {
    	return linkedTables$wrap(new ListBuilder<LinkedTable>().add(linkedTables).toList());
    }

    public LinkedTableBundleBuilder<P> linkedTables$wrap(Collection<? extends LinkedTable> linkedTables) {
		verifyMutable();

		if (this.linkedTables == null) {
			this.linkedTables = new ArrayList<LinkedTableBuilder<?>>();
		}
		if (linkedTables != null) {
			for (LinkedTable e : linkedTables) {
				LinkedTableBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(LinkedTableBuilder.class);
				CollectionUtils.addItem(this.linkedTables, wrapped);
			}
		}
		return this;
    }
    
    public LinkedTableBundleBuilder<P> linkedTables$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return linkedTables$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public LinkedTableBundleBuilder<P> linkedTables$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		verifyMutable();

		if (this.linkedTables == null) {
			this.linkedTables = new ArrayList<LinkedTableBuilder<?>>();
		}
		if (builderIds != null) {
	    	for (Object builderId : builderIds) {
	            Object restoredObject = repo.get(builderId);
	            if (restoredObject == null) {
	            	if (repo.isSupportLazy()) {
	            		repo.addObjectStoredListener(builderId, new Procedure() {
	    					public void execute(Object... arguments) {
	    						CollectionUtils.addItem(LinkedTableBundleBuilder.this.linkedTables, arguments[0]);
	    					}
	    				});
	            	}
	            	else {
	                    throw new IllegalStateException("Object does not exist with id " + builderId);
	            	}
	            }
	            else if (!(restoredObject instanceof LinkedTableBuilder)) {
	            	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + LinkedTableBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
	            }
	            else {
	                CollectionUtils.addItem(this.linkedTables, restoredObject);
	            }
	    	}
		}
        return this;
    }


    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     public LinkedTableBuilder<?> getRoot() {
    	 return this.getLinkedTables().get(0);
     }

     public List<LinkedTableBuilder<?>> getNonRoots() {
    	 return this.getLinkedTables().subList(1, this.getLinkedTables().size());
     }

}

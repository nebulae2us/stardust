package org.nebulae2us.stardust.sql.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;

@Builder(destination=LinkedTableEntityBundle.class)
public class LinkedTableEntityBundleBuilder<P> implements Wrappable<LinkedTableEntityBundle> {

	protected final LinkedTableEntityBundle $$$wrapped;

	protected final P $$$parentBuilder;
	
	public LinkedTableEntityBundleBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public LinkedTableEntityBundleBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected LinkedTableEntityBundleBuilder(LinkedTableEntityBundle wrapped) {
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public LinkedTableEntityBundleBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public LinkedTableEntityBundle getWrappedObject() {
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

    public LinkedTableEntityBundle toLinkedTableEntityBundle() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(LinkedTableEntityBundle.class);
    }



	private List<LinkedTableEntityBuilder<?>> linkedTableEntities;
	
	public List<LinkedTableEntityBuilder<?>> getLinkedTableEntities() {
		return linkedTableEntities;
	}

	public void setLinkedTableEntities(List<LinkedTableEntityBuilder<?>> linkedTableEntities) {
		verifyMutable();
		this.linkedTableEntities = linkedTableEntities;
	}

	public LinkedTableEntityBundleBuilder<P> linkedTableEntities(LinkedTableEntityBuilder<?> ... linkedTableEntities) {
		verifyMutable();
		return linkedTableEntities(new ListBuilder<LinkedTableEntityBuilder<?>>().add(linkedTableEntities).toList());
	}
	
	public LinkedTableEntityBundleBuilder<P> linkedTableEntities(Collection<LinkedTableEntityBuilder<?>> linkedTableEntities) {
		verifyMutable();
		if (this.linkedTableEntities == null) {
			this.linkedTableEntities = new ArrayList<LinkedTableEntityBuilder<?>>();
		}
		if (linkedTableEntities != null) {
			for (LinkedTableEntityBuilder<?> e : linkedTableEntities) {
				CollectionUtils.addItem(this.linkedTableEntities, e);
			}
		}
		return this;
	}

	public LinkedTableEntityBuilder<? extends LinkedTableEntityBundleBuilder<P>> linkedTableEntities$addLinkedTableEntity() {
		verifyMutable();
		if (this.linkedTableEntities == null) {
			this.linkedTableEntities = new ArrayList<LinkedTableEntityBuilder<?>>();
		}
		
		LinkedTableEntityBuilder<LinkedTableEntityBundleBuilder<P>> result =
				new LinkedTableEntityBuilder<LinkedTableEntityBundleBuilder<P>>(this);
		
		CollectionUtils.addItem(this.linkedTableEntities, result);
		
		return result;
	}
	

	public class LinkedTableEntities$$$builder<P1 extends LinkedTableEntityBundleBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected LinkedTableEntities$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public LinkedTableEntityBuilder<LinkedTableEntities$$$builder<P1>> linkedTableEntity$begin() {
			LinkedTableEntityBuilder<LinkedTableEntities$$$builder<P1>> result = new LinkedTableEntityBuilder<LinkedTableEntities$$$builder<P1>>(this);
			CollectionUtils.addItem(LinkedTableEntityBundleBuilder.this.linkedTableEntities, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public LinkedTableEntities$$$builder<? extends LinkedTableEntityBundleBuilder<P>> linkedTableEntities$list() {
		verifyMutable();
		if (this.linkedTableEntities == null) {
			this.linkedTableEntities = new ArrayList<LinkedTableEntityBuilder<?>>();
		}
		return new LinkedTableEntities$$$builder<LinkedTableEntityBundleBuilder<P>>(this);
	}

    public LinkedTableEntityBundleBuilder<P> linkedTableEntities$wrap(LinkedTableEntity ... linkedTableEntities) {
    	return linkedTableEntities$wrap(new ListBuilder<LinkedTableEntity>().add(linkedTableEntities).toList());
    }

    public LinkedTableEntityBundleBuilder<P> linkedTableEntities$wrap(Collection<? extends LinkedTableEntity> linkedTableEntities) {
		verifyMutable();

		if (this.linkedTableEntities == null) {
			this.linkedTableEntities = new ArrayList<LinkedTableEntityBuilder<?>>();
		}
		if (linkedTableEntities != null) {
			for (LinkedTableEntity e : linkedTableEntities) {
				LinkedTableEntityBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(LinkedTableEntityBuilder.class);
				CollectionUtils.addItem(this.linkedTableEntities, wrapped);
			}
		}
		return this;
    }
    
    public LinkedTableEntityBundleBuilder<P> linkedTableEntities$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return linkedTableEntities$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public LinkedTableEntityBundleBuilder<P> linkedTableEntities$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		verifyMutable();

		if (this.linkedTableEntities == null) {
			this.linkedTableEntities = new ArrayList<LinkedTableEntityBuilder<?>>();
		}
		if (builderIds != null) {
	    	for (Object builderId : builderIds) {
	            Object restoredObject = repo.get(builderId);
	            if (restoredObject == null) {
	            	if (repo.isSupportLazy()) {
	            		repo.addObjectStoredListener(builderId, new Procedure() {
	    					public void execute(Object... arguments) {
	    						CollectionUtils.addItem(LinkedTableEntityBundleBuilder.this.linkedTableEntities, arguments[0]);
	    					}
	    				});
	            	}
	            	else {
	                    throw new IllegalStateException("Object does not exist with id " + builderId);
	            	}
	            }
	            else if (!(restoredObject instanceof LinkedTableEntityBuilder)) {
	            	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + LinkedTableEntityBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
	            }
	            else {
	                CollectionUtils.addItem(this.linkedTableEntities, restoredObject);
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
     
	public LinkedTableEntityBuilder<?> getRoot() {
		return this.linkedTableEntities.get(0);
	}
     
     
}

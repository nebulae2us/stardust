package org.nebulae2us.stardust.sql.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;

@Builder(destination=LinkedEntityBundle.class)
public class LinkedEntityBundleBuilder<P> implements Wrappable<LinkedEntityBundle> {

	protected final LinkedEntityBundle $$$wrapped;

	protected final P $$$parentBuilder;
	
	public LinkedEntityBundleBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public LinkedEntityBundleBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected LinkedEntityBundleBuilder(LinkedEntityBundle wrapped) {
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public LinkedEntityBundleBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public LinkedEntityBundle getWrappedObject() {
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

    public LinkedEntityBundle toLinkedEntityBundle() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(LinkedEntityBundle.class);
    }



	private List<LinkedEntityBuilder<?>> linkedEntities;
	
	public List<LinkedEntityBuilder<?>> getLinkedEntities() {
		return linkedEntities;
	}

	public void setLinkedEntities(List<LinkedEntityBuilder<?>> linkedEntities) {
		verifyMutable();
		this.linkedEntities = linkedEntities;
	}

	public LinkedEntityBundleBuilder<P> linkedEntities(LinkedEntityBuilder<?> ... linkedEntities) {
		verifyMutable();
		return linkedEntities(new ListBuilder<LinkedEntityBuilder<?>>().add(linkedEntities).toList());
	}
	
	public LinkedEntityBundleBuilder<P> linkedEntities(Collection<LinkedEntityBuilder<?>> linkedEntities) {
		verifyMutable();
		if (this.linkedEntities == null) {
			this.linkedEntities = new ArrayList<LinkedEntityBuilder<?>>();
		}
		if (linkedEntities != null) {
			for (LinkedEntityBuilder<?> e : linkedEntities) {
				CollectionUtils.addItem(this.linkedEntities, e);
			}
		}
		return this;
	}

	public LinkedEntityBuilder<? extends LinkedEntityBundleBuilder<P>> linkedEntities$addLinkedEntity() {
		verifyMutable();
		if (this.linkedEntities == null) {
			this.linkedEntities = new ArrayList<LinkedEntityBuilder<?>>();
		}
		
		LinkedEntityBuilder<LinkedEntityBundleBuilder<P>> result =
				new LinkedEntityBuilder<LinkedEntityBundleBuilder<P>>(this);
		
		CollectionUtils.addItem(this.linkedEntities, result);
		
		return result;
	}
	

	public class LinkedEntities$$$builder<P1 extends LinkedEntityBundleBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected LinkedEntities$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public LinkedEntityBuilder<LinkedEntities$$$builder<P1>> linkedEntity$begin() {
			LinkedEntityBuilder<LinkedEntities$$$builder<P1>> result = new LinkedEntityBuilder<LinkedEntities$$$builder<P1>>(this);
			CollectionUtils.addItem(LinkedEntityBundleBuilder.this.linkedEntities, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public LinkedEntities$$$builder<? extends LinkedEntityBundleBuilder<P>> linkedEntities$list() {
		verifyMutable();
		if (this.linkedEntities == null) {
			this.linkedEntities = new ArrayList<LinkedEntityBuilder<?>>();
		}
		return new LinkedEntities$$$builder<LinkedEntityBundleBuilder<P>>(this);
	}

    public LinkedEntityBundleBuilder<P> linkedEntities$wrap(LinkedEntity ... linkedEntities) {
    	return linkedEntities$wrap(new ListBuilder<LinkedEntity>().add(linkedEntities).toList());
    }

    public LinkedEntityBundleBuilder<P> linkedEntities$wrap(Collection<? extends LinkedEntity> linkedEntities) {
		verifyMutable();

		if (this.linkedEntities == null) {
			this.linkedEntities = new ArrayList<LinkedEntityBuilder<?>>();
		}
		if (linkedEntities != null) {
			for (LinkedEntity e : linkedEntities) {
				LinkedEntityBuilder<?> wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(LinkedEntityBuilder.class);
				CollectionUtils.addItem(this.linkedEntities, wrapped);
			}
		}
		return this;
    }
    
    public LinkedEntityBundleBuilder<P> linkedEntities$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return linkedEntities$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public LinkedEntityBundleBuilder<P> linkedEntities$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		verifyMutable();

		if (this.linkedEntities == null) {
			this.linkedEntities = new ArrayList<LinkedEntityBuilder<?>>();
		}
		if (builderIds != null) {
	    	for (Object builderId : builderIds) {
	            Object restoredObject = repo.get(builderId);
	            if (restoredObject == null) {
	            	if (repo.isSupportLazy()) {
	            		repo.addObjectStoredListener(builderId, new Procedure() {
	    					public void execute(Object... arguments) {
	    						CollectionUtils.addItem(LinkedEntityBundleBuilder.this.linkedEntities, arguments[0]);
	    					}
	    				});
	            	}
	            	else {
	                    throw new IllegalStateException("Object does not exist with id " + builderId);
	            	}
	            }
	            else if (!(restoredObject instanceof LinkedEntityBuilder)) {
	            	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + LinkedEntityBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
	            }
	            else {
	                CollectionUtils.addItem(this.linkedEntities, restoredObject);
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
     
     public LinkedEntityBuilder<?> getRoot() {
    	 return this.linkedEntities.get(0);
     }
     
}

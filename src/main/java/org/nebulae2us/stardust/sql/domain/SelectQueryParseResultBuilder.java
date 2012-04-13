package org.nebulae2us.stardust.sql.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;

@Builder(destination=SelectQueryParseResult.class)
public class SelectQueryParseResultBuilder<P> implements Wrappable<SelectQueryParseResult> {

	protected final SelectQueryParseResult $$$wrapped;

	protected final P $$$parentBuilder;
	
	public SelectQueryParseResultBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public SelectQueryParseResultBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected SelectQueryParseResultBuilder(SelectQueryParseResult wrapped) {
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public SelectQueryParseResultBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public SelectQueryParseResult getWrappedObject() {
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

    public SelectQueryParseResult toSelectQueryParseResult() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(SelectQueryParseResult.class);
    }



	private LinkedTableEntityBundleBuilder<?> linkedTableEntityBundle;
	
	public LinkedTableEntityBundleBuilder<?> getLinkedTableEntityBundle() {
		return linkedTableEntityBundle;
	}

	public void setLinkedTableEntityBundle(LinkedTableEntityBundleBuilder<?> linkedTableEntityBundle) {
		verifyMutable();
		this.linkedTableEntityBundle = linkedTableEntityBundle;
	}

	public SelectQueryParseResultBuilder<P> linkedTableEntityBundle(LinkedTableEntityBundleBuilder<?> linkedTableEntityBundle) {
		verifyMutable();
		this.linkedTableEntityBundle = linkedTableEntityBundle;
		return this;
	}

    public SelectQueryParseResultBuilder<P> linkedTableEntityBundle$wrap(LinkedTableEntityBundle linkedTableEntityBundle) {
    	verifyMutable();
    	this.linkedTableEntityBundle = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(linkedTableEntityBundle).to(LinkedTableEntityBundleBuilder.class);
        return this;
    }
    
    public SelectQueryParseResultBuilder<P> linkedTableEntityBundle$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						SelectQueryParseResultBuilder.this.linkedTableEntityBundle = (LinkedTableEntityBundleBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else if (!(restoredObject instanceof LinkedTableEntityBundleBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + LinkedTableEntityBundleBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.linkedTableEntityBundle = (LinkedTableEntityBundleBuilder<?>)restoredObject;
        }
        return this;
    }

	public LinkedTableEntityBundleBuilder<? extends SelectQueryParseResultBuilder<P>> linkedTableEntityBundle$begin() {
		verifyMutable();
		LinkedTableEntityBundleBuilder<SelectQueryParseResultBuilder<P>> result = new LinkedTableEntityBundleBuilder<SelectQueryParseResultBuilder<P>>(this);
		this.linkedTableEntityBundle = result;
		return result;
	}

    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

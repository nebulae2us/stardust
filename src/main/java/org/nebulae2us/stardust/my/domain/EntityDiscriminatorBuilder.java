package org.nebulae2us.stardust.my.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;
import org.nebulae2us.stardust.db.domain.*;
import org.nebulae2us.stardust.internal.util.Builders;

@Builder(destination=EntityDiscriminator.class)
public class EntityDiscriminatorBuilder<P> implements Wrappable<EntityDiscriminator> {

	protected final EntityDiscriminator $$$wrapped;

	protected final P $$$parentBuilder;
	
	public EntityDiscriminatorBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public EntityDiscriminatorBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected EntityDiscriminatorBuilder(EntityDiscriminator wrapped) {
		if (wrapped == null) {
			throw new NullPointerException();
		}
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public EntityDiscriminatorBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public EntityDiscriminator getWrappedObject() {
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

    public EntityDiscriminator toEntityDiscriminator() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(EntityDiscriminator.class);
    }



	private ColumnBuilder<?> column;
	
	public ColumnBuilder<?> getColumn() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.column, ColumnBuilder.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, EntityDiscriminator.class, "column");
			this.column = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(ColumnBuilder.class);
		}

		return column;
	}

	public void setColumn(ColumnBuilder<?> column) {
		verifyMutable();
		this.column = column;
	}

	public EntityDiscriminatorBuilder<P> column(ColumnBuilder<?> column) {
		verifyMutable();
		this.column = column;
		return this;
	}

    public EntityDiscriminatorBuilder<P> column$wrap(Column column) {
    	verifyMutable();
    	this.column = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(column).to(ColumnBuilder.class);
        return this;
    }
    
    public EntityDiscriminatorBuilder<P> column$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						EntityDiscriminatorBuilder.this.column = (ColumnBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else if (!(restoredObject instanceof ColumnBuilder)) {
        	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + ColumnBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
        }
        else {
            this.column = (ColumnBuilder<?>)restoredObject;
        }
        return this;
    }

	public ColumnBuilder<? extends EntityDiscriminatorBuilder<P>> column$begin() {
		verifyMutable();
		ColumnBuilder<EntityDiscriminatorBuilder<P>> result = new ColumnBuilder<EntityDiscriminatorBuilder<P>>(this);
		this.column = result;
		return result;
	}

	private Object value;
	
	public Object getValue() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.value, Object.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, EntityDiscriminator.class, "value");
			this.value = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(Object.class);
		}

		return value;
	}

	public void setValue(Object value) {
		verifyMutable();
		this.value = value;
	}

	public EntityDiscriminatorBuilder<P> value(Object value) {
		verifyMutable();
		this.value = value;
		return this;
	}

    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

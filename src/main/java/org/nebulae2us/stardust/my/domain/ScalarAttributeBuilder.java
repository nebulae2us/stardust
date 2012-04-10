package org.nebulae2us.stardust.my.domain;

import java.lang.reflect.*;
import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;
import org.nebulae2us.stardust.db.domain.*;

@Builder(destination=ScalarAttribute.class)
public class ScalarAttributeBuilder<P> extends AttributeBuilder<P> {

	public ScalarAttributeBuilder() {
		super();
	}
	
	public ScalarAttributeBuilder(P parentBuilder) {
		super(parentBuilder);
	}

	protected ScalarAttributeBuilder(ScalarAttribute wrapped) {
		super(wrapped);
	}

	@Override
    public ScalarAttributeBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	@Override
	public ScalarAttribute getWrappedObject() {
		return (ScalarAttribute)this.$$$wrapped;
	}

    public ScalarAttribute toScalarAttribute() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(ScalarAttribute.class);
    }
    

	@Override
    public ScalarAttribute toAttribute() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(ScalarAttribute.class);
    }
    


	private Class<?> scalarType;
	
	public Class<?> getScalarType() {
		return scalarType;
	}

	public void setScalarType(Class<?> scalarType) {
		verifyMutable();
		this.scalarType = scalarType;
	}

	public ScalarAttributeBuilder<P> scalarType(Class<?> scalarType) {
		verifyMutable();
		this.scalarType = scalarType;
		return this;
	}

	private ColumnBuilder<?> column;
	
	public ColumnBuilder<?> getColumn() {
		return column;
	}

	public void setColumn(ColumnBuilder<?> column) {
		verifyMutable();
		this.column = column;
	}

	public ScalarAttributeBuilder<P> column(ColumnBuilder<?> column) {
		verifyMutable();
		this.column = column;
		return this;
	}

    public ScalarAttributeBuilder<P> column$wrap(Column column) {
    	verifyMutable();
    	this.column = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(column).to(ColumnBuilder.class);
        return this;
    }
    
    public ScalarAttributeBuilder<P> column$restoreFrom(BuilderRepository repo, Object builderId) {
    	verifyMutable();
    	
        Object restoredObject = repo.get(builderId);
        if (restoredObject == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						ScalarAttributeBuilder.this.column = (ColumnBuilder<?>)arguments[0];
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

	public ColumnBuilder<? extends ScalarAttributeBuilder<P>> column$begin() {
		verifyMutable();
		ColumnBuilder<ScalarAttributeBuilder<P>> result = new ColumnBuilder<ScalarAttributeBuilder<P>>(this);
		this.column = result;
		return result;
	}

	@Override
	public ScalarAttributeBuilder<P> field(Field field) {
		return (ScalarAttributeBuilder<P>)super.field(field);
	}

	@Override
	public ScalarAttributeBuilder<P> owningEntity(EntityBuilder<?> owningEntity) {
		return (ScalarAttributeBuilder<P>)super.owningEntity(owningEntity);
	}

	@Override
    public ScalarAttributeBuilder<P> owningEntity$wrap(Entity owningEntity) {
		return (ScalarAttributeBuilder<P>)super.owningEntity$wrap(owningEntity);
    }

	@Override
    public ScalarAttributeBuilder<P> owningEntity$restoreFrom(BuilderRepository repo, Object builderId) {
		return (ScalarAttributeBuilder<P>)super.owningEntity$restoreFrom(repo, builderId);
    }

	@SuppressWarnings("unchecked")
	@Override
	public EntityBuilder<? extends ScalarAttributeBuilder<P>> owningEntity$begin() {
		return (EntityBuilder<? extends ScalarAttributeBuilder<P>>)super.owningEntity$begin();
	}
}

package org.nebulae2us.stardust.my.domain;

import java.lang.reflect.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.stardust.db.domain.*;


public class ScalarAttributeBuilder<B> extends AttributeBuilder<B> implements Convertable {

	private final ScalarAttribute $$$savedTarget;

	private ConverterOption $$$option;

	private final B $$$parentBuilder;

	protected ScalarAttributeBuilder(ScalarAttribute scalarAttribute) {
		super(scalarAttribute);
		if (scalarAttribute == null) {
			throw new NullPointerException();
		}
	
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = scalarAttribute;
	}

	public ScalarAttributeBuilder(ConverterOption option, B parentBuilder) {
		super(option, parentBuilder);
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = parentBuilder;
		this.$$$savedTarget = null;
	}

	public ScalarAttributeBuilder() {
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public ScalarAttributeBuilder(ConverterOption option) {
		super(option);
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
	
	public ScalarAttribute getSavedTarget() {
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

    protected void copyAttributes(ScalarAttributeBuilder<?> copy) {
		super.copyAttributes(copy);
    	this.scalarType = copy.scalarType;
		this.column = copy.column;
    }

    public B end() {
        return this.$$$parentBuilder;
    }

    public ScalarAttributeBuilder<B> storeTo(BuilderRepository repo, int builderId) {
    	repo.put(builderId, this);
    	return this;
    }

    public ScalarAttribute toScalarAttribute() {
    	return new Converter(this.$$$option).convert(this).to(ScalarAttribute.class);
    }

    @Override
    public ScalarAttribute toAttribute() {
    	return new Converter(this.$$$option).convert(this).to(ScalarAttribute.class);
    }

    private Class scalarType;

    public Class getScalarType() {
        return this.scalarType;
    }

    public void setScalarType(Class scalarType) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.scalarType = scalarType;
    }

    public ScalarAttributeBuilder<B> scalarType(Class scalarType) {
        this.scalarType = scalarType;
        return this;
    }

    private ColumnBuilder<?> column;

    public ColumnBuilder<?> getColumn() {
        return this.column;
    }

    public void setColumn(ColumnBuilder<?> column) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.column = column;
    }

    public ColumnBuilder<? extends ScalarAttributeBuilder<B>> column() {
        ColumnBuilder<ScalarAttributeBuilder<B>> column = new ColumnBuilder<ScalarAttributeBuilder<B>>(this.$$$option, this);
        this.column = column;
        
        return column;
    }

    public ScalarAttributeBuilder<B> column(ColumnBuilder<?> column) {
        this.column = column;
        return this;
    }

    public ScalarAttributeBuilder<B> column(Column column) {
    	this.column = new WrapConverter(this.$$$option).convert(column).to(ColumnBuilder.class);
        return this;
    }

    public ScalarAttributeBuilder<B> column$restoreFrom(BuilderRepository repo, int builderId) {
        Object column = repo.get(builderId);
        if (column == null) {
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
        else {
            this.column = (ColumnBuilder<?>)column;
        }
        return this;
    }

	@Override
    public ScalarAttributeBuilder<B> field(Field field) {
        return (ScalarAttributeBuilder<B>)super.field(field);
    }

    @SuppressWarnings("unchecked")
    @Override
    public EntityBuilder<? extends ScalarAttributeBuilder<B>> owningEntity() {
        return (EntityBuilder<ScalarAttributeBuilder<B>>)super.owningEntity();
    }
    
    public ScalarAttributeBuilder<B> owningEntity(EntityBuilder<?> owningEntity) {
		return (ScalarAttributeBuilder<B>)super.owningEntity(owningEntity);
    }

    public ScalarAttributeBuilder<B> owningEntity(Entity owningEntity) {
		return (ScalarAttributeBuilder<B>)super.owningEntity(owningEntity);
    }

    public ScalarAttributeBuilder<B> owningEntity$restoreFrom(BuilderRepository repo, int builderId) {
		return (ScalarAttributeBuilder<B>)super.owningEntity$restoreFrom(repo, builderId);
    }    
}

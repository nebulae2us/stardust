package org.nebulae2us.stardust.my.domain;

import java.lang.reflect.*;
import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.stardust.db.domain.*;


public class EntityAttributeBuilder<B> extends AttributeBuilder<B> implements Convertable {

	private final EntityAttribute $$$savedTarget;

	private ConverterOption $$$option;

	private final B $$$parentBuilder;

	protected EntityAttributeBuilder(EntityAttribute entityAttribute) {
		super(entityAttribute);
		if (entityAttribute == null) {
			throw new NullPointerException();
		}
	
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = entityAttribute;
	}

	public EntityAttributeBuilder(ConverterOption option, B parentBuilder) {
		super(option, parentBuilder);
		this.$$$option = option != null ? option : ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = parentBuilder;
		this.$$$savedTarget = null;
	}

	public EntityAttributeBuilder() {
		this.$$$option = ConverterOptions.EMPTY_IMMUTABLE_OPTION;
		this.$$$parentBuilder = null;
		this.$$$savedTarget = null;
	}
	
	public EntityAttributeBuilder(ConverterOption option) {
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
	
	public EntityAttribute getSavedTarget() {
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

    protected void copyAttributes(EntityAttributeBuilder<?> copy) {
		super.copyAttributes(copy);
    	this.entity = copy.entity;
		this.relationalType = copy.relationalType;
		this.joinType = copy.joinType;
		this.leftColumns = copy.leftColumns;
		this.rightColumns = copy.rightColumns;
		this.junctionLeftColumns = copy.junctionLeftColumns;
		this.junctionRightColumns = copy.junctionRightColumns;
    }

    public B end() {
        return this.$$$parentBuilder;
    }

    public EntityAttributeBuilder<B> storeTo(BuilderRepository repo, int builderId) {
    	repo.put(builderId, this);
    	return this;
    }

    public EntityAttribute toEntityAttribute() {
    	return new Converter(this.$$$option).convert(this).to(EntityAttribute.class);
    }

    @Override
    public EntityAttribute toAttribute() {
    	return new Converter(this.$$$option).convert(this).to(EntityAttribute.class);
    }

    private EntityBuilder<?> entity;

    public EntityBuilder<?> getEntity() {
        return this.entity;
    }

    public void setEntity(EntityBuilder<?> entity) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.entity = entity;
    }

    public EntityBuilder<? extends EntityAttributeBuilder<B>> entity() {
        EntityBuilder<EntityAttributeBuilder<B>> entity = new EntityBuilder<EntityAttributeBuilder<B>>(this.$$$option, this);
        this.entity = entity;
        
        return entity;
    }

    public EntityAttributeBuilder<B> entity(EntityBuilder<?> entity) {
        this.entity = entity;
        return this;
    }

    public EntityAttributeBuilder<B> entity(Entity entity) {
    	this.entity = new WrapConverter(this.$$$option).convert(entity).to(EntityBuilder.class);
        return this;
    }

    public EntityAttributeBuilder<B> entity$restoreFrom(BuilderRepository repo, int builderId) {
        Object entity = repo.get(builderId);
        if (entity == null) {
        	if (repo.isSupportLazy()) {
        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						EntityAttributeBuilder.this.entity = (EntityBuilder<?>)arguments[0];
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.entity = (EntityBuilder<?>)entity;
        }
        return this;
    }

    private RelationalType relationalType;

    public RelationalType getRelationalType() {
        return this.relationalType;
    }

    public void setRelationalType(RelationalType relationalType) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.relationalType = relationalType;
    }

    public EntityAttributeBuilder<B> relationalType(RelationalType relationalType) {
        this.relationalType = relationalType;
        return this;
    }

    private JoinType joinType;

    public JoinType getJoinType() {
        return this.joinType;
    }

    public void setJoinType(JoinType joinType) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.joinType = joinType;
    }

    public EntityAttributeBuilder<B> joinType(JoinType joinType) {
        this.joinType = joinType;
        return this;
    }

    private List<ColumnBuilder<?>> leftColumns;

    public List<ColumnBuilder<?>> getLeftColumns() {
        return this.leftColumns;
    }

    public void setLeftColumns(List<ColumnBuilder<?>> leftColumns) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.leftColumns = leftColumns;
    }

    public ColumnBuilder<EntityAttributeBuilder<B>> leftColumn() {
        if (this.leftColumns == null) {
            this.leftColumns = new ArrayList<ColumnBuilder<?>>();
        }

        ColumnBuilder<EntityAttributeBuilder<B>> leftColumn = new ColumnBuilder<EntityAttributeBuilder<B>>(this.$$$option, this);
        
        this.leftColumns.add(leftColumn);
        
        return leftColumn;
    }

    public EntityAttributeBuilder<B> leftColumn(ColumnBuilder<?> leftColumn) {
        if (this.leftColumns == null) {
            this.leftColumns = new ArrayList<ColumnBuilder<?>>();
        }
        this.leftColumns.add(leftColumn);
        return this;
    }

    public EntityAttributeBuilder<B> leftColumn(Column leftColumn) {
        if (this.leftColumns == null) {
            this.leftColumns = new ArrayList<ColumnBuilder<?>>();
        }
    	ColumnBuilder<?> wrap = new WrapConverter(this.$$$option).convert(leftColumn).to(ColumnBuilder.class);
        this.leftColumns.add(wrap);
        return this;
    }

    public EntityAttributeBuilder<B> leftColumns(ColumnBuilder<?> ... leftColumns) {
        if (this.leftColumns == null) {
            this.leftColumns = new ArrayList<ColumnBuilder<?>>();
        }
        for (ColumnBuilder<?> o : leftColumns) {
            this.leftColumns.add(o);
        }
        return this;
    }

    public EntityAttributeBuilder<B> leftColumns(Column ... leftColumns) {
        if (this.leftColumns == null) {
            this.leftColumns = new ArrayList<ColumnBuilder<?>>();
        }
        for (Column o : leftColumns) {
	    	ColumnBuilder<?> wrap = new WrapConverter(this.$$$option).convert(o).to(ColumnBuilder.class);
            this.leftColumns.add(wrap);
        }
        return this;
    }

    public EntityAttributeBuilder<B> leftColumn$restoreFrom(BuilderRepository repo, int builderId) {
        Object leftColumn = repo.get(builderId);
        if (this.leftColumns == null) {
            this.leftColumns = new ArrayList<ColumnBuilder<?>>();
        }

        if (leftColumn == null) {
        	if (repo.isSupportLazy()) {
                
        		final int size = this.leftColumns.size();
        		this.leftColumns.add(null);

        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						EntityAttributeBuilder.this.leftColumns.set(size, (ColumnBuilder<?>)arguments[0]);
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.leftColumns.add((ColumnBuilder<?>)leftColumn);
        }
    	
    	return this;
    }

    public EntityAttributeBuilder<B> leftColumns$restoreFrom(BuilderRepository repo, int ... builderIds) {

    	for (int builderId : builderIds) {
    		leftColumn$restoreFrom(repo, builderId);
    	}
    	
    	return this;
    }

    private List<ColumnBuilder<?>> rightColumns;

    public List<ColumnBuilder<?>> getRightColumns() {
        return this.rightColumns;
    }

    public void setRightColumns(List<ColumnBuilder<?>> rightColumns) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.rightColumns = rightColumns;
    }

    public ColumnBuilder<EntityAttributeBuilder<B>> rightColumn() {
        if (this.rightColumns == null) {
            this.rightColumns = new ArrayList<ColumnBuilder<?>>();
        }

        ColumnBuilder<EntityAttributeBuilder<B>> rightColumn = new ColumnBuilder<EntityAttributeBuilder<B>>(this.$$$option, this);
        
        this.rightColumns.add(rightColumn);
        
        return rightColumn;
    }

    public EntityAttributeBuilder<B> rightColumn(ColumnBuilder<?> rightColumn) {
        if (this.rightColumns == null) {
            this.rightColumns = new ArrayList<ColumnBuilder<?>>();
        }
        this.rightColumns.add(rightColumn);
        return this;
    }

    public EntityAttributeBuilder<B> rightColumn(Column rightColumn) {
        if (this.rightColumns == null) {
            this.rightColumns = new ArrayList<ColumnBuilder<?>>();
        }
    	ColumnBuilder<?> wrap = new WrapConverter(this.$$$option).convert(rightColumn).to(ColumnBuilder.class);
        this.rightColumns.add(wrap);
        return this;
    }

    public EntityAttributeBuilder<B> rightColumns(ColumnBuilder<?> ... rightColumns) {
        if (this.rightColumns == null) {
            this.rightColumns = new ArrayList<ColumnBuilder<?>>();
        }
        for (ColumnBuilder<?> o : rightColumns) {
            this.rightColumns.add(o);
        }
        return this;
    }

    public EntityAttributeBuilder<B> rightColumns(Column ... rightColumns) {
        if (this.rightColumns == null) {
            this.rightColumns = new ArrayList<ColumnBuilder<?>>();
        }
        for (Column o : rightColumns) {
	    	ColumnBuilder<?> wrap = new WrapConverter(this.$$$option).convert(o).to(ColumnBuilder.class);
            this.rightColumns.add(wrap);
        }
        return this;
    }

    public EntityAttributeBuilder<B> rightColumn$restoreFrom(BuilderRepository repo, int builderId) {
        Object rightColumn = repo.get(builderId);
        if (this.rightColumns == null) {
            this.rightColumns = new ArrayList<ColumnBuilder<?>>();
        }

        if (rightColumn == null) {
        	if (repo.isSupportLazy()) {
                
        		final int size = this.rightColumns.size();
        		this.rightColumns.add(null);

        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						EntityAttributeBuilder.this.rightColumns.set(size, (ColumnBuilder<?>)arguments[0]);
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.rightColumns.add((ColumnBuilder<?>)rightColumn);
        }
    	
    	return this;
    }

    public EntityAttributeBuilder<B> rightColumns$restoreFrom(BuilderRepository repo, int ... builderIds) {

    	for (int builderId : builderIds) {
    		rightColumn$restoreFrom(repo, builderId);
    	}
    	
    	return this;
    }

    private List<ColumnBuilder<?>> junctionLeftColumns;

    public List<ColumnBuilder<?>> getJunctionLeftColumns() {
        return this.junctionLeftColumns;
    }

    public void setJunctionLeftColumns(List<ColumnBuilder<?>> junctionLeftColumns) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.junctionLeftColumns = junctionLeftColumns;
    }

    public ColumnBuilder<EntityAttributeBuilder<B>> junctionLeftColumn() {
        if (this.junctionLeftColumns == null) {
            this.junctionLeftColumns = new ArrayList<ColumnBuilder<?>>();
        }

        ColumnBuilder<EntityAttributeBuilder<B>> junctionLeftColumn = new ColumnBuilder<EntityAttributeBuilder<B>>(this.$$$option, this);
        
        this.junctionLeftColumns.add(junctionLeftColumn);
        
        return junctionLeftColumn;
    }

    public EntityAttributeBuilder<B> junctionLeftColumn(ColumnBuilder<?> junctionLeftColumn) {
        if (this.junctionLeftColumns == null) {
            this.junctionLeftColumns = new ArrayList<ColumnBuilder<?>>();
        }
        this.junctionLeftColumns.add(junctionLeftColumn);
        return this;
    }

    public EntityAttributeBuilder<B> junctionLeftColumn(Column junctionLeftColumn) {
        if (this.junctionLeftColumns == null) {
            this.junctionLeftColumns = new ArrayList<ColumnBuilder<?>>();
        }
    	ColumnBuilder<?> wrap = new WrapConverter(this.$$$option).convert(junctionLeftColumn).to(ColumnBuilder.class);
        this.junctionLeftColumns.add(wrap);
        return this;
    }

    public EntityAttributeBuilder<B> junctionLeftColumns(ColumnBuilder<?> ... junctionLeftColumns) {
        if (this.junctionLeftColumns == null) {
            this.junctionLeftColumns = new ArrayList<ColumnBuilder<?>>();
        }
        for (ColumnBuilder<?> o : junctionLeftColumns) {
            this.junctionLeftColumns.add(o);
        }
        return this;
    }

    public EntityAttributeBuilder<B> junctionLeftColumns(Column ... junctionLeftColumns) {
        if (this.junctionLeftColumns == null) {
            this.junctionLeftColumns = new ArrayList<ColumnBuilder<?>>();
        }
        for (Column o : junctionLeftColumns) {
	    	ColumnBuilder<?> wrap = new WrapConverter(this.$$$option).convert(o).to(ColumnBuilder.class);
            this.junctionLeftColumns.add(wrap);
        }
        return this;
    }

    public EntityAttributeBuilder<B> junctionLeftColumn$restoreFrom(BuilderRepository repo, int builderId) {
        Object junctionLeftColumn = repo.get(builderId);
        if (this.junctionLeftColumns == null) {
            this.junctionLeftColumns = new ArrayList<ColumnBuilder<?>>();
        }

        if (junctionLeftColumn == null) {
        	if (repo.isSupportLazy()) {
                
        		final int size = this.junctionLeftColumns.size();
        		this.junctionLeftColumns.add(null);

        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						EntityAttributeBuilder.this.junctionLeftColumns.set(size, (ColumnBuilder<?>)arguments[0]);
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.junctionLeftColumns.add((ColumnBuilder<?>)junctionLeftColumn);
        }
    	
    	return this;
    }

    public EntityAttributeBuilder<B> junctionLeftColumns$restoreFrom(BuilderRepository repo, int ... builderIds) {

    	for (int builderId : builderIds) {
    		junctionLeftColumn$restoreFrom(repo, builderId);
    	}
    	
    	return this;
    }

    private List<ColumnBuilder<?>> junctionRightColumns;

    public List<ColumnBuilder<?>> getJunctionRightColumns() {
        return this.junctionRightColumns;
    }

    public void setJunctionRightColumns(List<ColumnBuilder<?>> junctionRightColumns) {
    	if (this.$$$savedTarget != null) {
    		throw new IllegalStateException("Cannot mutate fields of immutable objects");
    	}
        this.junctionRightColumns = junctionRightColumns;
    }

    public ColumnBuilder<EntityAttributeBuilder<B>> junctionRightColumn() {
        if (this.junctionRightColumns == null) {
            this.junctionRightColumns = new ArrayList<ColumnBuilder<?>>();
        }

        ColumnBuilder<EntityAttributeBuilder<B>> junctionRightColumn = new ColumnBuilder<EntityAttributeBuilder<B>>(this.$$$option, this);
        
        this.junctionRightColumns.add(junctionRightColumn);
        
        return junctionRightColumn;
    }

    public EntityAttributeBuilder<B> junctionRightColumn(ColumnBuilder<?> junctionRightColumn) {
        if (this.junctionRightColumns == null) {
            this.junctionRightColumns = new ArrayList<ColumnBuilder<?>>();
        }
        this.junctionRightColumns.add(junctionRightColumn);
        return this;
    }

    public EntityAttributeBuilder<B> junctionRightColumn(Column junctionRightColumn) {
        if (this.junctionRightColumns == null) {
            this.junctionRightColumns = new ArrayList<ColumnBuilder<?>>();
        }
    	ColumnBuilder<?> wrap = new WrapConverter(this.$$$option).convert(junctionRightColumn).to(ColumnBuilder.class);
        this.junctionRightColumns.add(wrap);
        return this;
    }

    public EntityAttributeBuilder<B> junctionRightColumns(ColumnBuilder<?> ... junctionRightColumns) {
        if (this.junctionRightColumns == null) {
            this.junctionRightColumns = new ArrayList<ColumnBuilder<?>>();
        }
        for (ColumnBuilder<?> o : junctionRightColumns) {
            this.junctionRightColumns.add(o);
        }
        return this;
    }

    public EntityAttributeBuilder<B> junctionRightColumns(Column ... junctionRightColumns) {
        if (this.junctionRightColumns == null) {
            this.junctionRightColumns = new ArrayList<ColumnBuilder<?>>();
        }
        for (Column o : junctionRightColumns) {
	    	ColumnBuilder<?> wrap = new WrapConverter(this.$$$option).convert(o).to(ColumnBuilder.class);
            this.junctionRightColumns.add(wrap);
        }
        return this;
    }

    public EntityAttributeBuilder<B> junctionRightColumn$restoreFrom(BuilderRepository repo, int builderId) {
        Object junctionRightColumn = repo.get(builderId);
        if (this.junctionRightColumns == null) {
            this.junctionRightColumns = new ArrayList<ColumnBuilder<?>>();
        }

        if (junctionRightColumn == null) {
        	if (repo.isSupportLazy()) {
                
        		final int size = this.junctionRightColumns.size();
        		this.junctionRightColumns.add(null);

        		repo.addObjectStoredListener(builderId, new Procedure() {
					public void execute(Object... arguments) {
						EntityAttributeBuilder.this.junctionRightColumns.set(size, (ColumnBuilder<?>)arguments[0]);
					}
				});
        	}
        	else {
                throw new IllegalStateException("Object does not exist with id " + builderId);
        	}
        }
        else {
            this.junctionRightColumns.add((ColumnBuilder<?>)junctionRightColumn);
        }
    	
    	return this;
    }

    public EntityAttributeBuilder<B> junctionRightColumns$restoreFrom(BuilderRepository repo, int ... builderIds) {

    	for (int builderId : builderIds) {
    		junctionRightColumn$restoreFrom(repo, builderId);
    	}
    	
    	return this;
    }

	@Override
    public EntityAttributeBuilder<B> field(Field field) {
        return (EntityAttributeBuilder<B>)super.field(field);
    }

    @SuppressWarnings("unchecked")
    @Override
    public EntityBuilder<? extends EntityAttributeBuilder<B>> owningEntity() {
        return (EntityBuilder<EntityAttributeBuilder<B>>)super.owningEntity();
    }
    
    public EntityAttributeBuilder<B> owningEntity(EntityBuilder<?> owningEntity) {
		return (EntityAttributeBuilder<B>)super.owningEntity(owningEntity);
    }

    public EntityAttributeBuilder<B> owningEntity(Entity owningEntity) {
		return (EntityAttributeBuilder<B>)super.owningEntity(owningEntity);
    }

    public EntityAttributeBuilder<B> owningEntity$restoreFrom(BuilderRepository repo, int builderId) {
		return (EntityAttributeBuilder<B>)super.owningEntity$restoreFrom(repo, builderId);
    }    
}

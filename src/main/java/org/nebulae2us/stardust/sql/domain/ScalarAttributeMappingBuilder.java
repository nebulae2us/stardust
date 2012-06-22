package org.nebulae2us.stardust.sql.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;
import org.nebulae2us.stardust.my.domain.*;

@Builder(destination=ScalarAttributeMapping.class)
public class ScalarAttributeMappingBuilder<P> extends AttributeMappingBuilder<P> {

	public ScalarAttributeMappingBuilder() {
		super();
	}
	
	public ScalarAttributeMappingBuilder(P parentBuilder) {
		super(parentBuilder);
	}

	protected ScalarAttributeMappingBuilder(ScalarAttributeMapping wrapped) {
		super(wrapped);
	}

	@Override
    public ScalarAttributeMappingBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	@Override
	public ScalarAttributeMapping getWrappedObject() {
		return (ScalarAttributeMapping)this.$$$wrapped;
	}

    public ScalarAttributeMapping toScalarAttributeMapping() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(ScalarAttributeMapping.class);
    }
    

	@Override
    public ScalarAttributeMapping toAttributeMapping() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(ScalarAttributeMapping.class);
    }
    


	private int columnIndex;
	
	public int getColumnIndex() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.columnIndex, int.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, ScalarAttributeMapping.class, "columnIndex");
			this.columnIndex = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(o).to(int.class);
		}

		return columnIndex;
	}

	public void setColumnIndex(int columnIndex) {
		verifyMutable();
		this.columnIndex = columnIndex;
	}

	public ScalarAttributeMappingBuilder<P> columnIndex(int columnIndex) {
		verifyMutable();
		this.columnIndex = columnIndex;
		return this;
	}

	@Override
	public ScalarAttributeMappingBuilder<P> attribute(AttributeBuilder<?> attribute) {
		return (ScalarAttributeMappingBuilder<P>)super.attribute(attribute);
	}

	@Override
    public ScalarAttributeMappingBuilder<P> attribute$wrap(Attribute attribute) {
		return (ScalarAttributeMappingBuilder<P>)super.attribute$wrap(attribute);
    }

	@Override
    public ScalarAttributeMappingBuilder<P> attribute$restoreFrom(BuilderRepository repo, Object builderId) {
		return (ScalarAttributeMappingBuilder<P>)super.attribute$restoreFrom(repo, builderId);
    }

	@SuppressWarnings("unchecked")
	@Override
	public AttributeBuilder<? extends ScalarAttributeMappingBuilder<P>> attribute$begin() {
		return (AttributeBuilder<? extends ScalarAttributeMappingBuilder<P>>)super.attribute$begin();
	}

	@Override
	public EntityAttributeBuilder<? extends ScalarAttributeMappingBuilder<P>> attribute$asEntityAttribute$begin() {
		return (EntityAttributeBuilder<? extends ScalarAttributeMappingBuilder<P>>)super.attribute$asEntityAttribute$begin();
	}

	@Override
	public ScalarAttributeBuilder<? extends ScalarAttributeMappingBuilder<P>> attribute$asScalarAttribute$begin() {
		return (ScalarAttributeBuilder<? extends ScalarAttributeMappingBuilder<P>>)super.attribute$asScalarAttribute$begin();
	}

	@Override
	public ValueObjectAttributeBuilder<? extends ScalarAttributeMappingBuilder<P>> attribute$asValueObjectAttribute$begin() {
		return (ValueObjectAttributeBuilder<? extends ScalarAttributeMappingBuilder<P>>)super.attribute$asValueObjectAttribute$begin();
	}

    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

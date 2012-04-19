package org.nebulae2us.stardust.expr.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;

@Builder(destination=InListExpression.class)
public class InListExpressionBuilder<P> extends ExpressionBuilder<P> {

	public InListExpressionBuilder() {
		super();
	}
	
	public InListExpressionBuilder(P parentBuilder) {
		super(parentBuilder);
	}

	protected InListExpressionBuilder(InListExpression<?> wrapped) {
		super(wrapped);
	}

	@Override
    public InListExpressionBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	@Override
	public InListExpression<?> getWrappedObject() {
		return (InListExpression<?>)this.$$$wrapped;
	}

    public InListExpression<?> toInListExpression() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(InListExpression.class);
    }
    
    public <T> InListExpression<T> toInListExpression(Class<T> T) {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(InListExpression.class);
    }
	

	@Override
    public InListExpression<?> toExpression() {
    	return new Converter(new DestinationClassResolverByAnnotation(), true).convert(this).to(InListExpression.class);
    }
    


	private List<?> values;
	
	public List<?> getValues() {
		if (this.$$$wrapped != null && WrapHelper.valueNotSet(this.values, List.class)) {
			Object o = WrapHelper.getValue(this.$$$wrapped, InListExpression.class, "values");
			this.values = new WrapConverter(ExprBuilders.DESTINATION_CLASS_RESOLVER).convert(o).to(List.class);
		}

		return values;
	}

	public void setValues(List<?> values) {
		verifyMutable();
		this.values = values;
	}

	public InListExpressionBuilder<P> values(Object ... values) {
		verifyMutable();
		return values(new ListBuilder<Object>().add(values).toList());
	}
	
	public InListExpressionBuilder<P> values(Collection<Object> values) {
		verifyMutable();
		if (this.values == null) {
			this.values = new ArrayList<Object>();
		}
		if (values != null) {
			for (Object e : values) {
				CollectionUtils.addItem(this.values, e);
			}
		}
		return this;
	}



	@Override
	public InListExpressionBuilder<P> negated(boolean negated) {
		return (InListExpressionBuilder<P>)super.negated(negated);
	}

    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

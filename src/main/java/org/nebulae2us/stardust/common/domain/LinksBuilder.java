package org.nebulae2us.stardust.common.domain;

import java.util.*;
import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.*;
import org.nebulae2us.stardust.db.domain.*;

@Builder(destination=Links.class)
public class LinksBuilder<P> implements Wrappable<Links<? extends LinkedNode>> {

	protected final Links<? extends LinkedNode> $$$wrapped;

	protected final P $$$parentBuilder;
	
	public LinksBuilder() {
		this.$$$wrapped = null;
		this.$$$parentBuilder = null;
	}
	
	public LinksBuilder(P parentBuilder) {
		this.$$$wrapped = null;
		this.$$$parentBuilder = parentBuilder;
	}

	protected LinksBuilder(Links<? extends LinkedNode> wrapped) {
		this.$$$wrapped = wrapped;
		this.$$$parentBuilder = null;
	}
	
    public LinksBuilder<P> storeTo(BuilderRepository repo, Object builderId) {
    	repo.put(builderId, this);
    	return this;
    }

	public Links<? extends LinkedNode> getWrappedObject() {
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

    public Links<? extends LinkedNode> toLinks() {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(Links.class);
    }

    public <T extends LinkedNode> Links<T> toLinks(Class<T> T) {
    	return new Converter(new BuilderAnnotationDestinationClassResolver(), true).convert(this).to(Links.class);
    }


	private List<? extends LinkedNodeBuilder> nodes;
	
	public List<? extends LinkedNodeBuilder> getNodes() {
		return nodes;
	}

	public void setNodes(List<? extends LinkedNodeBuilder> nodes) {
		verifyMutable();
		this.nodes = nodes;
	}

	public LinksBuilder<P> nodes(LinkedNodeBuilder ... nodes) {
		verifyMutable();
		return nodes(new ListBuilder<LinkedNodeBuilder>().add(nodes).toList());
	}
	
	public LinksBuilder<P> nodes(Collection<LinkedNodeBuilder> nodes) {
		verifyMutable();
		if (this.nodes == null) {
			this.nodes = new ArrayList<LinkedNodeBuilder>();
		}
		if (nodes != null) {
			for (LinkedNodeBuilder e : nodes) {
				CollectionUtils.addItem(this.nodes, e);
			}
		}
		return this;
	}

	public LinkedTableBuilder<? extends LinksBuilder<P>> nodes$addLinkedTable() {
		verifyMutable();
		if (this.nodes == null) {
			this.nodes = new ArrayList<LinkedNodeBuilder>();
		}
		
		LinkedTableBuilder<LinksBuilder<P>> result =
				new LinkedTableBuilder<LinksBuilder<P>>(this);
		
		CollectionUtils.addItem(this.nodes, result);
		
		return result;
	}
	

	public class Nodes$$$builder<P1 extends LinksBuilder<P>> {
	
		private final P1 $$$parentBuilder1;
	
		protected Nodes$$$builder(P1 parentBuilder) {
			this.$$$parentBuilder1 = parentBuilder;
		}

		public LinkedTableBuilder<Nodes$$$builder<P1>> linkedTable$begin() {
			LinkedTableBuilder<Nodes$$$builder<P1>> result = new LinkedTableBuilder<Nodes$$$builder<P1>>(this);
			CollectionUtils.addItem(LinksBuilder.this.nodes, result);
			return result;
		}
		

		public P1 end() {
			return this.$$$parentBuilder1;
		}
	}
	
	public Nodes$$$builder<? extends LinksBuilder<P>> nodes$list() {
		verifyMutable();
		if (this.nodes == null) {
			this.nodes = new ArrayList<LinkedNodeBuilder>();
		}
		return new Nodes$$$builder<LinksBuilder<P>>(this);
	}

    public LinksBuilder<P> nodes$wrap(LinkedNode ... nodes) {
    	return nodes$wrap(new ListBuilder<LinkedNode>().add(nodes).toList());
    }

    public LinksBuilder<P> nodes$wrap(Collection<? extends LinkedNode> nodes) {
		verifyMutable();

		if (this.nodes == null) {
			this.nodes = new ArrayList<LinkedNodeBuilder>();
		}
		if (nodes != null) {
			for (LinkedNode e : nodes) {
				LinkedNodeBuilder wrapped = new WrapConverter(Builders.DESTINATION_CLASS_RESOLVER).convert(e).to(LinkedNodeBuilder.class);
				CollectionUtils.addItem(this.nodes, wrapped);
			}
		}
		return this;
    }
    
    public LinksBuilder<P> nodes$restoreFrom(BuilderRepository repo, Object ... builderIds) {
    	return nodes$restoreFrom(repo, new ListBuilder<Object>().add(builderIds).toList());
    }

    public LinksBuilder<P> nodes$restoreFrom(BuilderRepository repo, Collection<Object> builderIds) {
		verifyMutable();

		if (this.nodes == null) {
			this.nodes = new ArrayList<LinkedNodeBuilder>();
		}
		if (builderIds != null) {
	    	for (Object builderId : builderIds) {
	            Object restoredObject = repo.get(builderId);
	            if (restoredObject == null) {
	            	if (repo.isSupportLazy()) {
	            		repo.addObjectStoredListener(builderId, new Procedure() {
	    					public void execute(Object... arguments) {
	    						CollectionUtils.addItem(LinksBuilder.this.nodes, arguments[0]);
	    					}
	    				});
	            	}
	            	else {
	                    throw new IllegalStateException("Object does not exist with id " + builderId);
	            	}
	            }
	            else if (!(restoredObject instanceof LinkedNodeBuilder)) {
	            	throw new IllegalStateException("Type mismatch for id: " + builderId + ". " + LinkedNodeBuilder.class.getSimpleName() + " vs " + restoredObject.getClass().getSimpleName());
	            }
	            else {
	                CollectionUtils.addItem(this.nodes, restoredObject);
	            }
	    	}
		}
        return this;
    }

}

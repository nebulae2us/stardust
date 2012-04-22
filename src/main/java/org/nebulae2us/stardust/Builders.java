
package org.nebulae2us.stardust;

import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.db.domain.Column;
import org.nebulae2us.stardust.db.domain.ColumnBuilder;
import org.nebulae2us.stardust.db.domain.LinkedTable;
import org.nebulae2us.stardust.db.domain.LinkedTableBuilder;
import org.nebulae2us.stardust.db.domain.LinkedTableBundle;
import org.nebulae2us.stardust.db.domain.LinkedTableBundleBuilder;
import org.nebulae2us.stardust.db.domain.Table;
import org.nebulae2us.stardust.db.domain.TableBuilder;
import org.nebulae2us.stardust.my.domain.Attribute;
import org.nebulae2us.stardust.my.domain.AttributeBuilder;
import org.nebulae2us.stardust.my.domain.AttributeHolder;
import org.nebulae2us.stardust.my.domain.AttributeHolderBuilder;
import org.nebulae2us.stardust.my.domain.EntityDiscriminator;
import org.nebulae2us.stardust.my.domain.EntityDiscriminatorBuilder;
import org.nebulae2us.stardust.sql.domain.AliasJoin;
import org.nebulae2us.stardust.sql.domain.AliasJoinBuilder;
import org.nebulae2us.stardust.sql.domain.AttributeMapping;
import org.nebulae2us.stardust.sql.domain.AttributeMappingBuilder;
import org.nebulae2us.stardust.sql.domain.EntityMapping;
import org.nebulae2us.stardust.sql.domain.EntityMappingBuilder;
import org.nebulae2us.stardust.sql.domain.LinkedEntity;
import org.nebulae2us.stardust.sql.domain.LinkedEntityBuilder;
import org.nebulae2us.stardust.sql.domain.LinkedEntityBundle;
import org.nebulae2us.stardust.sql.domain.LinkedEntityBundleBuilder;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntity;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntityBuilder;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntityBundle;
import org.nebulae2us.stardust.sql.domain.LinkedTableEntityBundleBuilder;
import org.nebulae2us.stardust.sql.domain.SelectQuery;
import org.nebulae2us.stardust.sql.domain.SelectQueryBuilder;
import org.nebulae2us.stardust.sql.domain.SelectQueryParseResult;
import org.nebulae2us.stardust.sql.domain.SelectQueryParseResultBuilder;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityBuilder;
import org.nebulae2us.stardust.my.domain.EntityAttribute;
import org.nebulae2us.stardust.my.domain.EntityAttributeBuilder;
import org.nebulae2us.stardust.my.domain.EntityIdentifier;
import org.nebulae2us.stardust.my.domain.EntityIdentifierBuilder;
import org.nebulae2us.stardust.my.domain.ScalarAttribute;
import org.nebulae2us.stardust.my.domain.ScalarAttributeBuilder;
import org.nebulae2us.stardust.my.domain.ValueObject;
import org.nebulae2us.stardust.my.domain.ValueObjectBuilder;
import org.nebulae2us.stardust.my.domain.ValueObjectAttribute;
import org.nebulae2us.stardust.my.domain.ValueObjectAttributeBuilder;
import org.nebulae2us.stardust.sql.domain.EntityAttributeMapping;
import org.nebulae2us.stardust.sql.domain.EntityAttributeMappingBuilder;
import org.nebulae2us.stardust.sql.domain.ScalarAttributeMapping;
import org.nebulae2us.stardust.sql.domain.ScalarAttributeMappingBuilder;
import org.nebulae2us.stardust.sql.domain.ValueObjectAttributeMapping;
import org.nebulae2us.stardust.sql.domain.ValueObjectAttributeMappingBuilder;

public class Builders {

	public static final DestinationClassResolver DESTINATION_CLASS_RESOLVER = new DestinationClassResolverByMap(
			new MapBuilder<Class<?>, Class<?>> ()
				.put(Column.class, ColumnBuilder.class)
				.put(LinkedTable.class, LinkedTableBuilder.class)
				.put(LinkedTableBundle.class, LinkedTableBundleBuilder.class)
				.put(Table.class, TableBuilder.class)
				.put(Attribute.class, AttributeBuilder.class)
				.put(AttributeHolder.class, AttributeHolderBuilder.class)
				.put(EntityDiscriminator.class, EntityDiscriminatorBuilder.class)
				.put(AliasJoin.class, AliasJoinBuilder.class)
				.put(AttributeMapping.class, AttributeMappingBuilder.class)
				.put(EntityMapping.class, EntityMappingBuilder.class)
				.put(LinkedEntity.class, LinkedEntityBuilder.class)
				.put(LinkedEntityBundle.class, LinkedEntityBundleBuilder.class)
				.put(LinkedTableEntity.class, LinkedTableEntityBuilder.class)
				.put(LinkedTableEntityBundle.class, LinkedTableEntityBundleBuilder.class)
				.put(SelectQuery.class, SelectQueryBuilder.class)
				.put(SelectQueryParseResult.class, SelectQueryParseResultBuilder.class)
				.put(Entity.class, EntityBuilder.class)
				.put(EntityAttribute.class, EntityAttributeBuilder.class)
				.put(EntityIdentifier.class, EntityIdentifierBuilder.class)
				.put(ScalarAttribute.class, ScalarAttributeBuilder.class)
				.put(ValueObject.class, ValueObjectBuilder.class)
				.put(ValueObjectAttribute.class, ValueObjectAttributeBuilder.class)
				.put(EntityAttributeMapping.class, EntityAttributeMappingBuilder.class)
				.put(ScalarAttributeMapping.class, ScalarAttributeMappingBuilder.class)
				.put(ValueObjectAttributeMapping.class, ValueObjectAttributeMappingBuilder.class)
			.toMap()
			);

	public static Converter converter() {
		return new Converter(DESTINATION_CLASS_RESOLVER, true);
	}

    public static ColumnBuilder<?> column() {
        return new ColumnBuilder<Object>();
    }

    public static ColumnBuilder<?> column$restoreFrom(BuilderRepository repo, int builderId) {
        return (ColumnBuilder<?>)repo.get(builderId);
    }

    public static ColumnBuilder<?> column$copyFrom(Column column) {
    	ColumnBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(column).to(ColumnBuilder.class);
    	return result;
    }
    
    public static ColumnBuilder<?> wrap(Column column) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(column).to(ColumnBuilder.class);
    }

    public static LinkedTableBuilder<?> linkedTable() {
        return new LinkedTableBuilder<Object>();
    }

    public static LinkedTableBuilder<?> linkedTable$restoreFrom(BuilderRepository repo, int builderId) {
        return (LinkedTableBuilder<?>)repo.get(builderId);
    }

    public static LinkedTableBuilder<?> linkedTable$copyFrom(LinkedTable linkedTable) {
    	LinkedTableBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(linkedTable).to(LinkedTableBuilder.class);
    	return result;
    }
    
    public static LinkedTableBuilder<?> wrap(LinkedTable linkedTable) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(linkedTable).to(LinkedTableBuilder.class);
    }

    public static LinkedTableBundleBuilder<?> linkedTableBundle() {
        return new LinkedTableBundleBuilder<Object>();
    }

    public static LinkedTableBundleBuilder<?> linkedTableBundle$restoreFrom(BuilderRepository repo, int builderId) {
        return (LinkedTableBundleBuilder<?>)repo.get(builderId);
    }

    public static LinkedTableBundleBuilder<?> linkedTableBundle$copyFrom(LinkedTableBundle linkedTableBundle) {
    	LinkedTableBundleBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(linkedTableBundle).to(LinkedTableBundleBuilder.class);
    	return result;
    }
    
    public static LinkedTableBundleBuilder<?> wrap(LinkedTableBundle linkedTableBundle) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(linkedTableBundle).to(LinkedTableBundleBuilder.class);
    }

    public static TableBuilder<?> table() {
        return new TableBuilder<Object>();
    }

    public static TableBuilder<?> table$restoreFrom(BuilderRepository repo, int builderId) {
        return (TableBuilder<?>)repo.get(builderId);
    }

    public static TableBuilder<?> table$copyFrom(Table table) {
    	TableBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(table).to(TableBuilder.class);
    	return result;
    }
    
    public static TableBuilder<?> wrap(Table table) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(table).to(TableBuilder.class);
    }

    public static AttributeBuilder<?> attribute() {
        return new AttributeBuilder<Object>();
    }

    public static AttributeBuilder<?> attribute$restoreFrom(BuilderRepository repo, int builderId) {
        return (AttributeBuilder<?>)repo.get(builderId);
    }

    public static AttributeBuilder<?> attribute$copyFrom(Attribute attribute) {
    	AttributeBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(attribute).to(AttributeBuilder.class);
    	return result;
    }
    
    public static AttributeBuilder<?> wrap(Attribute attribute) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(attribute).to(AttributeBuilder.class);
    }

    public static AttributeHolderBuilder<?> attributeHolder() {
        return new AttributeHolderBuilder<Object>();
    }

    public static AttributeHolderBuilder<?> attributeHolder$restoreFrom(BuilderRepository repo, int builderId) {
        return (AttributeHolderBuilder<?>)repo.get(builderId);
    }

    public static AttributeHolderBuilder<?> attributeHolder$copyFrom(AttributeHolder attributeHolder) {
    	AttributeHolderBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(attributeHolder).to(AttributeHolderBuilder.class);
    	return result;
    }
    
    public static AttributeHolderBuilder<?> wrap(AttributeHolder attributeHolder) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(attributeHolder).to(AttributeHolderBuilder.class);
    }

    public static EntityDiscriminatorBuilder<?> entityDiscriminator() {
        return new EntityDiscriminatorBuilder<Object>();
    }

    public static EntityDiscriminatorBuilder<?> entityDiscriminator$restoreFrom(BuilderRepository repo, int builderId) {
        return (EntityDiscriminatorBuilder<?>)repo.get(builderId);
    }

    public static EntityDiscriminatorBuilder<?> entityDiscriminator$copyFrom(EntityDiscriminator entityDiscriminator) {
    	EntityDiscriminatorBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(entityDiscriminator).to(EntityDiscriminatorBuilder.class);
    	return result;
    }
    
    public static EntityDiscriminatorBuilder<?> wrap(EntityDiscriminator entityDiscriminator) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(entityDiscriminator).to(EntityDiscriminatorBuilder.class);
    }

    public static AliasJoinBuilder<?> aliasJoin() {
        return new AliasJoinBuilder<Object>();
    }

    public static AliasJoinBuilder<?> aliasJoin$restoreFrom(BuilderRepository repo, int builderId) {
        return (AliasJoinBuilder<?>)repo.get(builderId);
    }

    public static AliasJoinBuilder<?> aliasJoin$copyFrom(AliasJoin aliasJoin) {
    	AliasJoinBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(aliasJoin).to(AliasJoinBuilder.class);
    	return result;
    }
    
    public static AliasJoinBuilder<?> wrap(AliasJoin aliasJoin) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(aliasJoin).to(AliasJoinBuilder.class);
    }

    public static AttributeMappingBuilder<?> attributeMapping() {
        return new AttributeMappingBuilder<Object>();
    }

    public static AttributeMappingBuilder<?> attributeMapping$restoreFrom(BuilderRepository repo, int builderId) {
        return (AttributeMappingBuilder<?>)repo.get(builderId);
    }

    public static AttributeMappingBuilder<?> attributeMapping$copyFrom(AttributeMapping attributeMapping) {
    	AttributeMappingBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(attributeMapping).to(AttributeMappingBuilder.class);
    	return result;
    }
    
    public static AttributeMappingBuilder<?> wrap(AttributeMapping attributeMapping) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(attributeMapping).to(AttributeMappingBuilder.class);
    }

    public static EntityMappingBuilder<?> entityMapping() {
        return new EntityMappingBuilder<Object>();
    }

    public static EntityMappingBuilder<?> entityMapping$restoreFrom(BuilderRepository repo, int builderId) {
        return (EntityMappingBuilder<?>)repo.get(builderId);
    }

    public static EntityMappingBuilder<?> entityMapping$copyFrom(EntityMapping entityMapping) {
    	EntityMappingBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(entityMapping).to(EntityMappingBuilder.class);
    	return result;
    }
    
    public static EntityMappingBuilder<?> wrap(EntityMapping entityMapping) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(entityMapping).to(EntityMappingBuilder.class);
    }

    public static LinkedEntityBuilder<?> linkedEntity() {
        return new LinkedEntityBuilder<Object>();
    }

    public static LinkedEntityBuilder<?> linkedEntity$restoreFrom(BuilderRepository repo, int builderId) {
        return (LinkedEntityBuilder<?>)repo.get(builderId);
    }

    public static LinkedEntityBuilder<?> linkedEntity$copyFrom(LinkedEntity linkedEntity) {
    	LinkedEntityBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(linkedEntity).to(LinkedEntityBuilder.class);
    	return result;
    }
    
    public static LinkedEntityBuilder<?> wrap(LinkedEntity linkedEntity) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(linkedEntity).to(LinkedEntityBuilder.class);
    }

    public static LinkedEntityBundleBuilder<?> linkedEntityBundle() {
        return new LinkedEntityBundleBuilder<Object>();
    }

    public static LinkedEntityBundleBuilder<?> linkedEntityBundle$restoreFrom(BuilderRepository repo, int builderId) {
        return (LinkedEntityBundleBuilder<?>)repo.get(builderId);
    }

    public static LinkedEntityBundleBuilder<?> linkedEntityBundle$copyFrom(LinkedEntityBundle linkedEntityBundle) {
    	LinkedEntityBundleBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(linkedEntityBundle).to(LinkedEntityBundleBuilder.class);
    	return result;
    }
    
    public static LinkedEntityBundleBuilder<?> wrap(LinkedEntityBundle linkedEntityBundle) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(linkedEntityBundle).to(LinkedEntityBundleBuilder.class);
    }

    public static LinkedTableEntityBuilder<?> linkedTableEntity() {
        return new LinkedTableEntityBuilder<Object>();
    }

    public static LinkedTableEntityBuilder<?> linkedTableEntity$restoreFrom(BuilderRepository repo, int builderId) {
        return (LinkedTableEntityBuilder<?>)repo.get(builderId);
    }

    public static LinkedTableEntityBuilder<?> linkedTableEntity$copyFrom(LinkedTableEntity linkedTableEntity) {
    	LinkedTableEntityBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(linkedTableEntity).to(LinkedTableEntityBuilder.class);
    	return result;
    }
    
    public static LinkedTableEntityBuilder<?> wrap(LinkedTableEntity linkedTableEntity) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(linkedTableEntity).to(LinkedTableEntityBuilder.class);
    }

    public static LinkedTableEntityBundleBuilder<?> linkedTableEntityBundle() {
        return new LinkedTableEntityBundleBuilder<Object>();
    }

    public static LinkedTableEntityBundleBuilder<?> linkedTableEntityBundle$restoreFrom(BuilderRepository repo, int builderId) {
        return (LinkedTableEntityBundleBuilder<?>)repo.get(builderId);
    }

    public static LinkedTableEntityBundleBuilder<?> linkedTableEntityBundle$copyFrom(LinkedTableEntityBundle linkedTableEntityBundle) {
    	LinkedTableEntityBundleBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(linkedTableEntityBundle).to(LinkedTableEntityBundleBuilder.class);
    	return result;
    }
    
    public static LinkedTableEntityBundleBuilder<?> wrap(LinkedTableEntityBundle linkedTableEntityBundle) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(linkedTableEntityBundle).to(LinkedTableEntityBundleBuilder.class);
    }

    public static SelectQueryBuilder<?> selectQuery() {
        return new SelectQueryBuilder<Object>();
    }

    public static SelectQueryBuilder<?> selectQuery$restoreFrom(BuilderRepository repo, int builderId) {
        return (SelectQueryBuilder<?>)repo.get(builderId);
    }

    public static SelectQueryBuilder<?> selectQuery$copyFrom(SelectQuery selectQuery) {
    	SelectQueryBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(selectQuery).to(SelectQueryBuilder.class);
    	return result;
    }
    
    public static SelectQueryBuilder<?> wrap(SelectQuery selectQuery) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(selectQuery).to(SelectQueryBuilder.class);
    }

    public static SelectQueryParseResultBuilder<?> selectQueryParseResult() {
        return new SelectQueryParseResultBuilder<Object>();
    }

    public static SelectQueryParseResultBuilder<?> selectQueryParseResult$restoreFrom(BuilderRepository repo, int builderId) {
        return (SelectQueryParseResultBuilder<?>)repo.get(builderId);
    }

    public static SelectQueryParseResultBuilder<?> selectQueryParseResult$copyFrom(SelectQueryParseResult selectQueryParseResult) {
    	SelectQueryParseResultBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(selectQueryParseResult).to(SelectQueryParseResultBuilder.class);
    	return result;
    }
    
    public static SelectQueryParseResultBuilder<?> wrap(SelectQueryParseResult selectQueryParseResult) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(selectQueryParseResult).to(SelectQueryParseResultBuilder.class);
    }

    public static EntityBuilder<?> entity() {
        return new EntityBuilder<Object>();
    }

    public static EntityBuilder<?> entity$restoreFrom(BuilderRepository repo, int builderId) {
        return (EntityBuilder<?>)repo.get(builderId);
    }

    public static EntityBuilder<?> entity$copyFrom(Entity entity) {
    	EntityBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(entity).to(EntityBuilder.class);
    	return result;
    }
    
    public static EntityBuilder<?> wrap(Entity entity) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(entity).to(EntityBuilder.class);
    }

    public static EntityAttributeBuilder<?> entityAttribute() {
        return new EntityAttributeBuilder<Object>();
    }

    public static EntityAttributeBuilder<?> entityAttribute$restoreFrom(BuilderRepository repo, int builderId) {
        return (EntityAttributeBuilder<?>)repo.get(builderId);
    }

    public static EntityAttributeBuilder<?> entityAttribute$copyFrom(EntityAttribute entityAttribute) {
    	EntityAttributeBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(entityAttribute).to(EntityAttributeBuilder.class);
    	return result;
    }
    
    public static EntityAttributeBuilder<?> wrap(EntityAttribute entityAttribute) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(entityAttribute).to(EntityAttributeBuilder.class);
    }

    public static EntityIdentifierBuilder<?> entityIdentifier() {
        return new EntityIdentifierBuilder<Object>();
    }

    public static EntityIdentifierBuilder<?> entityIdentifier$restoreFrom(BuilderRepository repo, int builderId) {
        return (EntityIdentifierBuilder<?>)repo.get(builderId);
    }

    public static EntityIdentifierBuilder<?> entityIdentifier$copyFrom(EntityIdentifier entityIdentifier) {
    	EntityIdentifierBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(entityIdentifier).to(EntityIdentifierBuilder.class);
    	return result;
    }
    
    public static EntityIdentifierBuilder<?> wrap(EntityIdentifier entityIdentifier) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(entityIdentifier).to(EntityIdentifierBuilder.class);
    }

    public static ScalarAttributeBuilder<?> scalarAttribute() {
        return new ScalarAttributeBuilder<Object>();
    }

    public static ScalarAttributeBuilder<?> scalarAttribute$restoreFrom(BuilderRepository repo, int builderId) {
        return (ScalarAttributeBuilder<?>)repo.get(builderId);
    }

    public static ScalarAttributeBuilder<?> scalarAttribute$copyFrom(ScalarAttribute scalarAttribute) {
    	ScalarAttributeBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(scalarAttribute).to(ScalarAttributeBuilder.class);
    	return result;
    }
    
    public static ScalarAttributeBuilder<?> wrap(ScalarAttribute scalarAttribute) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(scalarAttribute).to(ScalarAttributeBuilder.class);
    }

    public static ValueObjectBuilder<?> valueObject() {
        return new ValueObjectBuilder<Object>();
    }

    public static ValueObjectBuilder<?> valueObject$restoreFrom(BuilderRepository repo, int builderId) {
        return (ValueObjectBuilder<?>)repo.get(builderId);
    }

    public static ValueObjectBuilder<?> valueObject$copyFrom(ValueObject valueObject) {
    	ValueObjectBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(valueObject).to(ValueObjectBuilder.class);
    	return result;
    }
    
    public static ValueObjectBuilder<?> wrap(ValueObject valueObject) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(valueObject).to(ValueObjectBuilder.class);
    }

    public static ValueObjectAttributeBuilder<?> valueObjectAttribute() {
        return new ValueObjectAttributeBuilder<Object>();
    }

    public static ValueObjectAttributeBuilder<?> valueObjectAttribute$restoreFrom(BuilderRepository repo, int builderId) {
        return (ValueObjectAttributeBuilder<?>)repo.get(builderId);
    }

    public static ValueObjectAttributeBuilder<?> valueObjectAttribute$copyFrom(ValueObjectAttribute valueObjectAttribute) {
    	ValueObjectAttributeBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(valueObjectAttribute).to(ValueObjectAttributeBuilder.class);
    	return result;
    }
    
    public static ValueObjectAttributeBuilder<?> wrap(ValueObjectAttribute valueObjectAttribute) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(valueObjectAttribute).to(ValueObjectAttributeBuilder.class);
    }

    public static EntityAttributeMappingBuilder<?> entityAttributeMapping() {
        return new EntityAttributeMappingBuilder<Object>();
    }

    public static EntityAttributeMappingBuilder<?> entityAttributeMapping$restoreFrom(BuilderRepository repo, int builderId) {
        return (EntityAttributeMappingBuilder<?>)repo.get(builderId);
    }

    public static EntityAttributeMappingBuilder<?> entityAttributeMapping$copyFrom(EntityAttributeMapping entityAttributeMapping) {
    	EntityAttributeMappingBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(entityAttributeMapping).to(EntityAttributeMappingBuilder.class);
    	return result;
    }
    
    public static EntityAttributeMappingBuilder<?> wrap(EntityAttributeMapping entityAttributeMapping) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(entityAttributeMapping).to(EntityAttributeMappingBuilder.class);
    }

    public static ScalarAttributeMappingBuilder<?> scalarAttributeMapping() {
        return new ScalarAttributeMappingBuilder<Object>();
    }

    public static ScalarAttributeMappingBuilder<?> scalarAttributeMapping$restoreFrom(BuilderRepository repo, int builderId) {
        return (ScalarAttributeMappingBuilder<?>)repo.get(builderId);
    }

    public static ScalarAttributeMappingBuilder<?> scalarAttributeMapping$copyFrom(ScalarAttributeMapping scalarAttributeMapping) {
    	ScalarAttributeMappingBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(scalarAttributeMapping).to(ScalarAttributeMappingBuilder.class);
    	return result;
    }
    
    public static ScalarAttributeMappingBuilder<?> wrap(ScalarAttributeMapping scalarAttributeMapping) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(scalarAttributeMapping).to(ScalarAttributeMappingBuilder.class);
    }

    public static ValueObjectAttributeMappingBuilder<?> valueObjectAttributeMapping() {
        return new ValueObjectAttributeMappingBuilder<Object>();
    }

    public static ValueObjectAttributeMappingBuilder<?> valueObjectAttributeMapping$restoreFrom(BuilderRepository repo, int builderId) {
        return (ValueObjectAttributeMappingBuilder<?>)repo.get(builderId);
    }

    public static ValueObjectAttributeMappingBuilder<?> valueObjectAttributeMapping$copyFrom(ValueObjectAttributeMapping valueObjectAttributeMapping) {
    	ValueObjectAttributeMappingBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(valueObjectAttributeMapping).to(ValueObjectAttributeMappingBuilder.class);
    	return result;
    }
    
    public static ValueObjectAttributeMappingBuilder<?> wrap(ValueObjectAttributeMapping valueObjectAttributeMapping) {
    	return new WrapConverter(DESTINATION_CLASS_RESOLVER).convert(valueObjectAttributeMapping).to(ValueObjectAttributeMappingBuilder.class);
    }

    /* CUSTOM CODE *********************************
     * 
     * Put your own custom code below. These codes won't be discarded during generation.
     * 
     */
     
     
     
}

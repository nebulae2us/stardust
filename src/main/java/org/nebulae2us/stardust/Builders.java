
package org.nebulae2us.stardust;

import org.nebulae2us.electron.*;
import org.nebulae2us.electron.util.*;
import org.nebulae2us.stardust.db.domain.Column;
import org.nebulae2us.stardust.db.domain.ColumnBuilder;
import org.nebulae2us.stardust.db.domain.JoinedTables;
import org.nebulae2us.stardust.db.domain.JoinedTablesBuilder;
import org.nebulae2us.stardust.db.domain.Table;
import org.nebulae2us.stardust.db.domain.TableBuilder;
import org.nebulae2us.stardust.db.domain.TableJoin;
import org.nebulae2us.stardust.db.domain.TableJoinBuilder;
import org.nebulae2us.stardust.expr.domain.Expression;
import org.nebulae2us.stardust.expr.domain.ExpressionBuilder;
import org.nebulae2us.stardust.my.domain.Attribute;
import org.nebulae2us.stardust.my.domain.AttributeBuilder;
import org.nebulae2us.stardust.my.domain.AttributeHolder;
import org.nebulae2us.stardust.my.domain.AttributeHolderBuilder;
import org.nebulae2us.stardust.my.domain.EntityIdentifier;
import org.nebulae2us.stardust.my.domain.EntityIdentifierBuilder;
import org.nebulae2us.stardust.sql.domain.AliasJoin;
import org.nebulae2us.stardust.sql.domain.AliasJoinBuilder;
import org.nebulae2us.stardust.sql.domain.EntityJoin;
import org.nebulae2us.stardust.sql.domain.EntityJoinBuilder;
import org.nebulae2us.stardust.sql.domain.RelationalEntities;
import org.nebulae2us.stardust.sql.domain.RelationalEntitiesBuilder;
import org.nebulae2us.stardust.sql.domain.SelectQuery;
import org.nebulae2us.stardust.sql.domain.SelectQueryBuilder;
import org.nebulae2us.stardust.sql.domain.SelectQueryParseResult;
import org.nebulae2us.stardust.sql.domain.SelectQueryParseResultBuilder;
import org.nebulae2us.stardust.expr.domain.LogicalExpression;
import org.nebulae2us.stardust.expr.domain.LogicalExpressionBuilder;
import org.nebulae2us.stardust.my.domain.Entity;
import org.nebulae2us.stardust.my.domain.EntityBuilder;
import org.nebulae2us.stardust.my.domain.EntityAttribute;
import org.nebulae2us.stardust.my.domain.EntityAttributeBuilder;
import org.nebulae2us.stardust.my.domain.ScalarAttribute;
import org.nebulae2us.stardust.my.domain.ScalarAttributeBuilder;
import org.nebulae2us.stardust.my.domain.ValueObject;
import org.nebulae2us.stardust.my.domain.ValueObjectBuilder;
import org.nebulae2us.stardust.my.domain.ValueObjectAttribute;
import org.nebulae2us.stardust.my.domain.ValueObjectAttributeBuilder;

public class Builders {

	public static final DestinationClassResolver DESTINATION_CLASS_RESOLVER = new DestinationClassResolverByMap(
			new MapBuilder<Class<?>, Class<?>> ()
				.put(Column.class, ColumnBuilder.class)
				.put(JoinedTables.class, JoinedTablesBuilder.class)
				.put(Table.class, TableBuilder.class)
				.put(TableJoin.class, TableJoinBuilder.class)
				.put(Expression.class, ExpressionBuilder.class)
				.put(Attribute.class, AttributeBuilder.class)
				.put(AttributeHolder.class, AttributeHolderBuilder.class)
				.put(EntityIdentifier.class, EntityIdentifierBuilder.class)
				.put(AliasJoin.class, AliasJoinBuilder.class)
				.put(EntityJoin.class, EntityJoinBuilder.class)
				.put(RelationalEntities.class, RelationalEntitiesBuilder.class)
				.put(SelectQuery.class, SelectQueryBuilder.class)
				.put(SelectQueryParseResult.class, SelectQueryParseResultBuilder.class)
				.put(LogicalExpression.class, LogicalExpressionBuilder.class)
				.put(Entity.class, EntityBuilder.class)
				.put(EntityAttribute.class, EntityAttributeBuilder.class)
				.put(ScalarAttribute.class, ScalarAttributeBuilder.class)
				.put(ValueObject.class, ValueObjectBuilder.class)
				.put(ValueObjectAttribute.class, ValueObjectAttributeBuilder.class)
			.toMap()
			);


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

    public static JoinedTablesBuilder<?> joinedTables() {
        return new JoinedTablesBuilder<Object>();
    }

    public static JoinedTablesBuilder<?> joinedTables$restoreFrom(BuilderRepository repo, int builderId) {
        return (JoinedTablesBuilder<?>)repo.get(builderId);
    }

    public static JoinedTablesBuilder<?> joinedTables$copyFrom(JoinedTables joinedTables) {
    	JoinedTablesBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(joinedTables).to(JoinedTablesBuilder.class);
    	return result;
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

    public static TableJoinBuilder<?> tableJoin() {
        return new TableJoinBuilder<Object>();
    }

    public static TableJoinBuilder<?> tableJoin$restoreFrom(BuilderRepository repo, int builderId) {
        return (TableJoinBuilder<?>)repo.get(builderId);
    }

    public static TableJoinBuilder<?> tableJoin$copyFrom(TableJoin tableJoin) {
    	TableJoinBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(tableJoin).to(TableJoinBuilder.class);
    	return result;
    }

    public static ExpressionBuilder<?> expression() {
        return new ExpressionBuilder<Object>();
    }

    public static ExpressionBuilder<?> expression$restoreFrom(BuilderRepository repo, int builderId) {
        return (ExpressionBuilder<?>)repo.get(builderId);
    }

    public static ExpressionBuilder<?> expression$copyFrom(Expression expression) {
    	ExpressionBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(expression).to(ExpressionBuilder.class);
    	return result;
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

    public static EntityJoinBuilder<?> entityJoin() {
        return new EntityJoinBuilder<Object>();
    }

    public static EntityJoinBuilder<?> entityJoin$restoreFrom(BuilderRepository repo, int builderId) {
        return (EntityJoinBuilder<?>)repo.get(builderId);
    }

    public static EntityJoinBuilder<?> entityJoin$copyFrom(EntityJoin entityJoin) {
    	EntityJoinBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(entityJoin).to(EntityJoinBuilder.class);
    	return result;
    }

    public static RelationalEntitiesBuilder<?> relationalEntities() {
        return new RelationalEntitiesBuilder<Object>();
    }

    public static RelationalEntitiesBuilder<?> relationalEntities$restoreFrom(BuilderRepository repo, int builderId) {
        return (RelationalEntitiesBuilder<?>)repo.get(builderId);
    }

    public static RelationalEntitiesBuilder<?> relationalEntities$copyFrom(RelationalEntities relationalEntities) {
    	RelationalEntitiesBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(relationalEntities).to(RelationalEntitiesBuilder.class);
    	return result;
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

    public static LogicalExpressionBuilder<?> logicalExpression() {
        return new LogicalExpressionBuilder<Object>();
    }

    public static LogicalExpressionBuilder<?> logicalExpression$restoreFrom(BuilderRepository repo, int builderId) {
        return (LogicalExpressionBuilder<?>)repo.get(builderId);
    }

    public static LogicalExpressionBuilder<?> logicalExpression$copyFrom(LogicalExpression logicalExpression) {
    	LogicalExpressionBuilder<?> result = new Converter(DESTINATION_CLASS_RESOLVER, false).convert(logicalExpression).to(LogicalExpressionBuilder.class);
    	return result;
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
}

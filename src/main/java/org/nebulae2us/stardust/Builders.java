
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

	public static final ConverterOption CONVERTER_OPTIONS_IMMUTABLE = new ConverterOption(
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
			.toMap(),
			true
			);

	public static final ConverterOption CONVERTER_OPTIONS_MUTABLE = new ConverterOption(
			CONVERTER_OPTIONS_IMMUTABLE.getAssociates(),
			false
			);


    public static ColumnBuilder<?> column() {
        return new ColumnBuilder<Object>(CONVERTER_OPTIONS_IMMUTABLE);
    }

    public static ColumnBuilder<?> column$restoreFrom(BuilderRepository repo, int builderId) {
        return (ColumnBuilder<?>)repo.get(builderId);
    }

    public static ColumnBuilder<?> column$copyFrom(Column column) {
    	ColumnBuilder<?> result = new Converter(CONVERTER_OPTIONS_MUTABLE).convert(column).to(ColumnBuilder.class);
    	result.setConverterOption(CONVERTER_OPTIONS_IMMUTABLE);
    	return result;
    }

    public static JoinedTablesBuilder<?> joinedTables() {
        return new JoinedTablesBuilder<Object>(CONVERTER_OPTIONS_IMMUTABLE);
    }

    public static JoinedTablesBuilder<?> joinedTables$restoreFrom(BuilderRepository repo, int builderId) {
        return (JoinedTablesBuilder<?>)repo.get(builderId);
    }

    public static JoinedTablesBuilder<?> joinedTables$copyFrom(JoinedTables joinedTables) {
    	JoinedTablesBuilder<?> result = new Converter(CONVERTER_OPTIONS_MUTABLE).convert(joinedTables).to(JoinedTablesBuilder.class);
    	result.setConverterOption(CONVERTER_OPTIONS_IMMUTABLE);
    	return result;
    }

    public static TableBuilder<?> table() {
        return new TableBuilder<Object>(CONVERTER_OPTIONS_IMMUTABLE);
    }

    public static TableBuilder<?> table$restoreFrom(BuilderRepository repo, int builderId) {
        return (TableBuilder<?>)repo.get(builderId);
    }

    public static TableBuilder<?> table$copyFrom(Table table) {
    	TableBuilder<?> result = new Converter(CONVERTER_OPTIONS_MUTABLE).convert(table).to(TableBuilder.class);
    	result.setConverterOption(CONVERTER_OPTIONS_IMMUTABLE);
    	return result;
    }

    public static TableJoinBuilder<?> tableJoin() {
        return new TableJoinBuilder<Object>(CONVERTER_OPTIONS_IMMUTABLE);
    }

    public static TableJoinBuilder<?> tableJoin$restoreFrom(BuilderRepository repo, int builderId) {
        return (TableJoinBuilder<?>)repo.get(builderId);
    }

    public static TableJoinBuilder<?> tableJoin$copyFrom(TableJoin tableJoin) {
    	TableJoinBuilder<?> result = new Converter(CONVERTER_OPTIONS_MUTABLE).convert(tableJoin).to(TableJoinBuilder.class);
    	result.setConverterOption(CONVERTER_OPTIONS_IMMUTABLE);
    	return result;
    }

    public static ExpressionBuilder<?> expression() {
        return new ExpressionBuilder<Object>(CONVERTER_OPTIONS_IMMUTABLE);
    }

    public static ExpressionBuilder<?> expression$restoreFrom(BuilderRepository repo, int builderId) {
        return (ExpressionBuilder<?>)repo.get(builderId);
    }

    public static ExpressionBuilder<?> expression$copyFrom(Expression expression) {
    	ExpressionBuilder<?> result = new Converter(CONVERTER_OPTIONS_MUTABLE).convert(expression).to(ExpressionBuilder.class);
    	result.setConverterOption(CONVERTER_OPTIONS_IMMUTABLE);
    	return result;
    }

    public static AttributeBuilder<?> attribute() {
        return new AttributeBuilder<Object>(CONVERTER_OPTIONS_IMMUTABLE);
    }

    public static AttributeBuilder<?> attribute$restoreFrom(BuilderRepository repo, int builderId) {
        return (AttributeBuilder<?>)repo.get(builderId);
    }

    public static AttributeBuilder<?> attribute$copyFrom(Attribute attribute) {
    	AttributeBuilder<?> result = new Converter(CONVERTER_OPTIONS_MUTABLE).convert(attribute).to(AttributeBuilder.class);
    	result.setConverterOption(CONVERTER_OPTIONS_IMMUTABLE);
    	return result;
    }

    public static AttributeHolderBuilder<?> attributeHolder() {
        return new AttributeHolderBuilder<Object>(CONVERTER_OPTIONS_IMMUTABLE);
    }

    public static AttributeHolderBuilder<?> attributeHolder$restoreFrom(BuilderRepository repo, int builderId) {
        return (AttributeHolderBuilder<?>)repo.get(builderId);
    }

    public static AttributeHolderBuilder<?> attributeHolder$copyFrom(AttributeHolder attributeHolder) {
    	AttributeHolderBuilder<?> result = new Converter(CONVERTER_OPTIONS_MUTABLE).convert(attributeHolder).to(AttributeHolderBuilder.class);
    	result.setConverterOption(CONVERTER_OPTIONS_IMMUTABLE);
    	return result;
    }

    public static EntityIdentifierBuilder<?> entityIdentifier() {
        return new EntityIdentifierBuilder<Object>(CONVERTER_OPTIONS_IMMUTABLE);
    }

    public static EntityIdentifierBuilder<?> entityIdentifier$restoreFrom(BuilderRepository repo, int builderId) {
        return (EntityIdentifierBuilder<?>)repo.get(builderId);
    }

    public static EntityIdentifierBuilder<?> entityIdentifier$copyFrom(EntityIdentifier entityIdentifier) {
    	EntityIdentifierBuilder<?> result = new Converter(CONVERTER_OPTIONS_MUTABLE).convert(entityIdentifier).to(EntityIdentifierBuilder.class);
    	result.setConverterOption(CONVERTER_OPTIONS_IMMUTABLE);
    	return result;
    }

    public static AliasJoinBuilder<?> aliasJoin() {
        return new AliasJoinBuilder<Object>(CONVERTER_OPTIONS_IMMUTABLE);
    }

    public static AliasJoinBuilder<?> aliasJoin$restoreFrom(BuilderRepository repo, int builderId) {
        return (AliasJoinBuilder<?>)repo.get(builderId);
    }

    public static AliasJoinBuilder<?> aliasJoin$copyFrom(AliasJoin aliasJoin) {
    	AliasJoinBuilder<?> result = new Converter(CONVERTER_OPTIONS_MUTABLE).convert(aliasJoin).to(AliasJoinBuilder.class);
    	result.setConverterOption(CONVERTER_OPTIONS_IMMUTABLE);
    	return result;
    }

    public static EntityJoinBuilder<?> entityJoin() {
        return new EntityJoinBuilder<Object>(CONVERTER_OPTIONS_IMMUTABLE);
    }

    public static EntityJoinBuilder<?> entityJoin$restoreFrom(BuilderRepository repo, int builderId) {
        return (EntityJoinBuilder<?>)repo.get(builderId);
    }

    public static EntityJoinBuilder<?> entityJoin$copyFrom(EntityJoin entityJoin) {
    	EntityJoinBuilder<?> result = new Converter(CONVERTER_OPTIONS_MUTABLE).convert(entityJoin).to(EntityJoinBuilder.class);
    	result.setConverterOption(CONVERTER_OPTIONS_IMMUTABLE);
    	return result;
    }

    public static RelationalEntitiesBuilder<?> relationalEntities() {
        return new RelationalEntitiesBuilder<Object>(CONVERTER_OPTIONS_IMMUTABLE);
    }

    public static RelationalEntitiesBuilder<?> relationalEntities$restoreFrom(BuilderRepository repo, int builderId) {
        return (RelationalEntitiesBuilder<?>)repo.get(builderId);
    }

    public static RelationalEntitiesBuilder<?> relationalEntities$copyFrom(RelationalEntities relationalEntities) {
    	RelationalEntitiesBuilder<?> result = new Converter(CONVERTER_OPTIONS_MUTABLE).convert(relationalEntities).to(RelationalEntitiesBuilder.class);
    	result.setConverterOption(CONVERTER_OPTIONS_IMMUTABLE);
    	return result;
    }

    public static SelectQueryBuilder<?> selectQuery() {
        return new SelectQueryBuilder<Object>(CONVERTER_OPTIONS_IMMUTABLE);
    }

    public static SelectQueryBuilder<?> selectQuery$restoreFrom(BuilderRepository repo, int builderId) {
        return (SelectQueryBuilder<?>)repo.get(builderId);
    }

    public static SelectQueryBuilder<?> selectQuery$copyFrom(SelectQuery selectQuery) {
    	SelectQueryBuilder<?> result = new Converter(CONVERTER_OPTIONS_MUTABLE).convert(selectQuery).to(SelectQueryBuilder.class);
    	result.setConverterOption(CONVERTER_OPTIONS_IMMUTABLE);
    	return result;
    }

    public static SelectQueryParseResultBuilder<?> selectQueryParseResult() {
        return new SelectQueryParseResultBuilder<Object>(CONVERTER_OPTIONS_IMMUTABLE);
    }

    public static SelectQueryParseResultBuilder<?> selectQueryParseResult$restoreFrom(BuilderRepository repo, int builderId) {
        return (SelectQueryParseResultBuilder<?>)repo.get(builderId);
    }

    public static SelectQueryParseResultBuilder<?> selectQueryParseResult$copyFrom(SelectQueryParseResult selectQueryParseResult) {
    	SelectQueryParseResultBuilder<?> result = new Converter(CONVERTER_OPTIONS_MUTABLE).convert(selectQueryParseResult).to(SelectQueryParseResultBuilder.class);
    	result.setConverterOption(CONVERTER_OPTIONS_IMMUTABLE);
    	return result;
    }

    public static LogicalExpressionBuilder<?> logicalExpression() {
        return new LogicalExpressionBuilder<Object>(CONVERTER_OPTIONS_IMMUTABLE);
    }

    public static LogicalExpressionBuilder<?> logicalExpression$restoreFrom(BuilderRepository repo, int builderId) {
        return (LogicalExpressionBuilder<?>)repo.get(builderId);
    }

    public static LogicalExpressionBuilder<?> logicalExpression$copyFrom(LogicalExpression logicalExpression) {
    	LogicalExpressionBuilder<?> result = new Converter(CONVERTER_OPTIONS_MUTABLE).convert(logicalExpression).to(LogicalExpressionBuilder.class);
    	result.setConverterOption(CONVERTER_OPTIONS_IMMUTABLE);
    	return result;
    }

    public static EntityBuilder<?> entity() {
        return new EntityBuilder<Object>(CONVERTER_OPTIONS_IMMUTABLE);
    }

    public static EntityBuilder<?> entity$restoreFrom(BuilderRepository repo, int builderId) {
        return (EntityBuilder<?>)repo.get(builderId);
    }

    public static EntityBuilder<?> entity$copyFrom(Entity entity) {
    	EntityBuilder<?> result = new Converter(CONVERTER_OPTIONS_MUTABLE).convert(entity).to(EntityBuilder.class);
    	result.setConverterOption(CONVERTER_OPTIONS_IMMUTABLE);
    	return result;
    }

    public static EntityAttributeBuilder<?> entityAttribute() {
        return new EntityAttributeBuilder<Object>(CONVERTER_OPTIONS_IMMUTABLE);
    }

    public static EntityAttributeBuilder<?> entityAttribute$restoreFrom(BuilderRepository repo, int builderId) {
        return (EntityAttributeBuilder<?>)repo.get(builderId);
    }

    public static EntityAttributeBuilder<?> entityAttribute$copyFrom(EntityAttribute entityAttribute) {
    	EntityAttributeBuilder<?> result = new Converter(CONVERTER_OPTIONS_MUTABLE).convert(entityAttribute).to(EntityAttributeBuilder.class);
    	result.setConverterOption(CONVERTER_OPTIONS_IMMUTABLE);
    	return result;
    }

    public static ScalarAttributeBuilder<?> scalarAttribute() {
        return new ScalarAttributeBuilder<Object>(CONVERTER_OPTIONS_IMMUTABLE);
    }

    public static ScalarAttributeBuilder<?> scalarAttribute$restoreFrom(BuilderRepository repo, int builderId) {
        return (ScalarAttributeBuilder<?>)repo.get(builderId);
    }

    public static ScalarAttributeBuilder<?> scalarAttribute$copyFrom(ScalarAttribute scalarAttribute) {
    	ScalarAttributeBuilder<?> result = new Converter(CONVERTER_OPTIONS_MUTABLE).convert(scalarAttribute).to(ScalarAttributeBuilder.class);
    	result.setConverterOption(CONVERTER_OPTIONS_IMMUTABLE);
    	return result;
    }

    public static ValueObjectBuilder<?> valueObject() {
        return new ValueObjectBuilder<Object>(CONVERTER_OPTIONS_IMMUTABLE);
    }

    public static ValueObjectBuilder<?> valueObject$restoreFrom(BuilderRepository repo, int builderId) {
        return (ValueObjectBuilder<?>)repo.get(builderId);
    }

    public static ValueObjectBuilder<?> valueObject$copyFrom(ValueObject valueObject) {
    	ValueObjectBuilder<?> result = new Converter(CONVERTER_OPTIONS_MUTABLE).convert(valueObject).to(ValueObjectBuilder.class);
    	result.setConverterOption(CONVERTER_OPTIONS_IMMUTABLE);
    	return result;
    }

    public static ValueObjectAttributeBuilder<?> valueObjectAttribute() {
        return new ValueObjectAttributeBuilder<Object>(CONVERTER_OPTIONS_IMMUTABLE);
    }

    public static ValueObjectAttributeBuilder<?> valueObjectAttribute$restoreFrom(BuilderRepository repo, int builderId) {
        return (ValueObjectAttributeBuilder<?>)repo.get(builderId);
    }

    public static ValueObjectAttributeBuilder<?> valueObjectAttribute$copyFrom(ValueObjectAttribute valueObjectAttribute) {
    	ValueObjectAttributeBuilder<?> result = new Converter(CONVERTER_OPTIONS_MUTABLE).convert(valueObjectAttribute).to(ValueObjectAttributeBuilder.class);
    	result.setConverterOption(CONVERTER_OPTIONS_IMMUTABLE);
    	return result;
    }
}

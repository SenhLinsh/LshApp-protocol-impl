package com.linsh.protocol.impl.db;

import com.linsh.protocol.Consumer;
import com.linsh.protocol.Task;
import com.linsh.protocol.db.QueryBuilder;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Callable;

import io.realm.Case;
import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmQuery;
import io.realm.Sort;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/22
 *    desc   :
 * </pre>
 */
class QueryBuilderImpl<T> implements QueryBuilder<T> {

    private final List<Consumer<RealmQuery<? extends RealmModel>>> consumers;
    private final Class<? extends RealmModel> clazz;

    QueryBuilderImpl(Class<T> clazz) {
        if (!RealmModel.class.isAssignableFrom(clazz))
            throw new IllegalArgumentException("clazz 必须为 RealmModel 的子类, 当前为: " + clazz);
        this.clazz = (Class<? extends RealmModel>) clazz;
        consumers = new LinkedList<>();
    }

    @Override
    public QueryBuilder equalTo(final String fieldName, final Object value) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                if (value instanceof String) {
                    realmQuery.equalTo(fieldName, (String) value);
                } else if (value instanceof Boolean) {
                    realmQuery.equalTo(fieldName, (Boolean) value);
                } else if (value instanceof Integer) {
                    realmQuery.equalTo(fieldName, (Integer) value);
                } else if (value instanceof Long) {
                    realmQuery.equalTo(fieldName, (Long) value);
                } else if (value instanceof Float) {
                    realmQuery.equalTo(fieldName, (Float) value);
                } else if (value instanceof Double) {
                    realmQuery.equalTo(fieldName, (Double) value);
                } else if (value instanceof Date) {
                    realmQuery.equalTo(fieldName, (Date) value);
                } else {
                    throw new IllegalArgumentException("value 为不支持的类型: " + value.getClass());
                }
            }
        });
        return this;
    }

    @Override
    public QueryBuilder notEqualTo(final String fieldName, final Object value) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                if (value instanceof String) {
                    realmQuery.notEqualTo(fieldName, (String) value);
                } else if (value instanceof Boolean) {
                    realmQuery.notEqualTo(fieldName, (Boolean) value);
                } else if (value instanceof Integer) {
                    realmQuery.notEqualTo(fieldName, (Integer) value);
                } else if (value instanceof Long) {
                    realmQuery.notEqualTo(fieldName, (Long) value);
                } else if (value instanceof Float) {
                    realmQuery.notEqualTo(fieldName, (Float) value);
                } else if (value instanceof Double) {
                    realmQuery.notEqualTo(fieldName, (Double) value);
                } else if (value instanceof Date) {
                    realmQuery.notEqualTo(fieldName, (Date) value);
                } else {
                    throw new IllegalArgumentException("value 为不支持的类型: " + value.getClass());
                }
            }
        });
        return this;
    }

    @Override
    public QueryBuilder in(final String fieldName, final Object... values) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                if (values instanceof String[]) {
                    realmQuery.in(fieldName, (String[]) values);
                } else if (values instanceof Boolean[]) {
                    realmQuery.in(fieldName, (Boolean[]) values);
                } else if (values instanceof Integer[]) {
                    realmQuery.in(fieldName, (Integer[]) values);
                } else if (values instanceof Long[]) {
                    realmQuery.in(fieldName, (Long[]) values);
                } else if (values instanceof Float[]) {
                    realmQuery.in(fieldName, (Float[]) values);
                } else if (values instanceof Double[]) {
                    realmQuery.in(fieldName, (Double[]) values);
                } else if (values instanceof Date[]) {
                    realmQuery.in(fieldName, (Date[]) values);
                } else {
                    throw new IllegalArgumentException("values 为不支持的类型: " + values.getClass());
                }
            }
        });
        return this;
    }

    @Override
    public <R extends Number> QueryBuilder greaterThan(final String fieldName, final R value) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                if (value instanceof Integer) {
                    realmQuery.greaterThan(fieldName, (Integer) value);
                } else if (value instanceof Long) {
                    realmQuery.greaterThan(fieldName, (Long) value);
                } else if (value instanceof Float) {
                    realmQuery.greaterThan(fieldName, (Float) value);
                } else if (value instanceof Double) {
                    realmQuery.greaterThan(fieldName, (Double) value);
                } else {
                    throw new IllegalArgumentException("value 为不支持的类型: " + value.getClass());
                }
            }
        });
        return this;
    }

    @Override
    public <R extends Number> QueryBuilder greaterThanOrEqualTo(final String fieldName, final R value) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                if (value instanceof Integer) {
                    realmQuery.greaterThanOrEqualTo(fieldName, (Integer) value);
                } else if (value instanceof Long) {
                    realmQuery.greaterThanOrEqualTo(fieldName, (Long) value);
                } else if (value instanceof Float) {
                    realmQuery.greaterThanOrEqualTo(fieldName, (Float) value);
                } else if (value instanceof Double) {
                    realmQuery.greaterThanOrEqualTo(fieldName, (Double) value);
                } else {
                    throw new IllegalArgumentException("value 为不支持的类型: " + value.getClass());
                }
            }
        });
        return this;
    }

    @Override
    public <R extends Number> QueryBuilder lessThan(final String fieldName, final R value) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                if (value instanceof Integer) {
                    realmQuery.lessThan(fieldName, (Integer) value);
                } else if (value instanceof Long) {
                    realmQuery.lessThan(fieldName, (Long) value);
                } else if (value instanceof Float) {
                    realmQuery.lessThan(fieldName, (Float) value);
                } else if (value instanceof Double) {
                    realmQuery.lessThan(fieldName, (Double) value);
                } else {
                    throw new IllegalArgumentException("value 为不支持的类型: " + value.getClass());
                }
            }
        });
        return this;
    }

    @Override
    public <R extends Number> QueryBuilder lessThanOrEqualTo(final String fieldName, final R value) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                if (value instanceof Integer) {
                    realmQuery.lessThan(fieldName, (Integer) value);
                } else if (value instanceof Long) {
                    realmQuery.lessThan(fieldName, (Long) value);
                } else if (value instanceof Float) {
                    realmQuery.lessThan(fieldName, (Float) value);
                } else if (value instanceof Double) {
                    realmQuery.lessThan(fieldName, (Double) value);
                } else {
                    throw new IllegalArgumentException("value 为不支持的类型: " + value.getClass());
                }
            }
        });
        return this;
    }

    @Override
    public <R extends Number> QueryBuilder between(final String fieldName, final R from, final R to) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                if (from instanceof Integer) {
                    realmQuery.between(fieldName, (Integer) from, (Integer) to);
                } else if (from instanceof Long) {
                    realmQuery.between(fieldName, (Long) from, (Long) to);
                } else if (from instanceof Float) {
                    realmQuery.between(fieldName, (Float) from, (Float) to);
                } else if (from instanceof Double) {
                    realmQuery.between(fieldName, (Double) from, (Double) to);
                } else {
                    throw new IllegalArgumentException("from 为不支持的类型: " + from.getClass());
                }
            }
        });
        return this;
    }

    @Override
    public QueryBuilder contains(final String fieldName, final String value) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.contains(fieldName, value);
            }
        });
        return this;
    }

    @Override
    public QueryBuilder beginsWith(final String fieldName, final String value) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.beginsWith(fieldName, value);
            }
        });
        return this;
    }

    @Override
    public QueryBuilder beginsWith(final String fieldName, final String value, final boolean isCaseSensitive) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.contains(fieldName, value, isCaseSensitive ? Case.SENSITIVE : Case.INSENSITIVE);
            }
        });
        return this;
    }

    @Override
    public QueryBuilder endsWith(final String fieldName, final String value) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.endsWith(fieldName, value);
            }
        });
        return this;
    }

    @Override
    public QueryBuilder endsWith(final String fieldName, final String value, final boolean isCaseSensitive) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.endsWith(fieldName, value, isCaseSensitive ? Case.SENSITIVE : Case.INSENSITIVE);
            }
        });
        return this;
    }

    @Override
    public QueryBuilder like(final String fieldName, final String value) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.like(fieldName, value);
            }
        });
        return this;
    }

    @Override
    public QueryBuilder like(final String fieldName, final String value, final boolean isCaseSensitive) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.like(fieldName, value, isCaseSensitive ? Case.SENSITIVE : Case.INSENSITIVE);
            }
        });
        return this;
    }

    @Override
    public QueryBuilder distinct(final String fieldName) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.distinct(fieldName);
            }
        });
        return this;
    }

    @Override
    public QueryBuilder isNull(final String fieldName) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.isNull(fieldName);
            }
        });
        return this;
    }

    @Override
    public QueryBuilder isNotNull(final String fieldName) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.isNotNull(fieldName);
            }
        });
        return this;
    }

    @Override
    public QueryBuilder isEmpty(final String fieldName) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.isEmpty(fieldName);
            }
        });
        return this;
    }

    @Override
    public QueryBuilder isNotEmpty(final String fieldName) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.isNotEmpty(fieldName);
            }
        });
        return this;
    }

    @Override
    public QueryBuilder sortAsc(final String fieldName) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.sort(fieldName, Sort.ASCENDING);
            }
        });
        return this;
    }

    @Override
    public QueryBuilder sortAsc(final String fieldName1, final String fieldName2) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.sort(fieldName1, Sort.ASCENDING, fieldName2, Sort.ASCENDING);
            }
        });
        return this;
    }

    @Override
    public QueryBuilder sortDesc(final String fieldName) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.sort(fieldName, Sort.DESCENDING);
            }
        });
        return this;
    }

    @Override
    public QueryBuilder sortDesc(final String fieldName1, final String fieldName2) {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.sort(fieldName1, Sort.DESCENDING, fieldName2, Sort.DESCENDING);
            }
        });
        return this;
    }

    @Override
    public QueryBuilder not() {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.not();
            }
        });
        return this;
    }

    @Override
    public QueryBuilder and() {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.and();
            }
        });
        return this;
    }

    @Override
    public QueryBuilder or() {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.or();
            }
        });
        return this;
    }

    @Override
    public QueryBuilder beginGroup() {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.beginGroup();
            }
        });
        return this;
    }

    @Override
    public QueryBuilder endGroup() {
        consumers.add(new Consumer<RealmQuery<? extends RealmModel>>() {
            @Override
            public void accept(RealmQuery<? extends RealmModel> realmQuery) throws Exception {
                realmQuery.endGroup();
            }
        });
        return this;
    }

    @Override
    public Task<Long> count() {
        return new RealmTask<>(new Callable<Long>() {
            @Override
            public Long call() throws Exception {
                return buildQuery().count();
            }
        });
    }

    @Override
    public Task<Number> max(final String fieldName) {
        return new RealmTask<>(new Callable<Number>() {
            @Override
            public Number call() throws Exception {
                return buildQuery().max(fieldName);
            }
        });
    }

    @Override
    public Task<Number> min(final String fieldName) {
        return new RealmTask<>(new Callable<Number>() {
            @Override
            public Number call() throws Exception {
                return buildQuery().min(fieldName);
            }
        });
    }

    @Override
    public Task<Number> sum(final String fieldName) {
        return new RealmTask<>(new Callable<Number>() {
            @Override
            public Number call() throws Exception {
                return buildQuery().sum(fieldName);
            }
        });
    }

    @Override
    public Task<Double> average(final String fieldName) {
        return new RealmTask<>(new Callable<Double>() {
            @Override
            public Double call() throws Exception {
                return buildQuery().average(fieldName);
            }
        });
    }

    @Override
    public Task<T> findFirst() {
        return new RealmTask<>(new Callable<T>() {
            @Override
            public T call() throws Exception {
                return (T) buildQuery().findFirst();
            }
        });
    }

    @Override
    public Task<List<T>> findAll() {
        return new RealmTask<>(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                return (List<T>) buildQuery().findAll();
            }
        });
    }

    private RealmQuery<? extends RealmModel> buildQuery() throws Exception {
        Realm realm = Realm.getDefaultInstance();
        RealmQuery<? extends RealmModel> query = realm.where(clazz);
        for (Consumer<RealmQuery<? extends RealmModel>> consumer : consumers) {
            consumer.accept(query);
        }
        return query;
    }
}

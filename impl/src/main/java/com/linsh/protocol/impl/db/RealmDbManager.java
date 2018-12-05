package com.linsh.protocol.impl.db;

import com.linsh.protocol.Result;
import com.linsh.protocol.Task;
import com.linsh.protocol.config.DbConfig;
import com.linsh.protocol.db.DbManager;
import com.linsh.protocol.db.QueryBuilder;
import com.linsh.protocol.impl.ResultImpl;

import java.util.List;
import java.util.concurrent.Callable;

import io.realm.Realm;
import io.realm.RealmModel;
import io.realm.RealmObject;
import io.realm.RealmQuery;
import io.realm.RealmResults;

/**
 * <pre>
 *    author : Senh Linsh
 *    github : https://github.com/SenhLinsh
 *    date   : 2018/11/22
 *    desc   :
 * </pre>
 */
public class RealmDbManager implements DbManager {

    private final DbConfig config;

    public RealmDbManager(DbConfig config) {
        this.config = config;
    }

    @Override
    public <T> QueryBuilder<T> query(Class<T> clazz) {
        return new QueryBuilderImpl<>(clazz);
    }

    @Override
    public <T> Task<List<T>> queryAll(Class<T> clazz) {
        checkRealmModel(clazz);
        final Class<? extends RealmModel> modelClazz = (Class<? extends RealmModel>) clazz;
        return new RealmTask<>(new Callable<List<T>>() {
            @Override
            public List<T> call() throws Exception {
                Realm realm = Realm.getDefaultInstance();
                RealmResults<? extends RealmModel> results = realm.where(modelClazz).findAll();
                return (List<T>) realm.copyFromRealm(results);
            }
        });
    }

    @Override
    public Task<Result> update(final Object entity) {
        checkRealmModel(entity);
        final RealmModel model = (RealmModel) entity;
        return new RealmTask<>(new Callable<Result>() {
            @Override
            public Result call() throws Exception {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.insertOrUpdate(model);
                realm.commitTransaction();
                return new ResultImpl();
            }
        });
    }

    @Override
    public Task<Result> delete(Object entity) {
        checkRealmModel(entity);
        final RealmModel model = (RealmModel) entity;
        return new RealmTask<>(new Callable<Result>() {
            @Override
            public Result call() throws Exception {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                RealmObject.deleteFromRealm(realm.copyToRealmOrUpdate(model));
                realm.commitTransaction();
                return new ResultImpl();
            }
        });
    }

    @Override
    public <T> Task<Result> deleteById(final Class<T> clazz, final Object value) {
        return deleteByKey(clazz, config.fieldNameForId(), value);
    }

    @Override
    public <T> Task<Result> deleteByKey(Class<T> clazz, final String fieldName, final Object value) {
        checkRealmModel(clazz);
        final Class<? extends RealmModel> modelClazz = (Class<? extends RealmModel>) clazz;
        return new RealmTask<>(new Callable<Result>() {
            @Override
            public Result call() throws Exception {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                RealmQuery<? extends RealmModel> query = realm.where(modelClazz);
                if (value instanceof String) {
                    query.equalTo(fieldName, (String) value);
                } else if (value instanceof Integer) {
                    query.equalTo(fieldName, (Integer) value);
                } else if (value instanceof Long) {
                    query.equalTo(fieldName, (Long) value);
                } else if (value instanceof Double) {
                    query.equalTo(fieldName, (Double) value);
                } else if (value instanceof Float) {
                    query.equalTo(fieldName, (Float) value);
                } else if (value instanceof Boolean) {
                    query.equalTo(fieldName, (Boolean) value);
                } else {
                    throw new IllegalArgumentException("value 的类型不正确: " + value.getClass());
                }
                query.findAll().deleteAllFromRealm();
                realm.commitTransaction();
                return new ResultImpl();
            }
        });
    }

    @Override
    public <T> Task<Result> deleteAll(List<T> entities) {
        if (entities == null || entities.size() == 0) {
            throw new IllegalArgumentException("无法传入空集合");
        }
        checkRealmModel(entities.get(0));
        final List<? extends RealmModel> models = (List<? extends RealmModel>) entities;
        return new RealmTask<>(new Callable<Result>() {
            @Override
            public Result call() throws Exception {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                List<? extends RealmModel> copies = realm.copyToRealmOrUpdate(models);
                for (RealmModel copy : copies) {
                    RealmObject.deleteFromRealm(copy);
                }
                realm.commitTransaction();
                return new ResultImpl();
            }
        });
    }

    @Override
    public Task<Result> deleteAll(Class<?> clazz) {
        checkRealmModel(clazz);
        final Class<? extends RealmModel> modelClazz = (Class<? extends RealmModel>) clazz;
        return new RealmTask<>(new Callable<Result>() {
            @Override
            public Result call() throws Exception {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.delete(modelClazz);
                realm.commitTransaction();
                return new ResultImpl();
            }
        });
    }

    @Override
    public Task<Result> insert(final Object entity) {
        checkRealmModel(entity);
        final RealmModel model = (RealmModel) entity;
        return new RealmTask<>(new Callable<Result>() {
            @Override
            public Result call() throws Exception {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.insert(model);
                realm.commitTransaction();
                return new ResultImpl();
            }
        });
    }

    @Override
    public Task<Result> insert(List<?> entities) {
        if (entities == null || entities.size() == 0) {
            throw new IllegalArgumentException("无法传入空集合");
        }
        checkRealmModel(entities.get(0));
        final List<? extends RealmModel> models = (List<? extends RealmModel>) entities;
        return new RealmTask<>(new Callable<Result>() {
            @Override
            public Result call() throws Exception {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.insert(models);
                realm.commitTransaction();
                return new ResultImpl();
            }
        });
    }

    @Override
    public Task<Result> insertOrUpdate(Object entity) {
        checkRealmModel(entity);
        final RealmModel model = (RealmModel) entity;
        return new RealmTask<>(new Callable<Result>() {
            @Override
            public Result call() throws Exception {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.insertOrUpdate(model);
                realm.commitTransaction();
                return new ResultImpl();
            }
        });
    }

    @Override
    public Task<Result> insertOrUpdate(List<?> entities) {
        if (entities == null || entities.size() == 0) {
            throw new IllegalArgumentException("无法传入空集合");
        }
        checkRealmModel(entities.get(0));
        final List<? extends RealmModel> models = (List<? extends RealmModel>) entities;
        return new RealmTask<>(new Callable<Result>() {
            @Override
            public Result call() throws Exception {
                Realm realm = Realm.getDefaultInstance();
                realm.beginTransaction();
                realm.insertOrUpdate(models);
                realm.commitTransaction();
                return new ResultImpl();
            }
        });
    }

    private void checkRealmModel(Object entity) {
        if (!(entity instanceof RealmModel))
            throw new IllegalArgumentException("entity 必须为 RealmModel 的实例");
    }

    private void checkRealmModel(Class<?> clazz) {
        if (!RealmModel.class.isAssignableFrom(clazz))
            throw new IllegalArgumentException("clazz 必须为的实现类");
    }
}

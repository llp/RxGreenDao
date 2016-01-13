package de.greenrobot.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import de.greenrobot.dao.internal.DaoConfig;
import de.greenrobot.dao.query.Query;
import de.greenrobot.dao.query.RxQuery;
import de.greenrobot.dao.query.RxQueryBuilder;
import rx.Observable;
import rx.Subscriber;
import rx.exceptions.Exceptions;

public abstract class RxAbstractDao<T, K> extends AbstractDao<T, K> {

    public RxAbstractDao(DaoConfig config) {
        this(config, null);
    }

    public RxAbstractDao(DaoConfig config, AbstractDaoSession daoSession) {
        super(config, daoSession);
    }

    public RxQueryBuilder<T> rxQueryBuilder() {
        return RxQueryBuilder.internalCreate(this);
    }


    public Observable<AbstractDaoSession> getRxSession() {
        return Observable.create(new Observable.OnSubscribe<AbstractDaoSession>() {
            @Override
            public void call(Subscriber<? super AbstractDaoSession> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(getSession());
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }


    public Observable<String> getRxTablename() {
        return Observable.create(new Observable.OnSubscribe<String>() {
            @Override
            public void call(Subscriber<? super String> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(getTablename());
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<Property[]> getRxProperties() {
        return Observable.create(new Observable.OnSubscribe<Property[]>() {
            @Override
            public void call(Subscriber<? super Property[]> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(getProperties());
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<Property> getRxPkProperty() {
        return Observable.create(new Observable.OnSubscribe<Property>() {
            @Override
            public void call(Subscriber<? super Property> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(getPkProperty());
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<String[]> getRxAllColumns() {
        return Observable.create(new Observable.OnSubscribe<String[]>() {
            @Override
            public void call(Subscriber<? super String[]> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(getAllColumns());
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<String[]> getRxPkColumns() {
        return Observable.create(new Observable.OnSubscribe<String[]>() {
            @Override
            public void call(Subscriber<? super String[]> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(getPkColumns());
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    public Observable<String[]> getRxNonPkColumns() {
        return Observable.create(new Observable.OnSubscribe<String[]>() {
            @Override
            public void call(Subscriber<? super String[]> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(getNonPkColumns());
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }


    /**
     * Gets the SQLiteDatabase for custom database access. Not needed for greenDAO entities.
     */
    public SQLiteDatabase getDatabase() {
        return db;
    }


    /**
     * Loads and entity for the given PK.
     *
     * @param key a PK value or null
     * @return The entity or null, if no entity matched the PK value
     */
    public Observable<T> rxLoad(final K key) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(load(key));
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }


    public Observable<T> rxLoadByRowId(final long rowId) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(loadByRowId(rowId));
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Loads all available entities from the database.
     */
    public Observable<List<T>> rxLoadAll() {
        return Observable.create(new Observable.OnSubscribe<List<T>>() {
            @Override
            public void call(Subscriber<? super List<T>> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(loadAll());
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Detaches an entity from the identity scope (session). Subsequent query results won't return this object.
     */
    public Observable<Boolean> rxDetach(final T entity) {
        return Observable.create(new Observable.OnSubscribe<Boolean>() {
            @Override
            public void call(Subscriber<? super Boolean> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(detach(entity));
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Inserts the given entities in the database using a transaction.
     *
     * @param entities The entities to insert.
     */
    public Observable<Void> rxInsertInTx(final Iterable<T> entities) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        insertInTx(entities);
                        subscriber.onNext(null);
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Inserts the given entities in the database using a transaction.
     *
     * @param entities The entities to insert.
     */
    public Observable<Void> rxInsertInTx(final T... entities) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        insertInTx(entities);
                        subscriber.onNext(null);
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Inserts the given entities in the database using a transaction. The given entities will become tracked if the PK
     * is set.
     *
     * @param entities      The entities to insert.
     * @param setPrimaryKey if true, the PKs of the given will be set after the insert; pass false to improve performance.
     */
    public Observable<Void> rxInsertInTx(final Iterable<T> entities, final boolean setPrimaryKey) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        insertInTx(entities, setPrimaryKey);
                        subscriber.onNext(null);
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Inserts or replaces the given entities in the database using a transaction. The given entities will become
     * tracked if the PK is set.
     *
     * @param entities      The entities to insert.
     * @param setPrimaryKey if true, the PKs of the given will be set after the insert; pass false to improve performance.
     */
    public Observable<Void> rxInsertOrReplaceInTx(final Iterable<T> entities, final boolean setPrimaryKey) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        insertOrReplaceInTx(entities, setPrimaryKey);
                        subscriber.onNext(null);
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }


    /**
     * Inserts or replaces the given entities in the database using a transaction.
     *
     * @param entities The entities to insert.
     */
    public Observable<Void> rxInsertOrReplaceInTx(final Iterable<T> entities) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        insertOrReplaceInTx(entities);
                        subscriber.onNext(null);
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Inserts or replaces the given entities in the database using a transaction.
     *
     * @param entities The entities to insert.
     */
    public Observable<Void> rxInsertOrReplaceInTx(final T... entities) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        insertOrReplaceInTx(entities);
                        subscriber.onNext(null);
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Insert an entity into the table associated with a concrete DAO.
     *
     * @return row ID of newly inserted entity
     */
    public Observable<Long> rxInsert(final T entity) {
        return Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(insert(entity));
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }


    /**
     * Insert an entity into the table associated with a concrete DAO <b>without</b> setting key property. Warning: This
     * may be faster, but the entity should not be used anymore. The entity also won't be attached to identy scope.
     *
     * @return row ID of newly inserted entity
     */
    public Observable<Long> rxInsertWithoutSettingPk(final T entity) {
        return Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(insertWithoutSettingPk(entity));
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Insert an entity into the table associated with a concrete DAO.
     *
     * @return row ID of newly inserted entity
     */
    public Observable<Long> rxInsertOrReplace(final T entity) {
        return Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(insertOrReplace(entity));
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * A raw-style query where you can pass any WHERE clause and arguments.
     */
    public Observable<List<T>> rxQueryRaw(final String where, final String... selectionArg) {
        return Observable.create(new Observable.OnSubscribe<List<T>>() {
            @Override
            public void call(Subscriber<? super List<T>> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(queryRaw(where, selectionArg));
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Creates a repeatable {@link Query} object based on the given raw SQL where you can pass any WHERE clause and
     * arguments.
     */
    public RxQuery<T> rxqueryRawCreate(String where, Object... selectionArg) {
        List<Object> argList = Arrays.asList(selectionArg);
        return rxQueryRawCreateListArgs(where, argList);
    }

    /**
     * Creates a repeatable {@link Query} object based on the given raw SQL where you can pass any WHERE clause and
     * arguments.
     */
    public RxQuery<T> rxQueryRawCreateListArgs(String where, Collection<Object> selectionArg) {
        return RxQuery.internalCreate(this, statements.getSelectAll() + where, selectionArg.toArray());
    }


    public Observable<Void> rxDeleteAll() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        deleteAll();
                        subscriber.onNext(null);
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Deletes the given entity from the database. Currently, only single value PK entities are supported.
     */
    public Observable<Void> rxDelete(final T entity) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        delete(entity);
                        subscriber.onNext(null);
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }


    /**
     * Deletes an entity with the given PK from the database. Currently, only single value PK entities are supported.
     */
    public Observable<Void> rxDeleteByKey(final K key) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        deleteByKey(key);
                        subscriber.onNext(null);
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }


    /**
     * Deletes the given entities in the database using a transaction.
     *
     * @param entities The entities to delete.
     */
    public Observable<Void> rxDeleteInTx(final Iterable<T> entities) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        deleteInTx(entities);
                        subscriber.onNext(null);
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Deletes the given entities in the database using a transaction.
     *
     * @param entities The entities to delete.
     */
    public Observable<Void> rxDeleteInTx(final T... entities) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        deleteInTx(entities);
                        subscriber.onNext(null);
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Deletes all entities with the given keys in the database using a transaction.
     *
     * @param keys Keys of the entities to delete.
     */
    public Observable<Void> rxDeleteByKeyInTx(final Iterable<K> keys) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        deleteByKeyInTx(keys);
                        subscriber.onNext(null);
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Deletes all entities with the given keys in the database using a transaction.
     *
     * @param keys Keys of the entities to delete.
     */
    public Observable<Void> rxDeleteByKeyInTx(final K... keys) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        deleteByKeyInTx(keys);
                        subscriber.onNext(null);
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Resets all locally changed properties of the entity by reloading the values from the database.
     */
    public Observable<Void> rxRefresh(final T entity) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        refresh(entity);
                        subscriber.onNext(null);
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }


    public Observable<Void> rxUpdate(final T entity) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        update(entity);
                        subscriber.onNext(null);
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }


    /**
     * Updates the given entities in the database using a transaction.
     *
     * @param entities The entities to insert.
     */
    public Observable<Void> rxUpdateInTx(Iterable<T> entities) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        rxUpdateInTx();
                        subscriber.onNext(null);
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }

    /**
     * Updates the given entities in the database using a transaction.
     *
     * @param entities The entities to update.
     */
    public Observable<Void> rxUpdateInTx(T... entities) {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        rxUpdateInTx();
                        subscriber.onNext(null);
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }


    public Observable<Long> rxCount() {
        return Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(count());
                    }
                } catch (Throwable t) {
                    Exceptions.throwIfFatal(t);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(t);
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }
}

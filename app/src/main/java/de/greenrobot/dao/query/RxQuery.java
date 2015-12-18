package de.greenrobot.dao.query;

import java.util.List;

import de.greenrobot.dao.AbstractDao;
import de.greenrobot.dao.DaoException;
import rx.Observable;
import rx.Subscriber;
import rx.exceptions.Exceptions;

/**
 * A repeatable query returning entities.
 *
 * @param <T> The entity class the query will return results for.
 * @author Markus
 */
public class RxQuery<T> extends AbstractQueryWithLimit<T> {
    private final static class QueryData<T2> extends AbstractQueryData<T2, RxQuery<T2>> {
        private final int limitPosition;
        private final int offsetPosition;

        QueryData(AbstractDao<T2, ?> dao, String sql, String[] initialValues, int limitPosition, int offsetPosition) {
            super(dao, sql, initialValues);
            this.limitPosition = limitPosition;
            this.offsetPosition = offsetPosition;
        }

        @Override
        protected RxQuery<T2> createQuery() {
            return new RxQuery<T2>(this, dao, sql, initialValues.clone(), limitPosition, offsetPosition);
        }

    }

    /**
     * For internal use by greenDAO only.
     */
    public static <T2> RxQuery<T2> internalCreate(AbstractDao<T2, ?> dao, String sql, Object[] initialValues) {
        return create(dao, sql, initialValues, -1, -1);
    }

    static <T2> RxQuery<T2> create(AbstractDao<T2, ?> dao, String sql, Object[] initialValues, int limitPosition,
                                   int offsetPosition) {
        QueryData<T2> queryData = new QueryData<T2>(dao, sql, toStringArray(initialValues), limitPosition,
                offsetPosition);
        return queryData.forCurrentThread();
    }

    private final QueryData<T> queryData;
    private final Query<T> query;

    private RxQuery(QueryData<T> queryData, AbstractDao<T, ?> dao, String sql, String[] initialValues, int limitPosition,
                    int offsetPosition) {
        super(dao, sql, initialValues, limitPosition, offsetPosition);
        this.queryData = queryData;
        this.query = Query.create(dao, sql, initialValues, limitPosition, offsetPosition);
    }

    public RxQuery<T> forCurrentThread() {
        return queryData.forCurrentThread(this);
    }

    /**
     * Executes the query and returns the result as a list containing all entities loaded into memory.
     */
    public Observable<List<T>> list() {
        return Observable.create(new Observable.OnSubscribe<List<T>>() {
            @Override
            public void call(Subscriber<? super List<T>> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(query.list());
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
     * Executes the query and returns the result as a list that lazy loads the entities on first access. Entities are
     * cached, so accessing the same entity more than once will not result in loading an entity from the underlying
     * cursor again.Make sure to close it to close the underlying cursor.
     */
    public Observable<LazyList<T>> listLazy() {
        return Observable.create(new Observable.OnSubscribe<LazyList<T>>() {
            @Override
            public void call(Subscriber<? super LazyList<T>> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(query.listLazy());
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
     * Executes the query and returns the result as a list that lazy loads the entities on every access (uncached).
     * Make sure to close the list to close the underlying cursor.
     */
    public Observable<LazyList<T>> listLazyUncached() {
        return Observable.create(new Observable.OnSubscribe<LazyList<T>>() {
            @Override
            public void call(Subscriber<? super LazyList<T>> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(query.listLazyUncached());
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
     * Executes the query and returns the result as a list iterator; make sure to close it to close the underlying
     * cursor. The cursor is closed once the iterator is fully iterated through.
     */
    public Observable<CloseableListIterator<T>> listIterator() {
        return Observable.create(new Observable.OnSubscribe<CloseableListIterator<T>>() {
            @Override
            public void call(Subscriber<? super CloseableListIterator<T>> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(query.listIterator());
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
     * Executes the query and returns the unique result or null.
     *
     * @return Entity or null if no matching entity was found
     * @throws DaoException if the result is not unique
     */
    public Observable<T> unique() {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(query.unique());
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
     * Executes the query and returns the unique result (never null).
     *
     * @return Entity
     * @throws DaoException if the result is not unique or no entity was found
     */
    public Observable<T> uniqueOrThrow() {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(query.uniqueOrThrow());
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

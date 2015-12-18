package de.greenrobot.dao.query;

import de.greenrobot.dao.AbstractDao;
import rx.Observable;
import rx.Subscriber;
import rx.exceptions.Exceptions;

/**
 * A repeatable query for deleting entities.<br/>
 * New API note: this is more likely to change.
 *
 * @param <T> The entity class the query will delete from.
 * @author Markus
 */
public class RxDeleteQuery<T> extends AbstractQuery<T> {
    private final static class QueryData<T2> extends AbstractQueryData<T2, RxDeleteQuery<T2>> {

        private QueryData(AbstractDao<T2, ?> dao, String sql, String[] initialValues) {
            super(dao, sql, initialValues);
        }

        @Override
        protected RxDeleteQuery<T2> createQuery() {
            return new RxDeleteQuery<T2>(this, dao, sql, initialValues.clone());
        }
    }

    static <T2> RxDeleteQuery<T2> create(AbstractDao<T2, ?> dao, String sql, Object[] initialValues) {
        QueryData<T2> queryData = new QueryData<T2>(dao, sql, toStringArray(initialValues));
        return queryData.forCurrentThread();
    }

    private final QueryData<T> queryData;
    private final DeleteQuery deleteQuery;

    private RxDeleteQuery(QueryData<T> queryData, AbstractDao<T, ?> dao, String sql, String[] initialValues) {
        super(dao, sql, initialValues);
        this.queryData = queryData;
        this.deleteQuery = DeleteQuery.create(dao, sql, initialValues);
    }

    public RxDeleteQuery<T> forCurrentThread() {
        return queryData.forCurrentThread(this);
    }

    /**
     * Deletes all matching entities without detaching them from the identity scope (aka session/cache). Note that this
     * method may lead to stale entity objects in the session cache. Stale entities may be returned when loaded by their
     * primary key, but not using queries.
     */
    public Observable<Void> executeDeleteWithoutDetachingEntities() {
        return Observable.create(new Observable.OnSubscribe<Void>() {
            @Override
            public void call(Subscriber<? super Void> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        deleteQuery.executeDeleteWithoutDetachingEntities();
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

}

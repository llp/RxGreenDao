package de.greenrobot.dao.query;

import de.greenrobot.dao.AbstractDao;
import rx.Observable;
import rx.Subscriber;
import rx.exceptions.Exceptions;

public class RxCountQuery<T> extends AbstractQuery<T> {

    private final static class QueryData<T2> extends AbstractQueryData<T2, RxCountQuery<T2>> {

        private QueryData(AbstractDao<T2, ?> dao, String sql, String[] initialValues) {
            super(dao, sql, initialValues);
        }

        @Override
        protected RxCountQuery<T2> createQuery() {
            return new RxCountQuery<T2>(this, dao, sql, initialValues.clone());
        }
    }

    static <T2> RxCountQuery<T2> create(AbstractDao<T2, ?> dao, String sql, Object[] initialValues) {
        QueryData<T2> queryData = new QueryData<T2>(dao, sql, toStringArray(initialValues));
        return queryData.forCurrentThread();
    }

    private final QueryData<T> queryData;
    private CountQuery<T> countQuery;

    private RxCountQuery(QueryData<T> queryData, AbstractDao<T, ?> dao, String sql, String[] initialValues) {
        super(dao, sql, initialValues);
        this.queryData = queryData;
        this.countQuery = CountQuery.create(dao, sql, toStringArray(initialValues));
    }

    public RxCountQuery<T> forCurrentThread() {
        return queryData.forCurrentThread(this);
    }

    /**
     * Returns the count (number of results matching the query). Uses SELECT COUNT (*) sematics.
     */
    public Observable<Long> count() {
        return Observable.create(new Observable.OnSubscribe<Long>() {
            @Override
            public void call(Subscriber<? super Long> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(countQuery.count());
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

package de.greenrobot.dao.query;

import android.database.Cursor;

import de.greenrobot.dao.AbstractDao;
import rx.Observable;
import rx.Subscriber;
import rx.exceptions.Exceptions;

public class RxCursorQuery<T> extends AbstractQueryWithLimit<T> {

    private final static class QueryData<T2> extends AbstractQueryData<T2, RxCursorQuery<T2>> {
        private final int limitPosition;
        private final int offsetPosition;

        QueryData(AbstractDao dao, String sql, String[] initialValues, int limitPosition, int offsetPosition) {
            super(dao, sql, initialValues);
            this.limitPosition = limitPosition;
            this.offsetPosition = offsetPosition;
        }

        @Override
        protected RxCursorQuery<T2> createQuery() {
            return new RxCursorQuery<T2>(this, dao, sql, initialValues.clone(), limitPosition, offsetPosition);
        }

    }

    /**
     * For internal use by greenDAO only.
     */
    public static <T2> RxCursorQuery<T2> internalCreate(AbstractDao<T2, ?> dao, String sql, Object[] initialValues) {
        return create(dao, sql, initialValues, -1, -1);
    }

    static <T2> RxCursorQuery<T2> create(AbstractDao<T2, ?> dao, String sql, Object[] initialValues, int limitPosition,
                                         int offsetPosition) {
        QueryData<T2> queryData = new QueryData<T2>(dao, sql, toStringArray(initialValues), limitPosition,
                offsetPosition);
        return queryData.forCurrentThread();
    }

    private final QueryData<T> queryData;
    private final CursorQuery cursorQuery;

    private RxCursorQuery(QueryData<T> queryData, AbstractDao<T, ?> dao, String sql, String[] initialValues, int limitPosition,
                          int offsetPosition) {
        super(dao, sql, initialValues, limitPosition, offsetPosition);
        this.queryData = queryData;
        this.cursorQuery = CursorQuery.create(dao, sql, initialValues, limitPosition, offsetPosition);
    }

    public RxCursorQuery forCurrentThread() {
        return queryData.forCurrentThread(this);
    }

    /**
     * Executes the query and returns a raw android.database.Cursor. Don't forget to close it.
     */
    public Observable<Cursor> query() {
        return Observable.create(new Observable.OnSubscribe<Cursor>() {
            @Override
            public void call(Subscriber<? super Cursor> subscriber) {
                try {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(cursorQuery.query());
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

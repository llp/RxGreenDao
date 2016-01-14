# RxGreenDao
RxGreenDao: Reactive Extensions for GreenDao

使GreenDao支持Rxjava的补丁包  
注意：    
1.    使用DaoGenerator生成出来的XXXDao要把继承关系从AbstractDao修改为RxAbstractDao        
2.    RxGreenDao提供的方法以rx开头，例如：xxxDao.rxCount()，xxxDao.rxInsert()...          
3.    提供xxxDao.rxQueryBuilder()       
#Sample
          Note note = new Note();
          note.setText("hello world!");
          noteDao.rxInsert(note)
                .doOnNext(new Action1<Long>() {
                    @Override
                    public void call(Long aLong) {
                        Log.e(TAG, "#-----doOnNext---->>>" + aLong);
                    }
                })
                .flatMap(new Func1<Long, Observable<List<Note>>>() {
                    @Override
                    public Observable<List<Note>> call(Long aLong) {
                        return noteDao.rxQueryBuilder().list();
                    }
                })
                .flatMap(new Func1<List<Note>, Observable<Note>>() {
                    @Override
                    public Observable<Note> call(List<Note> notes) {
                        return Observable.from(notes);
                    }
                })
                .flatMap(new Func1<Note, Observable<String>>() {
                    @Override
                    public Observable<String> call(Note note) {
                        return Observable.just(note.getText());
                    }
                })
                .subscribe(new Subscriber<String>() {
                    @Override
                    public void onCompleted() {
                        Log.e(TAG, "#-----onCompleted---->>>");
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(TAG, "#-----onError---->>>");
                    }

                    @Override
                    public void onNext(String s) {
                        Log.e(TAG, "#------onNext--->>>" + s);
                    }
                });



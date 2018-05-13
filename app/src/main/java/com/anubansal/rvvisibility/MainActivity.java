package com.anubansal.rvvisibility;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.anubansal.rvvisibility.databinding.ActivityMainBinding;

import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.observers.DisposableObserver;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    ActivityMainBinding mBinding;
    MyAdapter adapter;
    LinearLayoutManager layoutManager;
    int prevFirstPos, prevLastPos;

    private static final int INTERVAL = 3000;
    private Disposable disposable;
    private Subject<VisiblePositionPOJO> subject;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);
        ArrayList<String> dataList = initializeArrayList();
        adapter = new MyAdapter(dataList);
        layoutManager = new LinearLayoutManager(this);
        mBinding.recyclerView.setLayoutManager(layoutManager);
        mBinding.recyclerView.setAdapter(adapter);
        layoutManager.findFirstVisibleItemPosition();
        layoutManager.findLastVisibleItemPosition();
    }

    @Override
    protected void onResume() {
        super.onResume();
        mBinding.recyclerView.addOnScrollListener(onScrollListener);
        subject = PublishSubject.create();
        disposable = subject
                .distinctUntilChanged()
                .debounce(INTERVAL, TimeUnit.MILLISECONDS)
                .map(new Function<VisiblePositionPOJO, VisiblePositionPOJO>() {

                    @Override
                    public VisiblePositionPOJO apply(VisiblePositionPOJO value) {
                        int start=0,end=0;
                        if (value.lastPosition < prevFirstPos || value.firstPosition > prevLastPos || prevLastPos == 0 && prevFirstPos == 0) {
                            start = value.firstPosition;
                            end = value.lastPosition;
                        } else if (value.firstPosition < prevFirstPos && value.lastPosition > prevFirstPos) {
                            start = value.firstPosition;
                            end = prevFirstPos-1;
                        } else if (value.firstPosition > prevFirstPos && value.lastPosition > prevLastPos) {
                            start = prevLastPos+1;
                            end = value.lastPosition;
                        }
                        return new VisiblePositionPOJO(start, end);
                    }
                })
                .subscribeWith(new DisposableObserver<VisiblePositionPOJO>() {

                    @Override
                    public void onNext(VisiblePositionPOJO value) {
                        for (int i=value.firstPosition; i<=value.lastPosition; i++) {
                            Log.d(TAG, "position : " + i);
                        }
                        prevFirstPos = value.firstPosition;
                        prevLastPos = value.lastPosition;
                    }

                    @Override
                    public void onComplete() {

                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });
    }

    private RecyclerView.OnScrollListener onScrollListener = new RecyclerView.OnScrollListener() {

        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            int firstVisiblePosition = layoutManager.findFirstVisibleItemPosition();
            int lastVisiblePosition = layoutManager.findLastVisibleItemPosition();
            subject.onNext(new VisiblePositionPOJO(firstVisiblePosition, lastVisiblePosition));
        }
    };

    public class VisiblePositionPOJO {
        int firstPosition;
        int lastPosition;

        VisiblePositionPOJO(int firstPosition, int lastPosition) {
            this.firstPosition = firstPosition;
            this.lastPosition = lastPosition;
        }

        @Override
        public String toString() {
            return firstPosition + " " + lastPosition;
        }

        @Override
        public boolean equals(Object obj) {
            return this.firstPosition == ((VisiblePositionPOJO) obj).firstPosition && this.lastPosition == ((VisiblePositionPOJO)obj).lastPosition;
        }
    }

    private ArrayList<String> initializeArrayList() {
        ArrayList<String> list = new ArrayList<>();
        list.add("0");
        list.add("1");
        list.add("2");
        list.add("3");
        list.add("4");
        list.add("5");
        list.add("6");
        list.add("7");
        list.add("8");
        list.add("9");
        list.add("10");
        list.add("11");
        list.add("12");
        list.add("13");
        list.add("14");
        list.add("15");
        list.add("16");
        list.add("17");
        list.add("18");
        list.add("19");
        list.add("20");
        list.add("21");
        list.add("22");
        list.add("23");
        list.add("24");
        list.add("25");
        list.add("26");
        list.add("27");
        list.add("28");
        list.add("29");
        list.add("30");
        return list;
    }

    @Override
    protected void onPause() {
        super.onPause();
        mBinding.recyclerView.removeOnScrollListener(onScrollListener);
        if (disposable != null && !disposable.isDisposed()) {
            disposable.dispose();
        }
    }
}

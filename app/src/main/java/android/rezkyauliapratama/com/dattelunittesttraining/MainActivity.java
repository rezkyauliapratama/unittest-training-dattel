package android.rezkyauliapratama.com.dattelunittesttraining;

import android.databinding.DataBindingUtil;
import android.rezkyauliapratama.com.dattelunittesttraining.Schemas.Event;
import android.rezkyauliapratama.com.dattelunittesttraining.Schemas.ListEvent;
import android.rezkyauliapratama.com.dattelunittesttraining.databinding.ActivityMainBinding;
import android.rezkyauliapratama.com.dattelunittesttraining.network.NetworkApi;
import android.rezkyauliapratama.com.dattelunittesttraining.utils.TimeUtil;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

import io.reactivex.Scheduler;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

//cannot do the unittest into Activity
//cannot do the unittest into fragment
//cannot do the unittest into android component
// we can do the unittest into normal java class
public class MainActivity extends AppCompatActivity {

    ActivityMainBinding binding;
    NetworkApi mNetworkApi;
    MatchRvAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        initRetrofit();
        initSpinner();
        initRv();

    }

    private void initRv() {
        adapter = new MatchRvAdapter();
        binding.recyclerview.setAdapter(adapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this));
    }

    private void initRetrofit() {
        Gson gson = new GsonBuilder().create();

        Retrofit retrofit = new Retrofit.Builder().addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .baseUrl("https://www.thesportsdb.com/api/v1/json/1/").build();

        mNetworkApi = retrofit.create(NetworkApi.class);
    }

    private void initSpinner() {
        String[] leagues = getResources().getStringArray(R.array.league);
        String[] leaguesId = getResources().getStringArray(R.array.league_id);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>
                (this,
                        R.layout.support_simple_spinner_dropdown_item,
                        leagues);
        binding.spinner.setAdapter(adapter);

        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                fetchDataFromApi(leaguesId[i]);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

    }

    private void fetchDataFromApi(String s) {
        mNetworkApi.getEvent(s)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<ListEvent>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onSuccess(ListEvent listEvent) {

                        for (Event event : listEvent.getEvents()){
                            String temp = event.getDateEvent();

                            SimpleDateFormat simpleDateFormat1 = new SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());
                            TimeUtil timeUtil = new TimeUtil(simpleDateFormat1);
                            Date date = timeUtil.convertStringToDate(temp);

                            SimpleDateFormat simpleDateFormat2= new SimpleDateFormat("dd MMMM yyyy", Locale.getDefault());
                            timeUtil = new TimeUtil(simpleDateFormat2);
                            String result = timeUtil.getUserFriendlyDate(date);

                            event.setDateEvent(result);
                        }

                        adapter.bindData(listEvent.getEvents());
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.e(this.getClass().getName(),"error : "+new Gson().toJson(e));
                    }
                });
    }


}

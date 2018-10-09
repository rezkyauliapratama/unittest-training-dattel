package android.rezkyauliapratama.com.dattelunittesttraining.network;

import android.rezkyauliapratama.com.dattelunittesttraining.Schemas.ListEvent;

import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Query;

//we use Retrofit for httpclient
public interface NetworkApi {

    @GET("eventspastleague.php")
    Single<ListEvent> getEvent(
            @Query("id") String leagueId
    );


}

package weather.pactera.com.weatherapp.interceptor;

import android.app.Application;

import java.io.IOException;

import okhttp3.CacheControl;
import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;
import weather.pactera.com.weatherapp.utils.NetworkUtils;


public class ForceCacheInterceptor implements Interceptor {

    private final Application mContext
            ;

    public ForceCacheInterceptor(Application context) {
        this.mContext = context;
    }

    @Override
    public Response intercept(Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();
        if (!NetworkUtils.internetAvailable(this.mContext)) {
            builder.cacheControl(CacheControl.FORCE_CACHE);
        }

        return chain.proceed(builder.build());
    }
}

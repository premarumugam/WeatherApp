package weather.pactera.com.weatherapp.activity;

import java.lang.ref.WeakReference;

public class BaseActivity<T> {

    private WeakReference<T> view;

    public void setView(T view) {
        this.view = new WeakReference(view);
    }

    public T getView() {
        return view.get();
    }
}

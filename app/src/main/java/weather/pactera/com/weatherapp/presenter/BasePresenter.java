package weather.pactera.com.weatherapp.presenter;


import java.lang.ref.WeakReference;

public class BasePresenter<T> {

    private WeakReference<T> view;

    public void setView(T view) {
        this.view = new WeakReference(view);
    }

    public T getView() {
        return view.get();
    }
}

package config;

import api.AuthorsApi;
import api.BooksApi;
import com.google.inject.Binder;
import com.google.inject.Module;

public class ApiConfig implements Module {

    @Override
    public void configure(Binder binder) {
        binder.bind(BooksApi.class).toInstance(new BooksApi());
        binder.bind(AuthorsApi.class).toInstance(new AuthorsApi());
    }
}

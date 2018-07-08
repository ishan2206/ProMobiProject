package com.ur.promobiproject.ViewModel;

import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;

import com.ur.promobiproject.Modules.ArticleScope;
import com.ur.promobiproject.Repository.LocalRepository;
import com.ur.promobiproject.Repository.RemoteRepository;

import javax.inject.Inject;
import javax.inject.Named;

import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by Ishan on 08-07-2018.
 */

@ArticleScope
public class ArticleViewModelFactory implements ViewModelProvider.Factory {

    @Inject
    LocalRepository localRepository;
    @Inject
    RemoteRepository remoteRepository;
    @Inject @Named("vm")
    CompositeDisposable compositeDisposable;

    @Inject
    public ArticleViewModelFactory() {
    }

    @Override
    public <T extends ViewModel> T create(Class<T> modelClass) {

        if (modelClass.isAssignableFrom(ArticleViewModel.class)) {
            return (T) new ArticleViewModel(localRepository, remoteRepository, compositeDisposable);
        }
        throw new IllegalArgumentException("Wrong ViewModel class");
    }
}

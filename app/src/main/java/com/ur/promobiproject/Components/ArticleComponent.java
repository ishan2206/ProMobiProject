package com.ur.promobiproject.Components;

import com.ur.promobiproject.MainActivity;
import com.ur.promobiproject.Modules.ArticleModule;
import com.ur.promobiproject.Modules.ArticleScope;

import dagger.Subcomponent;

/**
 * Created by Ishan on 08-07-2018.
 */

@ArticleScope
@Subcomponent(modules = ArticleModule.class)
public interface ArticleComponent {
    void inject(MainActivity mainActivity);
}

package com.ur.promobiproject.Modules;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import javax.inject.Scope;

/**
 * Created by Ishan on 08-07-2018.
 */

@Scope
@Retention(value= RetentionPolicy.RUNTIME)
public @interface ArticleScope {
}

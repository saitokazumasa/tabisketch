package com.tabisketch.service;

import com.tabisketch.bean.form.CreatePlaceForm;
import com.tabisketch.exception.InsertFailedException;

public interface ICreatePlaceService {
    void execute(final CreatePlaceForm createPlaceForm) throws InsertFailedException;
}

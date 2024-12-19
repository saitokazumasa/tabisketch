package com.tabisketch.service;

import com.tabisketch.bean.form.CreateDayForm;
import com.tabisketch.exception.InsertFailedException;

public interface ICreateDayService {
    /** @return 作成したDayのID */
    int execute(final CreateDayForm createDayForm) throws InsertFailedException;
}

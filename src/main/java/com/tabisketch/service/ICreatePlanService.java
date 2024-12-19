package com.tabisketch.service;

import com.tabisketch.bean.form.CreatePlanForm;
import com.tabisketch.exception.InsertFailedException;

public interface ICreatePlanService {
    /** @return 作成したPlanのID */
    int execute(final CreatePlanForm createPlanForm) throws InsertFailedException;
}

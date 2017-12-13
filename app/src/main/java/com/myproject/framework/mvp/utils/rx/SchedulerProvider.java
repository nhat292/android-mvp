package com.myproject.framework.mvp.utils.rx;

import io.reactivex.Scheduler;

/**
 * Created by Nhat on 12/13/17.
 */


public interface SchedulerProvider {

    Scheduler ui();

    Scheduler computation();

    Scheduler io();

}

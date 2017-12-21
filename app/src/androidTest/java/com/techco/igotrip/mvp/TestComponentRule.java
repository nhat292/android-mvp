
package com.techco.igotrip.mvp;

import android.content.Context;

import com.techco.igotrip.dagger.component.TestComponent;
import com.techco.igotrip.data.DataManager;

import org.junit.rules.TestRule;
import org.junit.runner.Description;
import org.junit.runners.model.Statement;

/**
 * Created by Nhat on 12/13/17.
 */

public class TestComponentRule implements TestRule {

    private TestComponent mTestComponent;
    private Context mContext;

    public TestComponentRule(Context context) {
        mContext = context;
    }

    public Context getContext() {
        return mContext;
    }

    public DataManager getDataManager() {
        return mTestComponent.getDataManager();
    }

    private void setupDaggerTestComponentInApplication() {
       /* App application = ((App) mContext.getApplicationContext());
        mTestComponent = DaggerTestComponent.builder()
                .applicationTestModule(new ApplicationTestModule(application))
                .build();
        application.setComponent(mTestComponent);*/
    }

    @Override
    public Statement apply(final Statement base, Description description) {
        return new Statement() {
            @Override
            public void evaluate() throws Throwable {
                try {
                    setupDaggerTestComponentInApplication();
                    base.evaluate();
                } finally {
                    mTestComponent = null;
                }
            }
        };
    }
}

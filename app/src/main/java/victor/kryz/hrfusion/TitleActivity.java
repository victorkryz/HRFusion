package victor.kryz.hrfusion;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import java.io.IOException;

import victor.kryz.hrfusion.app.LibsLoader;
import victor.kryz.hrfusion.hrdb.Session;
import victor.kryz.hrfusion.hrdb.sqlt.SessionFactory;
import victor.kryz.hrfusion.views.base.ArgsKeys;
import victor.kryz.hrfusion.views.regions.ListActivity;

public class TitleActivity extends AppCompatActivity
{
    private static final String TRACE_TAG = TitleActivity.class.getSimpleName();
    static final String HR_DB_FILE_NAME = "hr.db";

    static {
        LibsLoader.load();
    }

    class HrViewLauncher implements Runnable
    {
        @Override
        public void run()
        {
            try
            {
                victor.kryz.hrfusion.app.AssetsFile dbFile =
                        new victor.kryz.hrfusion.app.AssetsFile(getActivity());

                final String strFilePath =  dbFile.update(HR_DB_FILE_NAME);
                Session ses = SessionFactory.createSession(strFilePath);

                getActivity().runOnUiThread(new Runnable() {
                    public void run()
                    {
                        Bundle args = new Bundle();
                        args.putString(ArgsKeys.ITEM_ID, "");
                        Intent intent = new Intent(getActivity(), ListActivity.class);
                        intent.putExtras(args);
                        startActivity(intent, args);
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                    }
                });
            }
            catch (IOException e) {
                Log.e(TRACE_TAG, e.getMessage());
            }
            catch (Exception e) {
                Log.e(TRACE_TAG, e.getMessage());
            }
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_title);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                    Thread th = new Thread(new HrViewLauncher());
                    th.start();
            }
        });
    }

    private Activity getActivity() {
        return this;
    }

}

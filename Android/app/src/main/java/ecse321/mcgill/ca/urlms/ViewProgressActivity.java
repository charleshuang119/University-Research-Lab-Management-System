package ecse321.mcgill.ca.urlms;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

/**
 * Created by Andi-Camille Bakti on 03/12/2017.
 */

public class ViewProgressActivity extends AppCompatActivity {

    @Override
    public void onCreate(Bundle bundle){
        super.onCreate(bundle);
        setContentView(R.layout.view_progress_activity);
        getSupportActionBar().setTitle("View Report");
        Intent intent = getIntent();
        String description = intent.getStringExtra(ProgressActivity.EXTRA_DESCRIPTION);

        if(description != ""){
            viewDescription(description);
        }
    }

    private void viewDescription(String description) {
        TextView text = (TextView) findViewById(R.id.text_progress_description);
        text.setText(description);
    }
}

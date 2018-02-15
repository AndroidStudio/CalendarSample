package calendar.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ViewTreeObserver;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final GraphView graphView = (GraphView) findViewById(R.id.graph);
        graphView.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                graphView.getViewTreeObserver().removeGlobalOnLayoutListener(this);
                graphView.addItems();
            }
        });
    }
}

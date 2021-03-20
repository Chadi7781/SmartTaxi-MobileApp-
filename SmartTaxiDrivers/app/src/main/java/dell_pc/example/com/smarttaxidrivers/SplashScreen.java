package dell_pc.example.com.smarttaxidrivers;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class SplashScreen extends AppCompatActivity {

    private TextView textView;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);
        init();
        Animation yanim= AnimationUtils.loadAnimation(this,R.anim.mytransition);
        imageView.startAnimation(yanim);


        Thread timmer=new Thread(){
            @Override
            public void run() {
                try {
                    sleep(5000);

                }catch (InterruptedException ex){
                    ex.printStackTrace();
                }
                finally {
//                    if(new TinyDB(SplashScreen.this).getString("connected").contains("connected")){
//                        final Intent intent=new Intent(SplashScreen.this, MainActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }else{
//                        final Intent intent=new Intent(SplashScreen.this, LoginActivity.class);
//                        startActivity(intent);
//                        finish();
//                    }
                    final Intent intent = new Intent(SplashScreen.this, MainActivity.class);
                    startActivity(intent);
                    finish();

                    // progressBar.setVisibility(View.GONE);

                }
            }
        };
        timmer.start();
    }
    public void init(){
        imageView=findViewById(R.id.iv);

    }
}




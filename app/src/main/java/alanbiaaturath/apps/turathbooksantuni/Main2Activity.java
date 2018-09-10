package alanbiaaturath.apps.turathbooksantuni;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

public class Main2Activity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
    }

    public void btn_onclik(View view)
    {
        Button b1 = ((Button) view);
        switch (b1.getId())
        {
            case R.id.button:
            {
                Intent i = new Intent(this, ScrollingActivity.class);
                i.putExtra("page", 0);
                startActivity(i);
                break;
            }
            case R.id.button1:
            {
                Intent i = new Intent(this, ScrollingActivity.class);
                i.putExtra("page", 4);
                startActivity(i);
                break;
            }

            case R.id.button2:
            {
                Intent i = new Intent(this, ScrollingActivity.class);
                i.putExtra("page", 5);
                startActivity(i);
                break;
            }
            case R.id.button3:
            {
                Intent i = new Intent(this, ScrollingActivity.class);
                i.putExtra("page",7);
                startActivity(i);
                break;
            }
            case R.id.button4:
            {
                Intent i = new Intent(this, ScrollingActivity.class);
                i.putExtra("page", 9);
                startActivity(i);
                break;
            }
            case R.id.button5:
            {
                Intent i = new Intent(this, ScrollingActivity.class);
                i.putExtra("page", 11);
                startActivity(i);
                break;
            }
            case R.id.button6:
            {
                Intent i = new Intent(this, ScrollingActivity.class);
                i.putExtra("page", 13);
                startActivity(i);
                break;
            }
            case R.id.button7:
            {
                Intent i = new Intent(this, ScrollingActivity.class);
                i.putExtra("page", 15);
                startActivity(i);
                break;
            }
            case R.id.button8:
            {
                Intent i = new Intent(this, ScrollingActivity.class);
                i.putExtra("page", 17);
                startActivity(i);
                break;
            }
            case R.id.button9:
            {
                Intent i = new Intent(this, ScrollingActivity.class);
                i.putExtra("page", 19);
                startActivity(i);
                break;
            }
            case R.id.button10:
            {
                Intent i = new Intent(this, ScrollingActivity.class);
                i.putExtra("page", 21);
                startActivity(i);
                break;
            }
            case R.id.button11:
            {
                Intent i = new Intent(this, ScrollingActivity.class);
                i.putExtra("page", 23);
                startActivity(i);
                break;
            }
            case R.id.button12:
            {
                Intent i = new Intent(this, ScrollingActivity.class);
                i.putExtra("page", 25);
                startActivity(i);
                break;
            }
            case R.id.button13:
            {
                Intent i = new Intent(this, ScrollingActivity.class);
                i.putExtra("page", 27);
                startActivity(i);
                break;
            }
            case R.id.button14:
            {
                Intent i = new Intent(this, ScrollingActivity.class);
                i.putExtra("page", 29);
                startActivity(i);
                break;
            }
            case R.id.button15:
            {
                Intent i = new Intent(this, ScrollingActivity.class);
                i.putExtra("page", 30);
                startActivity(i);
                break;
            }
        }
    }
}

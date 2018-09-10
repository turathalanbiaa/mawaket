package alanbiaaturath.apps.turathbooksantuni;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.res.AssetManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.pdf.PdfRenderer;
import android.os.Bundle;
import android.os.Handler;
import android.os.ParcelFileDescriptor;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MotionEvent;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.github.barteksc.pdfviewer.PDFView;
import com.github.barteksc.pdfviewer.listener.OnDrawListener;
import com.github.barteksc.pdfviewer.listener.OnLoadCompleteListener;
import com.github.barteksc.pdfviewer.listener.OnPageChangeListener;
import com.mikepenz.materialdrawer.AccountHeader;
import com.mikepenz.materialdrawer.AccountHeaderBuilder;
import com.mikepenz.materialdrawer.Drawer;
import com.mikepenz.materialdrawer.DrawerBuilder;
import com.mikepenz.materialdrawer.model.PrimaryDrawerItem;
import com.mikepenz.materialdrawer.model.interfaces.IDrawerItem;

import java.io.File;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.EmptyStackException;
import java.util.List;

import app.num.numandroidpagecurleffect.PageCurlView;

public class ScrollingActivity extends AppCompatActivity
{
    public static String pdfFile = "ah.pdf";
    private float x1, x2;
    static final int MIN_DISTANCE = 150;
    public PDFView pdfView;
    int page_count;
    public static int pagenum;
    int defult_page = 0;
    int page = 1;
    Button left_btn;
    Button right_btn;
    android.support.design.widget.AppBarLayout appBarLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scrolling);
        FloatingActionButton fab2 = (FloatingActionButton) findViewById(R.id.fab2);
        fab2.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                open_poster();
            }
        });
        FloatingActionButton fab3 = (FloatingActionButton) findViewById(R.id.fab3);
        fab3.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                pdfView.zoomWithAnimation(Float.valueOf(2));
            }
        });
        FloatingActionButton fab4 = (FloatingActionButton) findViewById(R.id.fab4);
        fab4.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View view)
            {
                pdfView.resetZoomWithAnimation();
            }
        });
        Intent i = getIntent();
        page = i.getIntExtra("page", 1);
        defult_page = page;
        final PDFView pdfView2;
        pdfView2 = (PDFView) findViewById(R.id.pdfview2);
        pdfView2.fromAsset(pdfFile)
                .enableSwipe(false)
                .enableDoubletap(true)
                .defaultPage(0)
                .enableAnnotationRendering(true)
                .password(null)
                .scrollHandle(null)
                .onLoad(new OnLoadCompleteListener()
                {
                    @Override
                    public void loadComplete(int nbPages)
                    {
                        page_count = pdfView2.getPageCount();
                    }
                })
                .load();
        /////
        pdfView = (PDFView) findViewById(R.id.pdfview);
        gotopage(defult_page);
        myOnClick();
        pagenum = 0;
    }

    private void myOnClick()
    {
        left_btn = (Button) findViewById(R.id.left_btn);
        right_btn = (Button) findViewById(R.id.right_btn);
        left_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (defult_page < 31)
                {
                    defult_page = defult_page + 1;
                    gotopage(defult_page);
                    Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideleft_to_right);
                    pdfView.startAnimation(animation2);
                }
            }
        });
        right_btn.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (defult_page != 0)
                {
                    defult_page = defult_page - 1;
                    gotopage(defult_page);
                    Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sliderite_to_left);
                    pdfView.startAnimation(animation2);
                }
            }
        });
        LinearLayout linearLayout = (LinearLayout) findViewById(R.id.my_scrool);
        linearLayout.setOnTouchListener(new View.OnTouchListener()
        {
            @Override
            public boolean onTouch(View v, MotionEvent event)
            {
                System.out.println(event.getX());
                switch (event.getAction())
                {
                    case MotionEvent.ACTION_DOWN:
                        x1 = event.getX();
                        break;
                    case MotionEvent.ACTION_UP:
                        x2 = event.getX();
                        float deltaX = x2 - x1;
                        if (deltaX > MIN_DISTANCE)
                        {
                            if (defult_page < 31)
                            {
                                defult_page = defult_page + 1;
                                gotopage(defult_page);
                                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.slideleft_to_right);
                                pdfView.startAnimation(animation2);
                            }
                        }
                        else if (deltaX * (-1) > MIN_DISTANCE)
                        {
                            if (defult_page != 0)
                            {
                                defult_page = defult_page - 1;
                                gotopage(defult_page);
                                Animation animation2 = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.sliderite_to_left);
                                pdfView.startAnimation(animation2);
                            }
                        }
                        break;
                }
//                Float f = Float.valueOf(2);
//
//                pdfView.zoomTo(f);
                return true;
            }
        });
    }

    public void gotopage(int indexpage)
    {
        final ProgressDialog pd = new ProgressDialog(ScrollingActivity.this);
        pd.setMessage("انتظر ...");
        defult_page = indexpage;
        if (pdfFile != null)
        {

            pd.show();

            pdfView.fromAsset(pdfFile)
                    .enableSwipe(false)
                    .enableDoubletap(true)
                    .defaultPage(indexpage)
                    .enableAnnotationRendering(true)
                    .password(null)
                    .pages(indexpage)
                    .scrollHandle(null)
                    .onLoad(new OnLoadCompleteListener()
                    {
                        @Override
                        public void loadComplete(int nbPages)
                        {
                            pd.hide();
                        }
                    })
                    .load();
            pdfView.resetZoomWithAnimation();


        }
//
        appBarLayout = (AppBarLayout) findViewById(R.id.app_bar);
        new Handler().postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                appBarLayout.setExpanded(false);
            }
        }, 1600);
    }

    private void s()
    {
        Toast.makeText(this, "ddd", Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_scrolling, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings)
        {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }
    // drawer

    public IDrawerItem[] getDrawerItems()
    {
        IDrawerItem[] items = new IDrawerItem[17];
        PrimaryDrawerItem item = new PrimaryDrawerItem().withIdentifier(1).withName("");
        PrimaryDrawerItem item1 = new PrimaryDrawerItem().withIdentifier(2).withName("كيف غيَّر أشهر مُلْحد رأيه؟");
        PrimaryDrawerItem item2 = new PrimaryDrawerItem().withIdentifier(3).withName("إنكاري للمقدَّس");
        PrimaryDrawerItem item3 = new PrimaryDrawerItem().withIdentifier(4).withName("صناعةُ مُلْحِد");
        PrimaryDrawerItem item4 = new PrimaryDrawerItem().withIdentifier(5).withName("إلى حيثُ يقودُ الدَّليل؟");
        PrimaryDrawerItem item5 = new PrimaryDrawerItem().withIdentifier(6).withName("إعادةُ النَّظَر في الإلحادِ بهدوء");
        PrimaryDrawerItem item6 = new PrimaryDrawerItem().withIdentifier(7).withName("اكتشافي للمقدَّس");
        PrimaryDrawerItem item7 = new PrimaryDrawerItem().withIdentifier(8).withName("حَجُّ العَقْل");
        PrimaryDrawerItem item8 = new PrimaryDrawerItem().withIdentifier(9).withName("مَنْ كَتَبَ قوانينَ الطَّبيعة؟");
        PrimaryDrawerItem item9 = new PrimaryDrawerItem().withIdentifier(10).withName("هل عَرَفَ الكونُ أنَّنا قادمون؟");
        PrimaryDrawerItem item10 = new PrimaryDrawerItem().withIdentifier(11).withName("كيف حدثت الحياة؟");
        PrimaryDrawerItem item11 = new PrimaryDrawerItem().withIdentifier(12).withName("هل جاءَ شيءٌ ما من لا شيء؟");
        PrimaryDrawerItem item12 = new PrimaryDrawerItem().withIdentifier(13).withName("إيجادُ مساحةٍ للإله");
        PrimaryDrawerItem item13 = new PrimaryDrawerItem().withIdentifier(14).withName("الطريقُ مفتوحٌ أمامَ إلهٍ كامل القدرة");
        PrimaryDrawerItem item14 = new PrimaryDrawerItem().withIdentifier(15).withName("الملاحق");
        PrimaryDrawerItem item15 = new PrimaryDrawerItem().withIdentifier(16).withName("الإلحاد الجديد");
        PrimaryDrawerItem item16 = new PrimaryDrawerItem().withIdentifier(17).withName("الوحي الذَّاتي للإله في التاريخ البشري");
        items[0] = item;
        items[1] = item1;
        items[2] = item2;
        items[3] = item3;
        items[4] = item4;
        items[5] = item5;
        items[6] = item6;
        items[7] = item7;
        items[8] = item8;
        items[9] = item9;
        items[10] = item10;
        items[11] = item11;
        items[12] = item12;
        items[13] = item13;
        items[14] = item14;
        items[15] = item15;
        items[16] = item16;
        return items;
    }

    private Drawer.OnDrawerItemClickListener clickListener = new Drawer.OnDrawerItemClickListener()
    {
        @Override
        public boolean onItemClick(View view, int position, IDrawerItem drawerItem)
        {
            switch ((int) drawerItem.getIdentifier())
            {
                case 1:
                    break;
                case 2:
                    gotopage(1);
                    break;
                case 3:
                    gotopage(10);
                    break;
                case 4:
                    gotopage(12);
                    break;
                case 5:
                    gotopage(40);
                    break;
                case 6:
                    gotopage(88);
                    break;
                case 7:
                    gotopage(112);
                    break;
                case 8:
                    gotopage(114);
                    break;
                case 9:
                    gotopage(128);
                    break;
                case 10:
                    gotopage(150);
                    break;
                case 11:
                    gotopage(164);
                    break;
                case 12:
                    gotopage(180);
                    break;
                case 13:
                    gotopage(198);
                    break;
                case 14:
                    gotopage(212);
                    break;
                case 15:
                    gotopage(220);
                    break;
                case 16:
                    gotopage(224);
                    break;
                case 17:
                    gotopage(256);
                    break;
            }
            return true;
        }
    };

    private void open_poster()
    {
        Intent i = new Intent(this, MainActivity.class);
        startActivity(i);
    }
}

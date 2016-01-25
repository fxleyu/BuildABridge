package com.zte.buildabridge;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.zte.buildabridge.database.DbUtils;
import com.zte.buildabridge.object.Record;
import com.zte.buildabridge.object.utils.LCD;
import com.zte.buildabridge.view.GameMainView;

public class MainActivity extends Activity {

    private DbUtils dbUtils;
    private GameMainView game;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        dbUtils = new DbUtils(this);
        
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);

        Button button = new Button(this);
        registerForContextMenu(button);
        setContentView(button);

        DisplayMetrics metric = new DisplayMetrics();
        getWindowManager().getDefaultDisplay().getMetrics(metric);
        LCD lcd = new LCD(metric.widthPixels, metric.heightPixels); // ��Ļ��ȣ����أ�
                                                                    // ��Ļ�߶ȣ����أ�
        setContentView(R.layout.main);
        LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        game = new GameMainView(this, lcd, dbUtils);
        layout.addView(game);
        game.setOnTouchListener(game.getListener());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.contextmenu, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
        case R.id.menu_rand_rank: {
            rankDialog();
            break;
        }
        case R.id.readme: {
            Toast.makeText(this,
                    "���ǹ���http://www.bxjkg.com/��AndroidӦ�ð汾�����漰��Ȩ���뼰ʱ��֪��",
                    Toast.LENGTH_LONG).show();
            break;
        }
        default:
            break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void rankDialog() {
        View rank = getRankView();
        
        Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("���а�");// ���öԻ������
        builder.setCancelable(false);
        builder.setView(rank);

        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        game.invalidate();
                    }
                });
        builder.show();
    }

    private View getRankView() {
        ListView result = new ListView(this);
        
        List<Map<String, String>> rankInfo = getRank();
        String[] from = {"����", "����", "�ɼ�", "ʱ��"};
        int[] to = {R.id.rank_id, R.id.rank_name, R.id.rank_score, R.id.rank_time};
        SimpleAdapter adapter = new SimpleAdapter(this, rankInfo, R.layout.item, from, to);
        
        result.setAdapter(adapter);
        return result;
    }

    private List<Map<String, String>> getRank() {
        List<Map<String, String>> result = new ArrayList<Map<String, String>>(10);
        
        List<Record> list = dbUtils.top10Record();
        Map<String, String> tempMap = new HashMap<String, String>();
        tempMap.put("����", "����");
        tempMap.put("����", "����");
        tempMap.put("�ɼ�", "�ɼ�");
        tempMap.put("ʱ��", "ʱ��");
        result.add(tempMap);
        int index = 1;
        for(Record record: list){
            Map<String, String> map = new HashMap<String, String>();
            map.put("����", index+++"");
            map.put("����", record.getName());
            map.put("�ɼ�", record.getScore()+"");
            map.put("ʱ��", record.getDate());
            result.add(map);
        }
        
        return result;
    }

    @Override
    protected void onDestroy() {
        dbUtils.close();
        super.onDestroy();
    }
}

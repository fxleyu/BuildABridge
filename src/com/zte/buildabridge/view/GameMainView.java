package com.zte.buildabridge.view;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Canvas;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

import com.zte.buildabridge.R;
import com.zte.buildabridge.database.DbUtils;
import com.zte.buildabridge.object.Population;
import com.zte.buildabridge.object.Record;
import com.zte.buildabridge.object.State;
import com.zte.buildabridge.object.TextShow;
import com.zte.buildabridge.object.utils.LCD;
import com.zte.buildabridge.view.handler.GameHandler;

public class GameMainView extends View {

    private Population population;
    private TextShow textShow;
    private Context context;

    private OnTouchListener listener;
    private GameHandler handler;
    private Activity activity;
    private DbUtils dbUtils;

    public GameMainView(Context context, LCD lcd, DbUtils dbUtils) {
        super(context);
        setBackgroundColor(0xff0099cc);

        this.context = context;
        handler = new GameHandler(this);
        population = new Population(context, lcd, handler);
        textShow = new TextShow();

        activity = (Activity) context;
        handler.setPopulation(population);
        listener = new OnTouchListenerImpl();
        this.dbUtils = dbUtils;
    }

    @Override
    public void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        population.draw(canvas);
        textShow.draw(canvas);
    }

    public void gameOver() {
        if (dbUtils.isTop1(State.getScore())) {
            gameSuccess();
        } else {
            gameFail();
        }
    }

    private void gameFail() {
        Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("游戏结束");// 设置对话框标题
        builder.setMessage(resultMessage(dbUtils.top1Record()));
        builder.setCancelable(false);

        builder.setPositiveButton("继续游戏",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        population.init();
                        invalidate();
                    }
                });
        builder.setNegativeButton("退出游戏",
                new DialogInterface.OnClickListener() {// 添加返回按钮
                    @Override
                    public void onClick(DialogInterface dialog, int which) {// 响应事件
                        activity.finish();
                    }
                });
        builder.show();

    }

    private void gameSuccess() {
        final View view = LayoutInflater.from(context).inflate(R.layout.input,
                null);
        Builder builder = new AlertDialog.Builder(activity);
        builder.setTitle("新纪录");// 设置对话框标题
        builder.setMessage("恭喜您创造了新记录！！");
        builder.setCancelable(false);
        builder.setView(view);

        builder.setPositiveButton("OK",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        TextView textView = (TextView) view
                                .findViewById(R.id.name);
                        updateScore(new Record(textView.getText().toString(),
                                State.getScore()));
                        population.init();
                        invalidate();
                    }
                });
        builder.show();
    }

    private void updateScore(Record record) {
        dbUtils.insertRecord(record);
    }

    private String resultMessage(Record record) {
        StringBuilder string = new StringBuilder();
        string.append("恭喜！！你过了" + State.getScore() + "！！ \n\n");
        string.append("历史最好成绩：" + record.getScore() + "\n");
        string.append("创造历史的人 ：" + record.getName() + "\n");
        string.append("创造历史时间：" + record.getDate() + "\n");
        string.append("是否继续？");
        return string.toString();
    }

    public OnTouchListener getListener() {
        return listener;
    }

    private class OnTouchListenerImpl implements OnTouchListener {
        @Override
        public boolean onTouch(View view, MotionEvent event) {
            if(population.isLock()) {
                return false;
            }
            switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                population.hasPankChange(true);
                population.plankChange();
                break;
            case MotionEvent.ACTION_MOVE: { // 不同系统其触发时间间隔不同，强烈不建议使用
                break;
            }
            case MotionEvent.ACTION_UP: {
                population.hasPankChange(false);
                population.startToBuild();
                break;
            }
            default:
                break;
            }
            return true;
        }
    }
}
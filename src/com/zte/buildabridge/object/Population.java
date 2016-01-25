package com.zte.buildabridge.object;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Handler; 
import com.zte.buildabridge.object.runnable.PersonAndPlankDown;
import com.zte.buildabridge.object.runnable.PlankChange;
import com.zte.buildabridge.object.runnable.StartToBuild;
import com.zte.buildabridge.object.runnable.StartToRun;
import com.zte.buildabridge.object.runnable.TranslationalMotion;
import com.zte.buildabridge.object.utils.GameLayout;
import com.zte.buildabridge.object.utils.IDrawBitmap;
import com.zte.buildabridge.object.utils.LCD;
import com.zte.buildabridge.object.utils.Location;
import com.zte.buildabridge.object.utils.Size;
import com.zte.buildabridge.setting.Configure;

public class Population {

    private Context context;
    private boolean isLock;
    private boolean hasChangePlank;

    private Person person;
    private Plank plank;
    private Platform aPlatform;
    private Platform bPlatform;
    private Platform cPlatform;

    private GameLayout layout;
    private Handler handler;
    private StartToBuild startToBuild;
    private StartToRun startToRun;
    private PersonAndPlankDown down;
    private PlankChange plankChange;
    private TranslationalMotion translationalMotion;

    private int safeMax;
    private int safeMin;

    // 画面中可动的物体的集合
    public Population(Context context, LCD lcd, Handler handler) {
        this.context = context;
        layout = new GameLayout(lcd);
        this.handler = handler;
        init();
    }

    public void init() {
        isLock = false;
        layout.init();
        State.init();
        Location location = new Location(0, Configure.BASE_POINT_Y);
        Size size = new Size(Configure.BASE_POINT_X, Configure.LCD_HEIGHT
                - Configure.BASE_POINT_Y);
        aPlatform = new Platform(context, location.copy(), size.copy());

        location.setWidth(Configure.BASE_POINT_X);

        plank = new Plank(context, Configure.PLANK_MAX, location.copy());
        person = new Person(context, location.copy(), new Size(
                Configure.PERSON_WIDTH, Configure.PERSON_HEIGHT));

        location.setWidth(location.getWidth() + layout.getInterval());
        size.setWidth(layout.getPlatformWidth());
        bPlatform = new Platform(context, location.copy(), size.copy());

        // 更新安全距离
        updateSafeDistance();

        layout.random();
        location.setWidth(Configure.LCD_WIDTH);
        size.setWidth(layout.getPlatformWidth());
        cPlatform = new Platform(context, location.copy(), size.copy());

        startToBuild = new StartToBuild(handler, this);
        startToRun = new StartToRun(handler, this);
        down = new PersonAndPlankDown(handler, this);
        translationalMotion = new TranslationalMotion(handler, this);
        plankChange = new PlankChange(handler, this);
    }

    private void updateSafeDistance() {
        safeMin = (int) bPlatform.getLocation().getWidth(); // 两个像素误差
        safeMax = (int) bPlatform.getBitmap().getWidth() + safeMin; // 两个像素误差
    }

    public void draw(Canvas canvas) {
        drawBitmap(canvas, aPlatform);
        drawBitmap(canvas, bPlatform);
        drawBitmap(canvas, cPlatform);
        drawBitmap(canvas, plank);

        if (person.isRunning()) {
            drawBitmap(canvas, person.step(),
                    person.getLocation());
        } else {
            drawBitmap(canvas, person.stand(), person.getLocation());
        }
    }

    private void drawBitmap(Canvas canvas, Bitmap bitmap, Location location) {
        canvas.drawBitmap(bitmap, location.getWidth(), location.getHeight(),
                null);
    }

    private void drawBitmap(Canvas canvas, IDrawBitmap drawBitmap) {
        drawBitmap(canvas, drawBitmap.getBitmap(), drawBitmap.getLocation());
    }

    public void nextLevel() {
        isLock = false;

        aPlatform = bPlatform;
        bPlatform = cPlatform;

        updateSafeDistance();

        Location location = new Location(Configure.BASE_POINT_X,
                Configure.BASE_POINT_Y);
        plank = new Plank(context, Configure.PLANK_MAX, location.copy());
        person.setLocation(location);

        layout.random();
        location.setWidth(Configure.LCD_WIDTH);
        Size size = new Size(layout.getPlatformWidth(), Configure.LCD_HEIGHT
                - Configure.BASE_POINT_Y);
        cPlatform = new Platform(context, location.copy(), size);

        startToBuild.init();

        handler.sendEmptyMessage(MessageInfo.INVALIDATE);
    }

    public void plankChange() {
        new Thread(plankChange).start();
    }

    public void startToBuild() {
        new Thread(startToBuild).start();
    }

    public void startToRun() {
        new Thread(startToRun).start();
    }

    public void down() {
        new Thread(down).start();
    }

    public void setRunning(boolean bool) {
        person.setRunning(bool);
    }

    public int getPlankLength() {
        return plank.getBitmap().getWidth();
    }

    public boolean personSafe() {
        int distance = plankMixDistance();
        return safeMin < distance && safeMax > distance;
    }

    private int plankMixDistance() {
        int result = Configure.BASE_POINT_X + plank.getBitmap().getWidth();
        return result;
    }

    public int personRunDistance() {
        int result = safeMax - Configure.BASE_POINT_X;
        return result;
    }

    public void down(int degree) {
        plank.rotate(degree);
        person.downLocation(degree);
    }

    public void translationalMotion() {
        new Thread(translationalMotion).start();
    }

    public int getTranslationDistanceA() {
        return safeMin - Configure.BASE_POINT_X
                + bPlatform.getBitmap().getWidth();
    }

    public float getTranslationDistanceB() {
        return Configure.LCD_WIDTH - Configure.BASE_POINT_X
                - layout.getInterval();
    }

    public void translation(int i, float multiple) {
        aPlatform.getLocation().decreaseWidth(1);
        bPlatform.getLocation().decreaseWidth(1);
        cPlatform.getLocation().decreaseWidth(multiple);
        plank.getLocation().decreaseWidth(1);
        person.getLocation().decreaseWidth(1);
    }

    public void lock() {
        isLock = true;
    }

    public void rotate(int degree) {
        plank.rotate(degree);
    }

    public boolean isLock() {
        return isLock;
    }

    public void hasPankChange(boolean bool) {
        hasChangePlank = bool;
    }

    public boolean hasChangePlank() {
        return hasChangePlank;
    }

    public void changePlank() {
        plank.change();
    }

    public void setPersonStep(int step) {
        person.setStep(step);
    }
}
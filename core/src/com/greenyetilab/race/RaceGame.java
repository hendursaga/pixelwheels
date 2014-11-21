package com.greenyetilab.race;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.utils.ScreenUtils;
import com.greenyetilab.utils.log.NLog;

/**
 * Created by aurelien on 21/11/14.
 */
public class RaceGame extends Game {
    private Assets mAssets;

    public Assets getAssets() {
        return mAssets;
    }

    @Override
    public void create() {
        mAssets = new Assets();
        showMainMenu();
    }

    public void showMainMenu() {
        Screen screen = new MainMenuScreen(this);
        setScreenAndDispose(screen);
    }

    public void start(MapInfo mapInfo) {
        NLog.i("mapName=%s", mapInfo.getTitle());
        Screen screen = new RaceGameScreen(this, mapInfo);
        setScreenAndDispose(screen);
    }

    private void setScreenAndDispose(Screen screen) {
        Screen oldScreen = getScreen();
        if (oldScreen != null) {
            oldScreen.dispose();
        }
        setScreen(screen);
    }

    public void showGameOverOverlay() {
        showOverlay("Game Over");
    }

    public void showFinishedOverlay(MapInfo mapInfo, float time) {
        float best = mapInfo.getBestTime();
        String text = "Finished in " + StringUtils.formatRaceTime(time);
        if (best == 0 || time < best) {
            text += "\n\nNew record!";
            if (best != 0) {
                text += "\n\nOld record was " + StringUtils.formatRaceTime(best);
            }
            mapInfo.setBestTime(time);
        } else if (best != 0) {
            text += "\n\nRecord is " + StringUtils.formatRaceTime(best);
        }
        showOverlay(text);
    }

    private void showOverlay(String text) {
        TextureRegion bg = ScreenUtils.getFrameBufferTexture();
        setScreenAndDispose(new OverlayScreen(this, bg, text));
    }
}
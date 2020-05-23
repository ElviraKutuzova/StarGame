package ru.geekbrains.utils;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class Regions {

    /**
     * Разбиваем TextereRegion на фреймы
     * @param region регион
     *  @param rows количество строк
     * @param cols количество столбцов
     * @param frames количество фреймов
     * @param массив регионов
     */
    public static TextureRegion[] split(TextureRegion region, int rows, int cols, int frames){
        if(region == null) throw new RuntimeException("Split null region");
        TextureRegion[] regions = new TextureRegion[frames];
        int titleWidth = region.getRegionWidth() / cols;
        int titleHeight = region.getRegionHeight() / rows;

        int frame = 0;
        for(int i=0; i < rows; i++){
            for(int j = 0; j < cols; j++){
                regions[frame] = new TextureRegion(region, titleWidth*j, titleHeight*i, titleWidth,titleHeight);
                if(frame == frames - 1) return regions;
                frame++;
            }
        }
        return regions;
    }

}

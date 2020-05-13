package ru.geekbrains.math;

import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
//Утилита для работы с матрицой
public class MatrixUtils {

    private MatrixUtils(){

    }

    /**
    *Расчет матрицы перехода 4x4
     * @param mat итоговая матрица перехода
     * @param src исходный квадрат
     * @param dst итоговый квадрат
     */
    public static void calcTransitionMatrix(Matrix4 mat, Rect src, Rect dst) {
        float scaleX = dst.getWidth() / src.getWidth();
        float scaleY = dst.getHeight() / src.getHeight();
        mat.idt().translate(dst.pos.x, dst.pos.y, 0f).scale(scaleX, scaleY,1f).translate(-src.pos.x, -src.pos.y, 0f);
    }

    /**
     * Расчет матрицы перехода 3х3
     * @param mat итоговая матрица перехода
     * @param src исходный квадрат
     * @param dst итоговый квадрат
     */
    public static void calcTransitionMatrix(Matrix3 mat, Rect src, Rect dst){
        float scaleX = dst.getWidth() / src.getWidth();
        float scaleY = dst.getHeight() / src.getHeight();
        mat.idt().translate(dst.pos.x, dst.pos.y).scale(scaleX,scaleY).translate(-src.pos.x, -src.pos.y);

    }
}

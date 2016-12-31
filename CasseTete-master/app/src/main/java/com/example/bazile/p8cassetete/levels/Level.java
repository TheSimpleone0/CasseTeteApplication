package com.example.bazile.p8cassetete.levels;

import android.util.Log;

import java.util.ArrayList;

/**
.
 */
public class Level {



    // taille de la carte
    public static final int carteWidth = 5;
    public static final int carteHeight = 5;
    public static final int carteTileSize = 105;
    /**
     * Tableau
     **/
    public int[][] Tab; /* Bloc principale */
    public ArrayList<Bloc> list_bloc;

    int finalPostion_x, finalPosition_y; // global


    public Level() {
        Tab = new int[carteHeight][carteWidth];
        init_Tab();
        list_bloc = new ArrayList<>();
    }


    void init_Tab() {
            for (int i = 0; i < carteHeight; i++) {
                for (int j = 0; j < carteWidth; j++) {
                    Tab[i][j] = 1;
                }

            }
        }


    public void add_Bloc(int posX, int posY, int tailleX, int tailleY, int[][] forme, int id) {
        Bloc b = new Bloc(posX, posY, tailleX, tailleY, forme, id);
        this.list_bloc.add(b);
    }

    public void affiche_Tab() {
        for (int i = 0; i < carteHeight; i++) {
            Log.d("Tab : ", " " + "Ligne :" + i);
            for (int j = 0; j < carteWidth; j++) {
                Log.d("Tab : ", " " + Tab[i][j]);
            }

        }
    }

}
